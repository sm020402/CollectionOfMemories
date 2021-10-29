package ui;

import model.Collection;
import model.Memory;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

//this class references code from this repo
//https://github.students.cs.ubc.ca/CPSC210/TellerApp

public class CollectionsApp {
    private static final String JSON_STORE = "./data/myFile.json";
    private Scanner input;
    private Collection january;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;



    //EFFECTS: runs the collections application
    public CollectionsApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        january = new Collection("january");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runCollections();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runCollections() {
        boolean cont = true;
        String command = null;
        input = new Scanner(System.in);

        initialize();

        while (cont) {
            display();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                cont = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("K, bye");
    }

    //MODIFIES: this
    //EFFECTS: processes specific user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("r")) {
            doRemove();
        } else if (command.equals("v")) {
            doView();
        } else if (command.equals("c")) {
            doCount();
        } else if (command.equals("s")) {
            saveCollections();
        } else if (command.equals("l")) {
            loadCollections();
        } else {
            System.out.println("Not an option good buddy, try again");
        }
    }

//    //REQUIRES: selection to be one of the given months
//    //EFFECTS: chooses collection to which the following action will be applied
//    private Collection selectCollection() {
//        System.out.println("Choose with month you'd like to deal with...");
//        displayMonths();
//        String selection = "";
//        selection = input.next();
//        selection = selection.toLowerCase();
//        return chooseMonth(selection);
//    }

    //MODIFIES: this
    //EFFECTS: initializes collections
    private void initialize() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");

    }

    //EFFECTS: displays options available to users
    private void display() {
        System.out.println("\nChoose an option below:");
        System.out.println("\ta -> add");
        System.out.println("\tr -> remove");
        System.out.println("\tv -> view");
        System.out.println("\tc -> count");
        System.out.println("\ts -> save collections to file");
        System.out.println("\tl -> load collections from file");
        System.out.println("\tq -> quit");

    }

//    //EFFECTS: displays the possible months that can be chosen as a collection
//    private void displayMonths() {
//        System.out.println("january");
//        System.out.println("february");
//        System.out.println("march");
//        System.out.println("april");
//        System.out.println("may");
//        System.out.println("june");
//        System.out.println("july");
//        System.out.println("august");
//        System.out.println("september");
//        System.out.println("october");
//        System.out.println("november");
//        System.out.println("december");
//    }


    //MODIFIES: this
    //EFFECTS: adds a memory to the chosen collection
    private void doAdd() {
        System.out.println("Ok what's the song name?");
        String songName = input.next();
        System.out.println("By who???");
        String artistName = input.next();
        System.out.println("dope");
        Memory mem = new Memory(songName, artistName);
        january.addMemory(mem);
    }

    //EFFECTS: returns the number of memories in the chosen collection
    private void doCount() {
        System.out.println(january.length());
    }

    //REQUIRES: song chosen to be removed must be in the chosen collection
    //MODIFIES: this
    //EFFECTS: removes the given memory from the chosen collection
    private void doRemove() {
        System.out.println("ayo type the name of the song you're booting");
        String songName = input.next();
        Memory mem = january.findMemoryBasedOnSong(songName);
        january.removeMemory(mem);
        System.out.println("I gotcha fam");
    }

    //EFFECTS: displays the memories in the chosen collection
    private void doView() {
        for (Memory m : january) {
            System.out.println(m.getSongName() + " - " + m.getArtist());
        }
    }

    //REQUIRES: selection to be one of the given months
    //EFFECTS: chooses month
//    private Collection chooseMonth(String selection) {
//        if (selection.equals("january")) {
//            return january;
//        } else if (selection.equals("february")) {
//            return february;
//        } else if (selection.equals("march")) {
//            return march;
//        } else if (selection.equals("april")) {
//            return april;
//        } else if (selection.equals("may")) {
//            return may;
//        } else if (selection.equals("june")) {
//            return june;
//        } else {
//            return chooseBetweenLastFewMonths(selection);
//        }
//    }
//
//    //EFFECTS: allows the selection to choose between months july - december
//    // necessary to avoid making method too long
//    private Collection chooseBetweenLastFewMonths(String selection) {
//        if (selection.equals("july")) {
//            return july;
//        } else if (selection.equals("august")) {
//            return august;
//        } else if (selection.equals("september")) {
//            return september;
//        } else if (selection.equals("october")) {
//            return october;
//        } else if (selection.equals("november")) {
//            return november;
//        } else {
//            return december;
//        }
//    }



    private void saveCollections() {
        try {
            jsonWriter.open();
            jsonWriter.write(january);
            jsonWriter.close();
            System.out.println("Saved collections");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save");
        }
    }

    private void loadCollections() {
        try {
            january = jsonReader.read();
            System.out.println("Loaded");
        } catch (IOException e) {
            System.out.println("Unable to load");
        }

    }
}
