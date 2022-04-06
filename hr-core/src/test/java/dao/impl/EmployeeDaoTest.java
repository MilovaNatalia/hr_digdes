package dao.impl;

import models.Employee;
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

    private EmployeeDao dao = new EmployeeDao("test_hr_digdes_schema");

    @Test
    @Order(1)
    void test_get_existingEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Ivan");
        employee.setLastName("Ivanov");
        employee.setPatronymic("Ivanovich");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(121, 3, 19));
        employee.setEmail("example@example.ru");
        employee.setDepartment_id(2L);
        employee.setPosition_id(4L);
        assertEquals(dao.get(1).get(), employee);
    }

    @Test
    @Order(2)
    void test_get_nonExistentEmployee() {
        assertEquals(dao.get(100), Optional.empty());
    }

    @Test
    @Order(3)
    void test_getAll_employees() {
        List<Employee> employees = dao.getAll();
        assertEquals(2, employees.size());
    }

    @Test
    @Order(4)
    void test_save_nonExistentEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Linux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(96, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartment_id(2L);
        employee.setPosition_id(4L);
        assertTrue(dao.save(employee));
    }

    @Test
    @Order(5)
    void test_save_existingEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Linux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(96, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartment_id(2L);
        employee.setPosition_id(4L);
        assertFalse(dao.save(employee));
    }

    @Test
    @Order(6)
    void test_update_existingEmployee() {
        Employee employee = new Employee();
        employee.setId(3L);
        employee.setFirstName("Tux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(100, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartment_id(2L);
        employee.setPosition_id(4L);
        assertTrue(dao.update(employee));
    }

    @Test
    @Order(7)
    void test_update_nonExistentEmployee() {
        Employee employee = new Employee();
        employee.setId(100L);
        employee.setFirstName("Tux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(100, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartment_id(2L);
        employee.setPosition_id(4L);
        assertFalse(dao.update(employee));
    }

    @Test
    @Order(8)
    void test_delete_existingEmployee() {
        Employee employee = new Employee();
        employee.setId(3L);
        employee.setFirstName("Tux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(100, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartment_id(2L);
        employee.setPosition_id(4L);
        assertTrue(dao.delete(employee));
    }

    @Test
    @Order(9)
    void test_delete_nonExistentDepartment() {
        Employee employee = new Employee();
        employee.setId(100L);
        employee.setFirstName("Tux");
        employee.setLastName("Tux");
        employee.setGender(EmployeeGender.MALE);
        employee.setBirthDate(new Date(100, 4, 9));
        employee.setEmail("example3@example.ru");
        employee.setDepartment_id(2L);
        employee.setPosition_id(4L);
        assertFalse(dao.delete(employee));
    }

    @Test
    @Order(10)
    void test_search_byFirstNameAndPositionId_existingEmployee() {
        Employee employeeRes = new Employee();
        employeeRes.setId(2L);
        employeeRes.setFirstName("David");
        employeeRes.setLastName("Jones");
        employeeRes.setGender(EmployeeGender.MALE);
        employeeRes.setBirthDate(new Date(100, 0, 1));
        employeeRes.setEmail("example2@example.ru");
        employeeRes.setDepartment_id(2L);
        employeeRes.setPosition_id(1L);
        Employee employeeReq = new Employee();
        employeeReq.setFirstName("David");
        employeeReq.setPosition_id(1L);
        assertEquals(dao.simpleSearch(employeeReq), List.of(employeeRes));
    }

    @Test
    @Order(11)
    void test_search_byId_existingEmployee() {
        Employee employeeRes = new Employee();
        employeeRes.setId(2L);
        employeeRes.setFirstName("David");
        employeeRes.setLastName("Jones");
        employeeRes.setGender(EmployeeGender.MALE);
        employeeRes.setBirthDate(new Date(100, 0, 1));
        employeeRes.setEmail("example2@example.ru");
        employeeRes.setDepartment_id(2L);
        employeeRes.setPosition_id(1L);
        Employee employeeReq = new Employee();
        employeeReq.setId(2L);
        assertEquals(dao.simpleSearch(employeeReq), List.of(employeeRes));
    }

    @Test
    @Order(12)
    void test_search_byLastName_nonExistentEmployee() {
        Employee employeeReq = new Employee();
        employeeReq.setLastName("Linux");
        assertEquals(dao.simpleSearch(employeeReq), new ArrayList<Employee>());
    }

    @Test
    @Order(13)
    void test_search_byGenderAndDepartmentId_multipleResults_existingEmployee() {
        Employee employeeRes1 = new Employee();
        employeeRes1.setId(2L);
        employeeRes1.setFirstName("David");
        employeeRes1.setLastName("Jones");
        employeeRes1.setGender(EmployeeGender.MALE);
        employeeRes1.setBirthDate(new Date(100, 0, 1));
        employeeRes1.setEmail("example2@example.ru");
        employeeRes1.setDepartment_id(2L);
        employeeRes1.setPosition_id(1L);
        Employee employeeRes2 = new Employee();
        employeeRes2.setId(1L);
        employeeRes2.setFirstName("Ivan");
        employeeRes2.setLastName("Ivanov");
        employeeRes2.setPatronymic("Ivanovich");
        employeeRes2.setGender(EmployeeGender.MALE);
        employeeRes2.setBirthDate(new Date(121, 3, 19));
        employeeRes2.setEmail("example@example.ru");
        employeeRes2.setDepartment_id(2L);
        employeeRes2.setPosition_id(4L);
        Employee employeeReq = new Employee();
        employeeReq.setGender(EmployeeGender.MALE);
        employeeReq.setDepartment_id(2L);
        assertEquals(dao.simpleSearch(employeeReq), List.of(employeeRes2, employeeRes1));
    }

    @AfterAll
    public static void cleanUp() {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("ALTER SEQUENCE test_hr_digdes_schema.employees_id_seq RESTART WITH 3;");
            prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}