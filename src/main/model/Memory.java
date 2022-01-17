package model;

//Represents a song described by its song name and artist

import org.json.JSONObject;
import persistence.Writable;

public class Memory implements Writable {
    private String songName;
    private String description;


    public Memory(String songName, String description) {
        this.songName = songName;
        this.description = description;
    }

    //EFFECTS: returns song name
    public String getSongName() {
        return songName;
    }

    //EFFECTS: returns artist
    public String getArtist() {
        return description;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", songName);
        json.put("description", description);
        return json;
    }

}
