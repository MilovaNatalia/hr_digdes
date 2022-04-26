package com.digdes.services;

import com.digdes.dto.UsersDto;
import com.digdes.exceptions.EntityDeleteException;
import com.digdes.exceptions.EntityNotFoundException;
import com.digdes.message.Message;
import com.digdes.message.impl.EmailMessage;
import com.digdes.models.Employee;
import com.digdes.models.Role;
import com.digdes.models.Users;
import com.digdes.notifier.EmailNotifier;
import com.digdes.notifier.Notifier;
import com.digdes.repositories.EmployeeRepository;
import com.digdes.repositories.RoleRepository;
import com.digdes.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersDataService{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Notifier notifier;

    public UsersDto create(UsersDto info) {
        try {
            Users user = mapDtoToUsers(info);
            Optional<Users> existingUser = usersRepository.findOne(Example.of(user));
            Users newUser = existingUser.orElseGet(() -> usersRepository.save(user));
            return mapUserToDto(newUser);
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    public UsersDto update(UsersDto info) {
        try {
            Users user = mapDtoToUsers(info);
            Optional<Users> existingUser = usersRepository.findOne(Example.of(user));
            if (existingUser.isEmpty())
                throw new EntityNotFoundException("Updated user is not found");
            Users updateUser = existingUser.get();
            Users newUser = usersRepository.save(getUpdateUser(user, updateUser));
            Optional<Employee> receiver = employeeRepository.findEmployeeByUser(user);
            if (receiver.isPresent() && receiver.get().getEmail() != null)
                notifier.sendMessage(getUpdateMessage(newUser, receiver.get().getEmail()));
            return mapUserToDto(newUser);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    public boolean delete(UsersDto info) {
        try {
            Users user = mapDtoToUsers(info);
            if (employeeRepository.findAll(Example.of(new Employee(user))).size() != 0)
                throw new EntityDeleteException("Reference on this user in table employees (user_id)");
            Optional<Users> oldUser = usersRepository.findById(user.getUsername());
            if (oldUser.isPresent()) {
                usersRepository.delete(user);
                return !usersRepository.existsById(user.getUsername());
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
        return true;
    }

    public Optional<UsersDto> get(String username) {
        Optional<Users> user = usersRepository.findById(username);
        return user.map(this::mapUserToDto);
    }

    public List<UsersDto> getAll() {
        return usersRepository.findAll()
                .stream().map(this::mapUserToDto)
                .collect(Collectors.toList());
    }

    private Users mapDtoToUsers(UsersDto dto){
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        if (dto.getRoleId() != null) {
            Optional<Role> role = roleRepository.findById(dto.getRoleId());
            if (role.isPresent())
                user.setRole(role.get());
            else
                throw new EntityNotFoundException("Reference role is not found");
        }
        return user;
    }

    private UsersDto mapUserToDto(Users user){
        UsersDto dto = new UsersDto();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRoleId(user.getRole().getId());
        return dto;
    }

    private Users getUpdateUser(Users info, Users updateUser) {
        if (info.getUsername() != null)
            updateUser.setUsername(info.getUsername());
        if (info.getPassword() != null)
            updateUser.setPassword(info.getPassword());
        if (info.getRole() != null)
            updateUser.setRole(info.getRole());
        return updateUser;
    }

    private Message getUpdateMessage(Users user, String receiver){
        Message message = new EmailMessage();
        message.setFrom(EmailNotifier.FROM_EMAIL);
        message.setTo(new String[]{receiver});
        message.setSubject("Update user data");
        message.setBody(String.format("Hello!\n Your user data updated \"%s\".", user.toString()));
        return message;
    }

}
