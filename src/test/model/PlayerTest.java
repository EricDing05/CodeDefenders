package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    // Tests for player

    Player player;

    @BeforeEach
    public void setUp() {
        this.player = new Player();
    }

    @Test
    public void testGetHealth() {
        assertEquals(10, player.getHealth());
    }

    @Test
    public void testSetHealth() {
        assertEquals(10, player.getHealth());
        player.setHealth(100);
        assertEquals(100, player.getHealth());
    }

    @Test
    public void testTakeDamage() {
        assertEquals(10, player.getHealth());
        player.takeDamage();
        assertEquals(0, player.getHealth());

    }

    @Test
    public void testGetScore() {
        assertEquals(0, player.getScore());
    }

    @Test
    public void testSetScore() {
        assertEquals(0, player.getScore());
        player.setScore(100);
        assertEquals(100, player.getScore());
    }

    @Test
    public void testToJson() {
        player.setScore(100);
        player.setHealth(10);
        JSONObject json = player.toJson();
        assertEquals(10, json.getInt("health"));
        assertEquals(100, json.getInt("score"));
    }
}

