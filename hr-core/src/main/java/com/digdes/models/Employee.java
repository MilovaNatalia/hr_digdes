package com.digdes.models;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeGender gender;

    @Column(name = "birth_date", nullable = false, columnDefinition = "ti")
    private Timestamp birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name="position_id", nullable = false)
    private Position position;

    @Column(name = "is_head", nullable = false)
    private Boolean isHead;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    public Employee() {
    }

    public Employee(Department department, Boolean isHead) {
        this.department = department;
        this.isHead = isHead;
    }

    public Employee(Users user) {
        this.user = user;
    }

    public Employee(Position position) {
        this.position = position;
    }

    public Employee(String email) {
        this.email = email;
    }

    public Employee(Department department) {
        this.department = department;
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

    public Boolean getHead() {
        return isHead;
    }

    public void setHead(Boolean head) {
        isHead = head;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && Objects.equals(patronymic, employee.patronymic) && email.equals(employee.email) && gender == employee.gender && birthDate.equals(employee.birthDate) && department.equals(employee.department) && position.equals(employee.position) && isHead.equals(employee.isHead) && Objects.equals(user, employee.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, patronymic, email, gender, birthDate, department, position, isHead, user);
    }

    //pretty to string
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Employee{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", patronymic='").append(patronymic).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", birthDate=").append(birthDate);
        sb.append(", department=").append(department);
        sb.append(", position=").append(position);
        sb.append(", isHead=").append(isHead);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
