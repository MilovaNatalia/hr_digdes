package services;

import dao.impl.DepartmentDao;
import dao.impl.EmployeeDao;
import dao.impl.PositionDao;
import dto.DepartmentDto;
import dto.EmployeeDto;
import dto.EmployeeResponseDto;
import dto.PositionDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeDataService implements DataService<EmployeeDto, EmployeeResponseDto> {

    private final EmployeeDao employeeDao = new EmployeeDao();
    private final DepartmentDao departmentDao = new DepartmentDao();
    private final PositionDao positionDao = new PositionDao();

    public boolean create(EmployeeDto info) {
        return employeeDao.save(info);
    }

    public boolean update(EmployeeDto info) {
        return employeeDao.update(info);
    }

    public boolean delete(EmployeeDto info) {
        return employeeDao.delete(info);
    }

    public List<EmployeeResponseDto> find(EmployeeDto searchRequest) {
        List<EmployeeDto> employees = employeeDao.simpleSearch(searchRequest);
        return employees.stream().map(this::getResponseDtoFromEmployeeDto).collect(Collectors.toList());
    }

    public Optional<EmployeeResponseDto> get(Long id) {
        Optional<EmployeeDto> employeeDto = employeeDao.get(id);
        return employeeDto.map(this::getResponseDtoFromEmployeeDto);
    }

    public List<EmployeeResponseDto> getAll() {
        List<EmployeeDto> employees = employeeDao.getAll();
        return employees.stream().map(this::getResponseDtoFromEmployeeDto).collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }

    private EmployeeResponseDto getResponseDtoFromEmployeeDto(EmployeeDto employee) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setPatronymic(employee.getPatronymic());
        dto.setGender(employee.getGender());
        dto.setBirthDate(employee.getBirthDate());
        dto.setEmail(employee.getEmail());
        if (employee.getDepartmentId() != null) {
            Optional<DepartmentDto> department = departmentDao.get(employee.getDepartmentId());
            String department_name = department.map(DepartmentDto::getName).orElse(null);
            dto.setDepartmentName(department_name);
        }
        if (employee.getPositionId() != null) {
            Optional<PositionDto> position = positionDao.get(employee.getPositionId());
            String position_name = position.map(PositionDto::getName).orElse(null);
            dto.setPositionName(position_name);
        }
        return dto;
    }

}
