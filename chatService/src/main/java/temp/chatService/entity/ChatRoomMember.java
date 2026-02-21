package temp.chatService.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_room_member",
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "user_id"}))
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @Column(nullable = false)
    private boolean isOnline;

    private ChatRoomMember(Long roomId, String userId) {
        this.roomId = roomId;
        this.userId = userId;
        this.joinedAt = LocalDateTime.now();
        this.isOnline = false;
    }

    public static ChatRoomMember create(Long roomId, String userId) {
        return new ChatRoomMember(roomId, userId);
    }

    public void updateOnlineStatus(boolean isOnline) {
        this.isOnline = isOnline;
    }
}
