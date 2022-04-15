package controllers;

import dto.PositionDto;
import services.PositionDataService;

import java.util.List;
import java.util.Optional;


public class PositionApiController{

    private final PositionDataService dataService = new PositionDataService();

    public boolean create(PositionDto info) {
        return dataService.create(info);
    }

    public boolean update(PositionDto info) {
        return dataService.update(info);
    }

    public boolean delete(PositionDto info) {
        return dataService.delete(info);
    }

    public List<PositionDto> find(PositionDto searchRequest) {
        return dataService.find(searchRequest);
    }

    public Optional<PositionDto> get(Long id) {
        return dataService.get(id);
    }

    public List<PositionDto> getAll() {
        return dataService.getAll();
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
