package com.example.demo.domain.user.service;

import com.example.demo.common.exception.DuplicateLoginIdException;
import com.example.demo.domain.user.dto.SignupRequest;
import com.example.demo.domain.user.dto.SignupResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserMapper;
import com.example.demo.global.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    private UserMapper userMapper;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        authService = new AuthService(userMapper, new BCryptPasswordEncoder(), mock(JwtProvider.class));
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
                new SignupRequest(" tester ", "password123!", " 테스터 ")
        );

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).insert(captor.capture());
        User savedUser = captor.getValue();

        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.loginId()).isEqualTo("tester");
        assertThat(response.nickname()).isEqualTo("테스터");
        assertThat(savedUser.getPassword()).isNotEqualTo("password123!");
        assertThat(new BCryptPasswordEncoder().matches("password123!", savedUser.getPassword())).isTrue();
        assertThat(savedUser.getRole()).isEqualTo("USER");
    }

    @Test
    void 아이디가_중복이면_회원가입에_실패한다() {
        when(userMapper.existsByLoginId("tester")).thenReturn(true);

        assertThatThrownBy(() -> authService.signup(
                new SignupRequest("tester", "password123!", "테스터")
        )).isInstanceOf(DuplicateLoginIdException.class);

        verify(userMapper, never()).insert(any(User.class));
    }
}
