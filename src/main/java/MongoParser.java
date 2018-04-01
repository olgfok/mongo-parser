import enums.ConditionOperator;
import enums.LogicalOperator;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MongoParser {

    public FilterItemGroupDto parse(RequestDto query) {
        Map<String, Object> map = query.getMap();
        Element element = parseMap(map);
        FilterItemGroupDto groupDto = new FilterItemGroupDto();

        collectFilters(groupDto, element);
        return groupDto;

    }

    private Element parseMap(Map<String, Object> map) {
        Element element = new Element();

        for (Map.Entry<String, Object> item : map.entrySet()) {
            Object val = item.getValue();
            if (isPrimitive(val)) {
                element.addSimpleAttr(item.getKey(), val);
            } else if (isMap(val)) {
                element.addItem(item.getKey(), parseMap((Map) val));
            } else if (isCollection(val)) {
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

    private boolean isCollection(Object obj) {
        return obj instanceof Collection;
    }

    private boolean isPrimitive(Object obj) {
        Class clazz = obj.getClass();
        boolean isPrimitive = clazz.isPrimitive()
                || clazz.getName().startsWith("java.lang.");
        if (!isPrimitive && isCollection(obj)) {
            Collection list = (Collection) obj;
            isPrimitive = (list.isEmpty() || isPrimitive(list.iterator().next()));
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
            if (ConditionOperator.getByName(key) != null) {
                condition.setOperator(key);
                path = parentPath;
            }
            FilterItemDto filter = new FilterItemDto(path, condition);
            groupDto.addFilter(filter);

        }
    }

    private void collectFilters(FilterItemGroupDto parent, Element element) {
        Map<String, Object> simpleAttrs = element.getSimpleAttrs();
        createFiltersFromMap(simpleAttrs, parent, null);

        Map<String, Element> nestedMaps = element.getNestedMaps();
        for (Map.Entry<String, Element> nested : nestedMaps.entrySet()) {
            String key = nested.getKey();
            if (LogicalOperator.getByName(key) != null) {
                FilterItemGroupDto child = new FilterItemGroupDto();
                collectFilters(child, nested.getValue());
                parent.addChild(child);
                child.setOperator(key);
            } else {
                Element value = nested.getValue();
                if (!value.getSimpleAttrs().isEmpty()) {
                    createFiltersFromMap(nested.getValue().getSimpleAttrs(), parent, key);
                }
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


}
