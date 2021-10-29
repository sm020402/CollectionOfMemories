package persistence;

import model.Collection;
import model.Memory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Collection c = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCollection.json");
        try {
            Collection c = reader.read();
            assertEquals("collection1", c.getName());
            assertEquals(0, c.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCollection.json");
        try {
            Collection c = reader.read();
            assertEquals("collection2", c.getName());
            List<Memory> mems = c.getMemories();
            assertEquals(2, mems.size());
            checkMemory("a", "b", mems.get(0));
            checkMemory("c", "d", mems.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
