package employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeesRepositoryIT {

    EmployeesRepository employeesRepository;

    @BeforeEach
    void init() {
        Properties properties = new Properties();
        try (var reader = new InputStreamReader(EmployeesMain.class.getResourceAsStream("/application-test.properties"), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not load properties file from classpath: application-test.properties", ioe);
        }

        var url = Optional.ofNullable(System.getProperty("jdbc.url"))
                .orElse(properties.getProperty("jdbc.url"));
        var user = Optional.ofNullable(System.getProperty("jdbc.user"))
                .orElse(properties.getProperty("jdbc.user"));
        var password = Optional.ofNullable(System.getProperty("jdbc.password"))
                .orElse(properties.getProperty("jdbc.password"));

        var dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        employeesRepository = new EmployeesRepository(dataSource);

        var jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("delete from employees");
    }

    @Test
    void create_then_list() {
        employeesRepository.saveEmployee(new Employee("John Doe", 100));
        var names = employeesRepository.listEmployeeNames();
        assertEquals(List.of("John Doe"), names);
    }
}
