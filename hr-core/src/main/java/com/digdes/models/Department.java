package com.digdes.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private DepartmentType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id")
    private Users moderator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Department parent;


    public Department() {
    }

    public Department(DepartmentType type) {
        this.type = type;
    }

    public Department(Department parent) {
        this.parent = parent;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public DepartmentType getType() {
        return type;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

    public Users getModerator() {
        return moderator;
    }

    public void setModerator(Users moderator) {
        this.moderator = moderator;
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
        Department that = (Department) o;
        return Objects.equals(id, that.id) && name.equals(that.name) && Objects.equals(employees, that.employees) && type.equals(that.type) && Objects.equals(moderator, that.moderator) && parent.equals(that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, employees, type, moderator, parent);
    }

    //pretty to string
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Department{");
        sb.append("id =").append(id);
        sb.append(", name ='").append(name).append('\'');
        sb.append(", type =").append(type);
        sb.append(", moderator =").append(moderator);
        sb.append(", parent =").append(parent);
        sb.append('}');
        return sb.toString();
    }
}
