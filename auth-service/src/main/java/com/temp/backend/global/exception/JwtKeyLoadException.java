package com.temp.backend.global.exception;

import temp.commonModule.code.ErrorCode;
import temp.commonModule.exception.BusinessException;

public class JwtKeyLoadException extends BusinessException {

    public JwtKeyLoadException(ErrorCode errorCode) {
        super(errorCode);
    }

    public JwtKeyLoadException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
