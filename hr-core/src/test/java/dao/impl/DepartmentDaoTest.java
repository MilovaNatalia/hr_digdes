package dao.impl;

import models.Department;
import org.junit.jupiter.api.*;
import util.C3p0DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DepartmentDaoTest {

    private DepartmentDao dao = new DepartmentDao("test_hr_digdes_schema");

    @Test
    @Order(1)
    void test_get_existingDepartment() {
        Department department = new Department(2L, "HR service", 2L, null , 1L);
        assertEquals(dao.get(2).get(),department);
    }

    @Test
    @Order(2)
    void test_get_nonExistentDepartment() {
        assertEquals(dao.get(100), Optional.empty());
    }

    @Test
    @Order(3)
    void test_getAll_departments() {
        List<Department> departments = dao.getAll();
        assertEquals(3, departments.size());
    }

    @Test
    @Order(4)
    void test_save_nonExistentDepartment() {
        Department department = new Department();
        department.setName("Finance Service");
        department.setType_id(2L);
        department.setParent_id(2L);
        assertTrue(dao.save(department));
    }

    @Test
    @Order(5)
    void test_save_existingDepartment() {
        Department department = new Department();
        department.setName("Finance Service");
        department.setType_id(2L);
        department.setParent_id(2L);
        assertFalse(dao.save(department));
    }

    @Test
    @Order(6)
    void test_update_existingDepartment() {
        Department department = new Department(4L,"Finance Service 2", 2L, null , 2L);
        assertTrue(dao.update(department));
    }

    @Test
    @Order(7)
    void test_update_nonExistentDepartment() {
        Department department = new Department(100L,"Finance Service", 2L, null , 2L);
        assertFalse(dao.update(department));
    }

    @Test
    @Order(8)
    void test_delete_existingDepartment() {
        Department department = new Department(4L,"Finance Service 2", 2L, null , 2L);
        assertTrue(dao.delete(department));
    }

    @Test
    @Order(9)
    void test_delete_nonExistentDepartment() {
        Department department = new Department(4L,"Finance Service 2", 2L, null , 2L);
        assertFalse(dao.delete(department));
    }

    @Test
    @Order(10)
    void test_search_byNameAndTypeId_existingDepartment() {
        Department departmentReq = new Department("It Company", 1L, null , null);
        Department departmentRes = new Department(1L,"It Company", 1L, null , null);
        assertEquals(dao.simpleSearch(departmentReq), List.of(departmentRes));
    }

    @Test
    @Order(11)
    void test_search_byName_nonExistentDepartment() {
        Department departmentReq = new Department("It Company 2", null, null , null);
        assertEquals(dao.simpleSearch(departmentReq), new ArrayList<Department>());
    }

    @Test
    @Order(12)
    void test_search_byId_existingDepartment() {
        Department departmentRes = new Department(1L,"It Company", 1L, null , null);
        Department departmentReq = new Department();
        departmentReq.setId(1L);
        assertEquals(dao.simpleSearch(departmentReq), List.of(departmentRes));
    }

    @Test
    @Order(13)
    void test_search_byTypeId_multipleResults_existingDepartment() {
        Department departmentRes1 = new Department(2L,"HR service", 2L, null , 1L);
        Department departmentRes2 = new Department(3L,"Developer service", 2L, null , 1L);
        Department departmentReq = new Department();
        departmentReq.setType_id(2L);
        assertEquals(dao.simpleSearch(departmentReq), List.of(departmentRes1, departmentRes2));
    }

    @Test
    @Order(14)
    void test_search_byParentIdTypeId_multipleResults_existingDepartment() {
        Department departmentRes1 = new Department(2L,"HR service", 2L, null , 1L);
        Department departmentRes2 = new Department(3L,"Developer service", 2L, null , 1L);
        Department departmentReq = new Department();
        departmentReq.setType_id(2L);
        departmentReq.setParent_id(1L);
        assertEquals(dao.simpleSearch(departmentReq), List.of(departmentRes1, departmentRes2));
    }

    @AfterAll
    public static void cleanUp(){
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("ALTER SEQUENCE test_hr_digdes_schema.departments_id_seq RESTART WITH 4;");
            prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}