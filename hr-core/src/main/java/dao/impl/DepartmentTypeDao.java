package dao.impl;

import dao.Dao;
import models.DepartmentType;
import util.C3p0DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentTypeDao implements Dao<DepartmentType> {
    @Override
    public Optional<DepartmentType> get(long id) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM hr_digdes_schema.department_types WHERE id=?");
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
        List<DepartmentType> positions = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM hr_digdes_schema.department_types");
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
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO hr_digdes_schema.department_types (name) VALUES (?)");
            prepared.setString(1,departmentType.getName());
            return prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public boolean update(DepartmentType departmentType) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (departmentType.getName() == null) {
                PreparedStatement prepared = connection.prepareStatement("UPDATE hr_digdes_schema.department_types SET name=? WHERE id=?");
                prepared.setString(1, departmentType.getName());
                prepared.setLong(2, departmentType.getId());
                return prepared.execute();
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
            PreparedStatement prepared = connection.prepareStatement("DELETE FROM hr_digdes_schema.department_types WHERE id=?");
            prepared.setLong(1, departmentType.getId());
            return prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public Optional<DepartmentType> search(DepartmentType departmentType) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (departmentType.getId() != null)
                return get(departmentType.getId());
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM hr_digdes_schema.department_types WHERE name=?");
            prepared.setString(1, departmentType.getName());
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next())
                return Optional.of(new DepartmentType(resultSet.getLong("id"), resultSet.getString("name")));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return Optional.empty();
    }
}
