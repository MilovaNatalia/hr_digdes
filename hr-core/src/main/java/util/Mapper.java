package util;

import dto.*;
import models.Department;
import models.DepartmentType;
import models.Employee;
import models.Position;

public class Mapper {
    private Mapper() {
    }

    //todo: refactoring
    public static PositionDto mapPosition(Position position){
        return new PositionDto(position.getId(), position.getName());
    }

    public static Position mapPositionDto(PositionDto dto){
        return new Position(dto.getId(), dto.getName());
    }

    public static DepartmentTypeDto mapDepartmentType(DepartmentType type){
        return new DepartmentTypeDto(type.getId(), type.getName());
    }

    public static DepartmentType mapDepartmentTypeDto(DepartmentTypeDto dto){
        return new DepartmentType(dto.getId(), dto.getName());
    }

    public static EmployeeDto mapEmployee(Employee employee){
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setPatronymic(employee.getPatronymic());
        dto.setGender(employee.getGender());
        dto.setBirthDate(employee.getBirthDate());
        dto.setEmail(employee.getEmail());
        dto.setDepartmentId(employee.getDepartment_id());
        dto.setPositionId(employee.getPosition_id());
        return dto;
    }

    public static Employee mapEmployeeDto(EmployeeDto dto){
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setPatronymic(dto.getPatronymic());
        employee.setGender(dto.getGender());
        employee.setBirthDate(dto.getBirthDate());
        employee.setEmail(dto.getEmail());
        employee.setPosition_id(dto.getPositionId());
        employee.setDepartment_id(dto.getDepartmentId());
        return employee;
    }

    public static DepartmentDto mapDepartment(Department department){
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setTypeId(department.getType_id());
        dto.setHeadId(department.getHead_id());
        dto.setParentId(department.getParent_id());
        return dto;
    }

    public static Department mapDepartmentDto(DepartmentDto dto){
        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setType_id(dto.getTypeId());
        department.setHead_id(dto.getHeadId());
        department.setParent_id(dto.getParentId());
        return department;
    }

    public static EmployeeDto mapEmployeeDtoFromRequest(EmployeeRequestDto dto){
        EmployeeDto employee = new EmployeeDto();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setPatronymic(dto.getPatronymic());
        employee.setGender(dto.getGender());
        employee.setBirthDate(dto.getBirthDate());
        employee.setEmail(dto.getEmail());
        employee.setPositionId(dto.getPositionId());
        employee.setDepartmentId(dto.getDepartmentId());
        return employee;
    }

    public static DepartmentDto mapDepartmentDtoFromRequest(DepartmentRequestDto dto){
        DepartmentDto department = new DepartmentDto();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setTypeId(dto.getTypeId());
        department.setHeadId(dto.getHeadId());
        department.setParentId(dto.getParentId());
        return department;
    }
}
