package com.digdes.dto;

import com.digdes.models.Role;

import java.util.Objects;

public class UsersDto {

    private String username;

    private String password;

    private Long roleId;

    public UsersDto() {
    }

    public UsersDto(String username, String password, Long roleId) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersDto usersDto = (UsersDto) o;
        return Objects.equals(username, usersDto.username) && Objects.equals(password, usersDto.password) && Objects.equals(roleId, usersDto.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, roleId);
    }
}
