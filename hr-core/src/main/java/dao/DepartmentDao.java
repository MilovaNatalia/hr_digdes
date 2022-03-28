package dao;

import models.*;

import java.util.List;
import java.util.Optional;

public class DepartmentDao implements Dao<Department>{
    @Override
    public Optional<Department> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Department> getAll() {
        return null;
    }

    @Override
    public Department save(Department department) {
        return null;
    }

    @Override
    public Department update(Department department, String... params) {
        return null;
    }

    @Override
    public boolean delete(Department department) {
        return false;
    }
}
