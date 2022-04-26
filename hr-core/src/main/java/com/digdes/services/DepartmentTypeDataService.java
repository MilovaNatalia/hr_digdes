package com.digdes.services;

import com.digdes.dto.DepartmentTypeDto;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityUpdateException;
import com.digdes.message.Message;
import com.digdes.message.impl.EmailMessage;
import com.digdes.models.Department;
import com.digdes.models.DepartmentType;
import com.digdes.models.Employee;
import com.digdes.notifier.EmailNotifier;
import com.digdes.notifier.Notifier;
import com.digdes.repositories.DepartmentRepository;
import com.digdes.repositories.DepartmentTypeRepository;
import com.digdes.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentTypeDataService extends DataService<DepartmentTypeDto, DepartmentTypeDto>{
    @Autowired
    private DepartmentTypeRepository typeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Notifier notifier;

    @Transactional
    @Override
    public DepartmentTypeDto create (DepartmentTypeDto info) {
        DepartmentType type = mapDtoToDepartmentType(info);
        Optional<DepartmentType> existingType = typeRepository.findOne(Example.of(type));
        return existingType.map(this::mapDepartmentTypeToDto).orElseGet(() -> mapDepartmentTypeToDto(typeRepository.save(type)));
    }

    @Transactional
    @Override
    public DepartmentTypeDto update (DepartmentTypeDto info) {
        DepartmentType type = mapDtoToDepartmentType(info);
        if (typeRepository.findOne(Example.of(new DepartmentType(type.getName()))).isPresent())
            throw new EntityUpdateException("This name of department type already exists");
        DepartmentTypeDto departmentTypeDto = mapDepartmentTypeToDto(typeRepository.save(type));
        notifier.sendMessage(getUpdateMessage(type));
        return departmentTypeDto;
    }

    @Transactional
    @Override
    public boolean delete(DepartmentTypeDto info) {
        Optional<DepartmentType> type = typeRepository.findById(info.getId());
        if (type.isPresent()) {
            if (departmentRepository.findAll(Example.of(new Department(type.get()))).size() != 0)
                throw new EntityDeleteException("Reference on this department type in table department (type_id)");
            typeRepository.delete(type.get());
            return !typeRepository.existsById(type.get().getId());
        }
        return false;
    }

    @Transactional
    @Override
    public List<DepartmentTypeDto> find(DepartmentTypeDto searchRequest) {
        return typeRepository.findAll(
                Example.of(mapDtoToDepartmentType(searchRequest)))
                        .stream().map(this::mapDepartmentTypeToDto)
                        .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<DepartmentTypeDto> get(Long id) {
        Optional<DepartmentType> type = typeRepository.findById(id);
        return type.map(this::mapDepartmentTypeToDto);
    }

    @Transactional
    @Override
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

    private Message getUpdateMessage(DepartmentType type){
        List<Department> departments = departmentRepository.findAll(Example.of(new Department(type)));
        List<String> receivers = new ArrayList<>();
        for (Department department : departments) {
            receivers.addAll(employeeRepository.findAll(Example.of(new Employee(department)))
                    .stream().map(Employee::getEmail).collect(Collectors.toList()));
        }
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers.toArray(String[]::new));
        message.setSubject("Update department type name");
        message.setBody(String.format("Hello!\n Your department type renamed \"%s\".", type.getName()));
        return message;
    }
}
