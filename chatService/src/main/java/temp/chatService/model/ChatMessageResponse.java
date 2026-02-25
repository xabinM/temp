package temp.chatService.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import temp.chatService.entity.ChatMessageEntity;
import temp.chatService.model.MessageType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageResponse {

    private Long id;
    private Long roomId;
    private String sender;
    private String content;
    private MessageType type;
    private LocalDateTime sentAt;

    public static ChatMessageResponse from(ChatMessageEntity entity) {
        return new ChatMessageResponse(
                entity.getId(),
                entity.getRoomId(),
                entity.getSender(),
                entity.getContent(),
                entity.getType(),
                entity.getSentAt()
        );
    }
}
