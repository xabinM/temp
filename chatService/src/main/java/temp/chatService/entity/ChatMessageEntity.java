package temp.chatService.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import temp.chatService.model.MessageType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_message")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private String content;

    private String sender;

    @Column(nullable = false, updatable = false)
    private LocalDateTime sentAt;

    private ChatMessageEntity(Long roomId, MessageType type, String content, String sender, LocalDateTime sentAt) {
        this.roomId = roomId;
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.sentAt = sentAt;
    }

    public static ChatMessageEntity create(Long roomId, MessageType type, String content, String sender) {
        return new ChatMessageEntity(roomId, type, content, sender, LocalDateTime.now());
    }
}
