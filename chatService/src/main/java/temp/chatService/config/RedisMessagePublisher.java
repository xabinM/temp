package temp.chatService.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import temp.chatService.model.ChatMessageDto;
import temp.chatService.model.MessageType;
import temp.commonModule.code.ErrorCode;

@Component
@RequiredArgsConstructor
public class RedisMessagePublisher {

    private static final String CHANNEL_PREFIX = "chat:room:";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public void publishChat(Long roomId, String senderId, String content) {
        ChatMessageDto message = new ChatMessageDto();
        message.setRoomId(roomId);
        message.setType(MessageType.CHAT);
        message.setSender(senderId);
        message.setContent(content);
        publish(message);
    }

    public void publishJoin(Long roomId, String userId) {
        ChatMessageDto event = new ChatMessageDto();
        event.setRoomId(roomId);
        event.setType(MessageType.JOIN);
        event.setSender(userId);
        publish(event);
    }

    public void publishLeave(Long roomId, String userId) {
        ChatMessageDto event = new ChatMessageDto();
        event.setRoomId(roomId);
        event.setType(MessageType.LEAVE);
        event.setSender(userId);
        publish(event);
    }

    private void publish(ChatMessageDto message) {
        try {
            String json = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend(CHANNEL_PREFIX + message.getRoomId(), json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(ErrorCode.REDIS_SERIALIZE_FAILED.getMessage(), e);
        }
    }
}
