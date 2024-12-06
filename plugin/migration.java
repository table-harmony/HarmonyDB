public class Migration {
  private String name;
  private List<DatabaseChange> changes;

  public Migration(JsonNode node) {
    this.name = node.get("name").asText();
    this.changes = node.get("changes").mapTo(DatabaseChange.class);
  }

  public void apply(Database database) throws MigrationValidationException {
    for (DatabaseChange change : changes) {
      if (!change.validate(database)) {
        throw new MigrationValidationException("Migration validation failed");
      }
  
      change.apply(database);
    }
  }
}

public class MigrationValidationException extends Exception {
  public MigrationValidationException(String message) {
    super(message);
  }
}
