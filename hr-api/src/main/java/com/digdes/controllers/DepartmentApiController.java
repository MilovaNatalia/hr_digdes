package com.digdes.controllers;


import com.digdes.dto.DepartmentDto;
import com.digdes.dto.DepartmentResponseDto;
import com.digdes.services.DepartmentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentApiController {
//todo: constraints for bad request
    @Autowired
    private DepartmentDataService dataService;

    @PostMapping(path = "/create")
    public DepartmentResponseDto create(@RequestBody DepartmentDto info) {
        return dataService.save(info);
    }


    @PutMapping(path = "/update")
    public ResponseEntity<DepartmentResponseDto> update(@RequestBody DepartmentDto info) {
        DepartmentResponseDto dto = dataService.save(info);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping(path = "/delete")
    public boolean delete(@RequestBody DepartmentDto info) {
        return dataService.delete(info);
    }


    @PostMapping(path = "/find")
    public List<DepartmentResponseDto> find(@RequestBody DepartmentDto searchRequest) {
        return dataService.find(searchRequest);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<DepartmentResponseDto> get(@PathVariable Long id) {
        DepartmentResponseDto department = dataService.get(id);
        return ResponseEntity.ok(department);
    }


    @GetMapping(path = "/all")
    public List<DepartmentResponseDto> getAll() {
        return dataService.getAll();
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
