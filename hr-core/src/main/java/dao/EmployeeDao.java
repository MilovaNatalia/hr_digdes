package dao;

import models.*;

import java.util.List;
import java.util.Optional;

public class EmployeeDao implements Dao<Employee>{
    @Override
    public Optional<Employee> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public Employee save(Employee employee) {
        return null;
    }

    @Override
    public Employee update(Employee employee, String... params) {
        return null;
    }

    @Override
    public boolean delete(Employee employee) {
        return false;
    }
}
