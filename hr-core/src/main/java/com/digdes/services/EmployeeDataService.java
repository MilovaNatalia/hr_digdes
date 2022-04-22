package com.digdes.services;

import com.digdes.dto.EmployeeResponseDto;
import com.digdes.dto.EmployeeDto;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.models.Department;
import com.digdes.models.Employee;
import com.digdes.models.Position;
import com.digdes.repositories.DepartmentRepository;
import com.digdes.repositories.EmployeeRepository;
import com.digdes.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeDataService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionRepository positionRepository;

    @Transactional
    public EmployeeResponseDto save(EmployeeDto info) {
        Employee employee = mapDtoToEmployee(info);
        return mapEmployeeToResponseDto(employeeRepository.save(employee));
    }

    @Transactional
    public boolean delete(EmployeeDto info) {
        Employee employee = mapDtoToEmployee(info);
        employeeRepository.delete(employee);
        return !employeeRepository.existsById(employee.getId());
    }

    @Transactional
    public List<EmployeeResponseDto> find(EmployeeDto searchRequest) {
        List<EmployeeResponseDto> employees =
                employeeRepository.findAll(
                                Example.of(mapDtoToEmployee(searchRequest)))
                        .stream().map(this::mapEmployeeToResponseDto)
                        .collect(Collectors.toList());;
        return employees;
    }

    @Transactional
    public Optional<EmployeeResponseDto> get(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(this::mapEmployeeToResponseDto);
    }

    @Transactional
    public List<EmployeeResponseDto> getAll() {
        return employeeRepository.findAll()
                .stream().map(this::mapEmployeeToResponseDto)
                .collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }


    private Employee mapDtoToEmployee(EmployeeDto dto){
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setBirthDate(dto.getBirthDate());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setPatronymic(dto.getPatronymic());
        employee.setGender(dto.getGender());
        employee.setEmail(dto.getEmail());
        if (dto.getPositionId() != null) {
            Optional<Position> position = positionRepository.findById(dto.getPositionId());
            if (position.isPresent())
                employee.setPosition(position.get());
            else
                throw new EntityNotFoundException();
        }
        if (dto.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(dto.getDepartmentId());
            if (department.isPresent())
                employee.setDepartment(department.get());
            else
                throw new EntityNotFoundException();
        }
        return employee;
    }

    private EmployeeResponseDto mapEmployeeToResponseDto (Employee employee){
        //todo:pretty birth date
        EmployeeResponseDto responseDto = new EmployeeResponseDto();
        responseDto.setId(employee.getId());
        responseDto.setFirstName(employee.getFirstName());
        responseDto.setLastName(employee.getLastName());
        responseDto.setPatronymic(employee.getPatronymic());
        responseDto.setBirthDate(employee.getBirthDate());
        responseDto.setEmail(employee.getEmail());
        responseDto.setGender(employee.getGender());
        if (employee.getPosition() != null)
            responseDto.setPositionName(employee.getPosition().getName());
        if (employee.getDepartment() != null)
            responseDto.setDepartmentName(employee.getDepartment().getName());
        return responseDto;
    }
}
