import java.sql.*;

public class CityQuery {

    private Connection connection;

    public CityQuery(Connection connection) {
        this.connection = connection;
    }

    public void selectAll() throws SQLException {
        String selectAll = "select * from city";
        Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(selectAll);
         while (resultSet.next()) {
             showCity(resultSet);
         }
    }

    public void insert (String cityName) throws SQLException {
        String insert = "insert into city values (default, ?)";
        PreparedStatement statement = connection.prepareStatement(insert);
        statement.setString(1, cityName);
        int rowsAffected = statement.executeUpdate();
        System.out.println("insert successfully");
    }

    public void update(String cityName, int id) throws SQLException {
        String update = "update city set name = ? where id = ?";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setString(1, cityName);
        statement.setInt(2, id);
        statement.executeUpdate();
        System.out.println("update successfully");
    }

    public void delete(int id) throws SQLException {
        String delete = "delete from city where id = ?";
        PreparedStatement statement = connection.prepareStatement(delete);
        statement.setInt(1, id);
        statement.executeUpdate();
        System.out.println("delete successfully");
    }
    public static void showCity(ResultSet resultSet) throws SQLException {
        System.out.println("Id: " + resultSet.getString("id") + "; "
                + "Name: " + resultSet.getString("name"));
    }
}
