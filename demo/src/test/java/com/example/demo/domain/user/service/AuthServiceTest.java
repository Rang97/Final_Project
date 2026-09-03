package com.example.demo.domain.user.service;

import com.example.demo.common.exception.DuplicateLoginIdException;
import com.example.demo.domain.user.dto.SignupRequest;
import com.example.demo.domain.user.dto.SignupResponse;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserMapper;
import com.example.demo.domain.saju.entity.SajuInput;
import com.example.demo.domain.saju.repository.SajuInputMapper;
import com.example.demo.global.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    private UserMapper userMapper;
    private SajuInputMapper sajuInputMapper;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        sajuInputMapper = mock(SajuInputMapper.class);
        authService = new AuthService(
                userMapper,
                new BCryptPasswordEncoder(),
                mock(JwtProvider.class),
                sajuInputMapper
        );
    }

    @Test
    void 회원가입에_성공하면_암호화된_비밀번호로_사용자를_저장한다() throws Exception {
        when(userMapper.existsByLoginId("tester")).thenReturn(false);
        when(userMapper.insert(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            Field id = User.class.getDeclaredField("userId");
            id.setAccessible(true);
            id.set(user, 1L);
            return 1;
        });

        SignupResponse response = authService.signup(
                new SignupRequest(
                        " tester ",
                        "password123!",
                        " 테스터 ",
                        LocalDate.of(2000, 1, 1),
                        BirthTimeBranch.CHUK
                )
        );

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).insert(captor.capture());
        User savedUser = captor.getValue();

        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.loginId()).isEqualTo("tester");
        assertThat(response.nickname()).isEqualTo("테스터");
        assertThat(response.birthDate()).isEqualTo(LocalDate.of(2000, 1, 1));
        assertThat(response.birthTimeBranch()).isEqualTo("CHUK");
        assertThat(savedUser.getPassword()).isNotEqualTo("password123!");
        assertThat(new BCryptPasswordEncoder().matches("password123!", savedUser.getPassword())).isTrue();
        assertThat(savedUser.getRole()).isEqualTo("USER");
        ArgumentCaptor<SajuInput> sajuInputCaptor = ArgumentCaptor.forClass(SajuInput.class);
        verify(sajuInputMapper).insert(sajuInputCaptor.capture());
        assertThat(sajuInputCaptor.getValue().getUserId()).isEqualTo(1L);
        assertThat(sajuInputCaptor.getValue().getBirthDate()).isEqualTo(LocalDate.of(2000, 1, 1));
        assertThat(sajuInputCaptor.getValue().getBirthTimeType()).isEqualTo("TIME_BRANCH");
        assertThat(sajuInputCaptor.getValue().getBirthTimeBranch()).isEqualTo(BirthTimeBranch.CHUK);
    }

    @Test
    void 아이디가_중복이면_회원가입에_실패한다() {
        when(userMapper.existsByLoginId("tester")).thenReturn(true);

        assertThatThrownBy(() -> authService.signup(
                new SignupRequest("tester", "password123!", "테스터", null, null)
        )).isInstanceOf(DuplicateLoginIdException.class);

        verify(userMapper, never()).insert(any(User.class));
        verify(sajuInputMapper, never()).insert(any(SajuInput.class));
    }

    @Test
    void 생년월일시가_없으면_사주_입력정보를_저장하지_않는다() throws Exception {
        when(userMapper.existsByLoginId("tester")).thenReturn(false);
        when(userMapper.insert(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            Field id = User.class.getDeclaredField("userId");
            id.setAccessible(true);
            id.set(user, 1L);
            return 1;
        });

        SignupResponse response = authService.signup(
                new SignupRequest("tester", "password123!", "테스터", null, null)
        );

        verify(sajuInputMapper, never()).insert(any(SajuInput.class));
        assertThat(response.birthDate()).isNull();
        assertThat(response.birthTimeBranch()).isNull();
    }
}
