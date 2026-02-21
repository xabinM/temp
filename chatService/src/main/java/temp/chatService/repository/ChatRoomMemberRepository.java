package temp.chatService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import temp.chatService.entity.ChatRoomMember;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    boolean existsByRoomIdAndUserId(Long roomId, String userId);

    void deleteByRoomIdAndUserId(Long roomId, String userId);
}
