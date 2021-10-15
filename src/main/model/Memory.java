package model;

//Represents a song described by its song name and artist

public class Memory {
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

}
