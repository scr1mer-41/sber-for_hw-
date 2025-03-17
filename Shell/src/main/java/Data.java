import java.time.format.DateTimeFormatter;

public class Data {
    public Data() {}

    public void writeData() {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        System.out.println(currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    public void writeDescription() {
        System.out.println("Displays the current date.");
    }
}
