package enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Logical operators
 */
public enum LogicalOperator {
    AND("$and"),
    OR("$or");

    private String name;

    LogicalOperator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Map<String, LogicalOperator> values = new HashMap<>();

    static {
        for (LogicalOperator val : values()) {
            values.put(val.getName(), val);
        }
    }

    public static LogicalOperator getByName(String key) {
        return key != null ? values.get(key.toLowerCase()) : null;
    }


}
