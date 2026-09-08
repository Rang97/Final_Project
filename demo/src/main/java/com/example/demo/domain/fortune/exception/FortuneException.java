package com.example.demo.domain.fortune.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

/** Only application-owned text and numeric upstream status may be exposed. */
public class FortuneException extends ResponseStatusException {
    private final String code;
    private final Map<String, String> details;

    public FortuneException(HttpStatus status, String code, String message, Map<String, String> details) {
        super(status, message);
        this.code = code;
        this.details = Map.copyOf(details);
    }
    public String code() { return code; }
    public Map<String, String> details() { return details; }

    public static FortuneException upstream(String provider, String stage, RestClientException error) {
        if (error instanceof RestClientResponseException response) {
            int status = response.getStatusCode().value();
            String hint = switch (status) {
                case 400, 422 -> "요청 형식과 API 지원 조건을 확인해 주세요.";
                case 401, 403 -> "API 키와 호출 권한을 확인해 주세요.";
                case 404 -> "API 주소와 모델명을 확인해 주세요.";
                case 429 -> "호출 한도·할당량을 확인해 주세요.";
                default -> "외부 서비스 상태를 확인해 주세요.";
            };
            return new FortuneException(HttpStatus.BAD_GATEWAY, provider + "_HTTP_ERROR",
                    provider + " 호출이 HTTP " + status + "로 실패했습니다. " + hint,
                    Map.of("stage", stage, "upstreamStatus", Integer.toString(status)));
        }
        return new FortuneException(HttpStatus.BAD_GATEWAY,
                provider + (error instanceof ResourceAccessException ? "_CONNECTION_ERROR" : "_CLIENT_ERROR"),
                provider + " 연결·시간 초과 또는 응답 처리 오류입니다.",
                Map.of("stage", stage, "exceptionType", error.getClass().getSimpleName()));
    }

    public static FortuneException invalid(String code, String message, String stage) {
        return new FortuneException(HttpStatus.BAD_GATEWAY, code, message, Map.of("stage", stage));
    }
}

