package org.example;

public class Employee {
    private String fullName;
    private Integer age;
    private String department;
    private Double salary;

    public Employee(String fullName, Integer age, String department, Double salary) {
        this.fullName = fullName;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String value) {
        this.fullName = value;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer value) {
        this.age = value;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String value) {
        this.department = value;
    }

    public Double getSalary() {
        return this.salary;
    }

    public void setSalary(Double value) {
        this.salary = value;
    }
}
