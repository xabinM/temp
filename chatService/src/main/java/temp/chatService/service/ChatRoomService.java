package temp.chatService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import temp.chatService.entity.ChatRoom;
import temp.chatService.entity.ChatRoomMember;
import temp.chatService.repository.ChatRoomMemberRepository;
import temp.chatService.repository.ChatRoomRepository;
import temp.commonModule.code.ErrorCode;
import temp.commonModule.exception.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    public ChatRoom createRoom(String name) {
        return chatRoomRepository.save(ChatRoom.create(name));
    }

    public List<ChatRoom> getRooms() {
        return chatRoomRepository.findAll();
    }

    @Transactional
    public void join(Long roomId, String userId) {
        if (!chatRoomRepository.existsById(roomId)) {
            throw new BusinessException(ErrorCode.ROOM_NOT_FOUND);
        }
        if (chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, userId)) {
            throw new BusinessException(ErrorCode.ROOM_ALREADY_JOINED);
        }
        chatRoomMemberRepository.save(ChatRoomMember.create(roomId, userId));
    }

    @Transactional
    public void leave(Long roomId, String userId) {
        if (!chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, userId)) {
            throw new BusinessException(ErrorCode.ROOM_NOT_JOINED);
        }
        chatRoomMemberRepository.deleteByRoomIdAndUserId(roomId, userId);
    }

    public boolean isMember(Long roomId, String userId) {
        return chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, userId);
    }

    @Transactional
    public void updateOnlineStatus(Long roomId, String userId, boolean isOnline) {
        chatRoomMemberRepository.findByRoomIdAndUserId(roomId, userId)
                .ifPresent(member -> member.updateOnlineStatus(isOnline));
    }
}
