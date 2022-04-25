package com.digdes.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users {

    @Id
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    private Employee employee;


    public Users() {
    }

    public Users(String username, String password, Role role, Employee employee) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.employee = employee;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(username, users.username) && Objects.equals(password, users.password) && Objects.equals(role, users.role) && Objects.equals(employee, users.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role, employee);
    }
}
