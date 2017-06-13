package ru.roman_fedorov.employees.pojos;

import java.util.List;

public class EmployeesTable {
    private List<EmployeesColumn> columns;
    private List<Object[]> employeesData;

    public EmployeesTable(List<EmployeesColumn> columns, List<Object[]> employeesData) {
        this.columns = columns;
        this.employeesData = employeesData;
    }

    public List<EmployeesColumn> getColumns() {
        return columns;
    }

    public List<Object[]> getEmployeesData() {
        return employeesData;
    }
}
