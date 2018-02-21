import java.util.ArrayList;
import java.util.List;

public class FilterItemDto {
    public FilterItemDto() {
    }

    public FilterItemDto(String path, FilterCondition condition) {
        this.path = path;
        this.condition = condition;
    }

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FilterCondition getCondition() {
        return condition;
    }

    public void setCondition(FilterCondition condition) {
        this.condition = condition;
    }

    private FilterCondition condition;

}
