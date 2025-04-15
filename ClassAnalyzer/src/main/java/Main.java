public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java ClassAnalyzer <full-class-name>");
            return;
        }

        String className = args[0]; // Получаем первый аргумент
        ClassAnalyzer.analyzeClass(className);
    }
}
