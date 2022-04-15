package dao.impl;

import dto.DepartmentDto;
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

    private DepartmentDao dao = new DepartmentDao();

    @Test
    @Order(1)
    void test_get_existingDepartment() {
        DepartmentDto department = new DepartmentDto(2L, "HR service", 2L, null , 1L);
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
        List<DepartmentDto> departments = dao.getAll();
        assertEquals(3, departments.size());
    }

    @Test
    @Order(4)
    void test_save_nonExistentDepartment() {
        DepartmentDto department = new DepartmentDto();
        department.setName("Finance Service");
        department.setTypeId(2L);
        department.setParentId(2L);
        assertTrue(dao.save(department));
    }

    @Test
    @Order(5)
    void test_save_existingDepartment() {
        DepartmentDto department = new DepartmentDto();
        department.setName("Finance Service");
        department.setTypeId(2L);
        department.setParentId(2L);
        assertFalse(dao.save(department));
    }

    @Test
    @Order(6)
    void test_update_existingDepartment() {
        DepartmentDto department = new DepartmentDto(4L,"Finance Service 2", 2L, null , 2L);
        assertTrue(dao.update(department));
    }

    @Test
    @Order(7)
    void test_update_nonExistentDepartment() {
        DepartmentDto department = new DepartmentDto(100L,"Finance Service", 2L, null , 2L);
        assertFalse(dao.update(department));
    }

    @Test
    @Order(8)
    void test_delete_existingDepartment() {
        DepartmentDto department = new DepartmentDto(4L,"Finance Service 2", 2L, null , 2L);
        assertTrue(dao.delete(department));
    }

    @Test
    @Order(9)
    void test_delete_nonExistentDepartment() {
        DepartmentDto department = new DepartmentDto(4L,"Finance Service 2", 2L, null , 2L);
        assertFalse(dao.delete(department));
    }

    @Test
    @Order(10)
    void test_search_byNameAndTypeId_existingDepartment() {
        DepartmentDto departmentReq = new DepartmentDto("It Company", 1L, null , null);
        DepartmentDto departmentRes = new DepartmentDto(1L,"It Company", 1L, null , null);
        assertEquals(dao.simpleSearch(departmentReq), List.of(departmentRes));
    }

    @Test
    @Order(11)
    void test_search_byName_nonExistentDepartment() {
        DepartmentDto departmentReq = new DepartmentDto("It Company 2", null, null , null);
        assertEquals(dao.simpleSearch(departmentReq), new ArrayList<DepartmentDto>());
    }

    @Test
    @Order(12)
    void test_search_byId_existingDepartment() {
        DepartmentDto departmentRes = new DepartmentDto(1L,"It Company", 1L, null , null);
        DepartmentDto departmentReq = new DepartmentDto();
        departmentReq.setId(1L);
        assertEquals(dao.simpleSearch(departmentReq), List.of(departmentRes));
    }

    @Test
    @Order(13)
    void test_search_byTypeId_multipleResults_existingDepartment() {
        DepartmentDto departmentRes1 = new DepartmentDto(2L,"HR service", 2L, null , 1L);
        DepartmentDto departmentRes2 = new DepartmentDto(3L,"Developer service", 2L, null , 1L);
        DepartmentDto departmentReq = new DepartmentDto();
        departmentReq.setTypeId(2L);
        assertEquals(dao.simpleSearch(departmentReq), List.of(departmentRes1, departmentRes2));
    }

    @Test
    @Order(14)
    void test_search_byParentIdTypeId_multipleResults_existingDepartment() {
        DepartmentDto departmentRes1 = new DepartmentDto(2L,"HR service", 2L, null , 1L);
        DepartmentDto departmentRes2 = new DepartmentDto(3L,"Developer service", 2L, null , 1L);
        DepartmentDto departmentReq = new DepartmentDto();
        departmentReq.setTypeId(2L);
        departmentReq.setParentId(1L);
        assertEquals(dao.simpleSearch(departmentReq), List.of(departmentRes1, departmentRes2));
    }

    @AfterAll
    public static void cleanUp(){
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("ALTER SEQUENCE departments_id_seq RESTART WITH 4;");
            prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}