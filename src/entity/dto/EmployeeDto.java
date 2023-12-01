package entity.dto;

public class EmployeeDto {
    private int id;
    private String name;
    private int salary;
    private int cityId;
    private Integer managerId;

    public EmployeeDto() {
    }

    public EmployeeDto(int id, String name, int salary, int cityId, Integer managerId) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.cityId = cityId;
        this.managerId = managerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}
