package info.josealonso.employeeservice.service.impl;

import info.josealonso.employeeservice.dto.EmployeeDto;
import info.josealonso.employeeservice.entity.Employee;
import info.josealonso.employeeservice.repository.EmployeeRepository;
import info.josealonso.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = convertEmployeeDtoToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertEmployeeToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return convertEmployeeToEmployeeDto(employee);
    }

    private Employee convertEmployeeDtoToEmployee(EmployeeDto employeeDto) {
        return Employee.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .build();
    }

    private EmployeeDto convertEmployeeToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
    }
}
