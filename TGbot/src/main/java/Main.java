import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.sql.SQLException;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws SQLException {

        BotInitializer bot = new BotInitializer();
        bot.init();
//        String botToken = "8110160268:AAGZiPGbIGv0Mey4CqLXubIRVNT548jB54k";
//        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
//            botsApplication.registerBot(botToken, new MyBot(botToken));
//            System.out.print("Bot started!");
//            Thread.currentThread().join();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
