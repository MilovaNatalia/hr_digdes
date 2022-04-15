package services;

import java.util.List;
import java.util.Optional;

public interface DataService<T, S> {
    public boolean create(T info);

    public boolean update(T info);

    public boolean delete(T info);

    public List<S> find(T searchRequest);

    public Optional<S> get(Long id);

    public List<S> getAll();

    public boolean loadFromFile(String fileName);
}
