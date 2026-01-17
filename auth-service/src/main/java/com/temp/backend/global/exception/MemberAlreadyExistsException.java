package com.temp.backend.global.exception;

import temp.commonModule.code.ErrorCode;
import temp.commonModule.exception.BusinessException;

public class MemberAlreadyExistsException extends BusinessException {

    public MemberAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
