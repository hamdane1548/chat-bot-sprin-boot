package net.oussama.chatboot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Component
public class Agentai {
    private ChatClient chatClient;
    public Agentai(ChatClient.Builder builder, ChatMemory chatMemory,ToolCallbackProvider toolCallbackProvider) {
        Arrays.stream(toolCallbackProvider.getToolCallbacks()).forEach(toolCallbacks -> {
            System.out.println("*******");
            System.out.println(toolCallbacks.getToolDefinition());
        });
        this.chatClient = builder
                .defaultSystem(
                        """
                                Vous un assistant qui se charge de repondre aux question de 
                                l'ustilsateur en fonction du context fourni Si aucun context n'est frouni
                                repon ave je ne sais pas
                                """
                )
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }
    public String chat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
