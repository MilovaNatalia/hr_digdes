package com.digdes.services;

import com.digdes.dto.EmployeeResponseDto;
import com.digdes.dto.EmployeeDto;
import com.digdes.exceptions.EntityCreateException;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.exceptions.EntityUpdateException;
import com.digdes.models.*;
import com.digdes.repositories.DepartmentRepository;
import com.digdes.repositories.EmployeeRepository;
import com.digdes.repositories.PositionRepository;
import com.digdes.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeDataService extends DataService{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    public EmployeeResponseDto create(EmployeeDto info) {
        try {
            Employee employee = mapDtoToEmployee(info);
            Optional<Employee> existingEmployee = employeeRepository.findOne(Example.of(employee));
            if (employeeRepository.findOne(Example.of(new Employee(employee.getEmail()))).isPresent())
                throw new EntityCreateException("Unique constraint violation field Email");
            return existingEmployee.map(this::mapEmployeeToResponseDto).orElseGet(() -> mapEmployeeToResponseDto(employeeRepository.save(employee)));
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public EmployeeResponseDto update(EmployeeDto info) {
        try{
            Employee employee = mapDtoToEmployee(info);
            Optional<Employee> updateEmployee = employeeRepository.findById(employee.getId());
            if (!updateEmployee.isPresent())
                throw new EntityNotFoundException("Updated employee is not found");
            if (employee.getEmail() != null) {
                if (employeeRepository.findOne(Example.of(new Employee(employee.getEmail()))).isPresent())
                    throw new EntityUpdateException("Unique constraint violation field Email");
            }
            return mapEmployeeToResponseDto(employeeRepository.save(getUpdateEmployee(employee, updateEmployee.get())));
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public boolean delete(EmployeeDto info) {
        try {
            Employee employee = mapDtoToEmployee(info);
            employeeRepository.delete(employee);
            return !employeeRepository.existsById(employee.getId());
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public List<EmployeeResponseDto> find(EmployeeDto searchRequest) {
        try {
            List<EmployeeResponseDto> employees =
                    employeeRepository.findAll(
                                    Example.of(mapDtoToEmployee(searchRequest)))
                            .stream().map(this::mapEmployeeToResponseDto)
                            .collect(Collectors.toList());
            ;
            return employees;
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
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
        employee.setHead(dto.getHead());
        if (dto.getPositionId() != null) {
            Optional<Position> position = positionRepository.findById(dto.getPositionId());
            if (position.isPresent())
                employee.setPosition(position.get());
            else
                throw new EntityNotFoundException("Reference position is not found");
        }
        if (dto.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(dto.getDepartmentId());
            if (department.isPresent())
                employee.setDepartment(department.get());
            else
                throw new EntityNotFoundException("Reference department is not found");
        }
        if (dto.getUserId() != null) {
            Optional<Users> user = usersRepository.findById(dto.getUserId());
            if (user.isPresent())
                employee.setUser(user.get());
            else
                throw new EntityNotFoundException("Reference user is not found");
        }
        return employee;
    }

    private EmployeeResponseDto mapEmployeeToResponseDto (Employee employee){
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

    private Employee getUpdateEmployee (Employee info, Employee updateEmployee){
        if (info.getFirstName() != null)
            updateEmployee.setFirstName(info.getFirstName());
        if (info.getLastName() != null)
            updateEmployee.setLastName(info.getLastName());
        if (info.getPatronymic() != null)
            updateEmployee.setPatronymic(info.getPatronymic());
        if (info.getBirthDate() != null)
            updateEmployee.setBirthDate(info.getBirthDate());
        if (info.getGender() != null)
            updateEmployee.setGender(info.getGender());
        if (info.getEmail() != null)
            updateEmployee.setEmail(info.getEmail());
        if (info.getUser() != null)
            updateEmployee.setUser(info.getUser());
        if (info.getHead() != null)
            updateEmployee.setHead(info.getHead());
        if (info.getDepartment() != null)
            updateEmployee.setDepartment(info.getDepartment());
        if (info.getPosition() != null)
            updateEmployee.setPosition(info.getPosition());
        return updateEmployee;
    }
}
