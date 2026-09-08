package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.LoginRequest;
import com.example.demo.domain.user.dto.LoginResponse;
import com.example.demo.domain.user.dto.LoginUserResponse;
import com.example.demo.domain.user.dto.SignupRequest;
import com.example.demo.domain.user.dto.SignupResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserMapper;
import com.example.demo.common.exception.DuplicateLoginIdException;
import com.example.demo.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        String loginId = request.loginId().trim();
        String nickname = request.nickname().trim();

        if (userMapper.existsByLoginId(loginId)) {
            throw new DuplicateLoginIdException();
        }

        User user = new User(
                loginId,
                passwordEncoder.encode(request.password()),
                nickname,
                "USER"
        );

        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException exception) {
            throw new DuplicateLoginIdException();
        }

        return SignupResponse.from(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userMapper.findByLoginId(request.loginId())
                .orElseThrow(AuthService::invalidCredentials);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw invalidCredentials();
        }

        String accessToken = jwtProvider.createAccessToken(
                user.getUserId(),
                user.getLoginId(),
                user.getRole()
        );

        return LoginResponse.bearer(
                accessToken,
                jwtProvider.getExpirationSeconds(),
                LoginUserResponse.from(user)
        );
    }

    public LoginUserResponse getCurrentUser(Long userId) {
        User user = userMapper.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "로그인 사용자를 찾을 수 없습니다."
                ));

        return LoginUserResponse.from(user);
    }

    private static ResponseStatusException invalidCredentials() {
        return new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "아이디 또는 비밀번호가 올바르지 않습니다."
        );
    }
}
