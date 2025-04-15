package commands;

import interfaces.Command;
import interfaces.CommandInfo;
import java.nio.file.Path;
import java.nio.file.Paths;

@CommandInfo(name = "pwd", description = "выводит текущую рабочую директорию")
public class PwdCommand implements Command {

    @Override
    public void execute(String[] args) {
        // Получаем текущую рабочую директорию
        String currentDir = System.getProperty("user.dir");

        // Можно использовать Paths для более "чистого" пути
        Path path = Paths.get(currentDir).toAbsolutePath().normalize();

        System.out.println(path.toString());
    }
}
