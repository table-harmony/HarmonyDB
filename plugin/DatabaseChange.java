public interface DatabaseChange {
  void apply(Database database) throws MigrationValidationException;
  boolean validate(Database database);
}