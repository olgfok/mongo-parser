package enums;

import java.util.HashMap;
import java.util.Map;

public enum LogicalOperator {
    $AND,
    $OR;


    public static Map<String, LogicalOperator> values = new HashMap<>();

    static {
        for (LogicalOperator val : values()) {
            values.put(val.name(), val);
        }
    }

    public static LogicalOperator of(String key) {
        return values.get(key.toUpperCase());
    }
}
