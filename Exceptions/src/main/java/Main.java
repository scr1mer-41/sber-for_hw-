import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CompositionOfEven composition = new CompositionOfEven();

        System.out.println("Введите четные числа для произведения!");
        System.out.print("Первое число: ");
        double a = scanner.nextDouble();
        System.out.print("Второе число: ");
        double b = scanner.nextDouble();

        try {
            double res = composition.composition(a,b);
            System.out.println(res);
        } catch (NumberNotEvenException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Программа закончила работу!");
        }
    }
}
