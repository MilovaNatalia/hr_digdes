package dao.impl;

import dto.EmployeeDto;
import models.EmployeeGender;
import org.junit.jupiter.api.*;
import util.C3p0DataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeDaoTest {

    private EmployeeDao dao = new EmployeeDao();

    @Test
    @Order(1)
    void test_get_existingEmployee() {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(1L);
        dto.setFirstName("Ivan");
        dto.setLastName("Ivanov");
        dto.setPatronymic("Ivanovich");
        dto.setGender(EmployeeGender.MALE);
        dto.setBirthDate(new Date(121, 3, 19));
        dto.setEmail("example@example.ru");
        dto.setDepartmentId(2L);
        dto.setPositionId(4L);
        assertEquals(dao.get(1).get(), dto);
    }

    @Test
    @Order(2)
    void test_get_nonExistentEmployee() {
        assertEquals(dao.get(100), Optional.empty());
    }

    @Test
    @Order(3)
    void test_getAll_employees() {
        List<EmployeeDto> employees = dao.getAll();
        assertEquals(2, employees.size());
    }

    @Test
    @Order(4)
    void test_save_nonExistentEmployee() {
        EmployeeDto employee = new EmployeeDto();
        employee.setFirstName("Linux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(96, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartmentId(2L);
        employee.setPositionId(4L);
        assertTrue(dao.save(employee));
    }

    @Test
    @Order(5)
    void test_save_existingEmployee() {
        EmployeeDto employee = new EmployeeDto();
        employee.setFirstName("Linux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(96, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartmentId(2L);
        employee.setPositionId(4L);
        assertFalse(dao.save(employee));
    }

    @Test
    @Order(6)
    void test_update_existingEmployee() {
        EmployeeDto employee = new EmployeeDto();
        employee.setId(3L);
        employee.setFirstName("Tux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(100, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartmentId(2L);
        employee.setPositionId(4L);
        assertTrue(dao.update(employee));
    }

    @Test
    @Order(7)
    void test_update_nonExistentEmployee() {
        EmployeeDto employee = new EmployeeDto();
        employee.setId(100L);
        employee.setFirstName("Tux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(100, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartmentId(2L);
        employee.setPositionId(4L);
        assertFalse(dao.update(employee));
    }

    @Test
    @Order(8)
    void test_delete_existingEmployee() {
        EmployeeDto employee = new EmployeeDto();
        employee.setId(3L);
        employee.setFirstName("Tux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(100, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartmentId(2L);
        employee.setPositionId(4L);
        assertTrue(dao.delete(employee));
    }

    @Test
    @Order(9)
    void test_delete_nonExistentDepartment() {
        EmployeeDto employee = new EmployeeDto();
        employee.setId(100L);
        employee.setFirstName("Tux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(100, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartmentId(2L);
        employee.setPositionId(4L);
        assertFalse(dao.delete(employee));
    }

    @Test
    @Order(10)
    void test_search_byFirstNameAndPositionId_existingEmployee() {
        EmployeeDto employeeRes = new EmployeeDto();
        employeeRes.setId(2L);
        employeeRes.setFirstName("David");
        employeeRes.setLastName("Jones");
        employeeRes.setGender(EmployeeGender.MALE);
        employeeRes.setBirthDate(new Date(100, 0, 1));
        employeeRes.setEmail("example2@example.ru");
        employeeRes.setDepartmentId(2L);
        employeeRes.setPositionId(1L);
        EmployeeDto employeeReq = new EmployeeDto();
        employeeReq.setFirstName("David");
        employeeReq.setPositionId(1L);
        assertEquals(dao.simpleSearch(employeeReq), List.of(employeeRes));
    }

    @Test
    @Order(11)
    void test_search_byId_existingEmployee() {
        EmployeeDto employeeRes = new EmployeeDto();
        employeeRes.setId(2L);
        employeeRes.setFirstName("David");
        employeeRes.setLastName("Jones");
        employeeRes.setGender(EmployeeGender.MALE);
        employeeRes.setBirthDate(new Date(100, 0, 1));
        employeeRes.setEmail("example2@example.ru");
        employeeRes.setDepartmentId(2L);
        employeeRes.setPositionId(1L);
        EmployeeDto employeeReq = new EmployeeDto();
        employeeReq.setId(2L);
        assertEquals(dao.simpleSearch(employeeReq), List.of(employeeRes));
    }

    @Test
    @Order(12)
    void test_search_byLastName_nonExistentEmployee() {
        EmployeeDto employeeReq = new EmployeeDto();
        employeeReq.setLastName("Linux");
        assertEquals(dao.simpleSearch(employeeReq), new ArrayList<EmployeeDto>());
    }

    @Test
    @Order(13)
    void test_search_byGenderAndDepartmentId_multipleResults_existingEmployee() {
        EmployeeDto employeeRes1 = new EmployeeDto();
        employeeRes1.setId(2L);
        employeeRes1.setFirstName("David");
        employeeRes1.setLastName("Jones");
        employeeRes1.setGender(EmployeeGender.MALE);
        employeeRes1.setBirthDate(new Date(100, 0, 1));
        employeeRes1.setEmail("example2@example.ru");
        employeeRes1.setDepartmentId(2L);
        employeeRes1.setPositionId(1L);
        EmployeeDto employeeRes2 = new EmployeeDto();
        employeeRes2.setId(1L);
        employeeRes2.setFirstName("Ivan");
        employeeRes2.setLastName("Ivanov");
        employeeRes2.setPatronymic("Ivanovich");
        employeeRes2.setGender(EmployeeGender.MALE);
        employeeRes2.setBirthDate(new Date(121, 3, 19));
        employeeRes2.setEmail("example@example.ru");
        employeeRes2.setDepartmentId(2L);
        employeeRes2.setPositionId(4L);
        EmployeeDto employeeReq = new EmployeeDto();
        employeeReq.setGender(EmployeeGender.MALE);
        employeeReq.setDepartmentId(2L);
        assertEquals(dao.simpleSearch(employeeReq), List.of(employeeRes2, employeeRes1));
    }

    @AfterAll
    public static void cleanUp() {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("ALTER SEQUENCE employees_id_seq RESTART WITH 3;");
            prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}