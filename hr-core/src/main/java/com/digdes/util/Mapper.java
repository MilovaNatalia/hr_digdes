package com.digdes.util;

import com.digdes.dto.*;
import com.digdes.models.Department;
import com.digdes.models.Position;
import com.digdes.models.DepartmentType;
import com.digdes.models.Employee;

import java.util.Optional;
import java.util.stream.Collectors;

public class Mapper {
    private Mapper() {
    }

    //todo: refactoring
    public static PositionDto mapPositionToDto(Position position){
        return new PositionDto(position.getId(), position.getName());
    }

    public static Position mapDtoToPosition(PositionDto dto){
        return new Position(dto.getId(), dto.getName());
    }

    public static DepartmentTypeDto mapDepartmentTypeToDto(DepartmentType type){
        return new DepartmentTypeDto(type.getId(), type.getName());
    }

    public static DepartmentType mapDtoToDepartmentType(DepartmentTypeDto dto){
        return new DepartmentType(dto.getId(), dto.getName());
    }

    public static EmployeeDto mapEmployeeToDto(Employee employee){
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setPatronymic(employee.getPatronymic());
        dto.setGender(employee.getGender());
        dto.setBirthDate(employee.getBirthDate());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        dto.setPosition(employee.getPosition());
        return dto;
    }

    public static Employee mapDtoToEmployee(EmployeeDto dto){
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setPatronymic(dto.getPatronymic());
        employee.setGender(dto.getGender());
        employee.setBirthDate(dto.getBirthDate());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setPosition(dto.getPosition());
        return employee;
    }

    public static DepartmentDto mapDepartmentToDto(Department department){
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setEmployees(department.getEmployees());
        dto.setHead(department.getHead());
        dto.setType(department.getType());
        dto.setParent(department.getParent());
        return dto;
    }

    public static Department mapDtoToDepartment(DepartmentDto dto){
        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setEmployees(dto.getEmployees());
        department.setHead(dto.getHead());
        department.setType(dto.getType());
        department.setParent(dto.getParent());
        return department;
    }

    public static EmployeeResponseDto mapEmployeeToResponseDto(Employee employee){
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setPatronymic(employee.getPatronymic());
        dto.setGender(employee.getGender());
        dto.setBirthDate(employee.getBirthDate());
        dto.setEmail(employee.getEmail());
        dto.setDepartmentName(employee.getDepartment().getName());
        dto.setPositionName(employee.getPosition().getName());
        return dto;
    }

    public static DepartmentResponseDto mapDepartmentToResponseDto(Department department) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setEmployees(department.getEmployees().stream().
                map(Mapper::mapEmployeeToDto).collect(Collectors.toList()));
        dto.setHeadName(department.getHead().getFirstName() + department.getHead().getLastName() +
                department.getHead().getPatronymic());
        dto.setParentName(department.getParent().getName());
        dto.setTypeName(department.getType().getName());
        return dto;
    }
}
