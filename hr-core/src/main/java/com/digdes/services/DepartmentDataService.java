package com.digdes.services;

import com.digdes.dto.DepartmentDto;
import com.digdes.dto.DepartmentResponseDto;
import com.digdes.dto.EmployeeResponseDto;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.models.*;
import com.digdes.repositories.DepartmentRepository;
import com.digdes.repositories.DepartmentTypeRepository;
import com.digdes.repositories.EmployeeRepository;
import com.digdes.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentDataService extends DataService{

    //todo: transactonal
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentTypeRepository typeRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    public DepartmentResponseDto create(DepartmentDto info) {
        try {
            Department department = mapDtoToDepartment(info);
            Optional<Department> existingDepartment = departmentRepository.findOne(Example.of(department));
            //todo: moderator by default
            return existingDepartment.map(this::mapDepartmentToResponseDto).orElseGet(() -> mapDepartmentToResponseDto(departmentRepository.save(department)));
        }
        catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }


    @Transactional
    public DepartmentResponseDto update(DepartmentDto info) {
        try {
            Department department = mapDtoToDepartment(info);
            Optional<Department> updateDepartment = departmentRepository.findById(department.getId());
            if (!updateDepartment.isPresent())
                throw new EntityNotFoundException("Updated department is not found");
            return mapDepartmentToResponseDto(departmentRepository.save(getUpdateDepartment(department, updateDepartment.get())));
        }
        catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public boolean delete(DepartmentDto info) {
        try {
            Department department = mapDtoToDepartment(info);
            if (employeeRepository.findAll(Example.of(new Employee(department))).size() != 0)
                throw new EntityDeleteException("Reference on this department in table employees (department_id)");
            if (departmentRepository.findAll(Example.of(new Department(department))).size() != 0)
                throw new EntityDeleteException("Reference on this department in table department (parent_id)");
            departmentRepository.delete(department);
            return !departmentRepository.existsById(department.getId());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public List<DepartmentResponseDto> find(DepartmentDto searchRequest) {
        try {
            return departmentRepository.findAll(
                            Example.of(
                                    mapDtoToDepartment(searchRequest)))
                    .stream().map(this::mapDepartmentToResponseDto)
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public DepartmentResponseDto get(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.map(this::mapDepartmentToResponseDto).orElseGet(DepartmentResponseDto::new);
    }


    @Transactional(readOnly = true)
    public List<DepartmentResponseDto> getAll() {
        return departmentRepository.findAll()
                .stream().map(this::mapDepartmentToResponseDto)
                .collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }

    private Department mapDtoToDepartment(DepartmentDto dto){
        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        if (dto.getTypeId() != null) {
            Optional<DepartmentType> type = typeRepository.findById(dto.getTypeId());
            if (type.isPresent())
                department.setType(type.get());
            else
                throw new EntityNotFoundException("Reference department type is not found");
        }
        if (dto.getModeratorId() != null){
            Optional<Users> moderator = usersRepository.findById(dto.getModeratorId());
            if (moderator.isPresent())
                department.setModerator(moderator.get());
            else
                throw new EntityNotFoundException("Reference moderator is not found");
        }
        if (dto.getParentId() != null) {
            Optional<Department> parent = departmentRepository.findById(dto.getParentId());
            if (parent.isPresent())
                department.setParent(parent.get());
            else
                throw new EntityNotFoundException("Reference parent is not found");
        }
        return department;
    }

    private DepartmentResponseDto mapDepartmentToResponseDto(Department department){
        DepartmentResponseDto responseDto = new DepartmentResponseDto();
        responseDto.setId(department.getId());
        responseDto.setName(department.getName());
        responseDto.setTypeName(department.getType().getName());
        List<String> headsNames = employeeRepository
                .findAll(Example.of(new Employee(department, true)))
                .stream().map(employee -> {
                    String fullHeadName =employee.getFirstName() + " " + employee.getLastName();
                    if (employee.getPatronymic() != null)
                        fullHeadName += " " +  employee.getPatronymic();
                    return fullHeadName;
                }).collect(Collectors.toList());
        responseDto.setHeadNames(headsNames);
        if (department.getParent() != null)
            responseDto.setParentName(department.getParent().getName());
        List<EmployeeResponseDto> employees = mapEmployeesToResponseDtos(employeeRepository.findAll(Example.of(new Employee(department))));
        responseDto.setEmployees(employees);
        return responseDto;
    }

    private List<EmployeeResponseDto> mapEmployeesToResponseDtos (List<Employee> employees){
        List<EmployeeResponseDto> responseDtos = employees.stream().map(employee -> {
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
        }).collect(Collectors.toList());
        return responseDtos;
    }

    private Department getUpdateDepartment(Department info, Department updateDepartment){
        if (info.getName() != null)
            updateDepartment.setName(info.getName());
        if (info.getModerator() != null)
            updateDepartment.setModerator(info.getModerator());
        if (info.getType() != null)
            updateDepartment.setType(info.getType());
        if (info.getParent() != null)
            updateDepartment.setParent(info.getParent());
        if (info.getEmployees() != null)
            updateDepartment.setEmployees(info.getEmployees());
        return updateDepartment;
    }
}
