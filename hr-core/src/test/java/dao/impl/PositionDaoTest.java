package dao.impl;

import models.Position;
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
class PositionDaoTest {
    private PositionDao dao = new PositionDao("test_hr_digdes_schema");

    @Test
    @Order(1)
    void test_get_existingPosition() {
        Position position = new Position(1L, "Junior java programmer");
        assertEquals(dao.get(1).get(),position);
    }

    @Test
    @Order(2)
    void test_get_nonExistentPosition() {
        assertEquals(dao.get(100), Optional.empty());
    }

    @Test
    @Order(3)
    void test_getAllPositions() {
        List<Position> positions = dao.getAll();
        assertEquals(5, positions.size());
    }

    @Test
    @Order(4)
    void test_save_nonExistentPosition() {
        Position position = new Position("Technical writer");
        assertTrue(dao.save(position));
    }

    @Test
    @Order(5)
    void test_save_existingPosition() {
        Position position = new Position("Technical writer");
        assertFalse(dao.save(position));
    }

    @Test
    @Order(6)
    void test_update_existingPosition() {
        Position position = new Position(6L,"Technical writer");
        assertTrue(dao.update(position));
    }

    @Test
    @Order(7)
    void test_update_nonExistentPosition() {
        Position position = new Position(100L,"Technical writer 2");
        assertFalse(dao.update(position));
    }

    @Test
    @Order(8)
    void test_delete_existingPosition() {
        Position position = new Position(6L,"Technical writer 2");
        assertTrue(dao.delete(position));
    }

    @Test
    @Order(9)
    void test_delete_nonExistentPosition() {
        Position position = new Position(6L,"Technical writer 2");
        assertFalse(dao.delete(position));
    }

    @Test
    @Order(10)
    void test_search_byName_existingPosition() {
        Position position = new Position(null, "Intern java programmer");
        assertEquals(dao.simpleSearch(position), List.of(new Position(2L,"Intern java programmer")));
    }

    @Test
    @Order(11)
    void test_search_byName_nonExistentPosition() {
        Position position = new Position(null, "Intern java programmer 2");
        assertEquals(dao.simpleSearch(position), new ArrayList<Position>());
    }

    @Test
    @Order(12)
    void test_search_byId_existingPosition() {
        Position position = new Position(3L, null);
        assertEquals(dao.simpleSearch(position), List.of(new Position(3L, "Senior java programmer")));
    }

    @AfterAll
    public static void cleanUp(){
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("ALTER SEQUENCE test_hr_digdes_schema.positions_id_seq RESTART WITH 6;");
            prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}