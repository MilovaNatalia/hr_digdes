package dao;

import notifier.Settings;

import java.util.List;
import java.util.Optional;

public class SettingsDao implements Dao<Settings>{
    @Override
    public Optional<Settings> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Settings> getAll() {
        return null;
    }

    @Override
    public Settings save(Settings settings) {
        return null;
    }

    @Override
    public Settings update(Settings settings, String... params) {
        return null;
    }

    @Override
    public boolean delete(Settings settings) {
        return false;
    }
}
