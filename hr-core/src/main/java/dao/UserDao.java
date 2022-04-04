package dao;

import dao.Dao;
import models.*;
import util.C3p0DataSource;

import java.util.List;
import java.util.Optional;

public class UserDao{
    //todo: auth
    public Optional<User> get(String username) {
        return Optional.empty();
    }

    public List<User> getAll() {
        return null;
    }

    public boolean save(User user) {
        return false;
    }

    public boolean update(User user) {
        return false;
    }

    public boolean delete(User user) {
        return false;
    }
    public Optional<User> search(User User) {
        return Optional.empty();
    }
}
