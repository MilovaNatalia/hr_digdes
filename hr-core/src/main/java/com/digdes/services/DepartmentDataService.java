package com.digdes.services;

import com.digdes.dto.DepartmentDto;
import com.digdes.dto.DepartmentResponseDto;
import com.digdes.models.Department;
import com.digdes.repositories.DepartmentRepository;
import com.digdes.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentDataService {

    @Autowired
    private DepartmentRepository repository;

    @Transactional
    public DepartmentResponseDto save(DepartmentDto info) {
        Department department = Mapper.mapDtoToDepartment(info);
        return Mapper.mapDepartmentToResponseDto(repository.save(department));
    }

    @Transactional
    public boolean delete(DepartmentDto info) {
        Department department = Mapper.mapDtoToDepartment(info);
        repository.delete(department);
        return !repository.existsById(department.getId());
    }

    @Transactional
    public List<DepartmentResponseDto> find(DepartmentDto searchRequest) {
        List<DepartmentResponseDto> departments =
                repository.findAll(
                                Example.of(
                                        Mapper.mapDtoToDepartment(searchRequest)))
                        .stream().map(Mapper::mapDepartmentToResponseDto)
                        .collect(Collectors.toList());;
        return departments;
    }

    @Transactional(readOnly = true)
    public Optional<DepartmentResponseDto> get(Long id) {
        Optional<Department> department = repository.findById(id);
        return department.map(Mapper::mapDepartmentToResponseDto);
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponseDto> getAll() {
        return repository.findAll()
                .stream().map(Mapper::mapDepartmentToResponseDto)
                .collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
