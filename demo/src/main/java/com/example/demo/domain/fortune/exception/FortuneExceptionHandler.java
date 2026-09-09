package com.example.demo.domain.fortune.exception;

import com.example.demo.common.exception.ErrorResponse;
import com.example.demo.domain.fortune.controller.FortuneController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = FortuneController.class)
public class FortuneExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleStatus(ResponseStatusException exception) {
        String code = exception instanceof FortuneException fortune ? fortune.code() : "FORTUNE_REQUEST_ERROR";
        Map<String, String> details = exception instanceof FortuneException fortune
                ? fortune.details() : Map.of();
        // Do not log exception objects: upstream bodies or SQL parameters could contain sensitive data.
        log.warn("Fortune request failed: status={}, code={}, details={}",
                exception.getStatusCode().value(), code, details);
        return ResponseEntity.status(exception.getStatusCode()).body(new ErrorResponse(code,
                exception.getReason() == null ? "운세 요청 처리에 실패했습니다." : exception.getReason(), details));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception) {
        log.error("Fortune internal failure: exceptionType={}", exception.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(
                "FORTUNE_INTERNAL_ERROR", "운세 처리 중 내부 오류입니다. DB 마이그레이션과 서버 설정을 확인해 주세요.",
                Map.of("exceptionType", exception.getClass().getSimpleName())));
    }
}

