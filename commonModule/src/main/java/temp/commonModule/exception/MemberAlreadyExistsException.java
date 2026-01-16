package temp.commonModule.exception;

import temp.commonModule.code.ErrorCode;
import lombok.Getter;

@Getter
public class MemberAlreadyExistsException extends RuntimeException {
    private final ErrorCode errorCode;

    public MemberAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
