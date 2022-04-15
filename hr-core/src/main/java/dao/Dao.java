package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    //todo: delete schema_name
    Optional<T> get(long id);
    List<T> getAll();
    boolean save(T t);
    boolean update(T t);
    boolean delete (T t);
    List<T> simpleSearch(T t);
}
