import java.util.List;
import java.util.Map;

public class MongoParser {

    public void parse(QueryDto query) {
        Map<String, Object> map = query.getMap();
        Element element = parseMap(map);
        System.out.println(element);

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

}
