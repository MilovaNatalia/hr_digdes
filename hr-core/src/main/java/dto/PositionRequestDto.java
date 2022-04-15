package dto;

import java.util.Objects;

public class PositionRequestDto {
    private String operation;
    private Long id;
    private String name;

    public PositionRequestDto(String operation, Long id, String name) {
        this.operation = operation;
        this.id = id;
        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionRequestDto that = (PositionRequestDto) o;
        return Objects.equals(operation, that.operation) && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, id, name);
    }
}
