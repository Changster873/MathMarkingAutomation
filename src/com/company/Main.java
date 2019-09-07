package com.company;

import personal.Automation;

import java.util.Scanner;

/**
 * @author Chansocheat Chheang
 */
public class Main {


    public Main (String pathToFile){
        new Automation(pathToFile);
    }

    /**
     *
     * This program takes an argument which should be the path to the file.
     * Once received, run marking automation on the given file.
     *
     * @param arg path to file.
     */
    public static void main (String arg[]){

        new Main("data.txt");
    }


}
