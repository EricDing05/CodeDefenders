package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ui.ConsoleDisplay;


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




    public Game(int x, int y, long tickSpeed) {
        this.level = 1;
        this.gameOver = false;
        this.tickSpeed = tickSpeed;
        this.maxX = x;
        this.maxY = y;
        this.codeSnippets = new ArrayList<CodeSnippet>();
        this.codeSnippetStrings = initializeCodeSnippets();
        this.player = new Player();
        this.incorrectlyTypedWords = new ArrayList<String>();
    }
    //EFFECTS: first checks to see if game is over, then advances snippets and checks for ones that have reached end
    // displays the snippets, handles user input, clears the screen and runs again

    public void runGame() throws InterruptedException, IOException {
        while (!gameOver) {
            checkGameOver();
            tick();
            ConsoleDisplay.displayCodeSnippets(codeSnippets);
            //Thread.sleep((1000L / tickSpeed));
            checkStringInput(HandleInput.handleInput());
            Thread.sleep((100L / tickSpeed));
            ConsoleDisplay.clearConsole();
            incrementDifficulty();
        }
        System.out.println("the incorrectly typed words were:" + incorrectlyTypedWords.toString());
    }

    public void tick() {
        codeSnippets.add(generateCodeSnippet());
        ArrayList<CodeSnippet> toBeRemoved = new ArrayList<CodeSnippet>();
        for (CodeSnippet c : codeSnippets) {
            if (c.reachedEnd(maxX, maxY)) {
                player.takeDamage();
                toBeRemoved.add(c);
            }
            c.move();
        }
        codeSnippets.removeAll(toBeRemoved);

    }

    public CodeSnippet generateCodeSnippet() {
        int powerUpStatus = ((int) (Math.random() * (10)));
        int x = (int) (Math.random() * (maxX + 1));
        int y = 0;
        int speed = (int) (Math.random() * (20));
        String string = codeSnippetStrings.get((int) (Math.random() * (10)));
        CodeSnippet c = new CodeSnippet(x, y, speed, string, powerUpStatus);
        return c;
    }
    // EFFECTS: if the input given matches one of the snippets, it will first check to see if there is a powerup,
    // and apply if possible, else it will just remove the snippet.

    public void checkStringInput(String s) {
        for (CodeSnippet c : this.codeSnippets) {
            if (c.checkIfStringMatches(s)) {
                if (c.getPowerupStatus() == 5) {
                    codeSnippets.clear();
                    break;
                }
                codeSnippets.remove(c);
                break;

            }
        }
        incorrectlyTypedWords.add(s);
    }


    private ArrayList<String> initializeCodeSnippets() {
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

    private void incrementDifficulty() {
        if (this.levelCounter == 10) {
            this.tickSpeed = this.tickSpeed + 5;
            this.levelCounter = 0;
        }
    }

    public void checkGameOver() {
        if (player.getHealth() <= 0) {
            gameOver = true;
        }
    }
}
