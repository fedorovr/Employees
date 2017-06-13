package ru.roman_fedorov.employees.pojos;

import java.util.List;

public class EmployeesColumnEnum extends EmployeesColumn {
    private List<String> values;

    public EmployeesColumnEnum(String columnName, String paramName, List<String> values) {
        super(columnName, paramName, EmployeesColumnType.ENUM);
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }
}
