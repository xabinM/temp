package temp.chatService.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomListResponse {

    private String message;
    private List<ChatRoomInfo> rooms;

    public static ChatRoomListResponse of(List<ChatRoomInfo> rooms, String message) {
        return new ChatRoomListResponse(message, rooms);
    }
}
