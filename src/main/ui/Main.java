package ui;

import model.Game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        TerminalGame game = new TerminalGame(200, 20000, 20);
        game.runGame();
    }
}
