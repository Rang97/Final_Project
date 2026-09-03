package com.example.demo.auth.service;

import com.example.demo.auth.dto.SignupRequest;
import com.example.demo.auth.dto.SignupResponse;
import com.example.demo.common.exception.DuplicateLoginIdException;
import com.example.demo.user.domain.User;
import com.example.demo.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
                User.Role.USER
        );

        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException exception) {
            // 사전 중복 확인 이후 동시에 가입하는 경우에도 동일한 응답을 보장한다.
            throw new DuplicateLoginIdException();
        }

        return new SignupResponse(
                user.getUserId(),
                user.getLoginId(),
                user.getNickname(),
                user.getRole().name()
        );
    }
}
