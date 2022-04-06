package models;

import java.util.Objects;

public class Department {
    private Long id;
    private String name;
    private Long type_id;
    private Long head_id;
    private Long parent_id;

    public Department(String name, Long type_id, Long head_id, Long parent_id) {
        this.name = name;
        this.type_id = type_id;
        this.head_id = head_id;
        this.parent_id = parent_id;
    }


    public Department(Long id, String name, Long type_id, Long head_id, Long parent_id) {
        this.id = id;
        this.name = name;
        this.type_id = type_id;
        this.head_id = head_id;
        this.parent_id = parent_id;
    }

    public Department() {
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

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    public Long getHead_id() {
        return head_id;
    }

    public void setHead_id(Long head_id) {
        this.head_id = head_id;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(type_id, that.type_id) && Objects.equals(head_id, that.head_id) && Objects.equals(parent_id, that.parent_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type_id, head_id, parent_id);
    }
}
