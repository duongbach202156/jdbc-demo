package service.impl;

import entity.Employee;
import entity.dto.EmployeeDto;
import entity.dto.EmployeeMapper;
import repository.EmployeeRepository;
import repository.impl.EmployeeQuery;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public void insertMultiple(List<EmployeeDto> employeeList) {
        List<Employee> employees = new ArrayList<>();
        for (EmployeeDto e: employeeList) {
            employees.add(employeeMapper.toEntity(e));
        }
        employeeRepository.insertMultiple(employees);
    }

    public void insert(EmployeeDto employeeDto) {
        employeeRepository.insert(employeeMapper.toEntity(employeeDto));
    }

    public void updateNameById(String name, int id) {
        employeeRepository.updateNameById(name, id);
    }

    public void delete(int id) {
        employeeRepository.delete(id);
    }
}
