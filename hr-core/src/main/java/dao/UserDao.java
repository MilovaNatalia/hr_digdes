package dao;

import models.*;

import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User>{
    @Override
    public Optional<User> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user, String... params) {
        return null;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }
}
