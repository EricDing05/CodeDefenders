package ui;

import model.Game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Runs the runGame() method to start the program

        TerminalGame game = new TerminalGame(600, 600, 2);
        game.runGame();
    }
}
