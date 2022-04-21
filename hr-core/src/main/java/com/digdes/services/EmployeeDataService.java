package com.digdes.services;

import com.digdes.dto.EmployeeResponseDto;
import com.digdes.dto.EmployeeDto;
import com.digdes.models.Employee;
import com.digdes.repositories.EmployeeRepository;
import com.digdes.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeDataService {

    @Autowired
    private EmployeeRepository repository;

    @Transactional
    public EmployeeResponseDto save(EmployeeDto info) {
        Employee employee = Mapper.mapDtoToEmployee(info);
        return Mapper.mapEmployeeToResponseDto(repository.save(employee));
    }

    @Transactional
    public boolean delete(EmployeeDto info) {
        Employee employee = Mapper.mapDtoToEmployee(info);
        repository.delete(employee);
        return !repository.existsById(employee.getId());
    }

    @Transactional
    public List<EmployeeResponseDto> find(EmployeeDto searchRequest) {
        List<EmployeeResponseDto> employees =
                repository.findAll(
                                Example.of(
                                        Mapper.mapDtoToEmployee(searchRequest)))
                        .stream().map(Mapper::mapEmployeeToResponseDto)
                        .collect(Collectors.toList());;
        return employees;
    }

    @Transactional
    public Optional<EmployeeResponseDto> get(Long id) {
        Optional<Employee> employee = repository.findById(id);
        return employee.map(Mapper::mapEmployeeToResponseDto);
    }

    @Transactional
    public List<EmployeeResponseDto> getAll() {
        return repository.findAll()
                .stream().map(Mapper::mapEmployeeToResponseDto)
                .collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }


}
