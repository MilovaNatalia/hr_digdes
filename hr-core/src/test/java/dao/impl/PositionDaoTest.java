package dao.impl;

import dto.PositionDto;
import models.Position;
import org.junit.jupiter.api.*;
import org.testng.annotations.BeforeClass;
import util.C3p0DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PositionDaoTest {
    private PositionDao dao = new PositionDao();


    @Test
    @Order(1)
    void test_get_existingPosition() {
        PositionDto dto = new PositionDto(1L, "Junior java programmer");
        assertEquals(dao.get(1).get(),dto);
    }

    @Test
    @Order(2)
    void test_get_nonExistentPosition() {
        assertEquals(dao.get(100), Optional.empty());
    }

    @Test
    @Order(3)
    void test_getAllPositions() {
        List<PositionDto> positions = dao.getAll();
        assertEquals(5, positions.size());
    }

    @Test
    @Order(4)
    void test_save_nonExistentPosition() {
        PositionDto dto = new PositionDto("Technical writer");
        assertTrue(dao.save(dto));
    }

    @Test
    @Order(5)
    void test_save_existingPosition() {
        PositionDto dto = new PositionDto("Technical writer");
        assertFalse(dao.save(dto));
    }

    @Test
    @Order(6)
    void test_update_existingPosition() {
        PositionDto dto = new PositionDto(6L,"Technical writer");
        assertTrue(dao.update(dto));
    }

    @Test
    @Order(7)
    void test_update_nonExistentPosition() {
        PositionDto dto = new PositionDto(100L,"Technical writer 2");
        assertFalse(dao.update(dto));
    }

    @Test
    @Order(8)
    void test_delete_existingPosition() {
        PositionDto dto = new PositionDto(6L,"Technical writer 2");
        assertTrue(dao.delete(dto));
    }

    @Test
    @Order(9)
    void test_delete_nonExistentPosition_returnValue() {
        PositionDto dto = new PositionDto(6L,"Technical writer 2");
        assertFalse(dao.delete(dto));
    }

    @Test
    @Order(11)
    void test_search_byName_existingPosition() {
        PositionDto dto = new PositionDto(null, "Intern java programmer");
        assertEquals(dao.simpleSearch(dto), List.of(new PositionDto(2L,"Intern java programmer")));
    }

    @Test
    @Order(12)
    void test_search_byName_nonExistentPosition() {
        PositionDto dto = new PositionDto(null, "Intern java programmer 2");
        assertEquals(dao.simpleSearch(dto), new ArrayList<PositionDto>());
    }

    @Test
    @Order(13)
    void test_search_byId_existingPosition() {
        PositionDto dto = new PositionDto(3L, null);
        assertEquals(dao.simpleSearch(dto), List.of(new PositionDto(3L, "Senior java programmer")));
    }

    @AfterAll
    public static void cleanUp(){
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("ALTER SEQUENCE positions_id_seq RESTART WITH 6;");
            prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}