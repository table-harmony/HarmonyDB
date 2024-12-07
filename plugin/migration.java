public class Migration {
    private String name;
    private List<CollectionChange> changes;
    
    public Migration(JsonNode node) {
        this.name = node.get("name").asText();
        this.changes = parseChanges(node.get("changes"));
    }

    private List<CollectionChange> parseChanges(JsonNode changesNode) {
        List<CollectionChange> changes = new ArrayList<>();
        
        for (JsonNode change : changesNode) {
            String action = change.get("action").asText();

            switch (action) {
                case "create" -> changes.add(new CreateCollectionChange(change));
                case "alter" -> changes.add(new AlterCollectionChange(change));
                case "drop" -> changes.add(new DropCollectionChange(change));
                default -> throw new IllegalArgumentException("Unknown change type: " + action);
            }
        }

        return changes;
    }

    public void apply(Database database) throws Exception {
        for (CollectionChange change : changes) {
            if (!change.isValid(database)) {
                throw new Exception("Invalid migration: " + name);
            }
        }

        for (CollectionChange change : changes) {
            change.apply(database);
        }

        //TODO: check database validity
        //TODO: rollback if invalid
        //TODO: persist migration
    }

    public void revert(Database database) {
        //TODO: revert migration
    }

    public String getName() {
        return name;
    }

    public List<CollectionChange> getChanges() {
        return changes;
    }

}