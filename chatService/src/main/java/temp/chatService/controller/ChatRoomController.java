package temp.chatService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import temp.chatService.model.ChatRoomInfo;
import temp.chatService.model.ChatRoomListResponse;
import temp.chatService.model.ChatRoomResponse;
import temp.chatService.service.ChatRoomService;
import temp.commonModule.code.SuccessCode;
import temp.commonModule.response.SuccessResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createRoom(@RequestParam String name) {
        return ResponseEntity
                .status(SuccessCode.ROOM_CREATE_SUCCESS.getStatus())
                .body(ChatRoomResponse.of(chatRoomService.createRoom(name), SuccessCode.ROOM_CREATE_SUCCESS.getMessage()));
    }

    @GetMapping
    public ResponseEntity<ChatRoomListResponse> getRooms(Authentication authentication) {
        List<ChatRoomInfo> rooms = chatRoomService.getMyRooms(authentication.getName()).stream()
                .map(ChatRoomInfo::from)
                .collect(Collectors.toList());
        return ResponseEntity
                .status(SuccessCode.ROOMS_FETCH_SUCCESS.getStatus())
                .body(ChatRoomListResponse.of(rooms, SuccessCode.ROOMS_FETCH_SUCCESS.getMessage()));
    }

    @PostMapping("/{roomId}/join")
    public ResponseEntity<ChatRoomResponse> join(@PathVariable Long roomId, Authentication authentication) {
        return ResponseEntity
                .status(SuccessCode.ROOM_JOIN_SUCCESS.getStatus())
                .body(ChatRoomResponse.of(chatRoomService.join(roomId, authentication.getName()), SuccessCode.ROOM_JOIN_SUCCESS.getMessage()));
    }

    @DeleteMapping("/{roomId}/leave")
    public ResponseEntity<SuccessResponse> leave(@PathVariable Long roomId, Authentication authentication) {
        chatRoomService.leave(roomId, authentication.getName());
        return ResponseEntity
                .status(SuccessCode.ROOM_LEAVE_SUCCESS.getStatus())
                .body(SuccessResponse.of(SuccessCode.ROOM_LEAVE_SUCCESS.getMessage()));
    }
}
