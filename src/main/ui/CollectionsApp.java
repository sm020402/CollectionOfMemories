package ui;

import model.Collection;
import model.Memory;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class CollectionsApp {
    private Collection january;
    private Collection february;
    private Collection march;
    private Collection april;
    private Collection may;
    private Collection june;
    private Collection july;
    private Collection august;
    private Collection september;
    private Collection october;
    private Collection november;
    private Collection december;
    private Scanner input;

    //EFFECTS: runs the collections application
    public CollectionsApp() {
        runCollections();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runCollections() {
        boolean cont = true;
        String command = null;

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
        } else {
            System.out.println("Not an option good buddy, try again");
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private Collection selectCollection() {
        System.out.println("Choose what month you wanna see...");
        String selection = "";
        selection = input.next();
        selection = selection.toLowerCase();
        return chooseMonth(selection);
    }

    //MODIFIES: this
    //EFFECTS: initializes collections
    private void initialize() {
        january = new Collection();
        february = new Collection();
        march = new Collection();
        april = new Collection();
        may = new Collection();
        june = new Collection();
        july = new Collection();
        august = new Collection();
        september = new Collection();
        october = new Collection();
        november = new Collection();
        december = new Collection();
        input = new Scanner(System.in);
        input.useDelimiter("\n");

    }

    //EFFECTS: displays options available to users
    private void display() {
        System.out.println("\nChoose an option below:");
        System.out.println("\ta -> add");
        System.out.println("\tr -> remove");
        System.out.println("\tv -> view");
        System.out.println("\tq -> quit");

    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void doAdd() {
        Collection chosen = selectCollection();
        System.out.println("Ok what's the song name?");
        String songName = input.next();
        System.out.println("By who???");
        String artistName = input.next();
        System.out.println("dope");
        Memory mem = new Memory(songName, artistName);
        chosen.addMemory(mem);
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void doRemove() {
        Collection chosen = selectCollection();
        System.out.println("ayo type the name of the song you're booting");
        String songName = input.next();
        Memory mem = chosen.findMemoryBasedOnSong(songName);
        chosen.removeMemory(mem);
        System.out.println("I gotcha fam");
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void doView() {
        Collection chosen = selectCollection();
        for (Memory m : chosen) {
            System.out.println(m.getSongName() + " - " + m.getArtist());
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private Collection chooseMonth(String selection) {
        if (selection.equals("january")) {
            return january;
        } else if (selection.equals("february")) {
            return february;
        } else if (selection.equals("march")) {
            return march;
        } else if (selection.equals("april")) {
            return april;
        } else if (selection.equals("may")) {
            return may;
        } else if (selection.equals("june")) {
            return june;
        } else if (selection.equals("july")) {
            return july;
        } else if (selection.equals("august")) {
            return august;
        } else if (selection.equals("september")) {
            return september;
        } else {
            return chooseBetweenLastFewMonths(selection);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private Collection chooseBetweenLastFewMonths(String selection) {
        if (selection.equals("october")) {
            return october;
        } else if (selection.equals("november")) {
            return november;
        } else {
            return december;
        }
    }
}
