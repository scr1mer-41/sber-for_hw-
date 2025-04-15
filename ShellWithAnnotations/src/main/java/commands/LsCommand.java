package commands;

import interfaces.Command;
import java.util.Arrays;
import interfaces.CommandInfo;
import java.io.File;

@CommandInfo(name = "ls", description = "выводит список файлов в текущем каталоге")
public class LsCommand implements Command {

    @Override
    public void execute(String[] args) throws CommandExecutionException {

        String path = args.length > 0 ? args[0] : System.getProperty("user.dir");

        File dir = new File(path);

        if (!dir.exists()) {
            throw new CommandExecutionException("ls","Директория не существует: " + path);
        }

        if (!dir.isDirectory()) {
            throw new CommandExecutionException("ls", "Указанный путь не является директорией: " + path);
        }

        File[] files = dir.listFiles();

        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}
