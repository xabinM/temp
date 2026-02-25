package temp.chatService.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageListResponse {

    private String message;
    private List<ChatMessageResponse> messages;

    public static ChatMessageListResponse of(List<ChatMessageResponse> messages, String message) {
        return new ChatMessageListResponse(message, messages);
    }
}
