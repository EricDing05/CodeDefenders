package model;

import java.io.IOException;
import java.util.ArrayList;

import ui.ConsoleDisplay;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.*;


public class Game {
    //Represents the CodeDefenders game

    private Player player;
    private final ArrayList<CodeSnippet> codeSnippets;
    private final ArrayList<String> codeSnippetStrings;
    private int level;
    private Boolean gameOver;
    private int maxX;
    private int maxY;
    private long tickSpeed;
    private ArrayList<String> incorrectlyTypedWords;
    private int levelCounter;
    private Screen screen;
    private String outputString;


    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<CodeSnippet> getCodeSnippets() {
        return this.codeSnippets;
    }


    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

    public void addCodeSnippet(CodeSnippet c) {
        this.codeSnippets.add(c);
    }

    public int getMaxY() {
        return maxY;
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public void increaseLevelCounter() {
        this.levelCounter++;
    }

    public ArrayList<String> getIncorrectlyTypedWords() {
        return this.incorrectlyTypedWords;
    }

    public long getTickSpeed() {
        return this.tickSpeed;
    }

    public Game(int x, int y, long tickSpeed) {
        this.levelCounter = 0;
        this.gameOver = false;
        this.tickSpeed = tickSpeed;
        this.maxX = x;
        this.maxY = y;
        this.codeSnippets = new ArrayList<CodeSnippet>();
        this.codeSnippetStrings = initializeCodeSnippets();
        this.player = new Player();
        this.incorrectlyTypedWords = new ArrayList<String>();
        this.outputString = "";

    }

    public void tick(int spawningOdds) {
        generateCodeSnippetRandomly(spawningOdds);
        ArrayList<CodeSnippet> toBeRemoved = new ArrayList<CodeSnippet>();
        for (CodeSnippet c : codeSnippets) {
            if (c.reachedEnd(maxY)) {
                player.takeDamage();
                toBeRemoved.add(c);
            }
            c.move();
        }
        codeSnippets.removeAll(toBeRemoved);

    }

    //EFFECTS: generates a new code snippet at y=0, with random x position in range [0,maxX]
    // speed in range [5, speedRange + 5], powerUpStatus in range [0,powerUpOdds] and with a string from the available
    // pool of string of index [0,stringRange]
    public CodeSnippet generateCodeSnippet(int maxX, int powerUpOdds, int speedRange, int stringRange) {
        int powerUpStatus = ((int) (Math.random() * (powerUpOdds)));
        int x = (int) (Math.random() * (maxX + 1));
        int y = 0;
        int speed = (int) (Math.random() * (speedRange) + 5);
        String string = codeSnippetStrings.get((int) (Math.random() * (stringRange)));
        CodeSnippet c = new CodeSnippet(x, y, speed, string, powerUpStatus);
        return c;
    }


    // TODO: move this to UI package
    public ArrayList<String> initializeCodeSnippets() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("int a=5;");
        list.add("a++;");
        list.add("a+=5;");
        list.add("--a;");
        list.add("a*=3;");
        list.add("a/=2;");
        list.add("a%=4;");
        list.add("b=!true;");
        list.add("c='A';");
        list.add("c++;");
        list.add("f=1.5f;");
        return list;
    }

    //MODIFIES: This
    //EFFECTS: increments the game tick-speed after the level counter equals 10 and resets the level counter

    public void incrementDifficulty() {
        if (this.levelCounter == 10) {
            this.tickSpeed = this.tickSpeed + 5;
            this.levelCounter = 0;
        }
    }

    //MODIFIES: this
    //EFFECTS: has a 1/odds chance of generating a new code snippet and adding it to the list of code snippets
    public void generateCodeSnippetRandomly(int odds) {
        int chanceOfSpawning = ((int) (Math.random() * (odds)));
        if (chanceOfSpawning <= 1) {
            CodeSnippet c = generateCodeSnippet(this.maxX, 10, 20,10);
            this.codeSnippets.add(c);
        }
    }

    //MODIFIES; this
    //EFFECTS: checks to see if the player has 0 health and ends the game if it does
    public void checkGameOver() {
        if (player.getHealth() <= 0) {
            gameOver = true;
        }
    }

    //MODIFIES; this
    //EFFECTS: clears all code snippets
    public void clearCodeSnippets() {
        codeSnippets.clear();
    }

    //MODIFIES: this
    //EFFECTS: removes CodeSnippet c from the current list of code snippets.
    public void removeCodeSnippet(CodeSnippet c) {
        codeSnippets.remove(c);
    }

    //MODIFIES: this
    //EFFECTS: adds s to the list of incorrectly typed words
    public void addIncorrectWord(String s) {
        this.incorrectlyTypedWords.add(s);
    }

    //MODIFIES: this
    //EFFECTS: removes the last char off of the current output string, as long as it isn't the empty string.
    // if output string is the empty string, does nothing.
    public void removeLastCharOffOutputString() {
        if (!this.outputString.isEmpty()) {
            this.outputString = this.getOutputString().substring(0, (outputString.length() - 1));
        }
    }


    // Below is code that has been refactored out of this class. Just keeping a copy here.
    // TODO: move this to UI package
//    public void handleInput() throws IOException {
//        char input = HandleInput.handleInput(this.screen);
//        if (input == ']') {
//            return;
//        }
//        if (input == ' ') {
//            checkStringInput(this.outputString);
//            this.outputString = "";
//            return;
//        }
//        this.outputString = this.outputString + input;
//    }

    //EFFECTS: first checks to see if game is over, then advances snippets and checks for ones that have reached end
    // displays the snippets, handles user input, clears the screen and runs again
    // TODO: move this to UI package

//    public void runGame() throws IOException, InterruptedException {
//        this.screen = new DefaultTerminalFactory()
//                .setPreferTerminalEmulator(false)
//                .setInitialTerminalSize(new TerminalSize(100, 40))
//                .createScreen();
//        screen.startScreen();
//        screen.doResizeIfNecessary();
//        screen.setCursorPosition(null);
//
//
//        while (!gameOver) {
//            checkGameOver();
//            tick();
//            ConsoleDisplay.displayCodeSnippets(codeSnippets, outputString);
//            handleInput();
//            Thread.sleep(300L);
//            ConsoleDisplay.clearConsole();
//            incrementDifficulty();
//        }
//        System.out.println("the incorrectly typed words were:" + incorrectlyTypedWords.toString());
//    }

    // TODO: move this to UI package
//    public void tick() {
//        generateCodeSnippetRandomly();
//        ArrayList<CodeSnippet> toBeRemoved = new ArrayList<CodeSnippet>();
//        for (CodeSnippet c : codeSnippets) {
//            if (c.reachedEnd(maxY)) {
//                player.takeDamage();
//                toBeRemoved.add(c);
//            }
//            c.move();
//        }
//        codeSnippets.removeAll(toBeRemoved);
//
//    }
    // EFFECTS: if the input given matches one of the snippets, it will first check to see if there is a powerup,
    // and apply if possible, else it will just remove the snippet.

//    public void checkStringInput(String s) {
//        for (CodeSnippet c : this.codeSnippets) {
//            if (c.checkIfStringMatches(s)) {
//                if (c.getPowerUpStatus() == 5) {
//                    codeSnippets.clear();
//                    break;
//                }
//                codeSnippets.remove(c);
//                break;
//
//            }
//        }
//        incorrectlyTypedWords.add(s);
//    }

}
