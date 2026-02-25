package temp.chatService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ChatRoomService chatRoomService;

    public void notifyOfflineMembers(Long roomId, String senderId, String content) {
        chatRoomService.getOfflineMembers(roomId).stream()
                .filter(member -> !member.getUserId().equals(senderId))
                .forEach(member -> sendFcm(member.getUserId(), roomId, content));
    }

    private void sendFcm(String userId, Long roomId, String content) {
        // TODO: FCM 연동
    }
}
