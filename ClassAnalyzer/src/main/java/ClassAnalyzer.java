import java.lang.reflect.*;

public class ClassAnalyzer {
    public static void analyzeClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            printClassInfo(clazz);
            printFieldsInfo(clazz);
            printMethodsInfo(clazz);
        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден: " + className);
        }
    }

    private static void printClassInfo(Class<?> clazz) {
        System.out.println("Класс: " + clazz.getName());
        System.out.println("Модификаторы: " + Modifier.toString(clazz.getModifiers()));
        System.out.println();
    }

    private static void printFieldsInfo(Class<?> clazz) {
        System.out.println("Поля:");
        Field[] fields = clazz.getDeclaredFields();

        if (fields.length == 0) {
            System.out.println("  Нет полей.");
            System.out.println();
            return;
        }

        for (Field field : fields) {
            System.out.println("  Название: " + field.getName());
            System.out.println("  Тип: " + field.getType().getName());
            System.out.println("  Модификаторы: " + Modifier.toString(field.getModifiers()));
            System.out.println();
        }
    }

    private static void printMethodsInfo(Class<?> clazz) {
        System.out.println("Методы:");
        Method[] methods = clazz.getDeclaredMethods();

        if (methods.length == 0) {
            System.out.println("  Нет методов.");
            return;
        }

        for (Method method : methods) {
            System.out.println("  Название: " + method.getName());
            System.out.println("  Возвращаемый тип: " + method.getReturnType().getName());
            System.out.println("  Модификаторы: " + Modifier.toString(method.getModifiers()));

            System.out.println("  Параметры:");
            Parameter[] params = method.getParameters();
            if (params.length == 0) {
                System.out.println("    Нет параметров.");
            } else {
                for (Parameter param : params) {
                    System.out.println("    " + param.getType().getName() + " " + param.getName());
                }
            }
            System.out.println();
        }
    }
}
