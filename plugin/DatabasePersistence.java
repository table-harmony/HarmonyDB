public interface DatabasePersistence {
    void load(Database database) throws IOException;
    void save(Database database) throws IOException;
}

public class JsonDatabasePersistence implements DatabasePersistence {
    private final String filePath;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DatabasePersistence(String filePath) {
        this.filePath = filePath;
    }

    public void load(Database database) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Database file not found: " + filePath);
        }

        JsonNode root = objectMapper.readTree(file);
        
        database.setName(root.get("name").asText());
        database.setVersion(root.get("version").asText());

        JsonNode collectionsNode = root.get("collections");
        for (JsonNode collectionNode : collectionsNode) {
            Collection collection = new Collection(collectionNode);
            database.getCollections().put(collection.getName(), collection);
        }

        JsonNode migrationsNode = root.get("migrations");
        for (JsonNode migrationNode : migrationsNode) {
            Migration migration = new Migration(migrationNode);
            database.getMigrations().add(migration);
        }
    }

    public void save(Database database) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", database.getName());
        data.put("version", database.getVersion());
        data.put("collections", database.getCollections().values());
        data.put("migrations", database.getMigrations());

        objectMapper.writeValue(new File(filePath), data);
    }
}