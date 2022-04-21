package com.digdes.controllers;

import com.digdes.dto.PositionDto;
import com.digdes.services.PositionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/positions")
class PositionApiController{
    @Autowired
    private PositionDataService dataService;

    public PositionApiController() {
    }

    @PostMapping(path = "/create")
    public PositionDto create(@RequestBody PositionDto info) {
        return dataService.save(info);
    }

    @PutMapping(path = "/update")
    public PositionDto update(@RequestBody PositionDto info) {
        return dataService.save(info);
    }

    @DeleteMapping(path = "/delete")
    public boolean delete(@RequestBody PositionDto info) {
        return dataService.delete(info);
    }

    @PostMapping(path = "/find")
    public List<PositionDto> find(@RequestBody PositionDto searchRequest) {
        return dataService.find(searchRequest);
    }

    @GetMapping(path = "/get/{id}")
    public PositionDto get(@PathVariable Long id) {
        Optional<PositionDto> position = dataService.get(id);
        return position.orElseGet(PositionDto::new);
    }

    @GetMapping(path = "/all")
    public List<PositionDto> getAll() {
        return dataService.getAll();
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
