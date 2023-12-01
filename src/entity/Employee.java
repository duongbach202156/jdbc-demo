package entity;

public class Employee {
    private int id;
    private String name;
    private int salary;
    private City city;

    private Employee manager;

    public Employee() {
    }

    public Employee(int id, String name, int salary, City city, Employee manager) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.city = city;
        this.manager = manager;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + "; "
                + "Name: " + this.name + "; "
                + "Salary: " + this.salary + "; "
                + "City: {" + this.city.toString() + "}; "
                + "Manager: " + (this.manager == null ? null : this.manager.getName()) ;
    }
}
