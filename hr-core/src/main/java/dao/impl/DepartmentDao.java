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

public class DepartmentDao implements Dao<Department> {
    @Override
    public Optional<Department> get(long id) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM hr_digdes_schema.departments WHERE id=?");
            prepared.setLong(1, id);
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next())
                return Optional.of(getDepartment(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return Optional.empty();
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM hr_digdes_schema.departments");
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next()) {
                departments.add(getDepartment(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return departments;
    }

    @Override
    public boolean save(Department department) {
        //todo: db trigger on insert
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO hr_digdes_schema.departments (name, head_id, parent_id) VALUES (?,?,?)");
            prepared.setString(1,department.getName());
            prepared.setLong(2,department.getHead_id());
            prepared.setLong(3,department.getParent_id());
            return prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public boolean update(Department department) {
        //todo: db trigger on update
        try (Connection connection = C3p0DataSource.getConnection()) {
            String statement = String.format("UPDATE hr_digdes_schema.departments SET %s WHERE id=%d",
                    getStatementArgs(department, ","), department.getId());
            PreparedStatement prepared = connection.prepareStatement(statement);
            return prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public boolean delete(Department department) {
        //todo: db trigger on delete
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("DELETE FROM hr_digdes_schema.deaprtments WHERE id=?");
            prepared.setLong(1, department.getId());
            return prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public Optional<Department> search(Department department) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (department.getId() != null)
                return get(department.getId());
            String statement = String.format("SELECT * FROM hr_digdes_schema.employees WHERE %s",
                    getStatementArgs(department, "AND"));
            PreparedStatement prepared = connection.prepareStatement(statement);
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next())
                return Optional.of(getDepartment(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return Optional.empty();
    }

    private Department getDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getLong("id"));
        department.setName(resultSet.getString("name"));
        department.setHead_id(resultSet.getLong("head_id"));
        department.setParent_id(resultSet.getLong("parent_id"));
        return department;
    }

    private String getStatementArgs(Department department, String delimiter) {
        List<String> set = new ArrayList<>();
        if (department.getName() != null)
            set.add(String.format("name='%s'", department.getName()));
        if (department.getHead_id() != null)
            set.add(String.format("head_id='%s'", department.getHead_id()));
        if (department.getParent_id() != null)
            set.add(String.format("parent_id='%s'", department.getParent_id()));
        return String.join(delimiter, set);
    }
}
