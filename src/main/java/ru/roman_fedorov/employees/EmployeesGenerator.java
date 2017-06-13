package ru.roman_fedorov.employees;

import ru.roman_fedorov.employees.pojos.Employee;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmployeesGenerator {
    public static List<String> positions = Arrays.asList("Junior Engineer", "Middle Engineer", "Senior Engineer",
            "QA Engineer", "Manager", "Scrum Master");
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    private static List<String> names = Arrays.asList("Ivan", "Petr", "Nikita", "Alex");
    private static List<String> surnames = Arrays.asList("Ivanov", "Petrov", "Nikitin", "Alexandrov");

    private static <E> E randomElement(List<E> list) {
        return list.get(random.nextInt(list.size()));
    }

    private static Employee generateEmployee() {
        return new Employee(randomElement(names), randomElement(surnames),
                random.nextInt(1, positions.size() + 1), randomDate());
    }

    private static LocalDate randomDate() {
        long minDay = LocalDate.of(1950, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(1995, 1, 1).toEpochDay();
        return LocalDate.ofEpochDay(random.nextLong(minDay, maxDay));
    }

    public static List<Employee> generate(int count) {
        return IntStream.range(0, count).mapToObj(_i -> generateEmployee()).collect(Collectors.toList());
    }
}
