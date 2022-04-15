package services;

import dao.impl.DepartmentDao;
import dao.impl.DepartmentTypeDao;
import dao.impl.EmployeeDao;
import dto.DepartmentDto;
import dto.DepartmentResponseDto;
import dto.DepartmentTypeDto;
import dto.EmployeeDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DepartmentDataService implements DataService<DepartmentDto, DepartmentResponseDto>{

    private final DepartmentDao departmentDao = new DepartmentDao();
    private final EmployeeDao employeeDao = new EmployeeDao();
    private final DepartmentTypeDao departmentTypeDao = new DepartmentTypeDao();

    public boolean create(DepartmentDto info) {
        return departmentDao.save(info);
    }

    public boolean update(DepartmentDto info) {
        return departmentDao.update(info);
    }

    public boolean delete(DepartmentDto info) {
        return departmentDao.delete(info);
    }

    public List<DepartmentResponseDto> find(DepartmentDto searchRequest) {
        List<DepartmentDto> departments = departmentDao.simpleSearch(searchRequest);
        return departments.stream().map(this::getResponseDtoFromDepartmentDto).collect(Collectors.toList());
    }

    public Optional<DepartmentResponseDto> get(Long id) {
        Optional<DepartmentDto> departmentDto = departmentDao.get(id);
        return departmentDto.map(this::getResponseDtoFromDepartmentDto);
    }

    public List<DepartmentResponseDto> getAll() {
        List<DepartmentDto> departments = departmentDao.getAll();
        return departments.stream().map(this::getResponseDtoFromDepartmentDto).collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }

    public DepartmentResponseDto getResponseDtoFromDepartmentDto(DepartmentDto department) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setEmployees(employeeDao.simpleSearch(new EmployeeDto(department.getId())));
        if (department.getTypeId() != null){
            Optional<DepartmentTypeDto> type = departmentTypeDao.get(department.getTypeId());
            type.ifPresent(departmentType -> dto.setTypeName(departmentType.getName()));
        }
        if(department.getParentId() != null){
            Optional<DepartmentDto> parent = departmentDao.get(department.getParentId());
            parent.ifPresent(parentDepartment -> dto.setParentName(parentDepartment.getName()));
        }
        if(department.getHeadId() != null){
            Optional<EmployeeDto> head = employeeDao.get(department.getHeadId());
            if (head.isPresent()){
                EmployeeDto employee = head.get();
                StringBuilder builder = new StringBuilder();
                builder.append(employee.getFirstName()).append(" ");
                builder.append(employee.getLastName());
                if (head.get().getPatronymic() != null)
                    builder.append(" ").append(employee.getPatronymic());
                dto.setHeadName(builder.toString());
            }
        }
        return dto;
    }
}
