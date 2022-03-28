package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(long id);
    List<T> getAll();
    T save(T t);
    T update(T t, String... params);
    boolean delete (T t);
}
