package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.UserGameCreateRequest;
import com.example.demo.domain.user.repository.UserGameMapper;
import com.example.demo.global.util.CurrentUserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserGameServiceTest {

    private UserGameMapper mapper;
    private CurrentUserProvider currentUserProvider;
    private UserGameService service;
    private UserGameCreateRequest request;

    @BeforeEach
    void setUp() throws Exception {
        mapper = mock(UserGameMapper.class);
        currentUserProvider = mock(CurrentUserProvider.class);
        service = new UserGameService(currentUserProvider, mapper);
        request = new ObjectMapper().readValue("{\"gameId\":10}", UserGameCreateRequest.class);
        when(currentUserProvider.getCurrentUserId()).thenReturn(7L);
    }

    @Test
    void fifthGameIsSavedForAuthenticatedUser() {
        when(mapper.existsGame(10L)).thenReturn(true);
        when(mapper.countByUserId(7L)).thenReturn(4);

        service.register(request);

        verify(mapper).insert(7L, 10L);
    }

    @Test
    void missingGameIsNotSaved() {
        assertFailure(HttpStatus.NOT_FOUND);
        verify(mapper, never()).countByUserId(anyLong());
    }

    @Test
    void duplicateGameIsNotSaved() {
        when(mapper.existsGame(10L)).thenReturn(true);
        when(mapper.existsByUserIdAndGameId(7L, 10L)).thenReturn(true);

        assertFailure(HttpStatus.CONFLICT);
        verify(mapper, never()).countByUserId(anyLong());
    }

    @Test
    void sixthGameIsNotSaved() {
        when(mapper.existsGame(10L)).thenReturn(true);
        when(mapper.countByUserId(7L)).thenReturn(5);

        assertFailure(HttpStatus.CONFLICT);
    }

    @Test
    void databaseDuplicateIsReportedAsConflict() {
        when(mapper.existsGame(10L)).thenReturn(true);
        when(mapper.insert(7L, 10L)).thenThrow(new DuplicateKeyException("duplicate"));

        assertThatThrownBy(() -> service.register(request))
                .isInstanceOfSatisfying(ResponseStatusException.class,
                        exception -> assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.CONFLICT));
    }

    @Test
    void unauthenticatedRequestDoesNotAccessDatabase() {
        when(currentUserProvider.getCurrentUserId()).thenThrow(new IllegalArgumentException("로그인이 필요합니다."));

        assertThatThrownBy(() -> service.register(request)).isInstanceOf(IllegalArgumentException.class);
        verifyNoInteractions(mapper);
    }

    @Test
    void mainGameChangeClearsPreviousSelectionBeforeSettingNewOne() {
        when(mapper.existsByUserIdAndGameId(7L, 10L)).thenReturn(true);
        when(mapper.setMain(7L, 10L)).thenReturn(1);

        service.changeMainGame(10L);

        var order = inOrder(mapper);
        order.verify(mapper).existsByUserIdAndGameId(7L, 10L);
        order.verify(mapper).clearMainByUserId(7L);
        order.verify(mapper).setMain(7L, 10L);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void unregisteredGameCannotClearExistingMainGame() {
        assertThatThrownBy(() -> service.changeMainGame(10L))
                .isInstanceOfSatisfying(ResponseStatusException.class,
                        exception -> assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
        verify(mapper, never()).clearMainByUserId(anyLong());
        verify(mapper, never()).setMain(anyLong(), anyLong());
    }

    @Test
    void failedMainAssignmentThrowsToTriggerTransactionRollback() {
        when(mapper.existsByUserIdAndGameId(7L, 10L)).thenReturn(true);
        when(mapper.setMain(7L, 10L)).thenReturn(0);

        assertThatThrownBy(() -> service.changeMainGame(10L))
                .isInstanceOfSatisfying(ResponseStatusException.class,
                        exception -> assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
        verify(mapper).clearMainByUserId(7L);
    }

    @Test
    void deletingGameDoesNotAssignAnotherMainGame() {
        when(mapper.deleteByUserIdAndGameId(7L, 10L)).thenReturn(1);

        service.delete(10L);

        verify(mapper).deleteByUserIdAndGameId(7L, 10L);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void deletingUnregisteredGameReturnsNotFound() {
        assertThatThrownBy(() -> service.delete(10L))
                .isInstanceOfSatisfying(ResponseStatusException.class,
                        exception -> assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
    }

    @Test
    void unauthenticatedMainChangeAndDeleteDoNotAccessDatabase() {
        when(currentUserProvider.getCurrentUserId()).thenThrow(new IllegalArgumentException("로그인이 필요합니다."));

        assertThatThrownBy(() -> service.changeMainGame(10L)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.delete(10L)).isInstanceOf(IllegalArgumentException.class);
        verifyNoInteractions(mapper);
    }

    private void assertFailure(HttpStatus status) {
        assertThatThrownBy(() -> service.register(request))
                .isInstanceOfSatisfying(ResponseStatusException.class,
                        exception -> assertThat(exception.getStatusCode()).isEqualTo(status));
        verify(mapper, never()).insert(anyLong(), anyLong());
    }
}
