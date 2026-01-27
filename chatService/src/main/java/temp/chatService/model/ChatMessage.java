package temp.chatService.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessage {

    private Long roomId;
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

}