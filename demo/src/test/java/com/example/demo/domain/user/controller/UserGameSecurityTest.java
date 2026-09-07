package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.service.UserGameService;
import com.example.demo.global.jwt.JwtProvider;
import com.example.demo.global.jwt.SecurityConfig;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserGameController.class)
@Import(SecurityConfig.class)
class UserGameSecurityTest {
    @Autowired private MockMvc mvc;
    @MockitoBean private UserGameService service;
    @MockitoBean private JwtProvider jwtProvider;

    @ParameterizedTest
    @CsvSource({"GET,/api/users/me/games", "POST,/api/users/me/games",
            "PATCH,/api/users/me/games/10/main", "DELETE,/api/users/me/games/10"})
    void unauthenticatedRequestsReturn401(String method, String path) throws Exception {
        mvc.perform(request(HttpMethod.valueOf(method), path)
                        .contentType(MediaType.APPLICATION_JSON).content("{\"gameId\":10}"))
                .andExpect(status().isUnauthorized());
        verifyNoInteractions(service);
    }
}
