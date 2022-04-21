package com.digdes.services;

import com.digdes.dto.DepartmentTypeDto;
import com.digdes.models.DepartmentType;
import com.digdes.repositories.DepartmentTypeRepository;
import com.digdes.util.Mapper;
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
        DepartmentType type = Mapper.mapDtoToDepartmentType(info);
        return Mapper.mapDepartmentTypeToDto(repository.save(type));
    }

    @Transactional
    public boolean delete(DepartmentTypeDto info) {
        DepartmentType type = Mapper.mapDtoToDepartmentType(info);
        repository.delete(type);
        return !repository.existsById(type.getId());
    }

    @Transactional
    public List<DepartmentTypeDto> find(DepartmentTypeDto searchRequest) {
        return repository.findAll(
                Example.of(
                        Mapper.mapDtoToDepartmentType(searchRequest)))
                        .stream().map(Mapper::mapDepartmentTypeToDto)
                        .collect(Collectors.toList());
    }

    @Transactional
    public Optional<DepartmentTypeDto> get(Long id) {
        Optional<DepartmentType> type = repository.findById(id);
        return type.map(Mapper::mapDepartmentTypeToDto);
    }

    @Transactional
    public List<DepartmentTypeDto> getAll() {
        return repository.findAll()
                .stream().map(Mapper::mapDepartmentTypeToDto)
                .collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
