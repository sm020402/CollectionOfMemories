package persistence;

import model.Memory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMemory(String song, Memory m) {
        assertEquals(song, m.getSongName());
    }

}
