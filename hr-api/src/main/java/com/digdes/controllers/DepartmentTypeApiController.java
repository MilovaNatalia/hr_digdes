package com.digdes.controllers;

import com.digdes.dto.DepartmentTypeDto;
;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.services.DepartmentTypeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/types")
public class DepartmentTypeApiController{
    @Autowired
    private DepartmentTypeDataService dataService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody DepartmentTypeDto info) {
        if (info.getId() != null)
            return ResponseEntity.badRequest().body("Department type id must be null for create. Use update method");
        return ResponseEntity.ok(dataService.create(info));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody DepartmentTypeDto info) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Department type id must not be null for update. Use create method");
        if (info.getName() == null)
            return ResponseEntity.badRequest().body("Department type name cannot be null");
        return ResponseEntity.ok(dataService.update(info));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestBody DepartmentTypeDto info) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Department type id must not be null for delete");
        try {
            Boolean result = dataService.delete(info);
            return ResponseEntity.ok(result);
        }
        catch (EntityDeleteException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "/find")
    public ResponseEntity<?> find(@RequestBody DepartmentTypeDto searchRequest) {
        if (searchRequest.getId() == null && searchRequest.getName() == null)
            return ResponseEntity.badRequest().body("Id and name fields cannot be null");
        return ResponseEntity.ok(dataService.find(searchRequest));
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<DepartmentTypeDto> type = dataService.get(id);
        return ResponseEntity.ok(type.orElseGet(DepartmentTypeDto::new));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(dataService.getAll());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
