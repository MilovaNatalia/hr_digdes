package com.digdes.services;

import com.digdes.dto.DepartmentDto;
import com.digdes.dto.DepartmentResponseDto;
import com.digdes.dto.EmployeeResponseDto;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.message.Message;
import com.digdes.message.impl.EmailMessage;
import com.digdes.models.*;
import com.digdes.notifier.EmailNotifier;
import com.digdes.notifier.Notifier;
import com.digdes.repositories.DepartmentRepository;
import com.digdes.repositories.DepartmentTypeRepository;
import com.digdes.repositories.EmployeeRepository;
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
public class DepartmentDataService extends DataService<DepartmentDto, DepartmentResponseDto>{

    //todo: transactonal
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentTypeRepository typeRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Notifier notifier;

    @Transactional
    public DepartmentResponseDto create(DepartmentDto info) {
        try {
            Department department = mapDtoToDepartment(info);
            Optional<Department> existingDepartment = departmentRepository.findOne(Example.of(department));
            Department newDepartment = existingDepartment.orElseGet(() -> (departmentRepository.save(department)));
            if (department.getParent() != null)
                notifier.sendMessage(getCreateMessage(newDepartment));
            return mapDepartmentToResponseDto(newDepartment);
        }
        catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }


    @Transactional
    public DepartmentResponseDto update(DepartmentDto info) {
        try {
            Department department = mapDtoToDepartment(info);
            Optional<Department> existingDepartment = departmentRepository.findById(department.getId());
            if (existingDepartment.isEmpty())
                throw new EntityNotFoundException("Updated department is not found");
            Department updateDepartment = existingDepartment.get();
            Department newDepartment = departmentRepository.save(getUpdateDepartment(department, updateDepartment));
            for (Message message : getUpdateMessages(department, updateDepartment, newDepartment)) {
                notifier.sendMessage(message);
            }
            return mapDepartmentToResponseDto(newDepartment);
        }
        catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public boolean delete(DepartmentDto info) {
        try {
            Department department = mapDtoToDepartment(info);
            if (employeeRepository.findAll(Example.of(new Employee(department))).size() != 0)
                throw new EntityDeleteException("Reference on this department in table employees (department_id)");
            if (departmentRepository.findAll(Example.of(new Department(department))).size() != 0)
                throw new EntityDeleteException("Reference on this department in table department (parent_id)");
            Optional<Department> oldDepartment = departmentRepository.findById(department.getId());
            if (oldDepartment.isPresent()) {
                departmentRepository.delete(department);
                boolean result = !departmentRepository.existsById(department.getId());
                if (department.getParent() != null)
                    notifier.sendMessage(getDeleteMessage(department));
                return result;
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
        return true;
    }

    @Transactional
    public List<DepartmentResponseDto> find(DepartmentDto searchRequest) {
        try {
            return departmentRepository.findAll(
                            Example.of(
                                    mapDtoToDepartment(searchRequest)))
                    .stream().map(this::mapDepartmentToResponseDto)
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<DepartmentResponseDto> get(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.map(this::mapDepartmentToResponseDto);
    }


    @Transactional(readOnly = true)
    @Override
    public List<DepartmentResponseDto> getAll() {
        return departmentRepository.findAll()
                .stream().map(this::mapDepartmentToResponseDto)
                .collect(Collectors.toList());
    }

    public boolean loadFromFile(String fileName) {
        return false;
    }

    private Department mapDtoToDepartment(DepartmentDto dto){
        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        if (dto.getTypeId() != null) {
            Optional<DepartmentType> type = typeRepository.findById(dto.getTypeId());
            if (type.isPresent())
                department.setType(type.get());
            else
                throw new EntityNotFoundException("Reference department type is not found");
        }
        if (dto.getModeratorId() != null){
            Optional<Users> moderator = usersRepository.findById(dto.getModeratorId());
            if (moderator.isPresent())
                department.setModerator(moderator.get());
            else
                throw new EntityNotFoundException("Reference moderator is not found");
        }
        if (dto.getParentId() != null) {
            Optional<Department> parent = departmentRepository.findById(dto.getParentId());
            if (parent.isPresent())
                department.setParent(parent.get());
            else
                throw new EntityNotFoundException("Reference parent is not found");
        }
        return department;
    }

    private DepartmentResponseDto mapDepartmentToResponseDto(Department department){
        DepartmentResponseDto responseDto = new DepartmentResponseDto();
        responseDto.setId(department.getId());
        responseDto.setName(department.getName());
        responseDto.setTypeName(department.getType().getName());
        List<String> headsNames = employeeRepository
                .findAll(Example.of(new Employee(department, true)))
                .stream().map(employee -> {
                    String fullHeadName =employee.getFirstName() + " " + employee.getLastName();
                    if (employee.getPatronymic() != null)
                        fullHeadName += " " +  employee.getPatronymic();
                    return fullHeadName;
                }).collect(Collectors.toList());
        responseDto.setHeadNames(headsNames);
        if (department.getParent() != null)
            responseDto.setParentName(department.getParent().getName());
        List<EmployeeResponseDto> employees = mapEmployeesToResponseDtos(employeeRepository.findAll(Example.of(new Employee(department))));
        responseDto.setEmployees(employees);
        return responseDto;
    }

    private List<EmployeeResponseDto> mapEmployeesToResponseDtos (List<Employee> employees){
        List<EmployeeResponseDto> responseDtos = employees.stream().map(employee -> {
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
        }).collect(Collectors.toList());
        return responseDtos;
    }

    private Department getUpdateDepartment(Department info, Department updateDepartment){
        if (info.getName() != null)
            updateDepartment.setName(info.getName());
        if (info.getModerator() != null)
            updateDepartment.setModerator(info.getModerator());
        if (info.getType() != null)
            updateDepartment.setType(info.getType());
        if (info.getParent() != null)
            updateDepartment.setParent(info.getParent());
        if (info.getEmployees() != null)
            updateDepartment.setEmployees(info.getEmployees());
        return updateDepartment;
    }

    //todo: create department --- heads of parent if not null
    private Message getCreateMessage(Department department){
        String[] receivers = getReceivers(department, true).toArray(String[]::new);
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers);
        message.setSubject("Subdivision created in your department");
        message.setBody(String.format("Hello!\n Subdivision \"%s\" created in your department.", department.getName()));
        return message;
    }

    //todo: update department --
    private List<Message> getUpdateMessages(Department updateInfo, Department updateDepartment, Department newDepartment) {
        //todo: check empty moders
        List<Message> messages = new ArrayList<>();
        // todo: name, type, parent, emplyees --- all employees
        if (updateInfo.getName() != null || updateInfo.getType() != null
                || updateInfo.getParent() != null) {
            String[] receivers = getReceivers(updateInfo, null).toArray(String[]::new);
            messages.add(getAllEmployeesMessage(receivers, newDepartment));
        }
        // todo: moderator --- old moderator, new if not null
        if (updateInfo.getModerator() != null)
            messages.add(getUpdateModeratorMessage(updateInfo, newDepartment, newDepartment));
        else if (updateDepartment.getModerator() != null)
            messages.add(getUpdateModeratorMessage(updateInfo, newDepartment, newDepartment));
        return messages;
    }

    private Message getUpdateModeratorMessage(Department updateInfo, Department updateDepartment, Department newDepartment){
        List<String> receivers = new ArrayList<>();
        if (updateInfo.getModerator() != null)
            employeeRepository
                    .findEmployeeByUser(updateInfo.getModerator())
                    .map(Employee::getEmail)
                    .ifPresent(receivers::add);
        if (updateDepartment.getModerator() != null)
            employeeRepository
                    .findEmployeeByUser(updateDepartment.getModerator())
                    .map(Employee::getEmail)
                    .ifPresent(receivers::add);
        return getUpdateModeratorMessage(receivers.toArray(String[]::new), newDepartment);
    }

    private Message getAllEmployeesMessage(String[] receivers, Department newDepartment){
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers);
        message.setSubject("Change data of department");
        message.setBody(String.format("Hello!\n New data of department is %s.", newDepartment.toString()));
        return message;
    }

    private Message getUpdateModeratorMessage(String[] receivers, Department newDepartment){
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers);
        message.setSubject("Change moderator of department");
        message.setBody(String.format("Hello!\n New moderator of department \"%s\" is \"%s\".", newDepartment.getName(), newDepartment.getModerator().getUsername()));
        return message;
    }

    //todo: delete department --- heads of parent if not null
    private Message getDeleteMessage(Department oldDepartment){
        String[] receivers = getReceivers(oldDepartment, true).toArray(String[]::new);
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(receivers);
        message.setSubject("Subdivision deleted from your department");
        message.setBody(String.format("Hello!\n Subdivision \"%s\" deleted from your department.", oldDepartment.getName()));
        return message;
    }

}
