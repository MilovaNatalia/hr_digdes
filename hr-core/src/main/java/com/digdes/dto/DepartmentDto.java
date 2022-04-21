package com.digdes.dto;

import com.digdes.models.Department;
import com.digdes.models.DepartmentType;
import com.digdes.models.Employee;

import java.util.List;
import java.util.Objects;


public class DepartmentDto {
    private Long id;
    private String name;

    private DepartmentType type;

    private List<Employee> employees;

    private Employee head;

    private Department parent;

    public DepartmentDto(Long id, String name, DepartmentType type,
                         List<Employee> employees, Employee head, Department parent) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.employees = employees;
        this.head = head;
        this.parent = parent;
    }

    public DepartmentDto() {
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

    public DepartmentType getType() {
        return type;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee getHead() {
        return head;
    }

    public void setHead(Employee head) {
        this.head = head;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentDto that = (DepartmentDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(employees, that.employees) && Objects.equals(head, that.head) && Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, employees, head, parent);
    }
}

