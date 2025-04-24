import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskListService extends ConnectionManager implements TaskListClassDAO{

    Connection connection = getConnection();

    public TaskListService() {
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
            System.out.println("Таблица задач создана!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(TaskListClass taskList) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO tasks (name) VALUES (?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, taskList.getName());
            preparedStatement.executeUpdate();
            System.out.print("Таблица обновлена!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Override
    public List<TaskListClass> getAll() throws SQLException {
        List<TaskListClass> taskList = new ArrayList<>();

        String sql = "SELECT * FROM tasks ORDER BY created_at";

        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                TaskListClass taskListClass = new TaskListClass();
                taskListClass.setId(resultSet.getLong("id"));
                taskListClass.setName(resultSet.getString("name"));
                taskListClass.setStatus(resultSet.getString("status"));
                taskListClass.setCreated_at(resultSet.getTimestamp("created_at"));
                taskList.add(taskListClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return taskList;
    }

    @Override
    public TaskListClass getById(Long id) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "SELECT * FROM tasks";

        TaskListClass taskListClass = new TaskListClass();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            taskListClass.setId(resultSet.getLong("id"));
            taskListClass.setName(resultSet.getString("name"));
            taskListClass.setStatus(resultSet.getString("status"));
            taskListClass.setCreated_at(resultSet.getTimestamp("created_at"));

            preparedStatement.executeUpdate();
            System.out.print("Таблица обновлена!\n");
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return taskListClass;
    }

    @Override
    public void update(TaskListClass taskList) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "UPDATE tasks SET status=? WHERE id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, taskList.getStatus());
            preparedStatement.setLong(2, taskList.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.print("Таблица обновлена!\n");
            } else {
                System.out.print("Задача с указанным ID не найдена!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Override
    public void delete(TaskListClass taskList) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM tasks WHERE id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, taskList.getId());

            preparedStatement.executeUpdate();

            System.out.print("Таблица обновлена!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
