import java.sql.*;

public class JdbcConnector {

    private static final String url = "jdbc:mysql://localhost:3306/employees";
    private static final String userName = "root";
    private static final String password = "root";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
