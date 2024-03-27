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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the UI handling of the user for the game
public class TerminalGame extends JFrame {
    private Game game;
    private JsonReader reader;
    private JsonWriter writer;
    private static final String JSON_STORE = "./data/game.json";
    private GameRenderer renderer;
    private KeyHandler keyHandler;
    private JFrame frame;


    // REQUIRES: x,y and tickSpeed all > 0
    // EFFECTS: Creates an instance of the terminal game
    public TerminalGame(int x, int y, long tickSpeed) {
        this.game = new Game(x, y, tickSpeed);
        this.reader = new JsonReader(JSON_STORE);
        this.writer = new JsonWriter(JSON_STORE);
        this.renderer = new GameRenderer(this.game);
        this.keyHandler = new KeyHandler();
        initFrame();
    }


    //MODIFIES: game
    //EFFECTS: main loop of the game. Initializes the screen of the game. Runs the loop as long as !gameOver
    public void runGame() throws IOException, InterruptedException {
        while (!game.getGameOver()) {
            frame.requestFocusInWindow();
            game.checkGameOver();
            if (game.tick(40)) {
                renderer.takeDamage();
            }
            renderer.repaint();
            Thread.sleep(300L);
            game.incrementDifficulty();

        }
        renderer.repaint();
    }


    // EFFECTS: Creates and initializes the JFrame
    private void initFrame() {
        this.frame = new JFrame("CodeDefenders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(renderer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(800, 600);
        JButton saveButton = new JButton("Save");
        ActionListener saveListener = e -> saveGame();
        saveButton.addActionListener(saveListener);
        JButton loadButton = new JButton("Load");
        ActionListener loadListener = e -> loadGame();
        loadButton.addActionListener(loadListener);
        renderer.add(saveButton);
        renderer.add(loadButton);
        frame.addKeyListener(this.keyHandler);
    }


    // MODIFIES: game
    // EFFECTS: take the input read from pollInput and processes it
    public void handleInput(int keyCode, char character) {

        if (keyCode == KeyEvent.VK_SHIFT) {
            return;
        }

        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            game.removeLastCharOffOutputString();
            return;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            checkStringInput(game.getOutputString());
            game.setOutputString("");
            return;
        }
        game.setOutputString(game.getOutputString() + character);
    }

    //MODIFIES: game
    //EFFECTS: given a string s, removes s from CodeSnippets if a match is found. In the case of two matching strings,
    // it will take the first. Also handles powerUp behavior.
    public void checkStringInput(String s) {
        boolean isCorrect = false;
        for (CodeSnippet c : game.getCodeSnippets()) {
            if (c.checkIfStringMatches(s)) {
                isCorrect = true;
                if (c.getPowerUpStatus() == 5) {
                    game.getPlayer().setScore(game.getPlayer().getScore() + game.getCodeSnippets().size());
                    game.clearCodeSnippets();
                    break;
                } else if (c.getPowerUpStatus() == 4) {
                    game.freezeCodeSnippets();
                }
                game.removeCodeSnippet(c);
                game.getPlayer().setScore(game.getPlayer().getScore() + 1);
                break;
            }
        }
        addWordIfNotCorrect(isCorrect, s);
    }

    // MODIFIES: Game
    // EFFECTS: adds a word to the list of incorrect words if it is not correct
    private void addWordIfNotCorrect(Boolean b, String s) {
        if (!b) {
            game.addIncorrectWord(s);
        }
    }

    // Represents a KeyHandler to detect user input
    // Credit to SpaceInvaders for the idea
    private class KeyHandler extends KeyAdapter {
        // EFFECTS: Detects key input and call handleInput() to parse it
        @Override
        public void keyPressed(KeyEvent e) {
            handleInput(e.getKeyCode(), e.getKeyChar());
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
            Game game = reader.read();
            this.game = game;
            renderer.setGame(game);
            renderer.repaint();
        } catch (IOException e) {
            System.out.println("Unable to read file");
        }

    }
}
