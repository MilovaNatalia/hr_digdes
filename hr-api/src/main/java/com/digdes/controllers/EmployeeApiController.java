package com.digdes.controllers;

import com.digdes.dto.EmployeeDto;
import com.digdes.dto.EmployeeResponseDto;
import com.digdes.exceptions.EntityCreateException;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.exceptions.EntityUpdateException;
import com.digdes.services.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> create(@RequestBody EmployeeDto info) {
        if (info.getId() != null)
            return ResponseEntity.badRequest().body("Employee id must be null for create. Use update method");
        if (info.getFirstName() == null || info.getLastName() == null
                || info.getBirthDate() == null || info.getGender() == null
                || info.getEmail() == null || info.getDepartmentId() == null
                || info.getPositionId() == null)
            return ResponseEntity.badRequest().body("Only employee patronymic field can be null. Check other fields");
        try{
            EmployeeResponseDto result = dataService.create(info);
            return ResponseEntity.ok(result);
        }
        catch (EntityNotFoundException | EntityCreateException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody EmployeeDto info) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Employee id must not be null for update. Use create method");
        if (info.getFirstName() == null && info.getLastName() == null
                && info.getBirthDate() == null && info.getGender() == null
                && info.getEmail() == null && info.getDepartmentId() == null
                && info.getPositionId() == null && info.getPatronymic() == null)
            return ResponseEntity.badRequest().body("One of updating fields must not be null");
        try{
            EmployeeResponseDto result = dataService.update(info);
            return ResponseEntity.ok(result);
        }
        catch (EntityNotFoundException | EntityUpdateException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestBody EmployeeDto info) {
        if (info.getId() == null)
            return ResponseEntity.badRequest().body("Employee id must not be null for delete");
        try {
            Boolean result = dataService.delete(info);
            return ResponseEntity.ok(result);
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
                && searchRequest.getId() == null)
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
