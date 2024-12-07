public interface CollectionChange {
    void apply(Database database);
    boolean isValid(Database database);
    String getCollectionName();
}

public class CreateCollectionChange implements CollectionChange {
    private final Schema schema;
    private final String collectionName;

    public CreateCollectionChange(JsonNode node) {
        this.collectionName = node.get("collection").asText();
        this.schema = new Schema(node.get("schema"));
    }

    @Override
    public boolean isValid(Database database) {
        return !database.hasCollection(collectionName);
    }

    @Override
    public void apply(Database database) {
        Collection collection = new Collection(collectionName, schema);
        database.addCollection(collection);
    }

    @Override
    public String getCollectionName() {
        return collectionName;
    }
}

public class DropCollectionChange implements CollectionChange {
    private final String collectionName;
    private final String columnName;
    private final boolean isDropColumn;

    public DropCollectionChange(JsonNode node) {
        this.collectionName = node.get("collection").asText();
        this.columnName = node.has("column_name") ? node.get("column_name").asText() : null;
        this.isDropColumn = columnName != null;
    }

    @Override
    public boolean isValid(Database database) {
        Collection collection = database.getCollection(collectionName);
        if (collection == null) {
            return false;
        }

        if (isDropColumn) {
            Schema schema = collection.getSchema();
            return schema.hasField(columnName);
        }
        
        return true;
    }

    @Override
    public void apply(Database database) {
        if (!isDropColumn) {
            database.removeCollection(collectionName);
            return;
        }

        Collection collection = database.getCollection(collectionName);
        Schema schema = collection.getSchema();
        schema.removeField(columnName);
    }

    @Override
    public String getCollectionName() {
        return collectionName;
    }
}


public class AlterCollectionChange implements CollectionChange {
    private final String columnName;
    private final FieldType columnType;
    private final String collectionName;

    public AlterCollectionChange(JsonNode node) {
        this.collectionName = node.get("collection").asText();
        this.columnName = node.get("column_name").asText();
        this.columnType = FieldType.valueOf(node.get("column_type").asText().toUpperCase());
    }

    @Override
    public boolean isValid(Database database) {
        Collection collection = database.getCollection(collectionName);
        if (collection == null) {
            return false;
        }
        
        Schema schema = collection.getSchema();
        return schema.hasField(columnName);
    }

    @Override
    public void apply(Database database) {
        Collection collection = database.getCollection(collectionName);
        Schema schema = collection.getSchema();
        
        Field newField = new Field(columnName, columnType);
        
        Field oldField = schema.getField(columnName);
        oldField.getConstraints().forEach(newField::addConstraint);
        
        schema.removeField(columnName);
        schema.addField(newField);
    }

    @Override
    public String getCollectionName() {
        return collectionName;
    }
}