package info.josealonso.employeeservice.service.impl;

import info.josealonso.employeeservice.dto.APIResponseDto;
import info.josealonso.employeeservice.dto.DepartmentDto;
import info.josealonso.employeeservice.dto.EmployeeDto;
import info.josealonso.employeeservice.entity.Employee;
import info.josealonso.employeeservice.mapper.EmployeeMapper;
import info.josealonso.employeeservice.repository.EmployeeRepository;
import info.josealonso.employeeservice.service.APIClient;
import info.josealonso.employeeservice.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String DEPARTMENT_URL = "http:localhost:8080/api/departments/";

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    // private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final APIClient apiClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

//    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        LOGGER.info("inside getEmployeeById method");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

      /*  ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity(DEPARTMENT_URL + employee.getDepartmentCode(),
                DepartmentDto.class);
        DepartmentDto departmentDto = responseEntity.getBody(); */

        DepartmentDto departmentDto = webClient.get()
                .uri(DEPARTMENT_URL + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

//        DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);

        APIResponseDto apiResponseDto = APIResponseDto.builder()
                .employee(employeeDto)
                .department(departmentDto)
                .build();
        return apiResponseDto;
    }

    public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
        LOGGER.info("inside getDefaultDepartment method");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        DepartmentDto departmentDto = DepartmentDto.builder()
                .departmentName("R&D Department")
                .departmentCode("RD001")
                .departmentDescription("Research and Development Department")
                .build();

        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);

        APIResponseDto apiResponseDto = APIResponseDto.builder()
                .employee(employeeDto)
                .department(departmentDto)
                .build();
        return apiResponseDto;
    }

}
