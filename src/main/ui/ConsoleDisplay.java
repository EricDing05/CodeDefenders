package ui;

import model.CodeSnippet;

import java.util.ArrayList;

public class ConsoleDisplay {


    public static void displayCodeSnippets(ArrayList<CodeSnippet> codeSnippets, String s) {
        for (CodeSnippet c: codeSnippets) {
            String powerUpStatus;
            if (c.getPowerUpStatus() == 5) {
                powerUpStatus = "NUKE";
            } else if (c.getPowerUpStatus() == 4) {
                powerUpStatus = "freeze";
            } else {
                powerUpStatus = "no powerup";
            }
            System.out.println("Snippet:" + c.getString() + " | XPos:" + c.getPositionX()
                    + " | YPos:" + c.getPositionY() + " | " + powerUpStatus);
        }
        System.out.println("Currently Typing: " + s);
    }

    //EFFECTS: attempts to clear the screen by printing blank lines
    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
