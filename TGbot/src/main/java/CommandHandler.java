import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandHandler {
    private TelegramClient telegramClient;

    public void setTelegramClient(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public InlineKeyboardButton createButton(String text, String callbackData) {
        InlineKeyboardButton botton = new InlineKeyboardButton(text);
        botton.setCallbackData(callbackData);
        return botton;
    }

    public void start(Long chat_id) {
        SendMessage hello = SendMessage.builder().chatId(chat_id)
                .text("Приветствую!").build();
        SendMessage description = SendMessage.builder().chatId(chat_id)
                .text("Это бот, который поможет Вам вести учет за вашими калориями) Вы всегда будете знать, когда, что и сколько вы съели, отправив пару сообщений! ").build();
        SendMessage registrationRequest = SendMessage.builder().chatId(chat_id)
                .text("Желаете ли вы зарегистрироваться? Если нет, то мы не сможем дальше работать.").build();

        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(true);
        hello.setReplyMarkup(replyKeyboardRemove);

        InlineKeyboardRow rowInline = new InlineKeyboardRow();
        List<InlineKeyboardRow> rowsInline = new ArrayList<>();

        rowInline.add(createButton("Да", "yes_register"));
        rowInline.add(createButton("Нет", "no_register"));
        rowsInline.add(rowInline);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(rowsInline);
        registrationRequest.setReplyMarkup(inlineKeyboardMarkup);
        try {
            telegramClient.execute(hello);
            telegramClient.execute(description);
            telegramClient.execute(registrationRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean validationReplyToMessage(Object reply, Answers answers) {
        switch (answers) {
            case Answers.ANSWERS1:
                String reply_string = reply.toString().toLowerCase(Locale.ROOT);
                if (reply_string.equals("мужчина")) {
                    return true;
                } else if (reply_string.equals("женщина")) {
                    return true;
                }
                break;
            case Answers.ANSWERS2, Answers.ANSWERS3:
                try {
                    Float _reply_ = Float.parseFloat(reply.toString());
                    if (!_reply_.isNaN()) {
                        if (_reply_ > 0) {
                            return true;
                        }
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    // - логгирование
                }
                break;
            case Answers.ANSWERS4, Answers.ANSWERS5, Answers.ANSWERS6, Answers.ANSWERS8:
                try {
                    Integer _reply_ = Integer.parseInt(reply.toString());
                    if (_reply_ > 0) {
                        return true;                                         // нужно ли проверять на отрицательные???
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    // - логгирование
                }
                break;
        }
        return false;
    }

    public void send(Long chat_id, String text) {
        SendMessage message = SendMessage.builder().chatId(chat_id).text(text).build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void menu(Long chat_id, String text) {
        KeyboardButton button_NewNote = new KeyboardButton("Новый прием пищи");
        KeyboardButton button_ShowNotes = new KeyboardButton("Мои записи");
        KeyboardButton button_StataDay = new KeyboardButton("Статистика");
        KeyboardButton button_NormalKal = new KeyboardButton("Норма калорий");
        KeyboardButton button_DeleteNote = new KeyboardButton("Удалить запись");
        KeyboardButton button_Clear = new KeyboardButton("Очистить записи");

        List<KeyboardButton> buttons = new ArrayList<>();
        buttons.add(button_NewNote);
        buttons.add(button_ShowNotes);
        buttons.add(button_StataDay);
        buttons.add(button_NormalKal);
        buttons.add(button_DeleteNote);
        buttons.add(button_Clear);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        for (KeyboardButton i : buttons) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(i);
            keyboardRows.add(keyboardRow);
        }

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        SendMessage message = SendMessage.builder().chatId(chat_id).text(text).build();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        message.setReplyMarkup(replyKeyboardMarkup);
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
