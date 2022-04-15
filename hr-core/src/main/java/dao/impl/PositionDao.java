package dao.impl;

import dao.Dao;
import dto.PositionDto;
import models.*;
import util.C3p0DataSource;
import util.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDao implements Dao<PositionDto> {

    @Override
    public Optional<PositionDto> get(long id) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM positions WHERE id=?");
            prepared.setLong(1, id);
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next())
                return Optional.of(Mapper.mapPosition(new Position(resultSet.getLong("id"), resultSet.getString("name"))));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return Optional.empty();
    }

    @Override
    public List<PositionDto> getAll() {
        //todo: pagination
        List<PositionDto> positions = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM positions");
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next()) {
                positions.add(Mapper.mapPosition(new Position(resultSet.getLong("id"), resultSet.getString("name"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return positions;
    }

    @Override
    public boolean save(PositionDto dto) {
        Position position = Mapper.mapPositionDto(dto);
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO positions (name) VALUES (?)");
            prepared.setString(1, position.getName());
            if (prepared.executeUpdate() > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public boolean update(PositionDto dto) {
        Position position = Mapper.mapPositionDto(dto);
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (position.getName() != null) {
                PreparedStatement prepared = connection.prepareStatement("UPDATE positions SET name=? WHERE id=?");
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
    public boolean delete(PositionDto dto) {
        Position position = Mapper.mapPositionDto(dto);
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("DELETE FROM positions WHERE id=?");
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
    public List<PositionDto> simpleSearch(PositionDto dto) {
        Position position = Mapper.mapPositionDto(dto);
        List<PositionDto> positions = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (position.getId() != null) {
                Optional<PositionDto> positionById = get(position.getId());
                positionById.ifPresent(positions::add);
                return positions;
            }
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM positions WHERE name=?");
            prepared.setString(1, position.getName());
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next())
                positions.add(Mapper.mapPosition(new Position(resultSet.getLong("id"), resultSet.getString("name"))));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return positions;
    }
}
