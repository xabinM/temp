package temp.commonModule.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member not found"),
    MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "Member already exists"),
    JWT_KEY_LOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load JWT key"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final HttpStatus status;
    private final String message;
}
