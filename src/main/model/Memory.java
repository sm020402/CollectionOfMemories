package model;

//Represents a song described by its song name and artist

import org.json.JSONObject;
import persistence.Writable;

public class Memory implements Writable {
    private String songName;
    private String artist;

    public Memory(String songName, String artist) {
        this.songName = songName;
        this.artist = artist;
    }

    //EFFECTS: returns song name
    public String getSongName() {
        return songName;
    }

    //EFFECTS: returns artist
    public String getArtist() {
        return artist;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", songName);
        json.put("artist", artist);
        return json;
    }

}
