package temp.chatService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import temp.chatService.entity.ChatMessageEntity;
import temp.chatService.model.ChatMessageDto;
import temp.chatService.repository.ChatMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void save(ChatMessageDto message) {
        chatMessageRepository.save(ChatMessageEntity.from(message));
    }

    public List<ChatMessageEntity> getMessagesByRoom(Long roomId) {
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }
}
