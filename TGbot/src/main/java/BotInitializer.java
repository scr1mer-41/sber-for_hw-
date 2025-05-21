import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class BotInitializer {
    BotConfig botConfig = new BotConfig();
    MyBot telegramBot = new MyBot(botConfig.getBotToken());

    public void init() {
        try (TelegramBotsLongPollingApplication telegramBotsApp = new TelegramBotsLongPollingApplication()) {
            telegramBotsApp.registerBot(botConfig.getBotToken(), telegramBot);
            System.out.println("Bot started!");
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
            // - логгирование
        }

    }
}
