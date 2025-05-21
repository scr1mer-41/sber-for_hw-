import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import java.util.*;

public class MyBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final CommandHandler commandHandler;
    private final DBService dbService;
    HashMap<String, Answers> answers = new HashMap<>();


    public MyBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
        commandHandler = new CommandHandler();
        commandHandler.setTelegramClient(telegramClient);
        dbService = new DBService();

        answers.put("Укажите ваш пол. Вам нужно вести \"Мужчина\" или \"Женщина\".", Answers.ANSWERS1);
        answers.put("Укажите ваш рост в сантиметрах?", Answers.ANSWERS2);
        answers.put("Укажите ваш вес в килограммах?", Answers.ANSWERS3);
        answers.put("Укажите номер уровня активности, который соответствует вашему?", Answers.ANSWERS4);
        answers.put("Укажите ваш возраст?", Answers.ANSWERS5);

        answers.put("Введите количество килокалорий:", Answers.ANSWERS6);
        answers.put("Введите комментарий к записи:", Answers.ANSWERS7);

        answers.put("Выберите номер записи, которую вы хотите удалить.", Answers.ANSWERS8);

    }

    @Override
    public void consume(Update update) {


        if (update.hasMessage() && "/start".equals(update.getMessage().getText())) {
            Long chat_id = update.getMessage().getChatId();

            if (!dbService.checkUserInDBbyChatID(chat_id)) {
                commandHandler.start(update.getMessage().getChatId());
            }
        }

        if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            Long chat_id = update.getCallbackQuery().getMessage().getChatId();

            switch (call_data) {
                case "yes_register":
                    if (!dbService.checkUserInDBbyChatID(chat_id)) {
                        User user = new User();
                        user.setChatID(chat_id);
                        user.setStatus(0);

                        SendMessage message = SendMessage.builder().chatId(chat_id).text("Хорошо, давайте начнем!" ).build();

                        try {
                            telegramClient.execute(message);
                            dbService.addUser(user);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                        ans(chat_id, Answers.ANSWERS1);

                    }
                    break;

                case "manually":
                    ans(chat_id, Answers.ANSWERS6);
                    break;

                case "automatically":
                    commandHandler.menu(chat_id, "Эта функция в разработке!");
                    break;

                case "statistic_for_week":
                    User user = dbService.getUserByChatID(chat_id);
                    List<Note> notes = dbService.getNotesByDateWeek(user.getId());
                    var kal = 0;
                    if (!notes.isEmpty()) {
                        for (Note note : notes) {
                            kal += note.getKal();
                        }
                    }
                    commandHandler.menu(chat_id, "За неделю вы набрали " + kal + " килокалорий.");
                    break;

                case "statistic_for_day":
                    User user1 = dbService.getUserByChatID(chat_id);
                    List<Note> notes1 = dbService.getNotesByDateDay(user1.getId());
                    var kal1 = 0;
                    if (!notes1.isEmpty()) {
                        for (Note note : notes1) {
                            kal1 += note.getKal();
                        }
                    }
                    commandHandler.menu(chat_id, "За день вы набрали "+ kal1 +" килокалорий.");
                    break;

                case "weight_loss":
                    User user2 = dbService.getUserByChatID(chat_id);
                    var norm_kal = CalorieRateCalculation(user2) - 355;
                    commandHandler.menu(chat_id, "Ваша дневная норма для сброса веса составляет "+norm_kal+" ккал!");
                    break;

                case "weight_maintenance":
                    User user3 = dbService.getUserByChatID(chat_id);
                    var norm_kal1 = CalorieRateCalculation(user3);
                    commandHandler.menu(chat_id, "Ваша дневная норма для поддержания веса составляет "+norm_kal1+" ккал!");
                    break;

                case "weight_gain":
                    User user4 = dbService.getUserByChatID(chat_id);
                    var norm_kal2 = CalorieRateCalculation(user4) + 355;
                    commandHandler.menu(chat_id, "Ваша дневная норма для набора веса составляет "+norm_kal2+" ккал!");
                    break;

                case "delete_all_notes":
                    User user5 = dbService.getUserByChatID(chat_id);
                    dbService.deleteAllNotesByUserID(user5.getId());
                    commandHandler.menu(chat_id, "Все записи удалены!");
                    break;

                case "don't_delete_all_notes":
                    commandHandler.menu(chat_id, "Записи не удалены.");
                    break;

            }








        } else if (update.hasMessage()) {
            Long chat_id = update.getMessage().getChatId();
            String textOfMessage = update.getMessage().getText();

            if (dbService.checkUserInDBbyChatID(chat_id)) {
                int user_status = dbService.getUserByChatID(chat_id).getStatus();

                if (update.getMessage().isReply()) {

                    Answers answer = answers.get(update.getMessage().getReplyToMessage().getText());
                    User user = dbService.getUserByChatID(chat_id);

                    if ((answer != null)) {
                        switch (answer) {
                            case Answers.ANSWERS1:
                                if (commandHandler.validationReplyToMessage(textOfMessage, Answers.ANSWERS1)) {

                                    if (textOfMessage.equals("Мужчина")) {
                                        user.setSex("male");
                                    } else {
                                        user.setSex("female");
                                    }

                                    dbService.updateUserData(user);

                                    String text = "Вы установили пол!";

                                    if (user_status != 1) {
                                        commandHandler.send(chat_id, "Вы установили пол!");
                                        ans(chat_id, Answers.ANSWERS2);
                                    } else {
                                        commandHandler.menu(chat_id, text);
                                    }

                                } else {
                                    commandHandler.send(chat_id, "Некорректные данные! Попробуйте снова!");
                                    ans(chat_id, Answers.ANSWERS1);
                                }

                                break;

                            case Answers.ANSWERS2:
                                if (commandHandler.validationReplyToMessage(textOfMessage, Answers.ANSWERS2)) {
                                    user.setHeight(Float.parseFloat(textOfMessage.toString()));

                                    dbService.updateUserData(user);

                                    String text = "Вы установили рост!";

                                    if (user_status != 1) {
                                        commandHandler.send(chat_id, text);
                                        ans(chat_id, Answers.ANSWERS3);
                                    } else {
                                        commandHandler.menu(chat_id, text);
                                    }

                                } else {
                                    commandHandler.send(chat_id, "Некорректные данные! Попробуйте снова!");
                                    ans(chat_id, Answers.ANSWERS2);
                                }

                                break;
                            case Answers.ANSWERS3:
                                if (commandHandler.validationReplyToMessage(textOfMessage, Answers.ANSWERS3)) {

                                    user.setWeight(Float.parseFloat(textOfMessage.toString()));

                                    dbService.updateUserData(user);

                                    String text = "Вы установили вес!";

                                    String text1 = "Уровни активности:\n 1. Физическая нагрузка отсутствует или минимальна;\n 2. Тренировки средней тяжести 3 раза в неделю;\n 3. Тренировки средней тяжести 5 раз в неделю; \n 4. Интенсивные тренировки 5 раз в неделю;\n 5. Тренировки каждый день;\n 6. Интенсивные тренировки каждый день по несколько раз;\n 7. Ежедневная нагрузка + физическая работа.";

                                    if (user_status != 1) {
                                        commandHandler.send(chat_id, text);
                                        ans(chat_id, Answers.ANSWERS4);
                                        commandHandler.send(chat_id,text1);
                                    } else {
                                        commandHandler.menu(chat_id, text);
                                    }

                                } else {
                                    commandHandler.send(chat_id, "Некорректные данные! Попробуйте снова!");
                                    ans(chat_id, Answers.ANSWERS3);
                                }

                                break;

                            case Answers.ANSWERS4:
                                if (commandHandler.validationReplyToMessage(textOfMessage, Answers.ANSWERS4)) {

                                    user.setActivity_factor(Integer.parseInt(textOfMessage));

                                    dbService.updateUserData(user);

                                    String text = "Вы установили фактор активности!";

                                    if (user_status != 1) {
                                        commandHandler.send(chat_id,text);
                                        ans(chat_id, Answers.ANSWERS5);
                                    } else {
                                        commandHandler.menu(chat_id, text);
                                    }

                                } else {
                                    commandHandler.send(chat_id, "Некорректные данные! Попробуйте снова!");
                                    ans(chat_id, Answers.ANSWERS4);
                                }

                                break;
                            case Answers.ANSWERS5:
                                if (commandHandler.validationReplyToMessage(textOfMessage, Answers.ANSWERS5)) {

                                    user.setAge(Integer.parseInt(textOfMessage));
                                    user.setStatus(1);

                                    dbService.updateUserData(user);

                                    String text = "Вы установили возраст!";

                                    if (user_status != 1) {
                                        commandHandler.send(chat_id, text);
                                        commandHandler.menu(chat_id, "Регистрация пройдена!");
                                    } else {
                                        commandHandler.menu(chat_id, text);
                                    }

                                } else {
                                    commandHandler.send(chat_id, "Некорректные данные! Попробуйте снова!");
                                    ans(chat_id, Answers.ANSWERS5);
                                }

                                break;

                            case Answers.ANSWERS6:
                                if (commandHandler.validationReplyToMessage(textOfMessage, Answers.ANSWERS6)) {

                                    Integer note_id = dbService.checkMaxIDinNotes(user.getId());
                                    Note note = dbService.getNoteByID(note_id);

                                    note.setKal(Integer.parseInt(textOfMessage));
                                    dbService.updateNoteData(note);
                                    commandHandler.send(chat_id, "Вы указали килокалории!");
                                    ans(chat_id, Answers.ANSWERS7);
                                } else {
                                    commandHandler.send(chat_id, "Некорректные данные! Попробуйте снова!");
                                    ans(chat_id, Answers.ANSWERS6);
                                }
                                break;

                            case Answers.ANSWERS7:

                                Integer note_id = dbService.checkMaxIDinNotes(user.getId());
                                Note note = dbService.getNoteByID(note_id);

                                note.setText(textOfMessage);
                                dbService.updateNoteData(note);
                                commandHandler.send(chat_id, "Вы установили комментарий к записи!");
                                commandHandler.menu(chat_id, "Запись создана!");
                                break;

                            case Answers.ANSWERS8:
                                if (commandHandler.validationReplyToMessage(textOfMessage, Answers.ANSWERS8)) {

                                    List<Note> notes = dbService.getAllNotesByUserID(user.getId());
                                    int index = Integer.parseInt(textOfMessage)-1;

                                    if ((index < 0) || (index >= notes.size())) {
                                        commandHandler.send(chat_id, "Неверный номер! Попробуйте снова!");
                                        ans(chat_id, Answers.ANSWERS8);
                                    } else {
                                        dbService.deleteNoteById(notes.get(index).getId(), user.getId());
                                    }
                                    commandHandler.menu(chat_id, "Записка удалена!");
                                } else {
                                    commandHandler.send(chat_id, "Некорректные данные! Попробуйте снова!");
                                    ans(chat_id, Answers.ANSWERS8);
                                }
                                break;
                        }
                    }
                }


                if (user_status == 1) {
                    if (textOfMessage.equals("Новый прием пищи")) {

                        SendMessage message1 = SendMessage.builder().chatId(chat_id).text("Давайте сделаем новую запись!").build();
                        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(true);
                        message1.setReplyMarkup(replyKeyboardRemove);

                        User user = dbService.getUserByChatID(chat_id);

                        Note note = new Note();
                        note.setDate(new java.sql.Date(update.getMessage().getDate() * 1000));
                        note.setUser_id(user.getId());
                        dbService.addNote(note);

                        SendMessage message = SendMessage.builder().chatId(chat_id).text("Как бы вы хотели ввести калории?").build();

                        InlineKeyboardRow keyboardRow = new InlineKeyboardRow();
                        List<InlineKeyboardRow> rowsInline = new ArrayList<>();

                        keyboardRow.add(commandHandler.createButton("Вручную", "manually"));
                        keyboardRow.add(commandHandler.createButton("Автоматически", "automatically"));
                        rowsInline.add(keyboardRow);

                        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(rowsInline);
                        message.setReplyMarkup(inlineKeyboardMarkup);

                        try {
                            telegramClient.execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }

                    if (textOfMessage.equals("Мои записи")) {
                        User user = dbService.getUserByChatID(chat_id);

                        List<Note> AllNotes = dbService.getAllNotesByUserID(user.getId());

                        if (!AllNotes.isEmpty()) {
                            commandHandler.send(chat_id, "Список записей:");
                            var cnt = 1;
                            for (Note note : AllNotes) {
                                commandHandler.send(chat_id, STR."№ \{cnt} | \{note.getKal()} ккал | Дата: \{note.getDate()} \nКомментарий: \{Optional.ofNullable(note.getText()).orElse("нет")}");
                                cnt += 1;
                            }
                        } else {
                            commandHandler.menu(chat_id, "Список ваших записей пуст!");
                        }
                    }



                    if (textOfMessage.equals("Статистика")) {
                        SendMessage message1 = SendMessage.builder().chatId(chat_id).text("Подсчитаем ваши килокалории!").build();
                        SendMessage message2 = SendMessage.builder().chatId(chat_id).text("За какой период вы бы хотели узнать результат?").build();
                        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(true);
                        message1.setReplyMarkup(replyKeyboardRemove);

                        InlineKeyboardRow keyboardRow = new InlineKeyboardRow();
                        List<InlineKeyboardRow> rowsInline = new ArrayList<>();

                        keyboardRow.add(commandHandler.createButton("За день", "statistic_for_day"));
                        keyboardRow.add(commandHandler.createButton("За неделю", "statistic_for_week"));
                        rowsInline.add(keyboardRow);

                        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(rowsInline);
                        message2.setReplyMarkup(inlineKeyboardMarkup);

                        try {
                            telegramClient.execute(message1);
                            telegramClient.execute(message2);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }

                    if (textOfMessage.equals("Норма калорий")) {
                        SendMessage message1 = SendMessage.builder().chatId(chat_id).text("Рассчитаем вашу норму калорий!").build();
                        SendMessage message2 = SendMessage.builder().chatId(chat_id).text("Выберите расчёт.").build();

                        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(true);
                        message1.setReplyMarkup(replyKeyboardRemove);

                        InlineKeyboardRow keyboardRow1 = new InlineKeyboardRow();
                        InlineKeyboardRow keyboardRow2 = new InlineKeyboardRow();
                        InlineKeyboardRow keyboardRow3 = new InlineKeyboardRow();


                        List<InlineKeyboardRow> rowsInline = new ArrayList<>();

                        keyboardRow1.add(commandHandler.createButton("Для сброса веса", "weight_loss"));
                        keyboardRow2.add(commandHandler.createButton("Для поддержания веса", "weight_maintenance"));
                        keyboardRow3.add(commandHandler.createButton("Для набора веса", "weight_gain"));
                        rowsInline.add(keyboardRow1);
                        rowsInline.add(keyboardRow2);
                        rowsInline.add(keyboardRow3);

                        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(rowsInline);
                        message2.setReplyMarkup(inlineKeyboardMarkup);

                        try {
                            telegramClient.execute(message1);
                            telegramClient.execute(message2);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }

                    if (textOfMessage.equals("Удалить запись")) {
                        User user = dbService.getUserByChatID(chat_id);
                        List<Note> AllNotes = dbService.getAllNotesByUserID(user.getId());

                        if (!AllNotes.isEmpty()) {
                            commandHandler.send(chat_id, "Список записей:");
                            int cnt = 1;
                            for (Note note : AllNotes) {
                                commandHandler.send(chat_id, STR."№ \{cnt} | \{note.getKal()} ккал | Дата: \{note.getDate()} \nКомментарий: \{note.getText()}");
                                cnt += 1;
                            }
                            ans(chat_id, Answers.ANSWERS8);
                        } else {
                            commandHandler.menu(chat_id, "Список ваших записей пуст!");
                        }
                    }

                    if (textOfMessage.equals("Очистить записи")) {
                        User user = dbService.getUserByChatID(chat_id);
                        List<Note> notes = dbService.getAllNotesByUserID(user.getId());
                        if (!notes.isEmpty()) {
                            SendMessage message1 = SendMessage.builder().chatId(chat_id).text("Вы уверены, что хотите удалить все ваши записи? (количество записей: "+notes.size()+")").build();

                            ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(true);
                            message1.setReplyMarkup(replyKeyboardRemove);

                            InlineKeyboardRow keyboardRow = new InlineKeyboardRow();

                            List<InlineKeyboardRow> rowsInline = new ArrayList<>();

                            keyboardRow.add(commandHandler.createButton("Да", "delete_all_notes"));
                            keyboardRow.add(commandHandler.createButton("Нет", "don't_delete_all_notes"));

                            rowsInline.add(keyboardRow);

                            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(rowsInline);
                            message1.setReplyMarkup(inlineKeyboardMarkup);

                            try {
                                telegramClient.execute(message1);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        } else {
                            commandHandler.menu(chat_id, "Список записей пуст!");
                        }
                    }
                }


            }




            //            else if (update.getMessage().getText().equals("Новый прием пищи")) {
            //                commandHandler.send(chat_id, "Давайте запишем новые калории!");
            //                User user = dbService.getUserByChatID(chat_id);
            //
            //                Note note = new Note();
            //                Date date = new Date(update.getMessage().getDate() * 1000);
            //                note.setData(date);
            //                note.setUser_id(user.getId());
            //
            //                dbService.addNote(note);
            //
            //                user.setStatus(2);
            //                dbService.updateUserData(user);
            //            }
            //            else {
            //                User user = dbService.getUserByChatID(chat_id);
            //                if (user.getStatus() == 2 & !update.getMessage().getText().equals("done")) {
            //
            //                }
            //
            //            }

        }


    }

    public void ans(Long chat_id, Answers answer) {
        SendMessage message = SendMessage.builder().chatId(chat_id).text("").build();

        Set<String> answersList = answers.keySet();
        for (String str : answersList) {
            if (answers.get(str) == answer) {
                message = SendMessage.builder().chatId(chat_id).text(str).build();
            }
        }

        ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
        message.setReplyMarkup(forceReplyKeyboard);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public Integer CalorieRateCalculation(User user) {
        Integer calorie_norm = 0;
        System.out.println(user.getActivity_factor());
        if (user.getSex().equals("male")) {
            Double preCalorie_Norm = (user.getWeight()*10 + user.getHeight()*6.25 - user.getAge()*5 + 5) * determinant_activity_factor(user.getActivity_factor());
            calorie_norm = preCalorie_Norm.intValue();
            System.out.println(preCalorie_Norm);
        } else if (user.getSex().equals("female")) {
            Double preCalorie_Norm = (user.getWeight()*10 + user.getHeight()*6.25 - user.getAge()*5 - 161) * determinant_activity_factor(user.getActivity_factor());
            calorie_norm = preCalorie_Norm.intValue();
        }

        return calorie_norm;
    }

    public Float determinant_activity_factor(Integer Number_user_activity_factor) {
        Float result = 1f;
        switch (Number_user_activity_factor) {
            case 1:
                result = 1.2f;
                break;
            case 2:
                result = 1.38f;
                break;
            case 3:
                result = 1.46f;
                break;
            case 4:
                result = 1.55f;
                break;
            case 5:
                result = 1.64f;
                break;
            case 6:
                result = 1.73f;
                break;
            case 7:
                result = 1.9f;
                break;
        }
        return result;
    }
}