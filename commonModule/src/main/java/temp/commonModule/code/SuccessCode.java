package temp.commonModule.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // Auth
    REGISTER_SUCCESS(HttpStatus.OK, "회원가입이 성공적으로 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인이 성공적으로 완료되었습니다."),

    // Chat
    ROOM_CREATE_SUCCESS(HttpStatus.CREATED, "채팅방이 성공적으로 생성되었습니다."),
    ROOM_JOIN_SUCCESS(HttpStatus.OK, "채팅방에 참여했습니다."),
    ROOM_LEAVE_SUCCESS(HttpStatus.OK, "채팅방에서 나갔습니다."),
    ROOMS_FETCH_SUCCESS(HttpStatus.OK, "채팅방 목록 조회 성공"),
    MESSAGES_FETCH_SUCCESS(HttpStatus.OK, "메시지 조회 성공");

    private final HttpStatus status;
    private final String message;
}
