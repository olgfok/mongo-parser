package enums;

import java.util.HashMap;
import java.util.Map;

public enum ConditionOperator {
    $IN,
    $EQ,
    $GT,
    $LT,
    $CONTAINS,
    $LTE,
    $GTE;
    public static Map<String, ConditionOperator> values = new HashMap<>();

    static {
        for (ConditionOperator val : values()) {
            values.put(val.name(), val);
        }
    }

    public static ConditionOperator of(String key) {
        return values.get(key.toUpperCase());
    }
}
