import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionH2 implements MyConnection {
    private static final String URL = "jdbc:h2:file:./databases/NutriScan";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.print("Connection OK\n");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Connection ERROR");
        }
        return connection;
    }
}
