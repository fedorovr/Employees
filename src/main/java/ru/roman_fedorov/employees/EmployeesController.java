package ru.roman_fedorov.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.roman_fedorov.employees.pojos.EmployeesTable;

@Controller
public class EmployeesController {
    private final DBUtilities dbUtilities;

    @Autowired
    public EmployeesController(DBUtilities dbUtilities) {
        this.dbUtilities = dbUtilities;
    }

    @RequestMapping(value = "/employees")
    public
    @ResponseBody
    EmployeesTable employees(
            @RequestParam(value = DBUtilities.FIRST_NAME_COLUMN, required = false) String name,
            @RequestParam(value = DBUtilities.LAST_NAME_COLUMN, required = false) String lastname,
            @RequestParam(value = DBUtilities.BIRTHDAY_COLUMN, required = false) String date,
            @RequestParam(value = DBUtilities.POSITION_COLUMN, required = false) String position
    ) {
        return dbUtilities.selectEmployees(name, lastname, date, position);
    }
}
