package services;

import dao.impl.PositionDao;
import dto.PositionDto;

import java.util.List;
import java.util.Optional;

public class PositionDataService implements DataService<PositionDto, PositionDto>{

    private PositionDao dao = new PositionDao();

    public boolean create(PositionDto info) {
        return dao.save(info);
    }

    public boolean update(PositionDto info) {
        return dao.update(info);
    }

    public boolean delete(PositionDto info) {
        return dao.delete(info);
    }

    public List<PositionDto> find(PositionDto searchRequest) {
        return dao.simpleSearch(searchRequest);
    }

    public Optional<PositionDto> get(Long id) {
        return dao.get(id);
    }

    public List<PositionDto> getAll() {
        return dao.getAll();
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
