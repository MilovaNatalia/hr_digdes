package com.digdes.controllers;


import com.digdes.dto.DepartmentDto;
import com.digdes.dto.DepartmentResponseDto;
import com.digdes.dto.EmployeeResponseDto;
import com.digdes.exceptions.EntityCreateException;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.exceptions.EntityUpdateException;
import com.digdes.services.DepartmentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/departments")
public class DepartmentApiController {
    @Autowired
    private DepartmentDataService dataService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody DepartmentDto info, Authentication authentication) {
        if (info.getId() != null)
            return ResponseEntity.badRequest().body("Department id must be null for create. Use update method");
        if (info.getName() == null || info.getTypeId() == null)
            return ResponseEntity.badRequest().body("Name and type id fields cannot be null");
        try {
            if (dataService.checkUser(authentication.getName(), info.getParentId())) {
                DepartmentResponseDto result = dataService.create(info);
                return ResponseEntity.ok(result);
            }
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("User %s cannot moderate this department", authentication.getName()));
        }
        catch (EntityNotFoundException | EntityCreateException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody DepartmentDto info, Authentication authentication) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Department id must not be null for update. Use create method");
        if (info.getName() == null && info.getParentId() == null
                && info.getTypeId() == null && info.getModeratorId() == null)
            return ResponseEntity.badRequest().body("One of updating fields must not be null");
        try{
            if (dataService.checkUser(authentication.getName(), info.getParentId())) {
                DepartmentResponseDto result = dataService.update(info);
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("User %s cannot moderate this department", authentication.getName()));
        }
        catch (EntityNotFoundException | EntityUpdateException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestBody DepartmentDto info, Authentication authentication) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Department id must not be null for delete");
        try {
            if (dataService.checkUser(authentication.getName(), info.getParentId())) {
                Boolean result = dataService.delete(info);
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("User %s cannot moderate this department", authentication.getName()));
        }
        catch (EntityNotFoundException | EntityDeleteException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "/find")
    public ResponseEntity<?> find(@RequestBody DepartmentDto searchRequest) {
        if (searchRequest.getName() == null && searchRequest.getParentId() == null && searchRequest.getTypeId() == null
                && searchRequest.getId() == null && searchRequest.getModeratorId() == null)
            return ResponseEntity.badRequest().body("One of fields must not be null");
        try {
            List<DepartmentResponseDto> result = dataService.find(searchRequest);
            return ResponseEntity.ok(result);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<DepartmentResponseDto> department = dataService.get(id);
        return ResponseEntity.ok(department.orElseGet(DepartmentResponseDto::new));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(dataService.getAll());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
