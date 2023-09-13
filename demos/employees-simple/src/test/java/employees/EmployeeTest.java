package employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @BeforeEach
    void traceTreads() {
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    @Tag("method")
    void increaseSalary() {
        var employee = new Employee("John Doe", 100);
        employee.increaseSalary(10);
        assertEquals(110, employee.getSalary());
    }

    @Test
    @Tag("getter")
    void getName() {
        var employee = new Employee("John Doe", 100);
        assertEquals("John Doe", employee.getName());
    }
}