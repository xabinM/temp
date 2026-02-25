package temp.commonModule.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessResponse {

    private String message;

    public static SuccessResponse of(String message) {
        return new SuccessResponse(message);
    }
}
