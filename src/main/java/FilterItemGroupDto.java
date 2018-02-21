import java.util.ArrayList;
import java.util.List;

public class FilterItemGroupDto {
    public void setOperator(String operator) {
        this.operator = operator;
    }

    private String operator;
    private List<FilterItemDto> filters = new ArrayList<>();

    private List<FilterItemGroupDto> children = new ArrayList<>();

    public void addChild(FilterItemGroupDto child) {
        children.add(child);
    }


    public void addFilter(FilterItemDto filter) {
        filters.add(filter);
    };

}
