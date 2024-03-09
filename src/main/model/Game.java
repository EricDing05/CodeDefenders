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
import org.json.JSONArray;
import org.json.JSONObject;


public class Game {
    //Represents the CodeDefenders game

    private Player player;
    private ArrayList<CodeSnippet> codeSnippets;
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


    // Getters and setters

    public Player getPlayer() {
        return this.player;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public void setPlayer(Player p) {
        this.player = p;
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

    public Boolean getGameOver() {
        return gameOver;
    }

    public void increaseLevelCounter() {
        this.levelCounter++;
    }

    public void setLevelCounter(int count) {
        this.levelCounter = count;
    }

    public int getLevelCounter() {
        return this.levelCounter;
    }

    public ArrayList<String> getIncorrectlyTypedWords() {
        return this.incorrectlyTypedWords;
    }

    public void setIncorrectlyTypedWords(ArrayList<String> words) {
        this.incorrectlyTypedWords = words;
    }

    public long getTickSpeed() {
        return this.tickSpeed;
    }

    public void setCodeSnippets(ArrayList<CodeSnippet> codeSnippets) {
        this.codeSnippets = codeSnippets;
    }



    //EFFECTS: Creates an instance of the game
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


    //REQUIRES: spawningOdds > 0
    //MODIFIES: this, player
    //EFFECTS: spawns a new CodeSnippet with 1/odds of spawning. Any code snippets that reach the end damage
    // the player and are removed.
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


    //MODIFIES: this
    //EFFECTS: Creates the list of available code snippets for the game to select from.
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
        list.add("if (x > y) return x;");
        list.add("int sum = a + b;");
        list.add("list.add(1);");
        list.add("for (int i=0; i<5; i++) {}");
        list.add("stack.push(10);");
        list.add("boolean flag = false;");
        list.add("list.remove(0);");
        list.add("n /= 3;");
        list.add("x %= 4;");

        return list;
    }


    //MODIFIES: This
    //EFFECTS: increments the LevelCounter, increments tickSpeed when LevelCounter reaches 10
    public void incrementDifficulty() {
        increaseLevelCounter();
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
            CodeSnippet c = generateCodeSnippet(this.maxX, 10, 20, 10);
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

    //MODIFIES: this
    //EFFECTS: sets the speed of all current code snippets to zero
    public void freezeCodeSnippets() {
        for (CodeSnippet c : this.codeSnippets) {
            c.freeze();
        }
    }

    // EFFECTS: Returns this as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", this.maxX);
        json.put("y", this.maxY);
        json.put("tickSpeed", this.tickSpeed);
        json.put("levelCounter", this.levelCounter);
        json.put("codeSnippets", codeSnippetsToJson());
        json.put("player", this.player.toJson());
        json.put("incorrectSnippet", new JSONArray(this.incorrectlyTypedWords));
        return json;
    }

    // EFFECTS: Returns this.CodeSnippets as JSON Array
    public JSONArray codeSnippetsToJson() {
        JSONArray json = new JSONArray();
        for (CodeSnippet c : this.codeSnippets) {
            json.put(c.toJson());
        }
        return json;
    }



}
