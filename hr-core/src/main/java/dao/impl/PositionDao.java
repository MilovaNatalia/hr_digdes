package dao.impl;

import dao.Dao;
import models.*;
import util.C3p0DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDao implements Dao<Position> {
   private String schemaName;

    public PositionDao(String schemaName) {
        this.schemaName = schemaName;
    }

    @Override
    public Optional<Position> get(long id) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(String.format("SELECT * FROM %s.positions WHERE id=?",schemaName));
            prepared.setLong(1, id);
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next())
                return Optional.of(new Position(resultSet.getLong("id"), resultSet.getString("name")));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return Optional.empty();
    }

    @Override
    public List<Position> getAll() {
        //todo: pagination
        List<Position> positions = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(String.format("SELECT * FROM %s.positions", schemaName));
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next()) {
                positions.add(new Position(resultSet.getLong("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return positions;
    }

    @Override
    public boolean save(Position position) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(String.format("INSERT INTO %s.positions (name) VALUES (?)",schemaName));
            prepared.setString(1,position.getName());
            if (prepared.executeUpdate() > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public boolean update(Position position) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (position.getName() != null) {
                PreparedStatement prepared = connection.prepareStatement(String.format("UPDATE %s.positions SET name=? WHERE id=?", schemaName));
                prepared.setString(1, position.getName());
                prepared.setLong(2, position.getId());
                if (prepared.executeUpdate() > 0)
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public boolean delete(Position position) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(String.format("DELETE FROM %s.positions WHERE id=?", schemaName));
            prepared.setLong(1, position.getId());
            if (prepared.executeUpdate() > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public List<Position> simpleSearch(Position position) {
        List<Position> positions = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (position.getId() != null){
                Optional<Position> positionById = get(position.getId());
                positionById.ifPresent(positions::add);
                return positions;
            }
            PreparedStatement prepared = connection.prepareStatement(String.format("SELECT * FROM %s.positions WHERE name=?", schemaName));
            prepared.setString(1, position.getName());
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next())
                positions.add(new Position(resultSet.getLong("id"), resultSet.getString("name")));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return positions;
    }
}
