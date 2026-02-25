package temp.chatService.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import temp.chatService.entity.ChatRoom;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomResponse {

    private String message;
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public static ChatRoomResponse of(ChatRoom room, String message) {
        return new ChatRoomResponse(message, room.getId(), room.getName(), room.getCreatedAt());
    }
}
