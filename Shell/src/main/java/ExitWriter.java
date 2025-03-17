public class ExitWriter implements Command{
    private final Exit exit = new Exit();

    public void execute() {
        exit.writeExit();
    }

    public void getDescription() {
        exit.writeExit();
    }
}
