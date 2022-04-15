package dto;

import java.util.Objects;

public class DepartmentDto {
    private Long id;
    private String name;
    private Long typeId;
    private Long headId;
    private Long parentId;

    public DepartmentDto(Long id, String name, Long type_id, Long head_id, Long parent_id) {
        this.id = id;
        this.name = name;
        this.typeId = type_id;
        this.headId = head_id;
        this.parentId = parent_id;
    }

    public DepartmentDto(String name, Long type_id, Long head_id, Long parent_id) {
        this.name = name;
        this.typeId = type_id;
        this.headId = head_id;
        this.parentId = parent_id;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getHeadId() {
        return headId;
    }

    public void setHeadId(Long headId) {
        this.headId = headId;
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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(typeId, that.typeId) && Objects.equals(headId, that.headId) && Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeId, headId, parentId);
    }
}

