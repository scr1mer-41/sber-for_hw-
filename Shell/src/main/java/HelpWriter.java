import java.util.Map;

public class HelpWriter implements Command{
    Map<String, Command> commandMap;
    Help help;

    public HelpWriter(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
        this.help = new Help(commandMap);
    }

    public void execute() {
        this.help.writeHelp();
    }

    public void getDescription() {
        this.help.getDescription();
    }
}
