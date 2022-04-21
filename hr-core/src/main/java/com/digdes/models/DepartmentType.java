package com.digdes.models;


import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "department_types")
public class DepartmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public DepartmentType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DepartmentType(Long id) {
        this.id = id;
    }

    public DepartmentType(String name) {
        this.name = name;
    }

    public DepartmentType() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentType that = (DepartmentType) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
