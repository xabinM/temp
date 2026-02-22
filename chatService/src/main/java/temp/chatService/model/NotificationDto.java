package temp.chatService.model;

import lombok.Getter;

@Getter
public class NotificationDto {

    private final Long roomId;
    private final String sender;
    private final String content;

    public NotificationDto(Long roomId, String sender, String content) {
        this.roomId = roomId;
        this.sender = sender;
        this.content = content;
    }
}
