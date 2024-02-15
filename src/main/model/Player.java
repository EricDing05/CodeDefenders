package model;

public class Player {
    // represents the player in the game

    private int health;
    private int score;


    public Player() {
        this.health = 10;
        this.score = 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void takeDamage() {
        this.health = this.health - 10;
    }
}
