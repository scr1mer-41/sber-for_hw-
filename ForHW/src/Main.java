import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        tenth();
    }

    public static void first() {
        System.out.print("Первое задание. \n");
        System.out.print("Hello World! \n");
    }

    public static void second() {
        System.out.print("Второе задание. \n");
        Scanner in = new Scanner(System.in);
        System.out.print("Введите число a: ");
        int a = in.nextInt();
        System.out.print("Введите число b: ");
        int b = in.nextInt();
        System.out.printf("%d / %d = %d и %d в остатке \n", a, b, a/b, a%b);
    }

    public static void three() {
        System.out.print("Третье задание. \n");
        Scanner in = new Scanner(System.in);
        System.out.print("\nПроверка на палиндром.");
        System.out.print("\nВведите число: ");
        String str = in.next();
        String str2 = new StringBuilder(str).reverse().toString();
        System.out.print(str + " and " + str2);
        if (Objects.equals(str, str2)) {
            System.out.print("\nЭто палиндром!");
        }
        else {
            System.out.print("\nЭто не палиндром!");
        }
    }

    public static void fourth() {
        System.out.print("Четвертое задание. Сумма цифр натурального числа. \n");
        Scanner in = new Scanner(System.in);
        System.out.print("Введите число: ");
        int n = in.nextInt();
        int n2 = n;
        int res = 0;
        while (n != 0) {
            res += n%10;
            n /= 10;
        }
        System.out.printf("Сумма цифр числа %d равна %d \n",n2,res);
    }

    public static void fifth() {
        System.out.print("Пятое задание. Вес человека на Луне. \n");
        System.out.print("Введите Ваш вес на Земле: ");
        Scanner in = new Scanner(System.in);
        float f_earth = in.nextFloat();
        System.out.printf("Ваш вес на Луне составляет %f ньютона.", f_earth*0.17f);

    }

    public static void sixth() {
        System.out.print("Шестое задание. Проверка на простоту. \nВведите число: ");
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        boolean is_simple = true;
        int i = 2;
        while ((i <= Math.sqrt(a)) && is_simple) {
            if (a%i == 0) {
                is_simple = false;
            }
            i++;
        }
        if (is_simple) {
            System.out.printf("Число %d являеться простым!",a);
        } else {
            System.out.printf("Число %d не являеться простым!",a);
        }
    }

    public static void seventh() {
        System.out.print("Седьмое задание. Первые n чисел Фибоначчи. \nВведите число: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int i = 2;
        long a1 = 0;
        long a2 = 0;
        long a3 = 1;
        System.out.printf("%d ", 1);
        while (i <= n) {
            a1 = a2 + a3;
            System.out.printf("%d ", a1);
            a2 = a3;
            a3 = a1;
            i++;
        }
    }

    public static void eighth() {
        System.out.print("Восьмое задание. Угадай букву от A до Z. \n");
        Random random = new Random();
        String alf = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int randomInt = random.nextInt(alf.length());
        Scanner in = new Scanner(System.in);
        boolean game_run = true;
        while (game_run) {
            System.out.print("Введите букву от A до Z: ");
            String a = in.next();
            int i = alf.indexOf(a);
            if (a.length() > 1) {
                System.out.print("Вы ввели больше одного символа! \n");
            } else if (i > randomInt) {
                System.out.print("You’re too high. \n");
            } else if (i < randomInt) {
                System.out.print("You’re too low. \n");
            } else if (i == randomInt) {
                System.out.print("Right! \n");
            }
        }
    }

    public static void ninth() {
        Scanner in = new Scanner(System.in);
        System.out.print("Девятое задание. Квадратное уравнение. \n");
        System.out.print("Введите коэффициент a: ");
        double a = in.nextDouble();
        System.out.print("Введите коэффициент b: ");
        double b = in.nextDouble();
        System.out.print("Введите коэффициент c: ");
        double c = in.nextDouble();

        double D = b*b - 4*a*c;
        System.out.printf("Дискриминант равен %.3f \n", D);

        double x1 = 0;
        double x2 = 0;
        if (D > 0) {
            x1 = (-b - Math.sqrt(D))/2*a;
            x2 = (-b + Math.sqrt(D))/2*a;
            System.out.printf("Корни уравнения: x1 = %.3f; x2 = %.3f",x1,x2);
        } else if (D == 0) {
            x1 = (-b)/2*a;
            System.out.printf("Корень уравнения: %.3f",x1);
        } else {
            System.out.printf("Корней нет!");
        }
    }

    public static void tenth() {
        System.out.print("Десятое задание. Римская запись числа. \n");
        Scanner in = new Scanner(System.in);
        System.out.print("Введите число: ");
        int n = in.nextInt();

        String[] ROMAN_NUMERALS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] DECIMAL_VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        if (n == 100) {
            System.out.printf("Римская запись числа: С");
        } else {
            var result = new StringBuilder();
            int remaining = n;
            for (int i = 0; i < ROMAN_NUMERALS.length; i++) {
                int decimalValue = DECIMAL_VALUES[i];
                String romanNumeral = ROMAN_NUMERALS[i];
                while (remaining >= decimalValue) {
                    result.append(romanNumeral);
                    remaining -= decimalValue;
                }
            }
            System.out.print(result.toString());
        }
    }
}