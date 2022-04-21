package com.digdes.dto;

import com.digdes.models.Department;
import com.digdes.models.EmployeeGender;
import com.digdes.models.Position;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;


public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private EmployeeGender gender;
    private Timestamp birthDate;

    private Department department;
    private Position position;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id) {
        this.id = id;
    }

    public EmployeeDto(Long id, String firstName, String lastName, String patronymic, String email, EmployeeGender gender,
                       Timestamp birthDate, Department department, Position position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.department = department;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeGender getGender() {
        return gender;
    }

    public void setGender(EmployeeGender gender) {
        this.gender = gender;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(patronymic, that.patronymic) && Objects.equals(email, that.email) && gender == that.gender && Objects.equals(birthDate, that.birthDate) && Objects.equals(department, that.department) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, patronymic, email, gender, birthDate, department, position);
    }
}
