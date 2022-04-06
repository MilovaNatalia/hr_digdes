package dao.impl;

import dao.Dao;
import models.*;
import util.C3p0DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDao implements Dao<Department> {
    private String schemaName;

    public DepartmentDao(String schemaName) {
        this.schemaName = schemaName;
    }

    @Override
    public Optional<Department> get(long id) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(String.format("SELECT * FROM %s.departments WHERE id=?", schemaName));
            prepared.setLong(1, id);
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next()){
                Department department = getDepartment(resultSet);
                return Optional.of(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return Optional.empty();
    }

    @Override
    public List<Department> getAll() {
        //todo: pagination
        List<Department> departments = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(String.format("SELECT * FROM %s.departments", schemaName));
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
            PreparedStatement prepared = connection.prepareStatement(String.format("INSERT INTO %s.departments (name, type_id, head_id, parent_id) VALUES (?,?,?,?)", schemaName));
            prepared.setString(1,department.getName());
            prepared.setLong(2,department.getType_id());
            if (department.getHead_id() == null)
                prepared.setNull(3, Types.BIGINT);
            else
                prepared.setLong(3, department.getHead_id());
            if (department.getParent_id() == null)
                prepared.setNull(4, Types.BIGINT);
            else
                prepared.setLong(4,department.getParent_id());
            if (prepared.executeUpdate() > 0)
                return true;
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
            String statement = String.format("UPDATE %s.departments SET %s WHERE id=%d",
                    schemaName,getStatementArgs(department, ","), department.getId());
            PreparedStatement prepared = connection.prepareStatement(statement);
            if (prepared.executeUpdate() > 0)
                return true;
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
            PreparedStatement prepared = connection.prepareStatement(String.format("DELETE FROM %s.departments WHERE id=?", schemaName));
            prepared.setLong(1, department.getId());
            if (prepared.executeUpdate() > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public List<Department> simpleSearch(Department department) {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (department.getId() != null){
                Optional<Department> departmentById = get(department.getId());
                departmentById.ifPresent(departments::add);
                return departments;
            }
            String statement = String.format("SELECT * FROM %s.departments WHERE %s",
                    schemaName, getStatementArgs(department, " AND "));
            PreparedStatement prepared = connection.prepareStatement(statement);
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next())
                departments.add(getDepartment(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return departments;
    }

    private Department getDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getLong("id"));
        department.setName(resultSet.getString("name"));
        department.setType_id(resultSet.getLong("type_id"));
        if (resultSet.getLong("head_id") == 0)
            department.setHead_id(null);
        else
            department.setHead_id(resultSet.getLong("head_id"));
        if (resultSet.getLong("parent_id") == 0)
            department.setParent_id(null);
        else
            department.setParent_id(resultSet.getLong("parent_id"));
        return department;
    }

    private String getStatementArgs(Department department, String delimiter) {
        List<String> set = new ArrayList<>();
        if (department.getName() != null)
            set.add(String.format("name='%s'", department.getName()));
        if (department.getHead_id() != null)
            set.add(String.format("head_id='%s'", department.getHead_id()));
        if (department.getType_id() != null)
            set.add(String.format("type_id='%s'", department.getType_id()));
        if (department.getParent_id() != null)
            set.add(String.format("parent_id='%s'", department.getParent_id()));
        return String.join(delimiter, set);
    }
}
