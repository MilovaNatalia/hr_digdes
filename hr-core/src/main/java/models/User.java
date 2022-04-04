package models;

import models.Employee;

public class User {
    private String userName;
    private String password;
    private Employee employeeInfo;
    private UserRoles role;

    public User(String userName, String password, Employee employeeInfo, UserRoles role) {
        this.userName = userName;
        this.password = password;
        this.employeeInfo = employeeInfo;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(Employee employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }
}
