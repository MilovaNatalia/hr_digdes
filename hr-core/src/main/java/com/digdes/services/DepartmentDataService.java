package com.digdes.services;

import com.digdes.dto.DepartmentDto;
import com.digdes.dto.DepartmentResponseDto;
import com.digdes.dto.EmployeeResponseDto;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.models.Department;
import com.digdes.models.DepartmentType;
import com.digdes.models.Employee;
import com.digdes.repositories.DepartmentRepository;
import com.digdes.repositories.DepartmentTypeRepository;
import com.digdes.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentDataService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentTypeRepository typeRepository;

    @Transactional
    public DepartmentResponseDto create(DepartmentDto info) {
        try {
            Department department = mapDtoToDepartment(info);
            Optional<Department> existingDepartment = departmentRepository.findOne(Example.of(department));
            return existingDepartment.map(this::mapDepartmentToResponseDto).orElseGet(() -> mapDepartmentToResponseDto(departmentRepository.save(department)));
        }
        catch (EntityNotFoundException exception){
            //todo: message
            throw new EntityNotFoundException();
        }
    }


    @Transactional
    public DepartmentResponseDto update(DepartmentDto info) {
        try {
            Department department = mapDtoToDepartment(info);
            Optional<Department> updateDepartment = departmentRepository.findById(department.getId());
            if (!updateDepartment.isPresent())
                //todo: message
                throw new EntityNotFoundException();
            return mapDepartmentToResponseDto(departmentRepository.save(getUpdateDepartment(department, updateDepartment.get())));
        }
        catch (EntityNotFoundException exception){
            //todo: message
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public boolean delete(DepartmentDto info) {
        try {
            Department department = mapDtoToDepartment(info);
            if (employeeRepository.findAll(Example.of(new Employee(department))).size() != 0)
                //todo: message
                //todo: cascade delete for users
                throw new EntityDeleteException();
            if (departmentRepository.findAll(Example.of(new Department(department))).size() != 0)
                //todo: message
                throw new EntityDeleteException();
            departmentRepository.delete(department);
            return !departmentRepository.existsById(department.getId());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public List<DepartmentResponseDto> find(DepartmentDto searchRequest) {
        try {
            List<DepartmentResponseDto> departments =
                    departmentRepository.findAll(
                                    Example.of(
                                            mapDtoToDepartment(searchRequest)))
                            .stream().map(this::mapDepartmentToResponseDto)
                            .collect(Collectors.toList());
            ;
            return departments;
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException();
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
                //todo: message
                throw new EntityNotFoundException();
        }
        if (dto.getHeadId() != null) {
            Optional<Employee> head = employeeRepository.findById(dto.getHeadId());
            if (head.isPresent())
                department.setHead(head.get());
            else
                //todo: message
                throw new EntityNotFoundException();
        }
        if (dto.getParentId() != null) {
            Optional<Department> parent = departmentRepository.findById(dto.getParentId());
            if (parent.isPresent())
                department.setParent(parent.get());
            else
                //todo: message
                throw new EntityNotFoundException();
        }
        return department;
    }

    private DepartmentResponseDto mapDepartmentToResponseDto(Department department){
        DepartmentResponseDto responseDto = new DepartmentResponseDto();
        responseDto.setId(department.getId());
        responseDto.setName(department.getName());
        responseDto.setTypeName(department.getType().getName());
        if (department.getHead() != null){
            String fullHeadName = department.getHead().getFirstName() + " " + department.getHead().getLastName();
            if (department.getHead().getPatronymic() != null)
                fullHeadName += " " +  department.getHead().getPatronymic();
            responseDto.setHeadName(fullHeadName);
        }
        if (department.getParent() != null)
            responseDto.setParentName(department.getParent().getName());
        List<EmployeeResponseDto> employees = mapEmployeesToResponseDtos(employeeRepository.findAll(Example.of(new Employee(department))));
        responseDto.setEmployees(employees);
        return responseDto;
    }

    private List<EmployeeResponseDto> mapEmployeesToResponseDtos (List<Employee> employees){
        //todo:pretty birth date
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
        if (info.getHead() != null)
            updateDepartment.setHead(info.getHead());
        if (info.getType() != null)
            updateDepartment.setType(info.getType());
        if (info.getParent() != null)
            updateDepartment.setParent(info.getParent());
        if (info.getEmployees() != null)
            updateDepartment.setEmployees(info.getEmployees());
        return updateDepartment;
    }
}
