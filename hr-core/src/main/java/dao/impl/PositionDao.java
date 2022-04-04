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
    @Override
    public Optional<Position> get(long id) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM hr_digdes_schema.positions WHERE id=?");
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
        List<Position> positions = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM hr_digdes_schema.positions");
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
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO hr_digdes_schema.positions (name) VALUES (?)");
            prepared.setString(1,position.getName());
            return prepared.execute();
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
                PreparedStatement prepared = connection.prepareStatement("UPDATE hr_digdes_schema.positions SET name=? WHERE id=?");
                prepared.setString(1, position.getName());
                prepared.setLong(2, position.getId());
                return prepared.execute();
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
            PreparedStatement prepared = connection.prepareStatement("DELETE FROM hr_digdes_schema.positions WHERE id=?");
            prepared.setLong(1, position.getId());
            return prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public Optional<Position> search(Position position) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (position.getId() != null)
                return get(position.getId());
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM hr_digdes_schema.positions WHERE name=?");
            prepared.setString(1, position.getName());
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next())
                return Optional.of(new Position(resultSet.getLong("id"), resultSet.getString("name")));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return Optional.empty();
    }
}
