import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Element {
    private Map<String, List<Element>> listItems = new HashMap<>();
    private Map<String, Element> mapItems = new HashMap<>();
    private Map<String, Object> simpleAttrs = new HashMap<>();

    public void addSimpleAttr(String key, Object attr) {
        simpleAttrs.put(key, attr);
    }


    //Может хранить простые атрибуты или списки примитивных типов

    public void addItem(String key, Element element) {
        mapItems.put(key, element);
    }

    public void addListItem(String key, Element element) {
        List<Element> list = listItems.computeIfAbsent(key, x -> new ArrayList());
        list.add(element);
    }

}
