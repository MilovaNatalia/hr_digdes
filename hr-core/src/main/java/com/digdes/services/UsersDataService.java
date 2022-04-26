package com.digdes.services;

import com.digdes.dto.UsersDto;
import com.digdes.models.Users;
import com.digdes.repositories.RoleRepository;
import com.digdes.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersDataService extends DataService<UsersDto, UsersDto>{

    //todo: data service
    //todo: notification update user --- employee if not null
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UsersDto create(UsersDto info) {
        return null;
    }

    @Override
    public UsersDto update(UsersDto info) {
        return null;
    }

    @Override
    public boolean delete(UsersDto info) {
        return false;
    }

    @Override
    public Optional<UsersDto> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UsersDto> getAll() {
        return null;
    }

    @Override
    public List<UsersDto> find(UsersDto searchRequest) {
        return null;
    }

    private Users mapDtoToUsers(UsersDto dto){
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        return user;
    }


}
