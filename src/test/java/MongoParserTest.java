import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.junit.Test;

import java.io.*;

public class MongoParserTest {

    private static Gson gson = new GsonBuilder().create();

    private static String QUERY_FILE = "/query.json";

    private static QueryDto readFromInputStream(InputStream inputStream)
            throws IOException {
        Reader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        JsonReader jsonReader = new JsonReader(bufferedReader);
        QueryDto query = gson.fromJson(jsonReader, QueryDto.class);
        return query;
    }

    private static QueryDto readFile() throws IOException {
        InputStream inputStream = MongoParserTest.class.getResourceAsStream(QUERY_FILE);
        return readFromInputStream(inputStream);
    }

    @Test
    public void testParser() throws IOException {
        QueryDto dto = readFile();
        new MongoParser().parse(dto);
    }
}
