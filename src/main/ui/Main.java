package ui;

import model.Game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Runs the runGame() method to start the program

        TerminalGame game = new TerminalGame(200, 600, 20);
        game.runGame();
    }
}
