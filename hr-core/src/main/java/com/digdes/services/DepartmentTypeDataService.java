package com.digdes.services;

import com.digdes.dto.DepartmentTypeDto;
import com.digdes.models.DepartmentType;
import com.digdes.repositories.DepartmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentTypeDataService{
    @Autowired
    private DepartmentTypeRepository repository;

    @Transactional
    public DepartmentTypeDto save (DepartmentTypeDto info) {
        DepartmentType type = mapDtoToDepartmentType(info);
        return mapDepartmentTypeToDto(repository.save(type));
    }

    @Transactional
    public boolean delete(DepartmentTypeDto info) {
        DepartmentType type = mapDtoToDepartmentType(info);
        repository.delete(type);
        return !repository.existsById(type.getId());
    }

    @Transactional
    public List<DepartmentTypeDto> find(DepartmentTypeDto searchRequest) {
        return repository.findAll(
                Example.of(mapDtoToDepartmentType(searchRequest)))
                        .stream().map(this::mapDepartmentTypeToDto)
                        .collect(Collectors.toList());
    }

    @Transactional
    public Optional<DepartmentTypeDto> get(Long id) {
        Optional<DepartmentType> type = repository.findById(id);
        return type.map(this::mapDepartmentTypeToDto);
    }

    @Transactional
    public List<DepartmentTypeDto> getAll() {
        return repository.findAll()
                .stream().map(this::mapDepartmentTypeToDto)
                .collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }

    private DepartmentTypeDto mapDepartmentTypeToDto(DepartmentType type){
        return new DepartmentTypeDto(type.getId(), type.getName());
    }

    private DepartmentType mapDtoToDepartmentType(DepartmentTypeDto dto){
        return new DepartmentType(dto.getId(), dto.getName());
    }
}
