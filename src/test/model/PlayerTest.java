package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

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
}
