package temp.chatService.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import temp.chatService.model.ChatMessageDto;
import temp.chatService.model.WsDestination;
import temp.chatService.service.ChatRoomService;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomService chatRoomService;

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = accessor.getDestination();

        if (destination == null || !destination.startsWith(WsDestination.CHAT_ROOM)) {
            return;
        }

        Long roomId = parseRoomId(destination);
        if (roomId == null) {
            return;
        }

        String username = accessor.getUser() != null ? accessor.getUser().getName() : null;
        if (username == null) {
            return;
        }

        if (!chatRoomService.isMember(roomId, username)) {
            return;
        }

        accessor.getSessionAttributes().put("username", username);
        accessor.getSessionAttributes().put("roomId", roomId);

        chatRoomService.updateOnlineStatus(roomId, username, true);

        ChatMessageDto joinMessage = new ChatMessageDto();
        joinMessage.setType(ChatMessageDto.MessageType.JOIN);
        joinMessage.setSender(username);
        joinMessage.setRoomId(roomId);

        messagingTemplate.convertAndSend(WsDestination.CHAT_ROOM + roomId, joinMessage);
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) accessor.getSessionAttributes().get("username");
        Long roomId = (Long) accessor.getSessionAttributes().get("roomId");

        if (username == null || roomId == null) {
            return;
        }

        // disconnect는 자리비움이므로 LEAVE 브로드캐스트 하지 않음
        // 멤버십이 남아있으면(명시적 leave 없이 끊긴 것) offline 상태로 전환 → 3단계에서 알림 대상으로 처리
        if (chatRoomService.isMember(roomId, username)) {
            chatRoomService.updateOnlineStatus(roomId, username, false);
        }
    }

    private Long parseRoomId(String destination) {
        try {
            return Long.parseLong(destination.substring(WsDestination.CHAT_ROOM.length()));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
