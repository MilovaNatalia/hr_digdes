package controllers;

import dto.DepartmentDto;
import dto.DepartmentResponseDto;
import services.DepartmentDataService;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Optional;

public class DepartmentApiController extends HttpServlet {

    private final DepartmentDataService departmentDataService = new DepartmentDataService();

    public boolean create(DepartmentDto info) {
        return departmentDataService.create(info);
    }

    public boolean update(DepartmentDto info) {
        return departmentDataService.update(info);
    }

    public boolean delete(DepartmentDto info) {
        return departmentDataService.delete(info);
    }

    public List<DepartmentResponseDto> find(DepartmentDto searchRequest) {
        return departmentDataService.find(searchRequest);
    }

    public Optional<DepartmentResponseDto> get(Long id) {
        return departmentDataService.get(id);
    }

    public List<DepartmentResponseDto> getAll() {
        return departmentDataService.getAll();
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
