package com.digdes.services;

import com.digdes.dto.DepartmentTypeDto;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityUpdateException;
import com.digdes.models.Department;
import com.digdes.models.DepartmentType;
import com.digdes.models.Employee;
import com.digdes.repositories.DepartmentRepository;
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
    private DepartmentTypeRepository typeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public DepartmentTypeDto create (DepartmentTypeDto info) {
        DepartmentType type = mapDtoToDepartmentType(info);
        Optional<DepartmentType> existingType = typeRepository.findOne(Example.of(type));
        return existingType.map(this::mapDepartmentTypeToDto).orElseGet(() -> mapDepartmentTypeToDto(typeRepository.save(type)));
    }

    @Transactional
    public DepartmentTypeDto update (DepartmentTypeDto info) {
        DepartmentType type = mapDtoToDepartmentType(info);
        if (typeRepository.findOne(Example.of(new DepartmentType(type.getName()))).isPresent())
            //todo: message
            throw new EntityUpdateException();
        return mapDepartmentTypeToDto(typeRepository.save(type));
    }

    @Transactional
    public boolean delete(DepartmentTypeDto info) {
        DepartmentType type = typeRepository.getById(info.getId());
        if (departmentRepository.findAll(Example.of(new Department(type))).size() != 0)
        //todo: message
            throw new EntityDeleteException();
        typeRepository.delete(type);
        return !typeRepository.existsById(type.getId());
    }

    @Transactional
    public List<DepartmentTypeDto> find(DepartmentTypeDto searchRequest) {
        return typeRepository.findAll(
                Example.of(mapDtoToDepartmentType(searchRequest)))
                        .stream().map(this::mapDepartmentTypeToDto)
                        .collect(Collectors.toList());
    }

    @Transactional
    public Optional<DepartmentTypeDto> get(Long id) {
        Optional<DepartmentType> type = typeRepository.findById(id);
        return type.map(this::mapDepartmentTypeToDto);
    }

    @Transactional
    public List<DepartmentTypeDto> getAll() {
        return typeRepository.findAll()
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
