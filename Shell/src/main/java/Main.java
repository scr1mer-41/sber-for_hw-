import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Command> commandMap = new HashMap<>();

        commandMap.put("date", new DataWriter());
        commandMap.put("time", new TimeWriter());
        commandMap.put("pwd", new DirectoryWriter());
        commandMap.put("exit", new ExitWriter());
        commandMap.put("help", new HelpWriter(commandMap));

        Scanner scanner = new Scanner(System.in);
        String commandName;

        while (true) {
            System.out.print("Enter command: ");
            commandName = scanner.nextLine().trim();

            if (commandMap.containsKey(commandName)) {
                Command command = commandMap.get(commandName);
                command.execute();

                if (commandName.equals("exit")) {
                    break; // Exit the loop when the "exit" command is entered
                }
            } else {
                System.out.println("Unknown command. Type 'help' to see available commands.");
            }
        }

        scanner.close();
    }
}
