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
    private Long departmentId;
    private Long positionId;
    private Boolean isHead;

    private String userId;

    public EmployeeDto() {
    }
    public EmployeeDto(Long id) {
        this.id = id;
    }

    public EmployeeDto(Long id, String firstName, String lastName, String patronymic,
                       String email, EmployeeGender gender, Timestamp birthDate,
                       Long departmentId, Long positionId, Boolean isHead, String userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.departmentId = departmentId;
        this.positionId = positionId;
        this.isHead = isHead;
        this.userId = userId;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Boolean getHead() {
        return isHead;
    }

    public void setHead(Boolean head) {
        isHead = head;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(patronymic, that.patronymic) && Objects.equals(email, that.email) && gender == that.gender && Objects.equals(birthDate, that.birthDate) && Objects.equals(departmentId, that.departmentId) && Objects.equals(positionId, that.positionId) && Objects.equals(isHead, that.isHead) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, patronymic, email, gender, birthDate, departmentId, positionId, isHead, userId);
    }
}
