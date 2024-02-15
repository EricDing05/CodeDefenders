package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        this.game = new Game(200, 200, 10);
    }


    @Test
    public void testIncrementDifficulty() {
        game.increaseLevelCounter();
        game.increaseLevelCounter();
        game.increaseLevelCounter();
        game.increaseLevelCounter();
        game.increaseLevelCounter();
        game.increaseLevelCounter();
        game.increaseLevelCounter();
        game.increaseLevelCounter();
        game.increaseLevelCounter();
        game.incrementDifficulty();
        assertEquals(game.getTickSpeed(), 10L);
        game.increaseLevelCounter();
        game.incrementDifficulty();
        assertEquals(game.getTickSpeed(), 15L);
    }

    @Test
    public void testCheckGameOver() {
        assertFalse(game.getGameOver());
        game.getPlayer().setHealth(0);
        game.checkGameOver();
        assertTrue(game.getGameOver());
    }

    @Test
    public void testGenerateCodeSnippet() {
        CodeSnippet reference = game.generateCodeSnippet(0,0,0,0);
        assertEquals(0, reference.getPositionX());
        assertEquals(0, reference.getPositionY());
        assertEquals(5, reference.getSpeed());
        assertEquals("int a=5;", reference.getString());
    }

    @Test
    public void testGenerateCodeSnippetRandomly() {
        assertTrue(game.getCodeSnippets().isEmpty());
        game.generateCodeSnippetRandomly(1);
        assertFalse(game.getCodeSnippets().isEmpty());
    }

    @Test
    public void testClearCodeSnippets() {
        game.generateCodeSnippetRandomly(1);
        game.generateCodeSnippetRandomly(1);
        game.generateCodeSnippetRandomly(1);
        assertFalse(game.getCodeSnippets().isEmpty());
        game.clearCodeSnippets();
        assertTrue(game.getCodeSnippets().isEmpty());
    }

    @Test
    public void testRemoveCodeSnippet() {
        CodeSnippet c = new CodeSnippet(10,10,10,"eric", 1);
        assertTrue(game.getCodeSnippets().isEmpty());
        game.addCodeSnippet(c);
        assertFalse(game.getCodeSnippets().isEmpty());
        game.removeCodeSnippet(c);
        assertTrue(game.getCodeSnippets().isEmpty());
    }

    @Test
    public void testAddIncorrectWord() {
        assertTrue(game.getIncorrectlyTypedWords().isEmpty());
        game.addIncorrectWord("eric");
        assertEquals("eric", game.getIncorrectlyTypedWords().get(0));
    }

    @Test
    public void testRemoveLastCharOffOutputStringNotEmptyString() {
        assertEquals(game.getOutputString(), "");
        game.setOutputString("eric");
        assertEquals("eric" , game.getOutputString());
        game.removeLastCharOffOutputString();
        assertEquals("eri", game.getOutputString());
    }

    @Test
    public void testRemoveLastCharOffOutputStringEmptyString() {
        assertEquals(game.getOutputString(), "");
        game.removeLastCharOffOutputString();
        assertEquals(game.getOutputString(), "");
    }

    @Test
    public void testInitializeCodeSnippets() {
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
        assertEquals(list, game.initializeCodeSnippets());
    }

    @Test
    public void testTick() {
        game.tick(1);
        assertEquals(1, game.getCodeSnippets().size());
    }
}
