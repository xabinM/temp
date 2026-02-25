package temp.chatService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import temp.chatService.entity.ChatRoomMember;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    boolean existsByRoomIdAndUserId(Long roomId, String userId);

    void deleteByRoomIdAndUserId(Long roomId, String userId);

    Optional<ChatRoomMember> findByRoomIdAndUserId(Long roomId, String userId);

    List<ChatRoomMember> findByRoomId(Long roomId);

    List<ChatRoomMember> findByUserId(String userId);
}
