import commands.*;
import interfaces.Command;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.util.*;

public class Shell {
    private final Map<String, Command> commands = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public void loadCommands() {
        // Автоматическая загрузка команд через Reflection
        try {
            Reflections reflections = new Reflections(
                    new ConfigurationBuilder()
                            .forPackages("commands")
                            .setScanners(Scanners.SubTypes.filterResultsBy(s -> true))
            );

            Set<Class<? extends Command>> classes =
                    reflections.getSubTypesOf(Command.class);

            for (Class<?> clazz : classes) {
                if (clazz != HelpCommand.class) {
                    Command cmd = (Command) clazz.getDeclaredConstructor().newInstance();
                    registerCommand(cmd);
                }
            }

            // HelpCommand требует специальной инициализации
            registerCommand(new HelpCommand(commands));

        } catch (Exception e) {
            System.err.println("Error loading commands: " + e.getMessage());
        }
    }

    public void run() {
        System.out.println("Добро пожаловать в Shell. Введите «help» для доступных команд.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+");
            String cmdName = parts[0];
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            Command cmd = commands.get(cmdName);
            if (cmd != null) {
                try {
                    cmd.execute(args);
                } catch (Exception e) {
                    System.err.println("Error executing command: " + e.getMessage());
                }
            } else {
                System.err.println("Unknown command: " + cmdName);
            }
        }
    }

}
