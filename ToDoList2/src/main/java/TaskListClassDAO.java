import java.sql.SQLException;
import java.util.List;

public interface TaskListClassDAO {

    void add(TaskListClass taskList) throws SQLException;

    List<TaskListClass> getAll() throws SQLException;

    TaskListClass getById(Long id) throws SQLException;

    void update(TaskListClass taskList) throws SQLException;

    void delete(TaskListClass taskList) throws SQLException;
}
