import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Database {
    private String name;
    private List<Migration> migrations;
    private List<Collection> collections;

    private final DatabasePersistence persistence;

    public Database(DatabasePersistence persistence) {
        this.persistence = persistence;

        load();
    }

    public void save() throws IOException {
        persistence.save(this);
    }

    public void load() throws IOException {
        persistence.load(this);
    }

    public String getName() { return name; }

    public void setMigrations(List<Migration> migrations) { 
        this.migrations = new ArrayList<>(migrations);
    }
    public void addMigration(Migration migration) { 
        this.migrations.add(migration);
    }
    public Migration getMigration(String name) { 
        return migrations.get(name);
    }
    public List<Migration> getMigrations() { 
        return new ArrayList<>(migrations); 
    }

    public void addCollection(Collection collection) { 
        this.collections.add(collection); 
    }
    public void setCollections(List<Collection> collections) { 
        this.collections = new ArrayList<>(collections); 
    }
    public Collection getCollection(String name) { 
        return collections.get(name); 
    }
    public List<Collection> getCollections() { 
        return new ArrayList<>(collections); 
    }
}