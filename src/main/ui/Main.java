package ui;

import model.Game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Game game = new Game(200, 200, 20);
        try {
            game.runGame();
        } catch (InterruptedException e) {
            System.out.println("error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
