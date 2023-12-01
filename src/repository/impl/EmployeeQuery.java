package repository.impl;

import entity.City;
import entity.Employee;
import entity.dto.EmployeeDto;
import repository.EmployeeRepository;
import repository.impl.CityQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeQuery implements EmployeeRepository {
    private Connection connection;

    public EmployeeQuery(Connection connection) {
        this.connection = connection;
    }
    // statement -- executeQuery: select
    // return ResultSet
    @Override
    public List<Employee> findAll(){
        String selectAll = "select * from employees";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAll);
            List<Employee> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(parseEmployee(resultSet));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertMultiple(List<Employee> employeeList) {
        String insert = "insert into employees values (default, ?, ?, ?, ? )";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            for (Employee employee : employeeList) {
                preparedStatement.setString(1, employee.getName());
                preparedStatement.setInt(2, employee.getSalary());
                preparedStatement.setInt(3, employee.getCity().getId());
                if (employee.getManager() == null) {
                    preparedStatement.setNull(4, Types.INTEGER);
                } else {
                    preparedStatement.setInt(4, employee.getManager().getId());
                }
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
                connection.commit();
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
    }

    // prepareStatement with parameter ? : update, insert, delete
    // return number rows affected
    @Override
    public void insert (Employee employee)  {
        String insert = "insert into employees values (default, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insert);
            statement.setString(1, employee.getName());
            statement.setInt(2, employee.getSalary());
            statement.setInt(3, employee.getCity().getId());
            if (employee.getManager() == null) {
                statement.setNull(4, Types.INTEGER);
            } else {
                statement.setInt(4, employee.getManager().getId());
            }
            statement.executeUpdate();
            System.out.println("insert successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void updateNameById(String name, int id)  {
        String update = "update employees set name = ? where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("update successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String delete = "delete from employees where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(delete);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("delete successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Callable Statement: call procedure or function
    @Override
    public Employee findEmployeeById(int id) {
        String procedure = "{call find_employees_by_id (?)}";
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(procedure);
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
                return parseEmployee(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int getTotalEmployeesByCityId(int city) {
        String function = "{? = call get_total_employees_by_city (?)}";
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(function);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setInt(2, city);
            callableStatement.execute();
            return callableStatement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public String checkSalaryStatusById(int id)  {
        String procedure = "{call check_salary_status_by_id (?, ?)}";
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(procedure);
            callableStatement.setInt(1, id);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.execute();
            return callableStatement.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // join to get full info of an employee
    public void selectAllInfo() throws SQLException {
        String selectAll = "select e.*, c.name as city, e2.name as manager from employees e" +
                " left join city c on e.city_id = c.id" +
                " left join employees e2 on e.manager_id = e2.id;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectAll);
        while (resultSet.next()) {
            showEmployeesFullInfo(resultSet);
        }
    }

    // transaction
    public void transactionDemo() throws SQLException {
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String insert1 = "insert into employees values (default, 'Hello1', 3000, 1, null)";
            statement.executeUpdate(insert1);
            System.out.println("insert1 success");
            String insert2 = "insert into employees values (default, 'Hello2', 3000, 100, null)";
            statement.executeUpdate(insert2);
            System.out.println("insert2 success");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            System.out.println("Rollback data");
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public Employee parseEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setName(resultSet.getString("name"));
        employee.setSalary(resultSet.getInt("salary"));
        CityQuery cityQuery = new CityQuery(connection);
        City city = cityQuery.findCityById(resultSet.getInt("city_id"));
        employee.setCity(city);
        Employee manager = this.findEmployeeById(resultSet.getInt("manager_id"));
        employee.setManager(manager);
        return employee;
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
