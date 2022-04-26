package com.digdes.services;

import com.digdes.dto.PositionDto;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityUpdateException;
import com.digdes.models.Employee;
import com.digdes.models.Position;
import com.digdes.repositories.EmployeeRepository;
import com.digdes.repositories.PositionRepository;
import com.digdes.message.Message;
import com.digdes.message.impl.EmailMessage;
import com.digdes.notifier.EmailNotifier;
import com.digdes.notifier.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PositionDataService extends DataService<PositionDto, PositionDto>{
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Notifier notifier;

    @Transactional
    @Override
    public PositionDto create (PositionDto info) {
        Position position = mapDtoToPosition(info);
        Optional<Position> existingPosition = positionRepository.findOne(Example.of(position));
        return existingPosition.map(this::mapPositionToDto).orElseGet(() -> mapPositionToDto(positionRepository.save(position)));
    }

    @Transactional
    @Override
    public PositionDto update (PositionDto info) {
        //todo: notifier
        Position position = mapDtoToPosition(info);
        if (positionRepository.findOne(Example.of(new Position(position.getName()))).isPresent())
            throw new EntityUpdateException("Updated position is not found");
        PositionDto result = mapPositionToDto(positionRepository.save(position));
        notifier.sendMessage(getUpdateMessage(position));
        return result;
    }

    @Transactional
    @Override
    public boolean delete(PositionDto info) {
        Position position = positionRepository.getById(info.getId());
        if (employeeRepository.findAll(Example.of(new Employee(position))).size() != 0)
            throw new EntityDeleteException("Reference on this position in table employees (position_id)");
        positionRepository.delete(position);
        return !positionRepository.existsById(position.getId());
    }

    @Transactional
    @Override
    public List<PositionDto> find(PositionDto searchRequest) {
        return positionRepository.findAll(
                Example.of(mapDtoToPosition(searchRequest)))
                        .stream().map(this::mapPositionToDto)
                        .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<PositionDto> get(Long id) {
        Optional<Position> position = positionRepository.findById(id);
        return position.map(this::mapPositionToDto);
    }

    @Transactional
    @Override
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

    private Message getUpdateMessage(Position position){
        String[] receivers = employeeRepository.findAll(Example.of(new Employee(position)))
                .stream().map(Employee::getEmail).toArray(String[]::new);
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers);
        message.setSubject("Update position name");
        message.setBody(String.format("Hello!\n Your position renamed \"%s\".", position.getName()));
        return message;
    }
}
