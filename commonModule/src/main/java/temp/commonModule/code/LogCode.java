package temp.commonModule.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogCode {

    // Redis
    REDIS_DESERIALIZE_FAILED("[Redis] 메시지 역직렬화 실패");

    private final String message;
}
