import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    ToDoList toDoList;
    private static Scanner scanner = new Scanner(System.in);
    private final Map<String, Method> commands = new HashMap<>();

//    public void registerCommand(Method method) {
//        commands.put(method.getAnnotation(CommandInfo.class).name(), method);
//    }
//
//    public void loadCommands() {
//        try {
//
//            registerCommand(ToDoList.class.getMethod("add"));
//            registerCommand(ToDoList.class.getMethod("done"));
//            registerCommand(ToDoList.class.getMethod("show"));
//            registerCommand(ToDoList.class.getMethod("delete"));
//
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }

    public App() {
        this.toDoList = new ToDoList();
    }

    public void run() {
        System.out.println("Добро пожаловать в ToDoList. Введите «help» для доступных команд.");
//        loadCommands();

        boolean running = true;
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;


            String[] parts = input.split("\\s+");


            String cmdName = parts[0].toString();
//            Method cmd = commands.get(cmdName);

            String args;
            if (parts.length > 1) {
                args = parts[1].toString();
            } else {
                args = "";
            }



            switch (cmdName) {
                case "add":
                    this.toDoList.add(String.valueOf(args));
                    break;
                case "show":
                    this.toDoList.show();
                    break;
                case "done":
                    this.toDoList.done(Long.parseLong(args.toString()));
                    break;
                case "delete":
                    this.toDoList.delete(Long.parseLong(args.toString()));
                    break;
                case "help":
                    help();
                    break;
                case "exit":
                    exit();
                    break;
            }
        }
    }

    public void help() {
        System.out.println("add - добавить задачу");
        System.out.println("show - показать все задачи");
        System.out.println("done - отметить задачу выполненной");
        System.out.println("delete - удалить задачу");
        System.out.println("exit - выход из программы");
    }

    public void exit() {
        try {
            if (this.toDoList.taskListService.connection != null) {
                this.toDoList.taskListService.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.print("Goodbye!");
            System.exit(0);
        }
    }
}
