package com.example.demo.domain.user.controller;

import com.example.demo.common.exception.GlobalExceptionHandler;
import com.example.demo.domain.user.dto.UserGameCreateRequest;
import com.example.demo.domain.user.service.UserGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserGameControllerTest {

    private UserGameService service;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        service = mock(UserGameService.class);
        mvc = MockMvcBuilders.standaloneSetup(new UserGameController(service))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void validRequestReturnsCreated() throws Exception {
        mvc.perform(post("/api/users/me/games")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"gameId\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("success").value(true));
        verify(service).register(any(UserGameCreateRequest.class));
    }

    @Test
    void mainGameChangeReturnsSuccess() throws Exception {
        mvc.perform(patch("/api/users/me/games/10/main"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true));
        verify(service).changeMainGame(10L);
    }

    @Test
    void deleteReturnsSuccess() throws Exception {
        mvc.perform(delete("/api/users/me/games/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true));
        verify(service).delete(10L);
    }

    @Test
    void unregisteredMainGameReturnsNotFound() throws Exception {
        doThrow(new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND))
                .when(service).changeMainGame(10L);
        mvc.perform(patch("/api/users/me/games/10/main"))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(strings = {"{}", "{\"gameId\":null}", "{\"gameId\":0}", "{\"gameId\":-1}"})
    void invalidGameIdIsRejectedBeforeService(String body) throws Exception {
        mvc.perform(post("/api/users/me/games")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("INVALID_INPUT"));
        verifyNoInteractions(service);
    }
}
