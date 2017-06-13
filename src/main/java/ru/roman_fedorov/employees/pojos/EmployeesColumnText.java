package ru.roman_fedorov.employees.pojos;

public class EmployeesColumnText extends EmployeesColumn {
    public EmployeesColumnText(String columnName, String paramName) {
        super(columnName, paramName, EmployeesColumnType.TEXT);
    }
}
