package commands;

import interfaces.Command;

public class ExitCommand implements Command {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Goodbye!");
        System.exit(0);
    }

    @Override
    public String getHelp() {
        return "exit - завершает программу";
    }
}
