package com.digdes.controllers;


import com.digdes.dto.DepartmentDto;
import com.digdes.dto.DepartmentResponseDto;
import com.digdes.exceptions.EntityCreateException;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.exceptions.EntityUpdateException;
import com.digdes.services.DepartmentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/departments")
public class DepartmentApiController {
//todo: constraints for bad request
//todo: exceptions
    @Autowired
    private DepartmentDataService dataService;



    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody DepartmentDto info, Authentication authentication) {
        if (info.getId() != null)
            return ResponseEntity.badRequest().body("Department id must be null for create. Use update method");
        if (info.getName() == null || info.getTypeId() == null)
            return ResponseEntity.badRequest().body("Name and type id fields cannot be null");
        try{
            DepartmentResponseDto result = dataService.create(info);
            return ResponseEntity.ok(result);
        }
        catch (EntityNotFoundException | EntityCreateException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody DepartmentDto info) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Department id must not be null for update. Use create method");
        if (info.getName() == null && info.getParentId() == null
                && info.getHeadId() == null && info.getTypeId() == null
                && info.getModeratorId() == null)
            return ResponseEntity.badRequest().body("One of updating fields must not be null");
        try{
            DepartmentResponseDto result = dataService.update(info);
            return ResponseEntity.ok(result);
        }
        catch (EntityNotFoundException | EntityUpdateException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    //todo: test delete trigger from path
    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestBody DepartmentDto info) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Department id must not be null for delete");
        try {
            Boolean result = dataService.delete(info);
            return ResponseEntity.ok(result);
        }
        catch (EntityNotFoundException | EntityDeleteException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "/find")
    public ResponseEntity<?> find(@RequestBody DepartmentDto searchRequest) {
        if (searchRequest.getName() == null && searchRequest.getParentId() == null
                && searchRequest.getHeadId() == null && searchRequest.getTypeId() == null
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
        DepartmentResponseDto department = dataService.get(id);
        return ResponseEntity.ok(department);
    }

    @GetMapping(path = "/get/sub_departments")
    public ResponseEntity<?> getSubDepartments(@RequestParam(name = "id") Long parentId) {
        return ResponseEntity.ok(dataService.getSubDepartments(parentId));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(dataService.getAll());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
