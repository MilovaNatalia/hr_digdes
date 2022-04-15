package controllers;

import dto.EmployeeDto;
import dto.EmployeeResponseDto;
import services.EmployeeDataService;
import java.util.List;
import java.util.Optional;

public class EmployeeApiController{

    private final EmployeeDataService employeeDataService = new EmployeeDataService();

    public boolean create(EmployeeDto info) {
        return employeeDataService.create(info);
    }

    public boolean update(EmployeeDto info) {
        return employeeDataService.update(info);
    }

    public boolean delete(EmployeeDto info) {
        return employeeDataService.delete(info);
    }

    public List<EmployeeResponseDto> find(EmployeeDto searchRequest) {
        return employeeDataService.find(searchRequest);
    }

    public Optional<EmployeeResponseDto> get(Long id) {
        return employeeDataService.get(id);
    }

    public List<EmployeeResponseDto> getAll() {
        return employeeDataService.getAll();
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }

}
