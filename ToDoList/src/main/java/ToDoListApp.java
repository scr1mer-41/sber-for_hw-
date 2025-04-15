import java.sql.*;
import java.util.Scanner;

public class ToDoListApp {
    private static final String URL = "jdbc:h2:file:./database/tododb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createTable(connection);
            String sql = "SELECT * FROM tasks ORDER BY created_at";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            boolean running = true;

            while (running) {
                System.out.println("\nДоступные команды:");
                System.out.println("add - добавить задачу");
                System.out.println("show - показать все задачи");
                System.out.println("done - отметить задачу выполненной");
                System.out.println("delete - удалить задачу");
                System.out.println("exit - выход из программы");
                System.out.print("Введите команду: ");

                String command = scanner.nextLine().trim().toLowerCase();

                switch (command) {
                    case "add":
                        addTask(connection);
                        break;
                    case "show":
                        showTasks(connection);
                        break;
                    case "done":
                        markTaskAsDone(connection);
                        break;
                    case "delete":
                        deleteTask(connection);
                        break;
                    case "exit":
                        running = false;
                        break;
                    default:
                        System.out.println("Неизвестная команда!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Программа завершена.");
    }

    private static boolean CheckforEmptiness(Connection connection)  throws SQLException {
        boolean isEmpty = true;

        String sql = "SELECT * FROM tasks ORDER BY created_at";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            isEmpty = false;
        }

        return isEmpty;
    }

    private static void createTable(Connection connection) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS tasks (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(100) NOT NULL,
                status VARCHAR(20) DEFAULT 'Not done',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Таблица задач создана!");
        }
    }

    private static void addTask(Connection connection) throws SQLException {
        System.out.print("Введите описание задачи: ");
        String taskName = scanner.nextLine().trim();

        if (taskName.isEmpty()) {
            System.out.println("Описание задачи не может быть пустым!");
            return;
        }

        String sql = "INSERT INTO tasks (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, taskName);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Задача добавлена!");
            }
        }
    }

    private static void showTasks(Connection connection) throws SQLException {
        String sql = "SELECT * FROM tasks ORDER BY created_at";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\nСписок задач:");
            boolean hasTasks = false;

            while (resultSet.next()) {
                hasTasks = true;
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String status = resultSet.getString("status");
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                System.out.printf("ID: %d | %s | %s | Создана: %s%n",
                        id, name, status, createdAt.toString());
            }

            if (CheckforEmptiness(connection)) {
                System.out.println("Нет задач в списке!");
            }
        }
    }

    private static void markTaskAsDone(Connection connection) throws SQLException {
        showTasks(connection);
        if (!CheckforEmptiness(connection)) {
            System.out.print("Введите ID задачи для отметки выполненной: ");
            try {
                int taskId = Integer.parseInt(scanner.nextLine().trim());
                String sql = "UPDATE tasks SET status = 'DONE' WHERE id = ?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, taskId);
                    int rowsUpdated = statement.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Задача отмечена выполненной!");
                    } else {
                        System.out.println("Задача с указанным ID не найдена!");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат ID!");
            }
        }
    }

    private static void deleteTask(Connection connection) throws SQLException {
        showTasks(connection);
        System.out.print("Введите ID задачи для удаления: ");

        try {
            int taskId = Integer.parseInt(scanner.nextLine().trim());
            String sql = "DELETE FROM tasks WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, taskId);
                int rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Задача удалена!");
                } else {
                    System.out.println("Задача с указанным ID не найдена!");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID!");
        }
    }
}