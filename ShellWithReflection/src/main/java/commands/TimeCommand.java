package commands;
import interfaces.Command;
import java.time.LocalTime;

public class TimeCommand implements Command {

    @Override
    public String getName() {
        return "time";
    }

    @Override
    public void execute(String[] args) {
        System.out.println(LocalTime.now().withNano(0));
    }

    @Override
    public String getHelp() {
        return "time - отображает текущее время";
    }
}
