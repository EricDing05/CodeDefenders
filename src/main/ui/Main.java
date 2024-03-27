package ui;

import model.Game;

import java.io.IOException;

// Runs the runGame() method to start the program
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        TerminalGame game = new TerminalGame(600, 600, 2);
        game.runGame();
    }
}
