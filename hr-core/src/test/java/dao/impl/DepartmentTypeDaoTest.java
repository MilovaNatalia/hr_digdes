package dao.impl;

import models.DepartmentType;
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
class DepartmentTypeDaoTest {

    private DepartmentTypeDao dao = new DepartmentTypeDao("test_hr_digdes_schema");

    @Test
    @Order(1)
    void test_get_existingDepartmentType() {
        DepartmentType type = new DepartmentType(1L, "Company");
        assertEquals(dao.get(1).get(),type);
    }

    @Test
    @Order(2)
    void test_get_nonExistentDepartmentType() {
        assertEquals(dao.get(100), Optional.empty());
    }

    @Test
    @Order(3)
    void test_getAllDepartmentTypes() {
        List<DepartmentType> types = dao.getAll();
        assertEquals(4, types.size());
    }

    @Test
    @Order(4)
    void test_save_nonExistentDepartmentType() {
        DepartmentType type = new DepartmentType("Subdivision");
        assertTrue(dao.save(type));
    }

    @Test
    @Order(5)
    void test_save_existingDepartmentType() {
        DepartmentType type = new DepartmentType("Subdivision");
        assertFalse(dao.save(type));
    }

    @Test
    @Order(6)
    void test_update_existingDepartmentType() {
        DepartmentType type = new DepartmentType(5L,"Subdivision 2");
        assertTrue(dao.update(type));
    }

    @Test
    @Order(7)
    void test_update_nonExistentDepartmentType() {
        DepartmentType type = new DepartmentType(100L,"Subdivision 2");
        assertFalse(dao.update(type));
    }

    @Test
    @Order(8)
    void test_delete_existingDepartmentType() {
        DepartmentType type = new DepartmentType(5L,"Subdivision 2");
        assertTrue(dao.delete(type));
    }

    @Test
    @Order(9)
    void test_delete_nonExistentDepartmentType() {
        DepartmentType position = new DepartmentType(5L,"Subdivision 2");
        assertFalse(dao.delete(position));
    }

    @Test
    @Order(10)
    void test_search_byName_existingDepartmentType() {
        DepartmentType type = new DepartmentType(null, "Service");
        assertEquals(dao.simpleSearch(type), List.of(new DepartmentType(2L,"Service")));
    }

    @Test
    @Order(11)
    void test_search_byName_nonExistentDepartmentType() {
        DepartmentType type = new DepartmentType(null, "Service 2");
        assertEquals(dao.simpleSearch(type), new ArrayList<DepartmentType>());
    }

    @Test
    @Order(12)
    void test_search_byId_existingDepartmentType() {
        DepartmentType type = new DepartmentType(3L, null);
        assertEquals(dao.simpleSearch(type), List.of(new DepartmentType(3L, "Group")));
    }

    @AfterAll
    public static void cleanUp(){
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(
                    "ALTER SEQUENCE test_hr_digdes_schema.department_types_id_seq RESTART WITH 5;");
            prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}