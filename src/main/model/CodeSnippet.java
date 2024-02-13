package model;

import jdk.jshell.Snippet;

public class CodeSnippet {
    // Represents a single code snippet moving across the screen.

    private String snippet;
    private int positionX;
    private int positionY;
    private int speed;
    private boolean hasBeenFullyTyped;
    private int powerUpStatus;


    //EFFECTS: Creates a CodeSnippet instance with an x,y position, speed and String to be typed
    public CodeSnippet(int x, int y, int speed, String s, int powerUp) {
        this.snippet = s;
        this.positionX = x;
        this.positionY = y;
        this.speed = speed;
        this.hasBeenFullyTyped = false;
        this.powerUpStatus = powerUp;
    }

    // getters and setters

    public boolean hasBeenFullyTyped() {
        return this.hasBeenFullyTyped;
    }

    public String getString() {
        return this.snippet;
    }
    

    public int getPositionX() {
        return this.positionX;
    }


    public int getPositionY() {
        return this.positionY;
    }

    public int getSpeed() {
        return this.speed;
    }


    //MODIFIES: this
    //EFFECTS: Increases the Y-position by the speed
    public void move() {
        this.positionY = positionY + speed;
    }

    public boolean checkIfStringMatches(String s) {
        return s.equals(snippet);
    }

    public boolean reachedEnd(int x, int y) {
        if (this.positionX >= x || this.positionY >= y) {
            return true;
        }
        return false;
    }

    public int getPowerupStatus() {
        return this.powerUpStatus;
    }


}
