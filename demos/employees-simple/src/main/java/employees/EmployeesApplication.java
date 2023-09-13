package employees;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class EmployeesApplication {

    public static void main(String[] args) {
        var properties = new Properties();
        try (var reader = new InputStreamReader(EmployeesMain.class.getResourceAsStream("/application.properties"), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not load properties file from classpath: application.properties", ioe);
        }

        var dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("jdbc.url"));
        dataSource.setUser(properties.getProperty("jdbc.username"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));

        var repository = new EmployeesRepository(dataSource);

        repository.saveEmployee(new Employee("John Doe", 100));
        System.out.println(repository.listEmployeeNames());

    }
}
