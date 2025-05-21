import org.checkerframework.checker.units.qual.N;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.runtime.SwitchBootstraps;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBService {
    ConnectionH2 connectionH2 = new ConnectionH2();
    Connection connection = connectionH2.getConnection();
    public DBService() {

        String sqlTableUsers = """
                CREATE TABLE IF NOT EXISTS users (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    chat_id BIGINT,
                    status TINYINT DEFAULT 0,
                    age INT DEFAULT 0,
                    sex TEXT DEFAULT 'male',
                    height FLOAT DEFAULT 0,
                    weight FLOAT DEFAULT 0,
                    activity_factor INT DEFAULT 1
                )
                """;

        String sqlTableNotes = """
                CREATE TABLE IF NOT EXISTS notes (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    user_id INT,
                    kal INT,
                    text TEXT,
                    date DATE DEFAULT CURRENT_DATE
                )
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlTableUsers);
            statement.execute(sqlTableNotes);
            System.out.println("Tables created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO users (chat_id, status) VALUES (?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, user.getChatID());
            preparedStatement.setInt(2, user.getStatus());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User add!"); // логгирование
                System.out.println("Добавлено: " + rowsUpdated);
            } else {
                // логгирование
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateUserData(User user) {
        PreparedStatement preparedStatement = null;

        String sql = "UPDATE users SET status=?, sex=?, height=?, weight=?, activity_factor=?, age=? WHERE chat_id=?";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getStatus());
            preparedStatement.setString(2, user.getSex());
            preparedStatement.setFloat(3, user.getHeight());
            preparedStatement.setFloat(4, user.getWeight());
            preparedStatement.setFloat(5, user.getActivity_factor());
            preparedStatement.setInt(6, user.getAge());
            preparedStatement.setLong(7, user.getChatID());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User update!");
                // логгирование
            } else {
                // логгирование
            }

            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserByChatID(Long chat_id) {
        Statement statement = null;

        User user = new User();

        String sql = "SELECT * FROM users WHERE chat_id="+chat_id;

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setChatID(resultSet.getLong("chat_id"));
                user.setStatus(resultSet.getInt("status"));
                user.setSex(resultSet.getString("sex"));
                user.setHeight(resultSet.getFloat("height"));
                user.setWeight(resultSet.getFloat("weight"));
                user.setActivity_factor(resultSet.getInt("activity_factor"));
                user.setAge(resultSet.getInt("age"));
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean checkUserInDBbyChatID(Long chat_id) {
        Statement statement = null;

        Boolean result = false;

        String sql = "SELECT * FROM users WHERE chat_id="+chat_id;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                result = true;
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return result;
    }

    public void addNote(Note note) {
        PreparedStatement preparedStatement = null;

        //String sql = "INSERT INTO notes (user_id, date) VALUES (?,?)";
        String sql = "INSERT INTO notes (user_id) VALUES (?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, note.getUser_id());
            //preparedStatement.setDate(2, note.getDate());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Note add!");
                // логгирование
            } else {
                // логгирование
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNoteData(Note note) {
        PreparedStatement preparedStatement = null;

        String sql = "UPDATE notes SET kal=?, text=? WHERE id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, note.getKal());
            preparedStatement.setString(2, note.getText());
            preparedStatement.setInt(3, note.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Note update!");
            } else {
                // - логгирование
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // - логгирование
        }
    }

    public Integer checkMaxIDinNotes(Integer user_id) {
        int result = 0;

        Statement statement = null;

        String sql = "SELECT * FROM notes WHERE user_id= "+user_id+" ORDER BY id DESC LIMIT 1";

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                result = resultSet.getInt("id");
            }
            statement.close();

        } catch (SQLException e){
            e.printStackTrace();
            // - логгирование
        }

        return result;
    }

    public Note getNoteByID(Integer id) {
        Note note = new Note();

        Statement statement = null;

        String sql = "SELECT * FROM notes WHERE id="+id;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                note.setId(resultSet.getInt("id"));
                note.setUser_id(resultSet.getInt("user_id"));
                note.setKal(resultSet.getInt("kal"));
                note.setText(resultSet.getString("text"));
                note.setDate(resultSet.getDate("date"));
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return note;
    }

    public List<Note> getNotesByDateWeek(Integer user_id) {
        List<Note> notes = new ArrayList<>();
        Statement statement = null;

        String sql = "SELECT * FROM notes WHERE (user_id="+user_id+") AND (date >= DATEADD(DAY, -7, CURRENT_DATE))";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setUser_id(resultSet.getInt("user_id"));
                note.setKal(resultSet.getInt("kal"));
                note.setText(resultSet.getString("text"));
                note.setDate(resultSet.getDate("date"));

                notes.add(note);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notes;
    }

    public List<Note> getNotesByDateDay(Integer user_id) {
        List<Note> notes = new ArrayList<>();
        Statement statement = null;

        String sql = "SELECT * FROM notes WHERE (user_id="+user_id+") AND (date >= DATEADD(DAY, -1, CURRENT_DATE))";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setUser_id(resultSet.getInt("user_id"));
                note.setKal(resultSet.getInt("kal"));
                note.setText(resultSet.getString("text"));
                note.setDate(resultSet.getDate("date"));

                notes.add(note);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notes;
    }

    public List<Note> getAllNotesByUserID(Integer user_id) {
        List<Note> notes = new ArrayList<>();

        Statement statement = null;

        String sql = "SELECT * FROM notes WHERE user_id="+user_id;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setUser_id(resultSet.getInt("user_id"));
                note.setKal(resultSet.getInt("kal"));
                note.setText(resultSet.getString("text"));
                note.setDate(resultSet.getDate("date"));

                notes.add(note);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notes;
    }

    public void deleteNoteById(Integer NoteId, Integer user_id) {

        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM notes WHERE id=? AND user_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, NoteId);
            preparedStatement.setInt(2, user_id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllNotesByUserID(Integer user_id) {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM notes WHERE user_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, user_id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
