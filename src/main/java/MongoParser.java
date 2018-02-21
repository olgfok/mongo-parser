import java.util.Map;

public class MongoParser {

    public void parse(QueryDto query) {
        Map<String, Object> map = query.getMap();
        Element root = new Element();
        for (Map.Entry<String, Object> item : map.entrySet()) {
            Object val = item.getValue();
            Element child = new Element();
            if (isPrimitive(val)) {
                child.setSimpleAttr(val);
            } else
                root.addItem(item.getKey(), );
        }
    }

    private boolean isPrimitive(Object obj) {
        Class clazz = obj.getClass();
        return clazz.isPrimitive()
                || clazz.getName().startsWith("java.lang.");
    }

}
