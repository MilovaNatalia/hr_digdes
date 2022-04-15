package services;

import dao.impl.DepartmentTypeDao;
import dto.DepartmentTypeDto;

import java.util.List;
import java.util.Optional;

public class DepartmentTypeDataService implements DataService<DepartmentTypeDto,DepartmentTypeDto>{

    DepartmentTypeDao dao = new DepartmentTypeDao();

    public boolean create(DepartmentTypeDto info) {
        return dao.save(info);
    }

    public boolean update(DepartmentTypeDto info) {
        return dao.update(info);
    }

    public boolean delete(DepartmentTypeDto info) {
        return dao.delete(info);
    }

    public List<DepartmentTypeDto> find(DepartmentTypeDto searchRequest) {
        return dao.simpleSearch(searchRequest);
    }

    public Optional<DepartmentTypeDto> get(Long id) {
        return dao.get(id);
    }

    public List<DepartmentTypeDto> getAll() {
        return dao.getAll();
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
