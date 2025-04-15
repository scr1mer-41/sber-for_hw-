package commands;

import interfaces.Command;
import interfaces.CommandInfo;
import java.time.LocalTime;

@CommandInfo(name = "time", description = "выводит текущее время")
public class TimeCommand implements Command {

    @Override
    public void execute(String[] args) {
        System.out.println(LocalTime.now().withNano(0));
    }
}
