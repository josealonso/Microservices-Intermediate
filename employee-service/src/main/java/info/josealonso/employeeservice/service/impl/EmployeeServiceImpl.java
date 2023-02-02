package info.josealonso.employeeservice.service.impl;

import info.josealonso.employeeservice.dto.APIResponseDto;
import info.josealonso.employeeservice.dto.DepartmentDto;
import info.josealonso.employeeservice.dto.EmployeeDto;
import info.josealonso.employeeservice.entity.Employee;
import info.josealonso.employeeservice.repository.EmployeeRepository;
import info.josealonso.employeeservice.service.APIClient;
import info.josealonso.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String DEPARTMENT_URL = "http:localhost:8080/api/departments/";
    private final EmployeeRepository employeeRepository;
    // private final RestTemplate restTemplate;
    // private final WebClient webClient;
    private final APIClient apiClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = convertEmployeeDtoToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertEmployeeToEmployeeDto(savedEmployee);
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

      /*  ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity(DEPARTMENT_URL + employee.getDepartmentCode(),
                DepartmentDto.class);
        DepartmentDto departmentDto = responseEntity.getBody(); */

      /*  DepartmentDto departmentDto = webClient.get()
                .uri(DEPARTMENT_URL + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();  */

        DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

        EmployeeDto employeeDto = convertEmployeeToEmployeeDto(employee);

        APIResponseDto apiResponseDto = APIResponseDto.builder()
                .employee(employeeDto)
                .department(departmentDto)
                .build();

        return apiResponseDto;
    }

    private Employee convertEmployeeDtoToEmployee(EmployeeDto employeeDto) {
        return Employee.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .departmentCode(employeeDto.getDepartmentCode())
                .build();
    }

    private EmployeeDto convertEmployeeToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .departmentCode(employee.getDepartmentCode())
                .build();
    }
}
