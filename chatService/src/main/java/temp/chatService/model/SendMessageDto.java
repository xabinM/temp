package temp.chatService.model;

import lombok.Data;

@Data
public class SendMessageDto {

    private Long roomId;
    private String content;
}
