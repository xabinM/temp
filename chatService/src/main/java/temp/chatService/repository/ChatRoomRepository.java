package temp.chatService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import temp.chatService.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
