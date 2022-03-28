import models.Position;

import java.util.Optional;

public class PositionApiController implements ApiController<Position> {
    @Override
    public Optional<Position> create(String info) {
        return Optional.empty();
    }

    @Override
    public Optional<Position> update(String id, String info) {
        return Optional.empty();
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Optional<Position> find(String searchRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<Position> view(String id) {
        return Optional.empty();
    }

    @Override
    public boolean loadFromFile(String fileName) {
        return false;
    }
}
