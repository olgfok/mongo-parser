import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class TestUtils {

    private static Gson gson = new GsonBuilder().create();

    private static RequestDto readFromInputStream(InputStream inputStream)
            throws IOException {
        Reader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        JsonReader jsonReader = new JsonReader(bufferedReader);
        RequestDto query = gson.fromJson(jsonReader, RequestDto.class);
        return query;
    }

    public static RequestDto readFile(String file) throws IOException {
        InputStream inputStream = TestUtils.class.getResourceAsStream(file);
        return readFromInputStream(inputStream);
    }
}
