package dao.impl;

import dao.Dao;
import models.DepartmentType;
import models.Position;
import util.C3p0DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentTypeDao implements Dao<DepartmentType> {
    private String schemaName;

    public DepartmentTypeDao(String schemaName) {
        this.schemaName = schemaName;
    }

    @Override
    public Optional<DepartmentType> get(long id) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(String.format("SELECT * FROM %s.department_types WHERE id=?", schemaName));
            prepared.setLong(1, id);
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next())
                return Optional.of(new DepartmentType(resultSet.getLong("id"), resultSet.getString("name")));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return Optional.empty();
    }

    @Override
    public List<DepartmentType> getAll() {
        //todo: pagination
        List<DepartmentType> positions = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(String.format("SELECT * FROM %s.department_types", schemaName));
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next()) {
                positions.add(new DepartmentType(resultSet.getLong("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return positions;
    }

    @Override
    public boolean save(DepartmentType departmentType) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(String.format("INSERT INTO %s.department_types (name) VALUES (?)", schemaName));
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
    public boolean update(DepartmentType type) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (type.getName() != null) {
                PreparedStatement prepared = connection.prepareStatement(String.format("UPDATE %s.department_types SET name=? WHERE id=?", schemaName));
                prepared.setString(1, type.getName());
                prepared.setLong(2, type.getId());
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
    public boolean delete(DepartmentType departmentType) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(String.format("DELETE FROM %s.department_types WHERE id=?", schemaName));
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
    public List<DepartmentType> simpleSearch(DepartmentType departmentType) {
        List<DepartmentType> departmentTypes = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (departmentType.getId() != null){
                Optional<DepartmentType> typeById = get(departmentType.getId());
                typeById.ifPresent(departmentTypes::add);
                return departmentTypes;
            }
            PreparedStatement prepared = connection.prepareStatement(String.format("SELECT * FROM %s.department_types WHERE name=?", schemaName));
            prepared.setString(1, departmentType.getName());
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next())
                departmentTypes.add(new DepartmentType(resultSet.getLong("id"), resultSet.getString("name")));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return departmentTypes;
    }
}
