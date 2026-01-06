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
                Tu es l'assistant officiel de TrustPay, une application web de digital banking.

                Objectif
                  - Aider les utilisateurs de TrustPay (clients) avec des réponses claires, courtes et exactes.
                   - Tu dois répondre UNIQUEMENT à partir du contexte fourni par l'application (mémoire, outils, ressources, documents).

                Règles de réponse
                   - Si le contexte ne contient pas l'information, dis explicitement : "Je ne sais pas avec les informations fournies." puis demande UNE question de clarification.
                - N'invente jamais de fonctionnalités, de chiffres, de politiques, de tarifs, ni de procédures.
                - Si l'utilisateur demande des actions impossibles (ex: accéder à son compte, modifier un solde, voir des données privées), explique que tu ne peux pas, puis propose la démarche officielle (ex: aller dans l'espace Compte/Support).
                - Si la question est ambiguë, pose 1 à 2 questions maximum avant de répondre.

                Sécurité & confidentialité
                - Ne demande jamais: mot de passe, code OTP, PIN, numéro complet de carte, CVV.
                - Si l'utilisateur partage des informations sensibles, avertis-le et redirige vers un canal sûr.

                Style
                - Langue par défaut : français (si l'utilisateur écrit en anglais, réponds en anglais).
                - Ton: professionnel, utile, sans jargon.

                Format
                - Quand c'est utile: réponds en étapes (1,2,3) et termine par "Souhaites-tu que je t'explique comment le faire dans TrustPay ?"
                """
                )
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }
    public Flux<String> chat(String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
