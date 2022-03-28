import models.Department;

import java.util.Optional;

public class DepartmentApiController implements ApiController<Department> {
    @Override
    public Optional<Department> create(String info) {
        return Optional.empty();
    }

    @Override
    public Optional<Department> update(String id, String info) {
        return Optional.empty();
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Optional<Department> find(String searchRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<Department> view(String id) {
        return Optional.empty();
    }

    @Override
    public boolean loadFromFile(String fileName) {
        return false;
    }
}
