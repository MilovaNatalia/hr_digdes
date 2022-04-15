package controllers;

import dto.DepartmentTypeDto;
import models.DepartmentType;;
import services.DepartmentTypeDataService;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Optional;


public class DepartmentTypeApiController extends HttpServlet {

    private DepartmentTypeDataService dataService = new DepartmentTypeDataService();

    public boolean create(DepartmentTypeDto info) {
        return dataService.create(info);
    }

    public boolean update(DepartmentTypeDto info) {
        return dataService.update(info);
    }

    public boolean delete(DepartmentTypeDto info) {
        return dataService.delete(info);
    }

    public List<DepartmentTypeDto> find(DepartmentTypeDto searchRequest) {
        return dataService.find(searchRequest);
    }

    public Optional<DepartmentTypeDto> get(Long id) {
        return dataService.get(id);
    }

    public List<DepartmentTypeDto> getAll() {
        return dataService.getAll();
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
