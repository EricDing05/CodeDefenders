package ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.screen.Screen;
import model.CodeSnippet;
import model.Game;
import model.HandleInput;

import java.io.IOException;
import java.util.ArrayList;

public class TerminalGame {
    private Game game;

    public TerminalGame(int x, int y, long tickSpeed) {
        this.game = new Game(x, y, tickSpeed);
    }


    //Credit to lab 4 for the creation and instantiation of the screen
    public void runGame() throws IOException, InterruptedException {
        game.setScreen(new DefaultTerminalFactory()
                .setPreferTerminalEmulator(false)
                .setInitialTerminalSize(new TerminalSize(100, 40))
                .createScreen());
        game.getScreen().startScreen();
        game.getScreen().doResizeIfNecessary();
        game.getScreen().setCursorPosition(null);

        while (!game.getGameOver()) {
            game.checkGameOver();
            game.tick(40);
            ConsoleDisplay.displayCodeSnippets(game.getCodeSnippets(), game.getOutputString());
            handleInput();
            Thread.sleep(300L);
            ConsoleDisplay.clearConsole();
            game.incrementDifficulty();
        }
        System.out.println("the incorrectly typed words were:" + game.getIncorrectlyTypedWords().toString());
    }

    public void handleInput() throws IOException {
        char input = pollInput(game.getScreen());
        if (input == ']') {
            return;
        }
        if (input == '[') {
            game.removeLastCharOffOutputString();
            return;
        }

        if (input == ' ') {
            checkStringInput(game.getOutputString());
            game.setOutputString("");
            return;
        }
        game.setOutputString(game.getOutputString() + input);
    }

    public static char pollInput(Screen s) throws IOException {
        KeyStroke k = s.pollInput();


        if (k == null || k.getCharacter() == null) {
            return ']';
        }
        if (k.getKeyType() == KeyType.Backspace) {
            return '[';
        }


        char c = k.getCharacter();
        return c;
    }


    public void checkStringInput(String s) {
        for (CodeSnippet c : game.getCodeSnippets()) {
            if (c.checkIfStringMatches(s)) {
                if (c.getPowerUpStatus() == 5) {
                    game.clearCodeSnippets();
                    break;
                } else if (c.getPowerUpStatus() == 4) {
                    game.freezeCodeSnippets();
                }
                game.removeCodeSnippet(c);
                break;

            }
        }
        game.addIncorrectWord(s);
    }
}
