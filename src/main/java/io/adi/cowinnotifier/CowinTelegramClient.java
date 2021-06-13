package io.adi.cowinnotifier;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// TODO: Explore APIs and clean up code
public class CowinTelegramClient {
    static Logger logger = LoggerFactory.getLogger(CowinTelegramClient.class);

    private String token;
    private TelegramBot bot;
    private DataStore dataStore;

    CowinTelegramClient(DataStore dataStore, String token) {
        this.dataStore = dataStore;
        this.token = token;
        this.bot = new TelegramBot(token);

        GetUpdates getUpdates = new GetUpdates().limit(1).offset(0).timeout(0);
        GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
        List<Update> updates = updatesResponse.updates();

        if (updates.size() > 0) {
            Long chatId = updates.get(0).message().chat().id();
            dataStore.getToNotifyChatIds().add(chatId);
            logger.info("CowinTelegramClient Add chatId: {}", chatId);
        }

    }

    public void sendMessage(String message) {
        for (Long chatId: dataStore.getToNotifyChatIds()) {
            bot.execute(new SendMessage(chatId, message));
            logger.info("CowinTelegramClient sendMessage: {} Message: {}", chatId, message);
        }
    }
}
