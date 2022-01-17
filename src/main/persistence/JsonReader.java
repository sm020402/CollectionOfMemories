package persistence;


import model.Collection;
import model.Memory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
//this class references code from this repo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads collection from file and returns, throws exception if file cannot be read
    public Collection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCollection(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));

        }
        return contentBuilder.toString();
    }

    //EFFECTS: parses collection from JSON object and returns it
    private Collection parseCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("month");
        Collection c = new Collection(name);
        addMemories(c, jsonObject);
        return c;

    }

    //MODIFIES: c
    //EFFECTS: parses memories from JSON object and adds them to the collection
    private void addMemories(Collection c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("memories");
        for (Object json : jsonArray) {
            JSONObject nextMemory = (JSONObject) json;
            addMemory(c, nextMemory);
        }
    }

    //MODIFIES: c
    //EFFECTS: parses memory from JSON object and adds it to collection
    private void addMemory(Collection c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        Memory m = new Memory(name, description);
        c.addMemory(m);

    }
}
