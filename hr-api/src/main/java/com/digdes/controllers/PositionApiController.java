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

    //todo: constraints for bad request
    @Autowired
    private PositionDataService dataService;

    public PositionApiController() {
    }

    //todo: test with null id? not null id and null other fields?
    //todo: need null id (existent id + new name = update, non existent id + new name = create with next id)
    //todo: test with null body = auto bad request
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody PositionDto info) {
        if (info.getId() != null)
            return ResponseEntity.badRequest().body("Position id must be null for create. Use update method");
        return ResponseEntity.ok(dataService.create(info));
    }

    //todo: test with null id + existing name = error unique by db, null id + non existent name = new object
    // todo: not null id and null other fields = not null name constraint in db
    //todo: test with null body = auto bad request
    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody PositionDto info) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Position id must not be null for update. Use create method");
        if (info.getName() == null)
            return ResponseEntity.badRequest().body("Position name cannot be null");
        return ResponseEntity.ok(dataService.update(info));
    }


    //todo: test with null name = ok, both null = illegal argument exception, name not need for delete
    //todo: don delete because use in employees
    //todo: test with null body = auto 400 bad request
    //todo: delete non existent = true
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

    //todo: test with null id = element by name? null name = get by id? null both of them = get all wrong, need bad request ?
    //todo: existing id + name = list with one element, non existent id/name = empty list
    //todo: test with null body = auto 400 bad request
    @PostMapping(path = "/find")
    public ResponseEntity<?> find(@RequestBody PositionDto searchRequest) {
        if (searchRequest.getId() == null && searchRequest.getName() == null)
            return ResponseEntity.badRequest().body("Id and name fields cannot be null");
        return ResponseEntity.ok(dataService.find(searchRequest));
    }

    //todo: test with null id == auto bad request
    //todo:  test with not null body == body ignored
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<PositionDto> position = dataService.get(id);
        return ResponseEntity.ok(position.orElseGet(PositionDto::new));
    }

    //todo: test with not null body == body ignored
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(dataService.getAll());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
