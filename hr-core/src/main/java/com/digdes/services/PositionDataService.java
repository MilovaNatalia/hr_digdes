package com.digdes.services;

import com.digdes.dto.PositionDto;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityUpdateException;
import com.digdes.models.Employee;
import com.digdes.models.Position;
import com.digdes.repositories.EmployeeRepository;
import com.digdes.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PositionDataService{
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public PositionDto create (PositionDto info) {
        Position position = mapDtoToPosition(info);
        Optional<Position> existingPosition = positionRepository.findOne(Example.of(position));
        return existingPosition.map(this::mapPositionToDto).orElseGet(() -> mapPositionToDto(positionRepository.save(position)));
    }

    @Transactional
    public PositionDto update (PositionDto info) {
        Position position = mapDtoToPosition(info);
        if (positionRepository.findOne(Example.of(new Position(position.getName()))).isPresent())
            //todo: message
            throw new EntityUpdateException();
        return mapPositionToDto(positionRepository.save(position));
    }

    @Transactional
    public boolean delete(PositionDto info) {
        Position position = positionRepository.getById(info.getId());
        if (employeeRepository.findAll(Example.of(new Employee(position))).size() != 0)
            //todo: message
            throw new EntityDeleteException();
        positionRepository.delete(position);
        return !positionRepository.existsById(position.getId());
    }

    @Transactional
    public List<PositionDto> find(PositionDto searchRequest) {
        return positionRepository.findAll(
                Example.of(mapDtoToPosition(searchRequest)))
                        .stream().map(this::mapPositionToDto)
                        .collect(Collectors.toList());
    }

    @Transactional
    public Optional<PositionDto> get(Long id) {
        Optional<Position> position = positionRepository.findById(id);
        return position.map(this::mapPositionToDto);
    }

    @Transactional
    public List<PositionDto> getAll() {
        return positionRepository.findAll()
                .stream().map(this::mapPositionToDto)
                .collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }

    private PositionDto mapPositionToDto(Position position){
        return new PositionDto(position.getId(), position.getName());
    }

    private Position mapDtoToPosition(PositionDto dto){
        return new Position(dto.getId(), dto.getName());
    }
}
