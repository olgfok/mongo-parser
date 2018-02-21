import java.util.HashMap;
import java.util.Map;

public class Element {
    private Map<String, Element> items = new HashMap<String, Element>();

    public Map<String, Element> getItems() {
        return items;
    }

    public void setItems(Map<String, Element> items) {
        this.items = items;
    }

    public Object getSimpleAttr() {
        return simpleAttr;
    }

    public void setSimpleAttr(Object simpleAttr) {
        this.simpleAttr = simpleAttr;
    }

    private Object simpleAttr;

    public void addItem(String key, Element element) {
        items.put(key, element);
    }

    public Element removeItem(String key) {
        return items.remove(key);
    }
}
