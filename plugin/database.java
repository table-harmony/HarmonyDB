import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Database {
    private String name;
    private String version;
    private Map<String, Migration> migrations;
    private Map<String, Collection> collections;

    private final DatabasePersistence persistence;

    public Database(DatabasePersistence persistence, String name, String version) {
        this.name = name;
        this.version = version;
        this.persistence = persistence;
        
        load();
    }

    public void save() throws IOException {
        persistence.save(this);
    }

    public void load() throws IOException {
        persistence.load(this);
    }

    public void applyChange(DatabaseChange change) throws MigrationValidationException {
        if (!change.validate(this)) {
            throw new MigrationValidationException("Invalid change: " + change);
        }

        //TODO: Apply change
    }

    public void applyMigration(Migration migration) throws MigrationValidationException {

        save();
    }

    public String getName() { return name; }
    public String getVersion() { return version; }

    public Migration getMigration(String name) { return migrations.get(name); }
    public Map<String, Migration> getMigrations() { return new HashMap<>(migrations); }

    public Collection getCollection(String name) { return collections.get(name); }
    public Map<String, Collection> getCollections() { return new HashMap<>(collections); }
}