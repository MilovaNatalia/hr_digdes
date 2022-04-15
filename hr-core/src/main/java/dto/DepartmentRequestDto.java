package dto;

import java.util.Objects;

public class DepartmentRequestDto {
    private String operation;
    private Long id;
    private String name;
    private Long typeId;
    private Long headId;
    private Long parentId;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
        DepartmentRequestDto that = (DepartmentRequestDto) o;
        return Objects.equals(operation, that.operation) && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(typeId, that.typeId) && Objects.equals(headId, that.headId) && Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, id, name, typeId, headId, parentId);
    }
}
