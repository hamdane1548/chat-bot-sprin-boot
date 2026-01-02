package net.oussama.chatboot.chatControlleur;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatControlleur {
    private ChatClient chatClient;
    public ChatControlleur(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    @GetMapping("/chat")
    public String chat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
