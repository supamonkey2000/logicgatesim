package com.jmoore.logicgatesim;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Logic Gate Sim
 * Created by Joshua Moore
 *
 * Core class houses all the main functions, as well as items that don't belong in a specific class.
 */
public class Core {
    static File DATA_DIR = new File("data/"); //Will probably be removed in future versions
    static File GATES_FILE = new File("data/gates.lgs"); //Same as this, there is no need for them
    static ArrayList<Gate> gates = new ArrayList<>(); //Contain the Gate_ objects that the UI will display (should this be moved to UI class?)

    static UI ui;
    private static Poller poller;

    static boolean RUNNING = true;
    static int GRID_SIZE = 30;

    /**
     * main method
     * runs all the startup threads
     * @param args Not used for any specific purpose
     */
    public static void main(String[] args) {
        System.err.println(" * * * Logic Gate Sim * * *");  sleep(100);
        check_and_create_files();                           sleep(100);
        create_and_show_ui();                               sleep(100);
        create_grid();                                      sleep(100);
        create_poll_thread();                               sleep(100);
    }

    /**
     * Checks if the data files and folders exist, and creates them.
     * This probably is not required as no IO is used.
     * @deprecated Because its useless
     */
    @Deprecated
    private static void check_and_create_files() {
        System.out.print("INFO.Core: Creating directory and files... ");
        if(!DATA_DIR.exists()) {
            if(!DATA_DIR.mkdirs()) {
                System.err.println("ERROR.Core: Directory could not be created, quitting!");
                System.exit(1);
            }
        }
        try {
            if (!GATES_FILE.exists()) {
                if(!GATES_FILE.createNewFile()) {
                    System.err.println("ERROR.Core: File could not be created, quitting!");
                    System.exit(1);
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Done.");
    }

    /**
     * Displays the UI
     */
    private static void create_and_show_ui() {
        System.out.print("INFO.Core: Creating User Interface... ");
        ui = new UI();
        System.out.println("Done.");
    }

    /**
     * Creates the grid system the UI will use
     */
    private static void create_grid() {
        System.out.print("INFO.Core: Creating grid... ");
        ui.set_grid(GRID_SIZE,GRID_SIZE);
        System.out.println("Done.");
    }

    /**
     * Creates the poller thread that will update the grid
     */
    private static void create_poll_thread() {
        System.out.print("INFO.Core: Creating Poller thread... ");
        poller = new Poller();
        System.out.println("Done.");
    }

    /**
     * Adds a new gate to the grid (this really just adds it to the ArrayList so the poller can update it)
     * @param gate_id Which gate is being places. 0-7 inclusive
     * @param x The x coordinate
     * @param y The y coordinate
     * @param orientation Used for Wires. 1-4, starting pointing -> and moving counterclockwise
     */
    static void add_gate(int gate_id, int x, int y, int orientation) {
        gates.add(new Gate(gates.size(),gate_id,x,y,orientation));
        System.out.println("INFO.Core: New Gate with ID " + gate_id + " at (" + x + "," + y + ")");
    }

    /**
     * Sleep for a specific amount of time. Used to replace the longhanded try/catch of Thread.sleep without throwing exceptions through methods.
     * @param time The time in milliseconds to sleep for
     */
    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch(InterruptedException ex) {
            System.err.println("ERROR.Core: Failed to sleep!");
        }
    }
}
