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

        JsonNode collectionsNode = root.get("collections");
        for (JsonNode collectionNode : collectionsNode) {
            Collection collection = new Collection(collectionNode);
            database.addCollection(collection);
        }

        JsonNode migrationsNode = root.get("migrations");
        for (JsonNode migrationNode : migrationsNode) {
            Migration migration = new Migration(migrationNode);
            database.addMigration(migration);
        }
    }

    public void save(Database database) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", database.getName());
        data.put("collections", database.getCollections());
        data.put("migrations", database.getMigrations());

        objectMapper.writeValue(new File(filePath), data);
    }
}