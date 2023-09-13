package employees;

import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {

    EmployeePresentationModel toPresentationModel(Employee employee);
}
