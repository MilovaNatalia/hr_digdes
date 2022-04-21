package com.digdes.services;

import com.digdes.dto.PositionDto;
import com.digdes.models.Position;
import com.digdes.repositories.PositionRepository;
import com.digdes.util.Mapper;
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
    private PositionRepository repository;

    @Transactional
    public PositionDto save (PositionDto info) {
        Position position = Mapper.mapDtoToPosition(info);
        return Mapper.mapPositionToDto(repository.save(position));
    }

    @Transactional
    public boolean delete(PositionDto info) {
        Position position = Mapper.mapDtoToPosition(info);
        repository.delete(position);
        return !repository.existsById(position.getId());
    }

    @Transactional
    public List<PositionDto> find(PositionDto searchRequest) {
        return repository.findAll(
                Example.of(
                        Mapper.mapDtoToPosition(searchRequest)))
                        .stream().map(Mapper::mapPositionToDto)
                        .collect(Collectors.toList());
    }

    @Transactional
    public Optional<PositionDto> get(Long id) {
        Optional<Position> position = repository.findById(id);
        return position.map(Mapper::mapPositionToDto);
    }

    @Transactional
    public List<PositionDto> getAll() {
        return repository.findAll()
                .stream().map(Mapper::mapPositionToDto)
                .collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }
}
