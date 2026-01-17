package temp.commonModule.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // Auth
    REGISTER_SUCCESS(HttpStatus.OK, "회원가입이 성공적으로 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인이 성공적으로 완료되었습니다.");

    private final HttpStatus status;
    private final String message;
}
