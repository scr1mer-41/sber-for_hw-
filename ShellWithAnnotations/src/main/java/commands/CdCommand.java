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
        if (args.length == 0) {
            String homeDir = System.getProperty("user.home");
            changeDirectory(homeDir);
            return;
        }

        String path = args[0];
        changeDirectory(path);
    }

    private void changeDirectory(String path) throws CommandExecutionException {
        try {
            if (path.startsWith("~")) {
                path = path.replaceFirst("~", System.getProperty("user.home"));
            }

            Path newPath = Paths.get(path).toAbsolutePath().normalize();

            if (!Files.exists(newPath)) {
                throw new CommandExecutionException("cd", "Директория не существует: " + newPath);
            }

            if (!Files.isDirectory(newPath)) {
                throw new CommandExecutionException("cd", "Указанный путь не является директорией: " + newPath);
            }

            System.setProperty("user.dir", newPath.toString());

        } catch (SecurityException e) {
            throw new CommandExecutionException("cd", "Нет прав доступа к директории: " + path);
        }
    }
}
