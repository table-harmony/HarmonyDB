public enum FieldType {
    UUID, 
    STRING, 
    NUMBER, 
    TIMESTAMP, 
    BOOLEAN
}

public class Constraint {
    private String type;
    private Object value;

    public Constraint(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}

public class Field {
    private String name;
    private FieldType type;
    private List<Constraint> constraints;

    public Field(String name, FieldType type) {
        this.name = name;
        this.type = type;
        this.constraints = new ArrayList<>();
    }

    public void addConstraint(Constraint constraint) {
        //TODO: validate constraint
          
        constraints.add(constraint);
    }

    public boolean hasConstraint(String constraintType) {
        return constraints.stream()
            .anyMatch(c -> c.getType().equals(constraintType));
    }

    public Constraint getConstraint(String constraintType) {
        return constraints.stream()
            .filter(c -> c.getType().equals(constraintType))
            .findFirst()
            .orElse(null);
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    public List<Constraint> getConstraints() {
        return new ArrayList<>(constraints);
    }
}

public class Schema {
    private Map<String, Field> fields;

    public Schema() {
        this.fields = new HashMap<>();
    }

    public void addField(Field field) {
        fields.put(field.getName(), field);
    }

    public Field getField(String fieldName) {
        return fields.get(fieldName);
    }

    public boolean hasField(String fieldName) {
        return fields.containsKey(fieldName);
    }

    public void removeField(String fieldName) {
        fields.remove(fieldName);
    }

    public Collection<Field> getAllFields() {
        return fields.values();
    }
}
