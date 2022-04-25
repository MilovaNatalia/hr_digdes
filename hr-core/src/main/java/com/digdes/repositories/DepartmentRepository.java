package com.digdes.repositories;

import com.digdes.models.Department;
import com.digdes.models.Employee;
import com.digdes.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(
            value = "SELECT * FROM departments WHERE path ~ CAST(:pathToSearch AS lquery)",
            nativeQuery = true)
    List<Department> getSubDepartments(@Param("pathToSearch") String pathToSearch);

    List<Department> getDepartmentsByModerator(Users moderator);
}
