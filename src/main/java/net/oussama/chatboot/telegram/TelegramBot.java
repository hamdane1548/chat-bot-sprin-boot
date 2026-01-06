package net.oussama.chatboot.telegram;

import jakarta.annotation.PostConstruct;
import net.oussama.chatboot.agents.Agentai;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import reactor.core.publisher.Flux;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.api.key}")
    private String token;
    private Agentai agentai;
    public TelegramBot(Agentai agentai) {
        this.agentai = agentai;
    }
    //execute just apres le contstructeur
    @PostConstruct
    public void registerTeleramBot() throws TelegramApiException {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        }catch (Exception e){
            throw new TelegramApiException(e.getMessage());
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
         if(!update.hasMessage()) return;
         String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        senTypingQuestion(chatId);
       Flux<String> answer= agentai.chat(message);
        sendTextMessage(chatId, String.valueOf(answer));
    }

    @Override
    public String getBotUsername() {
        return "MundiaBot";
    }
    @Override
    public String getBotToken() {
        return token;
    }
    public void sendTextMessage(Long messageId, String message) {
        SendMessage sendMessage = new SendMessage(String.valueOf(messageId),message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void senTypingQuestion(Long messageId) {
        SendChatAction sendChatAction = new SendChatAction();
        sendChatAction.setChatId(String.valueOf(messageId));
        sendChatAction.setAction(ActionType.TYPING);
        try {
            execute(sendChatAction);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
