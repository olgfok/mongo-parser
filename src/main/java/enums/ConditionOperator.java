package enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Possible comparison operators
 */
public enum ConditionOperator {
    IN("$in"),
    EQ("$eq"),
    GT("$gt"),
    LT("$lt"),
    CONTAINS("$contains"),//for searching substrings, instead of regex
    LTE("$lte"),
    GTE("$gte");

    private String name;

    public String getName() {
        return name;
    }

    ConditionOperator(String name) {
        this.name = name;
    }

    public static Map<String, ConditionOperator> values = new HashMap<>();

    static {
        for (ConditionOperator val : values()) {
            values.put(val.getName(), val);
        }
    }

    public static ConditionOperator getByName(String key) {
        return key != null ? values.get(key.toLowerCase()) : null;
    }


}
