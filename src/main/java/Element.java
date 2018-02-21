import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Element {
    public Map<String, List<Element>> getNestedLists() {
        return nestedLists;
    }

    public Map<String, Element> getNestedMaps() {
        return nestedMaps;
    }

    public Map<String, Object> getSimpleAttrs() {
        return simpleAttrs;
    }

    private Map<String, List<Element>> nestedLists = new HashMap<>();
    private Map<String, Element> nestedMaps = new HashMap<>();
    private Map<String, Object> simpleAttrs = new HashMap<>();

    public void addSimpleAttr(String key, Object attr) {
        simpleAttrs.put(key, attr);
    }


    //Может хранить простые атрибуты или списки примитивных типов

    public void addItem(String key, Element element) {
        nestedMaps.put(key, element);
    }

    public void addListItem(String key, Element element) {
        List<Element> list = nestedLists.computeIfAbsent(key, x -> new ArrayList());
        list.add(element);
    }

}
