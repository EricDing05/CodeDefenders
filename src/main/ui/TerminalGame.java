package ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.screen.Screen;
import model.CodeSnippet;
import model.Game;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TerminalGame {
    // Represents the UI handling of the user for the game
    private Game game;
    private JsonReader reader;
    private JsonWriter writer;
    private static final String JSON_STORE = "./data/game.json";

    public TerminalGame(int x, int y, long tickSpeed) {
        this.game = new Game(x, y, tickSpeed);
        this.reader = new JsonReader(JSON_STORE);
        this.writer = new JsonWriter(JSON_STORE);
    }


    //Credit to lab 4 for the creation and instantiation of the screen
    //MODIFIES: game
    //EFFECTS: main loop of the game. Initializes the screen of the game. Runs the loop as long as !gameOver
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


    // MODIFIES: game
    // EFFECTS: take the input read from pollInput and processes it
    public void handleInput() throws IOException {
        char input = pollInput(game.getScreen());
        if (input == ']') {
            return;
        }

        if (input == '[') {
            game.removeLastCharOffOutputString();
            return;
        }

        if (input == '`') {
            checkStringInput(game.getOutputString());
            game.setOutputString("");
            return;
        }
        game.setOutputString(game.getOutputString() + input);
    }

    //EFFECTS: reads user input from the screen
    public static char pollInput(Screen s) throws IOException {
        KeyStroke k = s.pollInput();

        if (k == null || k.getCharacter() == null) {
            return ']';
        }
        if (k.getKeyType() == KeyType.Backspace) {
            return '[';
        }
        if (k.getKeyType() == KeyType.Enter) {
            return '`';
        }

        char c = k.getCharacter();
        return c;
    }

    //MODIFIES: game
    //EFFECTS: given a string s, removes s from CodeSnippets if a match is found. In the case of two matching strings,
    // it will take the first. Also handles powerUp behavior.
    public void checkStringInput(String s) {
        boolean isCorrect = false;
        if (game.getOutputString().equals("save")) {
            saveGame();
            isCorrect = true;
        }
        if (game.getOutputString().equals("load")) {
            loadGame();
            isCorrect = true;
        }
        for (CodeSnippet c : game.getCodeSnippets()) {
            if (c.checkIfStringMatches(s)) {
                isCorrect = true;
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
        addWordIfNotCorrect(isCorrect, s);
    }

    // EFFECTS: adds a word to the list of incorrect words if it is not correct
    private void addWordIfNotCorrect(Boolean b, String s) {
        if (!b) {
            game.addIncorrectWord(s);
        }
    }

    // EFFECTS: Saves the game to file
    private void saveGame() {
        try {
            writer.open();
            writer.write(game);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file");
        }
    }

    // EFFECTS: Loads the game from a previous save
    private void loadGame() {
        try {
            game = reader.read();
            System.out.println("loaded the last save");
            game.setScreen(new DefaultTerminalFactory()
                    .setPreferTerminalEmulator(false)
                    .setInitialTerminalSize(new TerminalSize(100, 40))
                    .createScreen());
            game.getScreen().startScreen();
            game.getScreen().doResizeIfNecessary();
            game.getScreen().setCursorPosition(null);
        } catch (IOException e) {
            System.out.println("Unable to read file");
        }

    }
}
