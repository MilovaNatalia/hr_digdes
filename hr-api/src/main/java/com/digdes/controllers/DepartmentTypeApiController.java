package com.digdes.controllers;

import com.digdes.dto.DepartmentTypeDto;
;
import com.digdes.services.DepartmentTypeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/types")
public class DepartmentTypeApiController{

    @Autowired
    private DepartmentTypeDataService dataService;

    @PostMapping(path = "/create")
    public DepartmentTypeDto create(@RequestBody DepartmentTypeDto info) {
        return dataService.save(info);
    }

    @PutMapping(path = "/update")
    public DepartmentTypeDto update(@RequestBody DepartmentTypeDto info) {
        return dataService.save(info);
    }

    @DeleteMapping(path = "/delete")
    public boolean delete(@RequestBody DepartmentTypeDto info) {
        return dataService.delete(info);
    }

    @PostMapping(path = "/find")
    public List<DepartmentTypeDto> find(@RequestBody DepartmentTypeDto searchRequest) {
        return dataService.find(searchRequest);
    }

    @GetMapping(path = "/get/{id}")
    public DepartmentTypeDto get(@PathVariable Long id) {
        Optional<DepartmentTypeDto> type = dataService.get(id);
        return type.orElseGet(DepartmentTypeDto::new);
    }

    @GetMapping(path = "/all")
    public List<DepartmentTypeDto> getAll() {
        return dataService.getAll();
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
