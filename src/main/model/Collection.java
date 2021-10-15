package model;

import java.util.Iterator;
import java.util.LinkedList;

public class Collection implements Iterable<Memory> {
    private LinkedList<Memory> coll;

    public Collection() {
        coll = new LinkedList<Memory>();
    }

    //MODIFIES: this
    //EFFECTS: adds a memory to the collection
    public void addMemory(Memory memory) {
        if (-1 == coll.indexOf(memory)) {
            coll.add(memory);
        }
    }

    //REQUIRES: given memory must already be in the collection
    //MODIFIES:this
    //EFFECTS: removes the memory from the Collection
    public void removeMemory(Memory memory) {
        int indexOfMem = coll.indexOf(memory);
        coll.remove(indexOfMem);
    }

    //REQUIRES: Song name to be included in one of the memories in the collection
    //EFFECTS: finds the memory with the given song name
    public Memory findMemoryBasedOnSong(String s) {
        LinkedList<String> collectionSongs = new LinkedList<String>();
        for (Memory m: coll) {
            collectionSongs.addLast(m.getSongName());
        }

        int indexOfSong = collectionSongs.indexOf(s);
        return coll.get(indexOfSong);
    }


    //EFFECTS: returns size of given collection
    public int length() {

        return coll.size();
    }


    //EFFECTS: returns true if given memory is in the collection
    public boolean contains(Memory m) {
        return coll.contains(m);
    }

    //EFFECTS: enables use of for each loop on collection
    // this method references code from this website:
    // https://www.javabrahman.com/corejava/implementing-iterable-interface-in-java-to-enable-for-each-loop-based-iteration/

    @Override
    public Iterator<Memory> iterator() {
        return coll.iterator();
    }
}
