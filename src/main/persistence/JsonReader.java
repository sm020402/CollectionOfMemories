package persistence;

import com.sun.xml.internal.bind.api.impl.NameConverter;
import model.Collection;
import model.Memory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Collection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCollection(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));

        }
        return contentBuilder.toString();
    }

    private Collection parseCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("month");
        Collection c = new Collection(name);
        addMemories(c, jsonObject);
        return c;

    }

    private void addMemories(Collection c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("memories");
        for (Object json : jsonArray) {
            JSONObject nextMemory = (JSONObject) json;
            addMemory(c, nextMemory);
        }
    }

    private void addMemory(Collection c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String artist = jsonObject.getString("artist");
        Memory m = new Memory(name, artist);
        c.addMemory(m);

    }
}
