package dao.impl;

import dao.Dao;
import dto.DepartmentTypeDto;
import models.DepartmentType;
import models.Position;
import util.C3p0DataSource;
import util.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentTypeDao implements Dao<DepartmentTypeDto> {

    @Override
    public Optional<DepartmentTypeDto> get(long id) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM department_types WHERE id=?");
            prepared.setLong(1, id);
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next())
                return Optional.of(Mapper.mapDepartmentType(
                        new DepartmentType(resultSet.getLong("id"), resultSet.getString("name"))));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return Optional.empty();
    }

    @Override
    public List<DepartmentTypeDto> getAll() {
        //todo: pagination
        List<DepartmentTypeDto> positions = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM department_types");
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next()) {
                positions.add(Mapper.mapDepartmentType(
                        new DepartmentType(resultSet.getLong("id"), resultSet.getString("name"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return positions;
    }

    @Override
    public boolean save(DepartmentTypeDto dto) {
        DepartmentType departmentType = Mapper.mapDepartmentTypeDto(dto);
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO department_types (name) VALUES (?)");
            prepared.setString(1,departmentType.getName());
            if (prepared.executeUpdate() > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public boolean update(DepartmentTypeDto dto) {
        DepartmentType departmentType = Mapper.mapDepartmentTypeDto(dto);
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (departmentType.getName() != null) {
                PreparedStatement prepared = connection.prepareStatement("UPDATE department_types SET name=? WHERE id=?");
                prepared.setString(1, departmentType.getName());
                prepared.setLong(2, departmentType.getId());
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
    public boolean delete(DepartmentTypeDto dto) {
        DepartmentType departmentType = Mapper.mapDepartmentTypeDto(dto);
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("DELETE FROM department_types WHERE id=?");
            prepared.setLong(1, departmentType.getId());
            if (prepared.executeUpdate() > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public List<DepartmentTypeDto> simpleSearch(DepartmentTypeDto dto) {
        DepartmentType departmentType = Mapper.mapDepartmentTypeDto(dto);
        List<DepartmentTypeDto> departmentTypes = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (departmentType.getId() != null){
                Optional<DepartmentTypeDto> typeById = get(departmentType.getId());
                typeById.ifPresent(departmentTypes::add);
                return departmentTypes;
            }
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM department_types WHERE name=?");
            prepared.setString(1, departmentType.getName());
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next())
                departmentTypes.add(Mapper.mapDepartmentType(
                        new DepartmentType(resultSet.getLong("id"), resultSet.getString("name"))));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return departmentTypes;
    }
}
