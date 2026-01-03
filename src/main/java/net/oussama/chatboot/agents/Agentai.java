package net.oussama.chatboot.agents;

import net.oussama.chatboot.tools.Aitools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
@Component
public class Agentai {
    private ChatClient chatClient;
    public Agentai(ChatClient.Builder builder, ChatMemory chatMemory, Aitools aitools) {
        this.chatClient = builder
                .defaultSystem(
                        """
                                Vous un assistant qui se charge de repondre aux question de 
                                l'ustilsateur en fonction du context fourni Si aucun context n'est frouni
                                repon ave je ne sais pas
                                """
                )
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultTools(aitools)
                .build();
    }
    public Flux<String> chat(String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
