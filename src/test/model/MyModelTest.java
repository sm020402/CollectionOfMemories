package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CollectionTest {
     private Collection collection;

     @BeforeEach
    public void runBefore() {

         collection = new Collection("collectionTest");
     }

     @Test
    public void testAdd() {
         Memory newMem = new Memory("Cellar Door");
         collection.addMemory(newMem);
         assertEquals(1, collection.length());
     }

     @Test
    public void testAddWhenAlreadyPresent() {
         Memory newMem = new Memory("Cellar Door");
         Memory otherMem = new Memory("Show Business");
         collection.addMemory(otherMem);
         collection.addMemory(newMem);
         collection.addMemory(newMem);
         assertEquals(2, collection.length());
     }

     @Test
    public void removesProperly() {
         Memory newMem = new Memory("Cellar Door");
         Memory otherMem = new Memory("Show Business");
         collection.addMemory(otherMem);
         collection.addMemory(newMem);
         collection.removeMemory(newMem);
         assertEquals(1, collection.length());
     }

     @Test
    public void findMemory() {
         Memory newMem = new Memory("Cellar Door");
         Memory otherMem = new Memory("Show Business");
         collection.addMemory(otherMem);
         collection.addMemory(newMem);
         assertEquals(otherMem, collection.findMemoryBasedOnSong("Show Business"));
     }

     @Test
    public void containsTest() {
         Memory newMem = new Memory("Cellar Door");
         Memory otherMem = new Memory("Show Business");
         collection.addMemory(otherMem);
         collection.addMemory(newMem);
         assertTrue(collection.contains(newMem));

     }

     @Test
    public void containsWhenNotContained() {
         Memory newMem = new Memory("Cellar Door");
         Memory otherMem = new Memory("Show Business");
         collection.addMemory(otherMem);
         assertFalse(collection.contains(newMem));
     }

}