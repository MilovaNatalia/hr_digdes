package com.digdes.services;

import com.digdes.dto.EmployeeResponseDto;
import com.digdes.dto.EmployeeDto;
import com.digdes.exceptions.EntityCreateException;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.exceptions.EntityUpdateException;
import com.digdes.message.Message;
import com.digdes.message.impl.EmailMessage;
import com.digdes.models.*;
import com.digdes.notifier.EmailNotifier;
import com.digdes.notifier.Notifier;
import com.digdes.repositories.DepartmentRepository;
import com.digdes.repositories.EmployeeRepository;
import com.digdes.repositories.PositionRepository;
import com.digdes.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeDataService extends DataService<EmployeeDto, EmployeeResponseDto>{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Notifier notifier;

    @Transactional
    @Override
    public EmployeeResponseDto create(EmployeeDto info) {
        try {
            Employee employee = mapDtoToEmployee(info);
            Optional<Employee> existingEmployee = employeeRepository.findOne(Example.of(employee));
            if (employeeRepository.findOne(Example.of(new Employee(employee.getEmail()))).isPresent())
                throw new EntityCreateException("Unique constraint violation field Email");
            Employee newEmployee = existingEmployee.orElseGet(() -> employeeRepository.save(employee));
            notifier.sendMessage(getCreateMessage(newEmployee));
            return mapEmployeeToResponseDto(newEmployee);
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public EmployeeResponseDto update(EmployeeDto info) {
        try{
            Employee employee = mapDtoToEmployee(info);
            Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());
            if (existingEmployee.isEmpty())
                throw new EntityNotFoundException("Updated employee is not found");
            Employee updateEmployee = existingEmployee.get();
            if (employee.getEmail() != null) {
                if (employeeRepository.findOne(Example.of(new Employee(employee.getEmail()))).isPresent())
                    throw new EntityUpdateException("Unique constraint violation field Email");
            }
            Employee newEmployee = employeeRepository.save(getUpdateEmployee(employee, updateEmployee));
            for (Message message: getUpdateMessages(employee, updateEmployee, newEmployee)) {
                notifier.sendMessage(message);
            }
            return mapEmployeeToResponseDto(newEmployee);
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public boolean delete(EmployeeDto info) {
        try {
            Employee employee = mapDtoToEmployee(info);
            Optional<Employee> oldEmployee = employeeRepository.findById(employee.getId());
            if (oldEmployee.isPresent()) {
                employeeRepository.delete(employee);
                boolean result = !employeeRepository.existsById(employee.getId());
                notifier.sendMessage(getDeleteMessage(oldEmployee.get()));
                return result;
            }
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
        return true;
    }

    @Transactional
    @Override
    public List<EmployeeResponseDto> find(EmployeeDto searchRequest) {
        try {
            List<EmployeeResponseDto> employees =
                    employeeRepository.findAll(
                                    Example.of(mapDtoToEmployee(searchRequest)))
                            .stream().map(this::mapEmployeeToResponseDto)
                            .collect(Collectors.toList());
            ;
            return employees;
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Optional<EmployeeResponseDto> get(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(this::mapEmployeeToResponseDto);
    }

    @Transactional
    @Override
    public List<EmployeeResponseDto> getAll() {
        return employeeRepository.findAll()
                .stream().map(this::mapEmployeeToResponseDto)
                .collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }

    private Employee mapDtoToEmployee(EmployeeDto dto){
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setBirthDate(dto.getBirthDate());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setPatronymic(dto.getPatronymic());
        employee.setGender(dto.getGender());
        employee.setEmail(dto.getEmail());
        employee.setHead(dto.getHead());
        if (dto.getPositionId() != null) {
            Optional<Position> position = positionRepository.findById(dto.getPositionId());
            if (position.isPresent())
                employee.setPosition(position.get());
            else
                throw new EntityNotFoundException("Reference position is not found");
        }
        if (dto.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(dto.getDepartmentId());
            if (department.isPresent())
                employee.setDepartment(department.get());
            else
                throw new EntityNotFoundException("Reference department is not found");
        }
        if (dto.getUserId() != null) {
            Optional<Users> user = usersRepository.findById(dto.getUserId());
            if (user.isPresent())
                employee.setUser(user.get());
            else
                throw new EntityNotFoundException("Reference user is not found");
        }
        return employee;
    }

    private EmployeeResponseDto mapEmployeeToResponseDto (Employee employee){
        EmployeeResponseDto responseDto = new EmployeeResponseDto();
        responseDto.setId(employee.getId());
        responseDto.setFirstName(employee.getFirstName());
        responseDto.setLastName(employee.getLastName());
        responseDto.setPatronymic(employee.getPatronymic());
        responseDto.setBirthDate(employee.getBirthDate());
        responseDto.setEmail(employee.getEmail());
        responseDto.setGender(employee.getGender());
        if (employee.getPosition() != null)
            responseDto.setPositionName(employee.getPosition().getName());
        if (employee.getDepartment() != null)
            responseDto.setDepartmentName(employee.getDepartment().getName());
        return responseDto;
    }

    private Employee getUpdateEmployee (Employee info, Employee updateEmployee){
        if (info.getFirstName() != null)
            updateEmployee.setFirstName(info.getFirstName());
        if (info.getLastName() != null)
            updateEmployee.setLastName(info.getLastName());
        if (info.getPatronymic() != null)
            updateEmployee.setPatronymic(info.getPatronymic());
        if (info.getBirthDate() != null)
            updateEmployee.setBirthDate(info.getBirthDate());
        if (info.getGender() != null)
            updateEmployee.setGender(info.getGender());
        if (info.getEmail() != null)
            updateEmployee.setEmail(info.getEmail());
        if (info.getUser() != null)
            updateEmployee.setUser(info.getUser());
        if (info.getHead() != null)
            updateEmployee.setHead(info.getHead());
        if (info.getDepartment() != null)
            updateEmployee.setDepartment(info.getDepartment());
        if (info.getPosition() != null)
            updateEmployee.setPosition(info.getPosition());
        return updateEmployee;
    }

    private Message getCreateMessage(Employee employee){
        List<String> receivers = getReceivers(employee.getDepartment(), true);
        receivers.add(employee.getEmail());
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers.toArray(String[]::new));
        message.setSubject("Employee created");
        message.setBody(String.format("Hello!\n Employee \"%s %s\" created in \"%s\".", employee.getFirstName(), employee.getLastName(), employee.getDepartment().getName()));
        return message;
    }


    private List<Message> getUpdateMessages(Employee updateInfo, Employee oldEmployee, Employee newEmployee){
        List<Message> messages = new ArrayList<>();
        if (updateInfo.getHead() != null){
            String[] receivers = getReceivers(newEmployee.getDepartment(), null).toArray(String[]::new);
            messages.add(getUpdateHeadMessage(receivers, newEmployee));
        }
        if (updateInfo.getBirthDate() != null || updateInfo.getGender() != null
            || updateInfo.getEmail() != null || updateInfo.getUser() != null
                || updateInfo.getPatronymic() != null)
            messages.add(getUpdatePersonalDataMessage(new String[]{oldEmployee.getEmail()}, newEmployee));
        if ((updateInfo.getUser() == null && oldEmployee.getUser() != null)
                || (updateInfo.getPatronymic() == null && oldEmployee.getPatronymic() != null)){
            messages.add(getUpdatePersonalDataMessage(new String[]{oldEmployee.getEmail()},newEmployee));
        }
        if (updateInfo.getDepartment() != null){
            List<String> receivers = new ArrayList<>();
            receivers.add(oldEmployee.getEmail());
            receivers.addAll(getReceivers(oldEmployee.getDepartment(), true));
            receivers.addAll(getReceivers(newEmployee.getDepartment(), true));
            messages.add(getUpdateDepartmentMessage(receivers.toArray(String[]::new), newEmployee));
        }
        if (updateInfo.getFirstName() != null || updateInfo.getLastName() != null
            || updateInfo.getPosition() != null) {
            List<String> receivers = new ArrayList<>();
            receivers.add(oldEmployee.getEmail());
            receivers.addAll(getReceivers(oldEmployee.getDepartment(), null));
            messages.add(getUpdatePersonalDataMessage(receivers.toArray(String[]::new), newEmployee));
        }
        return messages;
    }

    private Message getUpdateDepartmentMessage(String[] receivers, Employee newEmployee){
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers);
        message.setSubject("Transfer of employee");
        message.setBody(String.format("Hello!\n Employee %s %s transfer to \"%s\".", newEmployee.getFirstName(),
                newEmployee.getLastName(), newEmployee.getDepartment().getName()));
        return message;
    }
    
    private Message getUpdateHeadMessage(String[] receivers, Employee newEmployee){
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers);
        message.setSubject("Add head of department");
        message.setBody(String.format("Hello!\n New head of your department is %s %s.",
                newEmployee.getFirstName(), newEmployee.getLastName()));
        return message;
    }
    
    private Message getUpdatePersonalDataMessage(String[] receivers, Employee newEmployee){
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers);
        message.setSubject("Change employee data");
        message.setBody(String.format("Hello!\n New data of employee is %s.", newEmployee.toString()));
        return message;
    }

    private Message getDeleteMessage(Employee oldEmployee){
        List<String> receivers = getReceivers(oldEmployee.getDepartment(), true);
        receivers.add(oldEmployee.getEmail());
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers.toArray(String[]::new));
        message.setSubject("Employee deleted");
        message.setBody(String.format("Hello!\n Employee \"%s %s\" deleted from \"%s\".", oldEmployee.getFirstName(), oldEmployee.getLastName(), oldEmployee.getDepartment().getName()));
        return message;
    }
}
