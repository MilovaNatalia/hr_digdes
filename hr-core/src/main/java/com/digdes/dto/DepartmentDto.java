package com.digdes.dto;


import java.util.Objects;


public class DepartmentDto {
    private Long id;
    private String name;
    private Long typeId;

    private String moderatorId;

    private Long parentId;

    public DepartmentDto() {
    }

    public DepartmentDto(Long id, String name, Long typeId, String moderatorId, Long parentId) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.moderatorId = moderatorId;
        this.parentId = parentId;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(String moderatorId) {
        this.moderatorId = moderatorId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentDto that = (DepartmentDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(typeId, that.typeId) && Objects.equals(moderatorId, that.moderatorId) && Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeId, moderatorId, parentId);
    }
}

