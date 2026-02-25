package temp.chatService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import temp.chatService.config.RedisMessagePublisher;
import temp.chatService.entity.ChatMessageEntity;
import temp.chatService.model.ChatMessageResponse;
import temp.chatService.model.MessageType;
import temp.chatService.model.SendMessageDto;
import temp.chatService.repository.ChatMessageRepository;
import temp.commonModule.code.ErrorCode;
import temp.commonModule.exception.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final RedisMessagePublisher redisMessagePublisher;
    private final NotificationService notificationService;
    private final ChatRoomService chatRoomService;

    public void sendMessage(SendMessageDto request, String senderId) {
        if (!chatRoomService.isMember(request.getRoomId(), senderId)) {
            throw new BusinessException(ErrorCode.ROOM_NOT_JOINED);
        }
        chatMessageRepository.save(
                ChatMessageEntity.create(request.getRoomId(), MessageType.CHAT, request.getContent(), senderId)
        );
        redisMessagePublisher.publishChat(request.getRoomId(), senderId, request.getContent());
        notificationService.notifyOfflineMembers(request.getRoomId(), senderId, request.getContent());
    }

    public List<ChatMessageResponse> getMessagesByRoom(Long roomId, String userId) {
        if (!chatRoomService.isMember(roomId, userId)) {
            throw new BusinessException(ErrorCode.ROOM_NOT_JOINED);
        }
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId).stream()
                .map(ChatMessageResponse::from)
                .collect(Collectors.toList());
    }
}
