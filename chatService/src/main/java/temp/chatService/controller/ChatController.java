package temp.chatService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import temp.chatService.model.ChatMessageListResponse;
import temp.chatService.service.ChatMessageService;
import temp.commonModule.code.SuccessCode;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<ChatMessageListResponse> getMessages(@PathVariable Long roomId, Authentication authentication) {
        return ResponseEntity
                .status(SuccessCode.MESSAGES_FETCH_SUCCESS.getStatus())
                .body(ChatMessageListResponse.of(
                        chatMessageService.getMessagesByRoom(roomId, authentication.getName()),
                        SuccessCode.MESSAGES_FETCH_SUCCESS.getMessage()
                ));
    }
}
