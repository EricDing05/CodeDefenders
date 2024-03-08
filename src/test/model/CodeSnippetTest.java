package model;

import model.CodeSnippet;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CodeSnippetTest {
    // Tests for CodeSnippet


    private CodeSnippet codeSnippet;

    @BeforeEach
    public void setUp() {
        this.codeSnippet = new CodeSnippet(0, 0, 10, "eric", 1);
    }


    @Test
    public void testConstructor() {
        assertEquals(0, codeSnippet.getPositionX());
        assertEquals(0, codeSnippet.getPositionY());
        assertEquals(10, codeSnippet.getSpeed());
        assertEquals("eric", codeSnippet.getString());
        assertEquals(1, codeSnippet.getPowerUpStatus());
    }

    @Test
    public void testMove() {
        assertEquals(0, codeSnippet.getPositionX());
        assertEquals(0, codeSnippet.getPositionY());
        codeSnippet.move();
        assertEquals(0, codeSnippet.getPositionX());
        assertEquals((0 + codeSnippet.getSpeed()), 10);
    }


    @Test
    public void testStringMatches() {
        assertTrue(codeSnippet.checkIfStringMatches("eric"));
        assertFalse(codeSnippet.checkIfStringMatches("felix"));
    }

    @Test
    public void testReachedEnd() {
        assertFalse(codeSnippet.reachedEnd(10));
        codeSnippet.move();
        assertTrue(codeSnippet.reachedEnd(10));
        codeSnippet.move();
        assertTrue((codeSnippet.reachedEnd(10)));
    }

    @Test
    public void testCheckIfStringMatches() {
        assertTrue(codeSnippet.checkIfStringMatches("eric"));
        assertFalse(codeSnippet.checkIfStringMatches("noteric"));
    }

    @Test
    public void testFreeze() {
        assertEquals(10, codeSnippet.getSpeed());
        codeSnippet.freeze();
        assertEquals(0, codeSnippet.getSpeed());
    }

    @Test
    public void testTOJson() {
        JSONObject json = codeSnippet.toJson();
        assertEquals("eric", json.getString("snippet"));
        assertEquals(0, json.getInt("x"));
        assertEquals(0, json.getInt("y"));
        assertEquals(10, json.getInt("speed"));
        assertEquals(1, json.getInt("powerUpStatus"));
    }


}
