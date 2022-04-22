package com.digdes.controllers;

import com.digdes.dto.EmployeeDto;
import com.digdes.dto.EmployeeResponseDto;
import com.digdes.services.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeApiController{

    //todo: constraints for bad request
    @Autowired
    private EmployeeDataService dataService;

    @PostMapping(path = "/create")
    public EmployeeResponseDto create(@RequestBody EmployeeDto info) {
        return dataService.save(info);
    }

    @PutMapping(path = "/update")
    public EmployeeResponseDto update(@RequestBody EmployeeDto info) {
        return dataService.save(info);
    }

    @DeleteMapping(path = "/delete")
    public boolean delete(@RequestBody EmployeeDto info) {
        return dataService.delete(info);
    }

    @PostMapping(path = "/find")
    public List<EmployeeResponseDto> find(@RequestBody EmployeeDto searchRequest) {
        return dataService.find(searchRequest);
    }

    @GetMapping(path = "/get/{id}")
    public EmployeeResponseDto get(@PathVariable Long id) {
        Optional<EmployeeResponseDto> employee = dataService.get(id);
        return employee.orElseGet(EmployeeResponseDto::new);
    }

    @GetMapping(path = "/all")
    public List<EmployeeResponseDto> getAll() {
        return dataService.getAll();
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }

}
