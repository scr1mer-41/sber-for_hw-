package commands;
import interfaces.Command;
import java.time.LocalDate;

public class DateCommand implements Command {

    @Override
    public String getName() {
        return "date";
    }

    @Override
    public void execute(String[] args) {
        System.out.println(LocalDate.now());
    }

    @Override
    public String getHelp() {
        return "data - отображает текущую дату";
    }

}
