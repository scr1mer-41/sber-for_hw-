package commands;

import interfaces.Command;
import interfaces.CommandInfo;

import java.time.LocalDate;

@CommandInfo(name = "date", description = "выводит текущую дату")
public class DateCommand implements Command {

    @Override
    public void execute(String[] args) {
        System.out.println(LocalDate.now());
    }
}
