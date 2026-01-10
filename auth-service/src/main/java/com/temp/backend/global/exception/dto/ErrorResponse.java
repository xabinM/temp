package com.temp.backend.global.exception.dto;

import com.temp.backend.global.code.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
    }
}
