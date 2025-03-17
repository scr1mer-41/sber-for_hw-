import java.util.Map;

public class Help {
    Map<String, Command> commandMap;

    public Help(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public void writeHelp() {
        System.out.println("Available commands:");
        for (Map.Entry<String, Command> entry : this.commandMap.entrySet()) {
            System.out.print(entry.getKey() + " - ");
            entry.getValue().getDescription();
        }
    }

    public void getDescription() {
        System.out.println("Displays the list of available commands with their descriptions.");
    }
}
