public class Collection {
    private String name;
    private Schema schema;
    private Location spawnLocation;

    public Collection(String name, Schema schema) {
        this.name = name;
        this.schema = schema;
        this.spawnLocation = new Location(0, 0, 0);
    }

    public Collection(String name, Schema schema, Location spawnLocation) {
        this.name = name;
        this.schema = schema;
        this.spawnLocation = spawnLocation;
    }

    public String getName() {
        return name;
    }

    public Schema getSchema() {
        return schema;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void updateSchema(Schema newSchema) {
        this.schema = newSchema;
    }

    public static class Location {
        private int x;
        private int y;
        private int z;

        public Location(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() { return x; }
        public int getY() { return y; }
        public int getZ() { return z; }

        @Override
        public String toString() {
            return "Location{x=" + x + ", y=" + y + ", z=" + z + '}';
        }
    }
}