package temp.chatService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import temp.chatService.entity.ChatRoom;
import temp.chatService.model.ChatMessageDto;
import temp.chatService.model.WsDestination;
import temp.chatService.service.ChatRoomService;

import java.util.List;

@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<ChatRoom> createRoom(@RequestParam String name) {
        return ResponseEntity.ok(chatRoomService.createRoom(name));
    }

    @GetMapping
    public ResponseEntity<List<ChatRoom>> getRooms() {
        return ResponseEntity.ok(chatRoomService.getRooms());
    }

    @PostMapping("/{roomId}/join")
    public ResponseEntity<Void> join(@PathVariable Long roomId, Authentication authentication) {
        chatRoomService.join(roomId, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{roomId}/leave")
    public ResponseEntity<Void> leave(@PathVariable Long roomId, Authentication authentication) {
        String userId = authentication.getName();
        chatRoomService.leave(roomId, userId);

        ChatMessageDto leaveMessage = new ChatMessageDto();
        leaveMessage.setType(ChatMessageDto.MessageType.LEAVE);
        leaveMessage.setSender(userId);
        leaveMessage.setRoomId(roomId);
        messagingTemplate.convertAndSend(WsDestination.CHAT_ROOM + roomId, leaveMessage);

        return ResponseEntity.ok().build();
    }
}
