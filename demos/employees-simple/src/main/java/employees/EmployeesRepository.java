package employees;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class EmployeesRepository {

    private JdbcTemplate jdbcTemplate;

    public EmployeesRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveEmployee(Employee employee) {
        jdbcTemplate.update("insert into employees(emp_name, salary) values (?, ?)",
                employee.getName(), employee.getSalary());
    }

    public List<String> listEmployeeNames() {
        return jdbcTemplate.query("select emp_name from employees order by emp_name",
                (rs, rowNum) -> rs.getString("emp_name"));
    }
}
