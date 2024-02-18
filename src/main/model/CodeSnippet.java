package model;

public class CodeSnippet {
    // Represents a single code snippet moving across the screen.

    private String snippet;
    private int positionX;
    private int positionY;
    private int speed;
    private int powerUpStatus;


    //EFFECTS: Creates a CodeSnippet instance with an x,y position, speed and String to be typed
    public CodeSnippet(int x, int y, int speed, String s, int powerUp) {
        this.snippet = s;
        this.positionX = x;
        this.positionY = y;
        this.speed = speed;
        this.powerUpStatus = powerUp;
    }

    // getters and setters

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

    public int getPowerUpStatus() {
        return this.powerUpStatus;
    }

    //MODIFIES: This
    //EFFECTS: Sets speed to zero
    public void freeze() {
        this.speed = 0;
    }


    //MODIFIES: this
    //EFFECTS: Increases the Y-position by the speed
    public void move() {
        this.positionY = positionY + speed;
    }


    //EFFECTS: returns true if string s matches the CodeSnippets string
    public boolean checkIfStringMatches(String s) {
        return s.equals(snippet);
    }


    //EFFECTS: returns true if the CodeSnippets Y position is greater than or equal to the given Y.
    public boolean reachedEnd(int y) {
        if (this.positionY >= y) {
            return true;
        }
        return false;
    }


}
