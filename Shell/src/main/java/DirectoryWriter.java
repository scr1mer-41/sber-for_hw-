public class DirectoryWriter implements Command{
    private final Directory dir = new Directory();

    public void execute() {
        dir.writeDirectory();
    }

    public void getDescription() {
        dir.writeDescription();
    }
}
