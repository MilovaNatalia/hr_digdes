package web;

import java.util.Optional;

public interface ApiController<T> {
    Optional<T> create(String info);

    Optional<T> update(String id, String info);

    boolean delete(String id);

    Optional<T> find(String searchRequest);

    Optional<T> view(String id);

    boolean loadFromFile(String fileName);

    //todo: parse request

}
