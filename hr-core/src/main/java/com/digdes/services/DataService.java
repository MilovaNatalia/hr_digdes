package com.digdes.services;

import com.digdes.models.Department;
import com.digdes.models.Role;
import com.digdes.models.Users;
import com.digdes.repositories.DepartmentRepository;
import com.digdes.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//todo: other abstract methods
@Service
public abstract class DataService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public boolean checkUser(String username, Long id) {
        Optional<Users> user = usersRepository.findById(username);
        if (user.isPresent()) {
            if(user.get().getRole().getName().equals(Role.ROLE_ADMIN))
                return true;
            if (id == null)
                return false;
            List<Department> moderatedDepartments =
                    departmentRepository.getDepartmentsByModerator(user.get());
            List<Long> availableDepartmentsId = new ArrayList<>();
            for (Department department1 : moderatedDepartments) {
                availableDepartmentsId.addAll(getSubDepartmentsId(department1));
            }
            boolean result = availableDepartmentsId.contains(id);
            return result;
        }
        return false;
    }

    public List<Long> getSubDepartmentsId(Department department) {
        String condition = "";
        if (department.getParent() == null)
            condition = "root";
        else
            condition = String.format("*.%d.*",department.getParent().getId());
        List<Long> ids = departmentRepository.getSubDepartments(condition)
                .stream().map(Department::getId)
                .collect(Collectors.toList());
        return ids;
    }
}
