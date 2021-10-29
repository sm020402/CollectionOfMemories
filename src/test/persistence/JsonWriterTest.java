package persistence;

import model.Collection;
import model.Memory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Collection c = new Collection("collection1");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCollection() {
        try {
            Collection c = new Collection("collection1");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCollection.json");
            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCollection.json");
            c = reader.read();
            assertEquals("collection1", c.getName());
            assertEquals(0, c.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralCollection() {
        try {
            Collection c = new Collection("collection2");
            c.addMemory(new Memory("a", "b"));
            c.addMemory(new Memory("c", "d"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCollection.json");
            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCollection.json");
            c = reader.read();
            assertEquals("collection2", c.getName());
            List<Memory> mems = c.getMemories();
            assertEquals(2, mems.size());
            checkMemory("a", "b", mems.get(0));
            checkMemory("c", "d", mems.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

