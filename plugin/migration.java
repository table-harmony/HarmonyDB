public interface DatabaseChange {
  void apply(Database database) throws MigrationValidationException;
  boolean validate(Database database);
}

public class CreateCollectionChange implements DatabaseChange {
    private String collectionName;
    private Map<String, Object> schema;

    @Override
    public void apply(Database database) {
    }

    @Override
    public boolean validate(Database database) {
    }
}

public class AlterCollectionChange implements DatabaseChange {
    private String collectionName;
    private String columnName;
    private Map<String, Object> newColumnDefinition;

    @Override
    public void apply(Database database) {
    }

    @Override
    public boolean validate(Database database) {
    }
}

public class DropCollectionChange implements DatabaseChange {
    private String collectionName;

    @Override
    public void apply(Database database) {
    }

    @Override
    public boolean validate(Database database) {
    }
}

private class DropColumnChange implements DatabaseChange {
  private String collectionName;
  private String columnName;

  @Override
  public void apply(Database database) {
    if (!validate(database)) {
        throw new MigrationValidationException("Migration validation failed");
    }

    //TODO: apply change
  }

  public boolean validate(Database database) {
    return true;
  }
}

public class Migration {
  private String name;
  private List<DatabaseChange> changes;

  public Migration(String migrationJson) {
    // TODO: Parse migrationJson
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
