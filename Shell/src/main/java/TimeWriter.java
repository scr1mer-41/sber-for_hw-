public class TimeWriter implements Command{
    private final Time time = new Time();

    public void execute() {
        time.writeTime();
    }

    public void getDescription() {
        time.writeDescription();
    }
}
