package temp.chatService.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private ChatRoom(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public static ChatRoom create(String name) {
        return new ChatRoom(name);
    }
}
