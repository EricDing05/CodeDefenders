package ui;

import model.CodeSnippet;

import java.util.ArrayList;

public class ConsoleDisplay {


    public static void displayCodeSnippets(ArrayList<CodeSnippet> codeSnippets) {
        for (CodeSnippet c: codeSnippets) {
            String powerUpStatus;
            if (c.getPowerupStatus() == 5) {
                powerUpStatus = "POWERUP";
            } else {
                powerUpStatus = "no powerup";
            }
            System.out.println("Snippet:" + c.getString() + " | XPos:" + c.getPositionX()
                    + " | YPos:" + c.getPositionY() + " | " + powerUpStatus);
        }
    }

    public static void clearConsole() {
        // Attempt to clear the screen by printing new lines
        for (int i = 0; i < 50; i++) { // Adjust the number of lines as needed
            System.out.println();
        }
    }
}
