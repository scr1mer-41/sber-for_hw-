import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите уравнение:");
        String equation = scanner.next();
        while (!Objects.equals(equation, "exit")) {
            System.out.print("Результат: " + calculator.calculate(equation) + "\n");
            System.out.println("Введите уравнение:");
            equation = scanner.next();
        }
        System.out.print("Результат: " + calculator.calculate(equation) + "\n");

    }
}