package temp.chatService.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import temp.chatService.model.ChatMessageDto;
import temp.chatService.model.WsDestination;
import temp.commonModule.code.LogCode;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ChatMessageDto chatMessage = objectMapper.readValue(message.getBody(), ChatMessageDto.class);
            messagingTemplate.convertAndSend(WsDestination.CHAT_ROOM + chatMessage.getRoomId(), chatMessage);
        } catch (IOException e) {
            log.error(LogCode.REDIS_DESERIALIZE_FAILED.getMessage(), e);
        }
    }
}
