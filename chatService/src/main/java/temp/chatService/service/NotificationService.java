package temp.chatService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import temp.chatService.model.ChatMessageDto;
import temp.chatService.model.NotificationDto;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomService chatRoomService;

    public void notifyOfflineMembers(ChatMessageDto chatMessage, String senderId) {
        NotificationDto notification = new NotificationDto(
                chatMessage.getRoomId(),
                senderId,
                chatMessage.getContent()
        );

        chatRoomService.getOfflineMembers(chatMessage.getRoomId()).stream()
                .filter(member -> !member.getUserId().equals(senderId))
                .forEach(member -> messagingTemplate.convertAndSendToUser(
                        member.getUserId(), "/queue/notification", notification));
    }
}
