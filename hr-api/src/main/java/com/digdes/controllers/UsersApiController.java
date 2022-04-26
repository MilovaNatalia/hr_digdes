package com.digdes.controllers;

import com.digdes.dto.UsersDto;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.services.UsersDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class UsersApiController {

    @Autowired
    private UsersDataService dataService;


    public UsersApiController() {
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody UsersDto info) {
        if (info.getUsername() == null)
            return ResponseEntity.badRequest().body("Username must not be null for create");
        return ResponseEntity.ok(dataService.create(info));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody UsersDto info) {
        if (info.getUsername() == null)
            return ResponseEntity.badRequest().body("Username must not be null for update");
        if (info.getRoleId() == null)
            return ResponseEntity.badRequest().body("Role id cannot be null");
        return ResponseEntity.ok(dataService.update(info));
    }


    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestBody UsersDto info) {
        if (info.getUsername() == null)
            return ResponseEntity.badRequest().body("Username must not be null for delete");
        try {
            Boolean result = dataService.delete(info);
            return ResponseEntity.ok(result);
        }
        catch (EntityDeleteException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<UsersDto> user = dataService.get(id);
        return ResponseEntity.ok(user.orElseGet(UsersDto::new));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(dataService.getAll());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
