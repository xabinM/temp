package temp.commonModule.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
    JWT_KEY_LOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "JWT 키 로딩에 실패했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    // Redis
    REDIS_SERIALIZE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Redis 메시지 직렬화에 실패했습니다."),

    // Chat Room
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 채팅방입니다."),
    ROOM_ALREADY_JOINED(HttpStatus.CONFLICT, "이미 참여 중인 채팅방입니다."),
    ROOM_NOT_JOINED(HttpStatus.BAD_REQUEST, "채팅방 멤버가 아닙니다.");

    private final HttpStatus status;
    private final String message;
}
