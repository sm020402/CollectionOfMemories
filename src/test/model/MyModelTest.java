package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectionTest {
     private Collection collection;

     @BeforeEach
    public void runBefore() {
         collection = new Collection();
     }

     @Test
    public void testAdd() {
         Memory newMem = new Memory("Cellar Door",
                 "Angus and Julia Stone");
         collection.addMemory(newMem);
         assertEquals(1, collection.length());
     }

     @Test
    public void testAddWhenAlreadyPresent() {
         Memory newMem = new Memory("Cellar Door",
                 "Angus and Julia Stone");
         Memory otherMem = new Memory("Show Business",
                 "A Tribe Called Quest");
         collection.addMemory(otherMem);
         collection.addMemory(newMem);
         collection.addMemory(newMem);
         assertEquals(2, collection.length());
     }

     @Test
    public void removesProperly() {
         Memory newMem = new Memory("Cellar Door",
                 "Angus and Julia Stone");
         Memory otherMem = new Memory("Show Business",
                 "A Tribe Called Quest");
         collection.addMemory(otherMem);
         collection.addMemory(newMem);
         collection.removeMemory(newMem);
         assertEquals(1, collection.length());
     }

     @Test
    public void findMemory() {
         Memory newMem = new Memory("Cellar Door",
                 "Angus and Julia Stone");
         Memory otherMem = new Memory("Show Business",
                 "A Tribe Called Quest");
         collection.addMemory(otherMem);
         collection.addMemory(newMem);
         assertEquals(otherMem, collection.findMemoryBasedOnSong("Show Business"));
     }

}