package services;

import java.util.Optional;

public interface DataService <T>{
    Optional<T> create(String info);
    Optional<T> update(String id, String info);
    boolean delete(String id);
    Optional<T> view (String id);
    Optional<T> find (String searchRequest);
    boolean loadFromFile(String fileName);
}
