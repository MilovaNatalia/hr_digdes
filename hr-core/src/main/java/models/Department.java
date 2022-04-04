package models;

public class Department {
    private Long id;
    private String name;
    private Long head_id;
    private Long parent_id;
    //TODO constructor


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
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
}
