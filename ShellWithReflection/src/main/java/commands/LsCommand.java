package commands;

import interfaces.Command;

import java.util.Arrays;

public class LsCommand implements Command {

    @Override
    public String getName() {
        return "ls";
    }

    @Override
    public void execute(String[] args) {
        Arrays.stream(new java.io.File(".").listFiles())
                .forEach(f -> System.out.println(f.getName()));
    }

    @Override
    public String getHelp() {
        return "ls - выводит список файлов в текущем каталоге";
    }
}
