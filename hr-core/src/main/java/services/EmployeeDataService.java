package services;

import models.Employee;

import java.util.Optional;

public class EmployeeDataService implements DataService<Employee>{
    @Override
    public Optional<Employee> create(String info) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> update(String id, String info) {
        return Optional.empty();
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Optional<Employee> view(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> find(String searchRequest) {
        return Optional.empty();
    }

    @Override
    public boolean loadFromFile(String fileName) {
        return false;
    }
}
