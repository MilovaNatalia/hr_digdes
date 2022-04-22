package com.digdes.dto;


import java.util.List;
import java.util.Objects;


public class DepartmentResponseDto {
    private Long id;
    private String name;
    private String typeName;
    private String headName;
    private String parentName;
    private List<EmployeeResponseDto> employees;
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

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(typeName, that.typeName) && Objects.equals(headName, that.headName) && Objects.equals(parentName, that.parentName) && Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeName, headName, parentName, employees);
    }
}
