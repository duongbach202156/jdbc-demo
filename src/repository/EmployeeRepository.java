package repository;

import entity.City;
import entity.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> findAll();

    Employee findEmployeeById(int id);

    void insertMultiple(List<Employee> employeeList);

    void insert(Employee employee);

    void updateNameById(String name, int id);

    void delete(int id);
}
