package commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import interfaces.Command;
import interfaces.CommandInfo;

@CommandInfo(name = "cd", description = "изменяет текущую рабочую директорию")
public class CdCommand implements Command {

    @Override
    public void execute(String[] args) throws CommandExecutionException {
        // Если аргументов нет, переходим в домашнюю директорию
        if (args.length == 0) {
            String homeDir = System.getProperty("user.home");
            changeDirectory(homeDir);
            return;
        }

        // Получаем путь из первого аргумента
        String path = args[0];
        changeDirectory(path);
    }

    private void changeDirectory(String path) throws CommandExecutionException {
        try {
            // Обрабатываем специальный символ ~ (домашняя директория)
            if (path.startsWith("~")) {
                path = path.replaceFirst("~", System.getProperty("user.home"));
            }

            // Получаем абсолютный путь
            Path newPath = Paths.get(path).toAbsolutePath().normalize();

            // Проверяем, что путь существует и это директория
            if (!Files.exists(newPath)) {
                throw new CommandExecutionException("cd", "Директория не существует: " + newPath);
            }

            if (!Files.isDirectory(newPath)) {
                throw new CommandExecutionException("cd", "Указанный путь не является директорией: " + newPath);
            }

            // Меняем рабочую директорию
            System.setProperty("user.dir", newPath.toString());

        } catch (SecurityException e) {
            throw new CommandExecutionException("cd", "Нет прав доступа к директории: " + path);
        }
    }
}
