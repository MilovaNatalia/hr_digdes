package dao.impl;

import dao.Dao;
import dto.EmployeeDto;
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

public class EmployeeDao implements Dao<EmployeeDto> {

    @Override
    public Optional<EmployeeDto> get(long id) {
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM employees WHERE id=?");
            prepared.setLong(1, id);
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next())
                return Optional.of(Mapper.mapEmployee(getEmployee(resultSet)));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return Optional.empty();
    }

    @Override
    public List<EmployeeDto> getAll() {
        //todo: pagination
        List<EmployeeDto> employees = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM employees");
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next()) {
                employees.add(Mapper.mapEmployee(getEmployee(resultSet)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return employees;
    }

    @Override
    public boolean save(EmployeeDto dto) {
        Employee employee = Mapper.mapEmployeeDto(dto);
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement(
                    "INSERT INTO employees " +
                            "(first_name, last_name, patronymic, gender, birth_date, email, department_id, position_id) " +
                            "VALUES (?,?,?,?,?,?,?,?)");
            prepared.setString(1, employee.getFirstName());
            prepared.setString(2, employee.getLastName());
            prepared.setString(3, employee.getPatronymic());
            prepared.setString(4, employee.getGender().name());
            prepared.setDate(5, employee.getBirthDate());
            prepared.setString(6, employee.getEmail());
            prepared.setLong(7, employee.getDepartment_id());
            prepared.setLong(8, employee.getPosition_id());
            if (prepared.executeUpdate() > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public boolean update(EmployeeDto dto) {
        Employee employee = Mapper.mapEmployeeDto(dto);
        try (Connection connection = C3p0DataSource.getConnection()) {
            String statement = String.format("UPDATE employees SET %s WHERE id=%d", getStatementArgs(employee, ","), employee.getId());
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
    public boolean delete(EmployeeDto dto) {
        Employee employee = Mapper.mapEmployeeDto(dto);
        try (Connection connection = C3p0DataSource.getConnection()) {
            PreparedStatement prepared = connection.prepareStatement("DELETE FROM employees WHERE id=?");
            prepared.setLong(1, employee.getId());
            if (prepared.executeUpdate() > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return false;
    }

    @Override
    public List<EmployeeDto> simpleSearch(EmployeeDto dto) {
        Employee employee = Mapper.mapEmployeeDto(dto);
        List<EmployeeDto> employees = new ArrayList<>();
        try (Connection connection = C3p0DataSource.getConnection()) {
            if (employee.getId() != null){
                Optional<EmployeeDto> employeeById = get(employee.getId());
                employeeById.ifPresent(employees::add);
                return employees;
            }
            String statement = String.format("SELECT * FROM employees WHERE %s", getStatementArgs(employee, " AND "));
            PreparedStatement prepared = connection.prepareStatement(statement);
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next())
                employees.add(Mapper.mapEmployee(getEmployee(resultSet)));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: log this
        }
        return employees;
    }

    private Employee getEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setPatronymic(resultSet.getString("patronymic"));
        employee.setGender(EmployeeGender.valueOf(resultSet.getString("gender")));
        employee.setBirthDate(resultSet.getDate("birth_date"));
        employee.setDepartment_id(resultSet.getLong("department_id"));
        employee.setPosition_id(resultSet.getLong("position_id"));
        employee.setEmail(resultSet.getString("email"));
        return employee;
    }

    private String getStatementArgs(Employee employee, String delimiter) {
        List<String> set = new ArrayList<>();
        if (employee.getFirstName() != null)
            set.add(String.format("first_name='%s'", employee.getFirstName()));
        if (employee.getLastName() != null)
            set.add(String.format("last_name='%s'", employee.getLastName()));
        if (employee.getPatronymic() != null)
            set.add(String.format("patronymic='%s'", employee.getPatronymic()));
        if (employee.getGender() != null)
            set.add(String.format("gender='%s'", employee.getGender().name()));
        if (employee.getBirthDate() != null)
            set.add(String.format("birth_date='%s'", employee.getBirthDate().toString()));
        if (employee.getEmail() != null)
            set.add(String.format("email='%s'", employee.getEmail()));
        if (employee.getDepartment_id() != null)
            set.add(String.format("department_id='%d'", employee.getDepartment_id()));
        if (employee.getPosition_id() != null)
            set.add(String.format("position_id='%d'", employee.getPosition_id()));
        return String.join(delimiter, set);
    }
}
