import java.time.format.DateTimeFormatter;

public class Time {
    public Time() {};

    public void writeTime() {
        java.time.LocalTime currentTime = java.time.LocalTime.now();
        System.out.println(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    public void writeDescription() {
        System.out.println("Displays the current date.");
    }
}
