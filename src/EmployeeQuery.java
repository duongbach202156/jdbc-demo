import java.sql.*;

public class EmployeeQuery {
    private Connection connection;

    public EmployeeQuery(Connection connection) {
        this.connection = connection;
    }
    // statement -- executeQuery: select
    // return ResultSet
    public void selectAll() throws SQLException {
        String selectAll = "select * from employees";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectAll);
        while (resultSet.next()) {
            showEmployees(resultSet);
        }
    }

    // prepareStatement with parameter ? : update, insert, delete
    // return number rows affected
    public void insert (String name, int salary, int cityId, Integer managerId) throws SQLException {
        String insert = "insert into employees values (default, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insert);
        statement.setString(1, name);
        statement.setInt(3, cityId);
        statement.setInt(2, salary);
        if (managerId == null) {
            statement.setNull(4, Types.INTEGER);
        } else {
            statement.setInt(4, managerId);
        }
        statement.executeUpdate();
        System.out.println("insert successfully");
    }

    public void updateNameById(String name, int id) throws SQLException {
        String update = "update employees set name = ? where id = ?";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setString(1, name);
        statement.setInt(2, id);
        statement.executeUpdate();
        System.out.println("update successfully");
    }

    public void delete(int id) throws SQLException {
        String delete = "delete from employees where id = ?";
        PreparedStatement statement = connection.prepareStatement(delete);
        statement.setInt(1, id);
        statement.executeUpdate();
        System.out.println("delete successfully");
    }


    // Callable Statement: call procedure or function

    public int getTotalEmployeesByCityId(int city) throws SQLException {
        String function = "{? = call get_total_employees_by_city (?)}";
        CallableStatement callableStatement = connection.prepareCall(function);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.setInt(2, city);
        callableStatement.execute();
        return callableStatement.getInt(1);
    }

    public void findEmployeesById(int id) throws SQLException {
        String procedure = "{call find_employees_by_id (?)}";
        CallableStatement callableStatement = connection.prepareCall(procedure);
        callableStatement.setInt(1, id);
        ResultSet resultSet = callableStatement.executeQuery();
        while (resultSet.next()) {
            showEmployees(resultSet);
        }
    }

    public String checkSalaryStatusById(int id) throws SQLException {
        String procedure = "{call check_salary_status_by_id (?, ?)}";
        CallableStatement callableStatement = connection.prepareCall(procedure);
        callableStatement.setInt(1, id);
        callableStatement.registerOutParameter(2, Types.VARCHAR);
        callableStatement.execute();
        return callableStatement.getString(2);
    }

    // join to get full info of an employee
    public void selectAllInfo() throws SQLException {
        String selectAll = "select e.id, e.name, e.salary, c.name as city, e2.name as manager from employees e" +
                " left join city c on e.city_id = c.id" +
                " left join employees e2 on e.manager_id = e2.id;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectAll);
        while (resultSet.next()) {
            showEmployeesFullInfo(resultSet);
        }
    }

    // transaction
    public void transaction() throws SQLException {
        try {
            connection.setAutoCommit(false);
            CityQuery cityQuery = new CityQuery(connection);
            cityQuery.insert("Lao Cai");
            insert("Nguyen Van G", 4000, 10,1);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            System.out.println("Rollback data");
        }
    }



    public static void showEmployees(ResultSet resultSet) throws SQLException {
        System.out.println("Id: " + resultSet.getString("id") + "; "
                + "Name: " + resultSet.getString("name") + "; "
                + "Salary: " + resultSet.getString("salary") + "; "
                + "CityId: " + resultSet.getString("city_id") + "; "
                + "ManagerId: " + resultSet.getString("manager_id"));
    }

    public static void showEmployeesFullInfo(ResultSet resultSet) throws SQLException {
        System.out.println("Id: " + resultSet.getString("id") + "; "
                + "Name: " + resultSet.getString("name") + "; "
                + "Salary: " + resultSet.getString("salary") + "; "
                + "City: " + resultSet.getString("city") + "; "
                + "Manager: " + resultSet.getString("manager"));
    }
}
