import java.util.List;
import java.util.Map;

public class MongoParser {

    public void parse(QueryDto query) {
        Map<String, Object> map = query.getMap();
        Element element = parseMap(map);
        FilterItemGroupDto groupDto = new FilterItemGroupDto();

        collectFilters(groupDto, element);
        System.out.println(groupDto);

    }

    private Element parseMap(Map<String, Object> map) {
        Element element = new Element();

        for (Map.Entry<String, Object> item : map.entrySet()) {
            Object val = item.getValue();
            if (isPrimitive(val)) {
                element.addSimpleAttr(item.getKey(), val);
            } else if (isMap(val)) {
                element.addItem(item.getKey(), parseMap((Map) val));
            } else if (isList(val)) {
                for (Object listItem : (List) val) {
                    element.addListItem(item.getKey(), parseMap((Map) listItem));
                }
            }
        }
        return element;
    }


    private boolean isMap(Object obj) {
        return obj instanceof Map;
    }

    private boolean isList(Object obj) {
        return obj instanceof List;
    }

    private boolean isPrimitive(Object obj) {
        Class clazz = obj.getClass();
        boolean isPrimitive = clazz.isPrimitive()
                || clazz.getName().startsWith("java.lang.");
        if (!isPrimitive && isList(obj)) {
            List list = (List) obj;
            isPrimitive = (list.isEmpty() || isPrimitive(list.get(0)));
        }
        return isPrimitive;
    }

    private void createFiltersFromMap(Map<String, Object> simpleAttrs, FilterItemGroupDto groupDto,
                                      String parentPath) {
        for (Map.Entry<String, Object> attr : simpleAttrs.entrySet()) {
            String key = attr.getKey();
            String path = key;
            FilterCondition condition = new FilterCondition();
            condition.setValue(attr.getValue().toString());
            if (isConditionOperation(key)) {
                condition.setOperator(key);
                path = parentPath;
            }
            FilterItemDto filter = new FilterItemDto(path, condition);
            groupDto.addFilter(filter);

        }
    }

    public void collectFilters(FilterItemGroupDto parent, Element element) {
        Map<String, Object> simpleAttrs = element.getSimpleAttrs();
        createFiltersFromMap(simpleAttrs, parent, null);

        Map<String, Element> nestedMaps = element.getNestedMaps();
        for (Map.Entry<String, Element> nested : nestedMaps.entrySet()) {
            String key = nested.getKey();
            if (!isLogicalOperator(key)) {
                Element value = nested.getValue();
                if (!value.getSimpleAttrs().isEmpty()) {
                    createFiltersFromMap(nested.getValue().getSimpleAttrs(), parent, key);
                }
            } else {
                FilterItemGroupDto child = new FilterItemGroupDto();
                collectFilters(child, nested.getValue());
                parent.addChild(child);
                child.setOperator(key);
            }
        }

        Map<String, List<Element>> nestedLists = element.getNestedLists();
        for (Map.Entry<String, List<Element>> nested : nestedLists.entrySet()) {
            FilterItemGroupDto child = new FilterItemGroupDto();
            child.setOperator(nested.getKey());
            parent.addChild(child);
            List<Element> elements = nested.getValue();
            for (Element el : elements) {
                collectFilters(child, el);
            }
        }

    }


    //TODO USE enum
    private boolean isConditionOperation(String key) {
        return "$in".equalsIgnoreCase(key) ||
                "$eq".equalsIgnoreCase(key) || "$gt".equalsIgnoreCase(key)
                || "$lt".equalsIgnoreCase(key) ||
                "$contains".equalsIgnoreCase(key);
    }

    //TODO USE enum
    private boolean isLogicalOperator(String key) {
        return "$and".equalsIgnoreCase(key) ||
                "$or".equalsIgnoreCase(key);
    }

}
