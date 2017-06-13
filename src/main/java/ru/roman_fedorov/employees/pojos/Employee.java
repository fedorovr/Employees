package ru.roman_fedorov.employees.pojos;

import java.time.LocalDate;

public class Employee {
    private final String name;
    private final String surname;
    private final int positionId;
    private final LocalDate birthday;

    public Employee(String name, String surname, int positionId, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.positionId = positionId;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getPositionId() {
        return positionId;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
