public class DataWriter implements Command {
    private final Data data = new Data();

    public void execute() {
        data.writeData();
    }

    public void getDescription() {
        data.writeDescription();
    }
}
