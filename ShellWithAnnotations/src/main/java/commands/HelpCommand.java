package commands;

import interfaces.Command;
import interfaces.CommandInfo;
import java.util.Map;

@CommandInfo(name = "help", description = "показывает справку по всем командам или конкретной команде")
public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {

            System.out.println("Доступные команды:");

            commands.values().stream().distinct().forEach(cmd -> {
                        CommandInfo info = cmd.getClass().getAnnotation(CommandInfo.class);
                        System.out.printf("%-10s - %s%n", info.name(), info.description());
                    });

        } else {

            String commandName = args[0];
            Command cmd = commands.get(commandName);

            if (cmd == null) {
                System.out.println("Неизвестная команда: " + commandName);
                return;
            }

            CommandInfo info = cmd.getClass().getAnnotation(CommandInfo.class);
            System.out.printf("Команда: %s%n", info.name());
            System.out.printf("Описание: %s%n", info.description());
        }
    }
}
