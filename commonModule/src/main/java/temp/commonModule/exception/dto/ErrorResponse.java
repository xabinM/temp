package temp.commonModule.exception.dto;

import temp.commonModule.code.ErrorCode;
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
