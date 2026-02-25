package temp.chatService.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import temp.chatService.model.WsDestination;
import temp.chatService.service.ChatRoomService;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final RedisMessagePublisher redisMessagePublisher;
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

        // UNSUBSCRIBE 프레임은 destination 없이 subscriptionId만 오므로
        // subscriptionId → roomId 매핑을 세션에 저장
        getSubscriptionMap(accessor).put(accessor.getSubscriptionId(), roomId);

        chatRoomService.updateOnlineStatus(roomId, username, true);

        redisMessagePublisher.publishJoin(roomId, username);
    }

    @EventListener
    public void handleUnsubscribe(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        // subscriptionId로 roomId 조회
        Long roomId = getSubscriptionMap(accessor).remove(accessor.getSubscriptionId());
        if (roomId == null) {
            return;
        }

        String username = (String) accessor.getSessionAttributes().get("username");
        if (username == null) {
            return;
        }

        chatRoomService.updateOnlineStatus(roomId, username, false);
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) accessor.getSessionAttributes().get("username");
        if (username == null) {
            return;
        }

        // 앱 종료 시 아직 구독 중인 모든 방을 offline 처리
        // Redis DEL은 존재하지 않는 키에 대해 no-op이므로 별도 검증 불필요
        getSubscriptionMap(accessor).values()
                .forEach(roomId -> chatRoomService.updateOnlineStatus(roomId, username, false));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Long> getSubscriptionMap(StompHeaderAccessor accessor) {
        return (Map<String, Long>) accessor.getSessionAttributes()
                .computeIfAbsent("subscriptions", k -> new HashMap<>());
    }

    private Long parseRoomId(String destination) {
        try {
            return Long.parseLong(destination.substring(WsDestination.CHAT_ROOM.length()));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
