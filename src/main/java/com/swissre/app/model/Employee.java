package com.swissre.app.model;

import java.util.*;

public final class Employee {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final double salary;
    private final Optional<Integer> managerId;

    private final List<Employee> directReports = new ArrayList<>();

    public Employee(int id, String firstName, String lastName, double salary, Integer managerId) {
        this.id = id;
        this.firstName = Objects.requireNonNull(firstName);
        this.lastName = Objects.requireNonNull(lastName);
        this.salary = salary;
        this.managerId = Optional.ofNullable(managerId);
    }
    public int getId() {
        return id;
    }
    public double getSalary() {
        return salary;
    }
    public Optional<Integer> getManagerId() {
        return managerId;
    }
    public List<Employee> getDirectReports() {
        return Collections.unmodifiableList(directReports);
    }
    public void addDirectReport(Employee e) {
        directReports.add(e);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + id + ")";
    }
}