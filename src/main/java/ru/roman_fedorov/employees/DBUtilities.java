package ru.roman_fedorov.employees;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.roman_fedorov.employees.pojos.EmployeesColumnDate;
import ru.roman_fedorov.employees.pojos.EmployeesColumnEnum;
import ru.roman_fedorov.employees.pojos.EmployeesColumnText;
import ru.roman_fedorov.employees.pojos.EmployeesTable;

import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBUtilities {
    public static final String POSITION_COLUMN = "position";
    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LAST_NAME_COLUMN = "last_name";
    public static final String BIRTHDAY_COLUMN = "birthday";
    private static final int EMPLOYEES_TO_GENERATE = 20;
    private static final String POSITIONS_TABLE = "Positions";
    private static final String EMPLOYEES_TABLE = "Employees";
    private JdbcTemplate database;

    public DBUtilities(JdbcTemplate database) {
        this.database = database;
    }

    public void dropTables() {
        database.execute("DROP TABLE " + POSITIONS_TABLE + " IF EXISTS");
        database.execute("DROP TABLE " + EMPLOYEES_TABLE + " IF EXISTS");
    }

    public void createTables() {
        database.execute("CREATE TABLE " + POSITIONS_TABLE + "(id SERIAL PRIMARY KEY , " +
                POSITION_COLUMN + " VARCHAR(255))");
        database.execute("CREATE TABLE " + EMPLOYEES_TABLE + "(id SERIAL PRIMARY KEY , " +
                FIRST_NAME_COLUMN + " VARCHAR(255), " + LAST_NAME_COLUMN + " VARCHAR(255), " +
                BIRTHDAY_COLUMN + " DATE, profession_id INT, " +
                "FOREIGN KEY (profession_id) REFERENCES " + POSITIONS_TABLE + "(id))");
    }

    public void insertPositions() {
        database.batchUpdate("INSERT INTO " + POSITIONS_TABLE + "(" + POSITION_COLUMN + ") VALUES (?)",
                EmployeesGenerator.positions.stream().map(p -> new Object[]{p}).collect(Collectors.toList()));
    }

    public void insertEmployees() {
        database.batchUpdate("INSERT INTO " + EMPLOYEES_TABLE + "(" + FIRST_NAME_COLUMN + ", " +
                        LAST_NAME_COLUMN + ", " + BIRTHDAY_COLUMN + ", profession_id) VALUES (?, ?, ?, ?)",
                EmployeesGenerator.generate(EMPLOYEES_TO_GENERATE).stream()
                        .map(e -> new Object[]{e.getName(), e.getSurname(),
                                Date.valueOf(e.getBirthday()), e.getPositionId()})
                        .collect(Collectors.toList()));
    }

    public List<String> selectPositions() {
        return database.query("SELECT " + POSITION_COLUMN + " FROM " + POSITIONS_TABLE,
                (rs, rowNum) -> rs.getString(POSITION_COLUMN));
    }

    public EmployeesTable selectEmployees(String name, String lastname, String date, String position) {
        return new EmployeesTable(
                Arrays.asList(
                        new EmployeesColumnText("First name", FIRST_NAME_COLUMN),
                        new EmployeesColumnText("Last name", LAST_NAME_COLUMN),
                        new EmployeesColumnDate("Birthday", BIRTHDAY_COLUMN),
                        new EmployeesColumnEnum("Position", POSITION_COLUMN, selectPositions())),
                evaluateQuery(name, lastname, date, position));
    }

    private List<Object[]> evaluateQuery(String name, String lastname, String date, String position) {
        final String baseSelectClause = "SELECT " + FIRST_NAME_COLUMN + ", " + LAST_NAME_COLUMN + ", " +
                BIRTHDAY_COLUMN + ", " + POSITION_COLUMN + " FROM " + EMPLOYEES_TABLE + " JOIN " + POSITIONS_TABLE +
                " ON " + EMPLOYEES_TABLE + ".profession_id = " + POSITIONS_TABLE + ".id";
        RowMapper<Object[]> rowMapper = (rs, rowNum) -> new Object[]{
                rs.getString(FIRST_NAME_COLUMN),
                rs.getString(LAST_NAME_COLUMN),
                rs.getDate(BIRTHDAY_COLUMN),
                rs.getString(POSITION_COLUMN)};

        if (Stream.of(name, lastname, date, position).allMatch(this::isNullOrEmpty)) {
            return database.query(baseSelectClause, rowMapper);
        } else {
            List<Object> args = new ArrayList<>();
            String dateStr = null;
            if (!isNullOrEmpty(date)) {
                dateStr = Instant.ofEpochMilli(Long.parseLong(date)).atZone(ZoneId.systemDefault())
                        .toLocalDate().toString();
            }
            String sql = baseSelectClause + " WHERE " +
                    addIfExists(name, FIRST_NAME_COLUMN, args, "LIKE") +
                    addIfExists(lastname, LAST_NAME_COLUMN, args, "LIKE") +
                    addIfExists(position, POSITION_COLUMN, args, "LIKE") +
                    addIfExists(dateStr, BIRTHDAY_COLUMN, args, "=");
            return database.query(sql, args.toArray(), rowMapper);
        }
    }

    private String addIfExists(String s, String column, List<Object> args, String condition) {
        if (isNullOrEmpty(s)) {
            return " ";
        } else {
            args.add(s);
            return (args.size() > 1 ? " AND " : " ") + " (" + column + " " + condition + " ?) ";
        }
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
