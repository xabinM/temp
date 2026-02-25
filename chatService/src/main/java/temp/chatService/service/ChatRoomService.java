package temp.chatService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import temp.chatService.config.RedisMessagePublisher;
import temp.chatService.entity.ChatRoom;
import temp.chatService.entity.ChatRoomMember;
import temp.chatService.repository.ChatRoomMemberRepository;
import temp.chatService.repository.ChatRoomRepository;
import temp.chatService.repository.OnlineStatusRedisRepository;
import temp.commonModule.code.ErrorCode;
import temp.commonModule.exception.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final OnlineStatusRedisRepository onlineStatusRedisRepository;
    private final RedisMessagePublisher redisMessagePublisher;

    public ChatRoom createRoom(String name) {
        return chatRoomRepository.save(ChatRoom.create(name));
    }

    public List<ChatRoom> getMyRooms(String userId) {
        List<Long> roomIds = chatRoomMemberRepository.findByUserId(userId).stream()
                .map(ChatRoomMember::getRoomId)
                .collect(Collectors.toList());
        return chatRoomRepository.findAllById(roomIds);
    }

    @Transactional
    public ChatRoom join(Long roomId, String userId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_NOT_FOUND));
        if (chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, userId)) {
            throw new BusinessException(ErrorCode.ROOM_ALREADY_JOINED);
        }
        chatRoomMemberRepository.save(ChatRoomMember.create(roomId, userId));
        return room;
    }

    @Transactional
    public void leave(Long roomId, String userId) {
        if (!chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, userId)) {
            throw new BusinessException(ErrorCode.ROOM_NOT_JOINED);
        }
        chatRoomMemberRepository.deleteByRoomIdAndUserId(roomId, userId);
        onlineStatusRedisRepository.setOffline(roomId, userId);
        redisMessagePublisher.publishLeave(roomId, userId);
    }

    public boolean isMember(Long roomId, String userId) {
        return chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, userId);
    }

    public void updateOnlineStatus(Long roomId, String userId, boolean isOnline) {
        if (isOnline) {
            onlineStatusRedisRepository.setOnline(roomId, userId);
        } else {
            onlineStatusRedisRepository.setOffline(roomId, userId);
        }
    }

    public List<ChatRoomMember> getOfflineMembers(Long roomId) {
        return chatRoomMemberRepository.findByRoomId(roomId).stream()
                .filter(member -> !onlineStatusRedisRepository.isOnline(roomId, member.getUserId()))
                .collect(Collectors.toList());
    }
}
