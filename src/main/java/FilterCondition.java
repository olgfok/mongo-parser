public class FilterCondition {
    /**
     * One of mongo comparison operators ($eq,$in..)
     */
    private String operator;

    /**
     * Comparison operator's value (20, [1,2,3],"")
     */
    private String value;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
