package dao;

import models.*;

import java.util.List;
import java.util.Optional;

public class PositionDao implements Dao<Position>{
    @Override
    public Optional<Position> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Position> getAll() {
        return null;
    }

    @Override
    public Position save(Position position) {
        return null;
    }

    @Override
    public Position update(Position position, String... params) {
        return null;
    }

    @Override
    public boolean delete(Position position) {
        return false;
    }
}
