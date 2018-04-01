import java.util.ArrayList;
import java.util.List;


public class FilterItemGroupDto {
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Logical operator
     */
    private String operator;

    /**
     * List of filters
     */
    private List<FilterItemDto> filters = new ArrayList<>();

    /**
     * List of nested filter groups.
     */
    private List<FilterItemGroupDto> nested = new ArrayList<>();

    public void addChild(FilterItemGroupDto child) {
        nested.add(child);
    }

    public void addFilter(FilterItemDto filter) {
        filters.add(filter);
    }


    public String getOperator() {
        return operator;
    }

    public List<FilterItemDto> getFilters() {
        return filters;
    }

    public List<FilterItemGroupDto> getNested() {
        return nested;
    }
}
