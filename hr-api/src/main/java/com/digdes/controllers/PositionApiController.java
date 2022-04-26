package com.digdes.controllers;

import com.digdes.dto.PositionDto;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.services.PositionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/positions")
class PositionApiController{

    @Autowired
    private PositionDataService dataService;

    public PositionApiController() {
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody PositionDto info) {
        if (info.getId() != null)
            return ResponseEntity.badRequest().body("Position id must be null for create. Use update method");
        return ResponseEntity.ok(dataService.create(info));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody PositionDto info) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Position id must not be null for update. Use create method");
        if (info.getName() == null)
            return ResponseEntity.badRequest().body("Position name cannot be null");
        return ResponseEntity.ok(dataService.update(info));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestBody PositionDto info) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Position id must not be null for delete");
        try {
            Boolean result = dataService.delete(info);
            return ResponseEntity.ok(result);
        }
        catch (EntityDeleteException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "/find")
    public ResponseEntity<?> find(@RequestBody PositionDto searchRequest) {
        if (searchRequest.getId() == null && searchRequest.getName() == null)
            return ResponseEntity.badRequest().body("Id and name fields cannot be null");
        return ResponseEntity.ok(dataService.find(searchRequest));
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<PositionDto> position = dataService.get(id);
        return ResponseEntity.ok(position.orElseGet(PositionDto::new));
    }
    
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(dataService.getAll());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
