import java.sql.*;

public class JdbcConnector {

    private static final String url = "jdbc:mysql://localhost:3306/employees";
    private static final String userName = "root";
    private static final String password = "root";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
