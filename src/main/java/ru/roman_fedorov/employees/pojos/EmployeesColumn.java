package ru.roman_fedorov.employees.pojos;

public abstract class EmployeesColumn {
    private String columnName;
    private String paramName;
    private EmployeesColumnType columnType;

    public EmployeesColumn(String columnName, String paramName, EmployeesColumnType columnType) {
        this.columnName = columnName;
        this.paramName = paramName;
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getParamName() {
        return paramName;
    }

    public EmployeesColumnType getColumnType() {
        return columnType;
    }
}
