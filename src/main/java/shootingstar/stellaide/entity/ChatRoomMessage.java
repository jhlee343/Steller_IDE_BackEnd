package shootingstar.stellaide.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoomId")
    private ChatRoom chatRoom;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    private String sender;
    private String message;
    private LocalDateTime createTime;

    public ChatRoomMessage(ChatRoom chatRoom, MessageType messageType, String sender, String message) {
        this.chatRoom = chatRoom;
        this.messageType = messageType;
        this.sender = sender;
        this.message = message;
        this.createTime = LocalDateTime.now();
    }
}