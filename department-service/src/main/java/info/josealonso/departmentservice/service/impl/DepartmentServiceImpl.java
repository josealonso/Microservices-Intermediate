package info.josealonso.departmentservice.service.impl;

import info.josealonso.departmentservice.dto.DepartmentDto;
import info.josealonso.departmentservice.entity.Department;
import info.josealonso.departmentservice.repository.DepartmentRepository;
import info.josealonso.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = convertDepartmentDtoToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);
        return convertDepartmentToDepartmentDto(savedDepartment);
    }

    private Department convertDepartmentDtoToDepartment(DepartmentDto departmentDto) {
        return Department.builder()
                .id(departmentDto.getId())
                .departmentCode(departmentDto.getDepartmentCode())
                .departmentName(departmentDto.getDepartmentName())
                .departmentDescription(departmentDto.getDepartmentDescription())
                .build();
    }

    private DepartmentDto convertDepartmentToDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .departmentCode(department.getDepartmentCode())
                .departmentName(department.getDepartmentName())
                .departmentDescription(department.getDepartmentDescription())
                .build();
    }
}
