import entity.City;
import entity.Employee;
import entity.dto.EmployeeDto;
import entity.dto.EmployeeMapper;
import repository.CityRepository;
import repository.EmployeeRepository;
import repository.impl.CityQuery;
import repository.impl.EmployeeQuery;
import service.impl.EmployeeService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        CityRepository cityRepository = new CityQuery(connection);
        EmployeeRepository employeeRepository = new EmployeeQuery(connection);
        EmployeeMapper employeeMapper = new EmployeeMapper(cityRepository, employeeRepository);
        EmployeeService employeeService = new EmployeeService(employeeRepository, employeeMapper);
//        transactionDemo();
//        insertEmployee(employeeService);
//        deleteEmployee(employeeService);
//        insertMultipleEmployee(employeeService);
//        employeeService.getAll().forEach(System.out::println);
//        getTotalEmployeeByCityId();
//        checkSalary();
        CityQuery cityQuery = new CityQuery(connection);
        cityQuery.resultSetUpdatable();
        connection.close();
    }

    public static void insertEmployee(EmployeeService employeeService) {
        employeeService.insert(new EmployeeDto(1, "Bach", 3000, 1, 2));
    }

    public static void getTotalEmployeeByCityId() {
        Connection connection = JdbcConnector.getConnection();
        EmployeeQuery employeeQuery = new EmployeeQuery(connection);
        System.out.println("Status: " + employeeQuery.getTotalEmployeesByCityId(1));
    }
    public static void checkSalary() {
        Connection connection = JdbcConnector.getConnection();
        EmployeeQuery employeeQuery = new EmployeeQuery(connection);
        System.out.println("Total: " + employeeQuery.checkSalaryStatusById(1));
    }
    public static void insertMultipleEmployee(EmployeeService employeeService) {
        List<EmployeeDto> employeeList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            employeeList.add(new EmployeeDto(0, "Emp" + i, 3000 + i * 100, i, 2));
        }
        employeeService.insertMultiple(employeeList);
    }
    public static void insertMultipleCity() {
        Connection connection = JdbcConnector.getConnection();
        CityQuery cityQuery = new CityQuery(connection);
        List<City> cityList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            cityList.add(new City(0, "city" + i));
        }
        cityQuery.insertMultiple(cityList);
    }

    public static void transactionDemo() throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        EmployeeQuery employeeQuery = new EmployeeQuery(connection);
        System.out.println("Before");
        employeeQuery.findAll().forEach(System.out::println);
        employeeQuery.transactionDemo();
        System.out.println("After");
        employeeQuery.findAll().forEach(System.out::println);
    }
}