import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MongoParserTest {


    private static String QUERY_FILE = "/query.json";

    private List<String> simpleAttrs = Arrays.asList("product.code", "product.age");

    private List<String> simpleNestedAttrs = Arrays.asList("product.id", "product.services.name", "product.name");

    @Test
    public void testParser() throws IOException {
        RequestDto dto = TestUtils.readFile(QUERY_FILE);
        FilterItemGroupDto groupDto = new MongoParser().parse(dto);
        List<FilterItemDto> filters = groupDto.getFilters();
        assertEquals(4, filters.size());
        filters.forEach(filter -> assertTrue(simpleAttrs.contains(filter.getPath())));

        //check nested map with filters
        List<FilterItemGroupDto> nested = groupDto.getNested();
        assertEquals(1, nested.size());

        FilterItemGroupDto nestedFilter = nested.get(0);
        assertEquals("$and", nestedFilter.getOperator());
        filters = nestedFilter.getFilters();
        assertEquals(3, filters.size());
        filters.forEach(filter -> assertTrue(simpleNestedAttrs.contains(filter.getPath())));
        nested = nestedFilter.getNested();
        assertEquals(1, nested.size());
        nestedFilter = nested.get(0);
        assertEquals("$or", nestedFilter.getOperator());
        assertEquals(4, nestedFilter.getFilters().size());

    }
}
