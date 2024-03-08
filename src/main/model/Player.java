package model;

import org.json.JSONObject;

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

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void takeDamage() {
        this.health = this.health - 10;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("health", this.health);
        json.put("score", this.score);
        return json;
    }
}
