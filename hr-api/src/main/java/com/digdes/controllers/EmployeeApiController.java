package com.digdes.controllers;

import com.digdes.dto.EmployeeDto;
import com.digdes.dto.EmployeeResponseDto;
import com.digdes.exceptions.EntityCreateException;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.exceptions.EntityUpdateException;
import com.digdes.services.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeApiController{
    @Autowired
    private EmployeeDataService dataService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody EmployeeDto info, Authentication authentication) {
        if (info.getId() != null)
            return ResponseEntity.badRequest().body("Employee id must be null for create. Use update method");
        if (info.getFirstName() == null || info.getLastName() == null
                || info.getBirthDate() == null || info.getGender() == null
                || info.getEmail() == null || info.getDepartmentId() == null
                || info.getPositionId() == null || info.getHead() == null)
            return ResponseEntity.badRequest().body("Only employee patronymic and userId fields can be null. Check other fields");
        try{
            if (dataService.checkUser(authentication.getName(), info.getDepartmentId())) {
                EmployeeResponseDto result = dataService.create(info);
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("User %s cannot moderate this employee", authentication.getName()));
        }
        catch (EntityNotFoundException | EntityCreateException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody EmployeeDto info, Authentication authentication) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Employee id must not be null for update. Use create method");
        if (info.getFirstName() == null && info.getLastName() == null
                && info.getBirthDate() == null && info.getGender() == null
                && info.getEmail() == null && info.getDepartmentId() == null
                && info.getPositionId() == null && info.getPatronymic() == null
                && info.getHead() == null && info.getUserId() == null)
            return ResponseEntity.badRequest().body("One of updating fields must not be null");
        try{
            if (dataService.checkUser(authentication.getName(), info.getDepartmentId())) {
                EmployeeResponseDto result = dataService.update(info);
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("User %s cannot moderate this employee", authentication.getName()));
        }
        catch (EntityNotFoundException | EntityUpdateException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestBody EmployeeDto info, Authentication authentication) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Employee id must not be null for delete");
        try {
            if (dataService.checkUser(authentication.getName(), info.getDepartmentId())) {
                Boolean result = dataService.delete(info);
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("User %s cannot moderate this employee", authentication.getName()));
        }
        catch (EntityNotFoundException | EntityDeleteException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "/find")
    public ResponseEntity<?> find(@RequestBody EmployeeDto searchRequest) {
        if (searchRequest.getFirstName() == null && searchRequest.getLastName() == null
                && searchRequest.getBirthDate() == null && searchRequest.getGender() == null
                && searchRequest.getEmail() == null && searchRequest.getDepartmentId() == null
                && searchRequest.getPositionId() == null && searchRequest.getPatronymic() == null
                && searchRequest.getId() == null && searchRequest.getHead() == null
                && searchRequest.getUserId() == null)
            return ResponseEntity.badRequest().body("One of fields must not be null");
        try {
            List<EmployeeResponseDto> result = dataService.find(searchRequest);
            return ResponseEntity.ok(result);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<EmployeeResponseDto> employee = dataService.get(id);
        return ResponseEntity.ok(employee.orElseGet(EmployeeResponseDto::new));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(dataService.getAll());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }

}
