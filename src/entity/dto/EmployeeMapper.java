package entity.dto;

import entity.Employee;
import repository.CityRepository;
import repository.EmployeeRepository;

public class EmployeeMapper {

    private CityRepository cityRepository;

    private EmployeeRepository employeeRepository;

    public EmployeeMapper(CityRepository cityRepository, EmployeeRepository employeeRepository) {
        this.cityRepository = cityRepository;
        this.employeeRepository = employeeRepository;
    }

    public Employee toEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setSalary(employeeDto.getSalary());
        employee.setCity(cityRepository.findCityById(employeeDto.getCityId()));
        employee.setManager(employeeRepository.findEmployeeById(employeeDto.getManagerId()));
        return employee;
    }
}
