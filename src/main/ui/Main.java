package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new CollectionsApp();
        } catch (FileNotFoundException e) {
            System.out.println("unable to run application");
        }
    }
}
