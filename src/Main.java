import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        CityQuery cityQuery = new CityQuery(connection);
        EmployeeQuery employeeQuery = new EmployeeQuery(connection);
//        cityQuery.insert("Hai Phong");
//        cityQuery.update("Cao Bang", 6);
//        cityQuery.delete(6);
//        cityQuery.selectAll();
//        System.out.println("Total: " + employeeQuery.getTotalEmployeesByCityId(3));
//        cityQuery.selectAll();
//        employeeQuery.selectAll();
//        employeeQuery.transaction();
//        System.out.println("After transaction");
//        cityQuery.selectAll();
//        employeeQuery.selectAll();
//        employeeQuery.findEmployeesById(1);
//        String status = employeeQuery.checkSalaryStatusById(1);
//        System.out.println(status);
//        employeeQuery.insert("v", 2000, 1,null);
    }

}