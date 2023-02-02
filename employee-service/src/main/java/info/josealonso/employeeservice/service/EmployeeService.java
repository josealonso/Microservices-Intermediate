package info.josealonso.employeeservice.service;

import info.josealonso.employeeservice.dto.APIResponseDto;
import info.josealonso.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long employeeId);
}
