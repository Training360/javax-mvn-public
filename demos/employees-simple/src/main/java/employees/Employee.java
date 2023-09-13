package employees;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

    private String name;

    private int salary;

    public void increaseSalary(int amount) {
        salary += amount;
    }
}
