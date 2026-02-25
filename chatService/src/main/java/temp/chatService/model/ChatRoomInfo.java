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
public class ChatRoomInfo {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public static ChatRoomInfo from(ChatRoom room) {
        return new ChatRoomInfo(room.getId(), room.getName(), room.getCreatedAt());
    }
}
