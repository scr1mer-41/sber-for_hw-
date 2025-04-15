package commands;

import interfaces.Command;
import interfaces.CommandInfo;

@CommandInfo(name = "exit", description = "завершает программу")
public class ExitCommand implements Command {

    @Override
    public void execute(String[] args) {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
