package com.example.demo.common.exception;

import java.util.Map;

public record ErrorResponse(String code, String message, Map<String, String> errors) {

    public ErrorResponse(String code, String message) {
        this(code, message, Map.of());
    }
}
