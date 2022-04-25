package com.digdes.dto;


import java.util.List;
import java.util.Objects;


public class DepartmentResponseDto {
    private Long id;
    private String name;
    private String typeName;
    private List<String> headNames;
    private String parentName;
    private List<EmployeeResponseDto> employees;

    public DepartmentResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<String> getHeadNames() {
        return headNames;
    }

    public void setHeadNames(List<String> headNames) {
        this.headNames = headNames;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<EmployeeResponseDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponseDto> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentResponseDto that = (DepartmentResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(typeName, that.typeName) && Objects.equals(headNames, that.headNames) && Objects.equals(parentName, that.parentName) && Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeName, headNames, parentName, employees);
    }
}
