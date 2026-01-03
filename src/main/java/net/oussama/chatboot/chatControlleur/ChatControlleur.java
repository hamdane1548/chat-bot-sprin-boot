package net.oussama.chatboot.chatControlleur;

import net.oussama.chatboot.agents.Agentai;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatControlleur {
   private Agentai agentai;
   public ChatControlleur(Agentai agentai) {
       this.agentai = agentai;
   }
   @GetMapping(value = "/chat",produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> chatAgent(String message) {
       return agentai.chat(message);
   }
}
