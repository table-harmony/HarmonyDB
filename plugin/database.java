import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Database {    
    private final String filePath; // Path to the database file
    private Map<String, Object> metadata; // Metadata of the database
    private final ObjectMapper objectMapper = new ObjectMapper(); // Object mapper for JSON

    public Database(String filePath) throws IOException {
      this.filePath = filePath;
      load();
    }

    private void load() throws IOException {
      File file = new File(filePath);

      if (!file.exists()) {
        throw new IOException("Database file not found at: " + filePath);
      }

      metadata = objectMapper.readValue(file, Map.class);
    }

    private void save() throws IOException {
      objectMapper.writeValue(new File(filePath), metadata);
    }

    public String getName() {
      return (String) data.get("name");
    }

    public String getVersion() {
      return (String) data.get("version");
    }

    public List<Map<String, Migration>> getMigrations() {
      return (List<Map<String, Migration>>) data.get("migrations");
    }

    public List<Map<String, Collection>> getCollections() {
      return (List<Map<String, Collection>>) data.get("collections");
    }
}
