package persistence;

import model.Collection;
import model.Event;
import model.EventLog;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
//this class references code from this repo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: writes JSON representation of collection to file
    public void write(Collection c) {
        JSONObject json = c.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Collection loaded"));
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
