package ru.roman_fedorov.employees.pojos;

public class EmployeesColumnDate extends EmployeesColumn {
    public EmployeesColumnDate(String columnName, String paramName) {
        super(columnName, paramName, EmployeesColumnType.DATE);
    }
}
