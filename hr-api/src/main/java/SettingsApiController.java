import notifier.Settings;

import java.util.Optional;

public class SettingsApiController implements ApiController<Settings> {
    @Override
    public Optional<Settings> create(String info) {
        return Optional.empty();
    }

    @Override
    public Optional<Settings> update(String id, String info) {
        return Optional.empty();
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Optional<Settings> find(String searchRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<Settings> view(String id) {
        return Optional.empty();
    }

    @Override
    public boolean loadFromFile(String fileName) {
        return false;
    }
}
