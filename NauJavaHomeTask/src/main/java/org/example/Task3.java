package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Task3 {
    private ArrayList<Employee> employees;

    public Task3() {
        var employee1 = new Employee("Иван Иванов", 25, "IT", 20.0);
        var employee2 = new Employee("Иван Васильев", 27, "Финансы", 20.0);
        var employee3 = new Employee("Дмитрий Петров", 28, "Клиенты", 20.0);
        var employee4 = new Employee("Данил Смирнов", 23, "IT", 20.0);
        var employee5 = new Employee("Антон Попов", 35, "Юридический", 20.0);
        employees = new ArrayList<>(Arrays.asList(employee1, employee2, employee3, employee4, employee5));
    }

    public ArrayList<String> GetDepartmentByName() {
        return employees.stream()
                .map(emp -> emp.getFullName().toString() + " - " + emp.getDepartment().toString())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
