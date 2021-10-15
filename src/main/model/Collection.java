package model;

import java.util.Iterator;
import java.util.LinkedList;

public class Collection implements Iterable<Memory> {
    private LinkedList<Memory> coll;

    public Collection() {
        coll = new LinkedList<Memory>();
    }

    //MODIFIES:
    //EFFECTS:
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

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public Memory findMemoryBasedOnSong(String s) {
        LinkedList<String> collectionSongs = new LinkedList<String>();
        for (Memory m: coll) {
            collectionSongs.addLast(m.getSongName());
        }

        int indexOfSong = collectionSongs.indexOf(s);
        return coll.get(indexOfSong);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public int length() {

        return coll.size();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public boolean contains(Memory m) {

        return coll.contains(m);
    }

    @Override
    public Iterator<Memory> iterator() {
        return coll.iterator();
    }
}
