public class Directory {
    public Directory() {}

    public void writeDirectory() {
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System: " + currentDir);
    }

    public void writeDescription() {
        System.out.println("Displays the current working directory.");
    }

}
