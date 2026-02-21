package temp.chatService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import temp.chatService.entity.ChatMessageEntity;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findByRoomIdOrderBySentAtAsc(Long roomId);
}
