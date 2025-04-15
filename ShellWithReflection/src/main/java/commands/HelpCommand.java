package commands;
import interfaces.Command;
import java.util.Map;

public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Доступные команды:");
            commands.values().forEach(c -> System.out.println("  " + c.getHelp()));
        } else {
            Command cmd = commands.get(args[0]);
            if (cmd != null) {
                System.out.println(cmd.getHelp());
            } else {
                System.out.println("Command not found: " + args[0]);
            }
        }
    }

    @Override
    public String getHelp() {
        return "help [command] - показывает справку по всем командам или конкретной команде";
    }
}
