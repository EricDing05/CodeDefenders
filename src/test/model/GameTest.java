package model;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.json.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

import com.googlecode.lanterna.screen.Screen;

public class GameTest {
    // Tests for Game

    private Game game;

    @BeforeEach
    public void setUp() {
        this.game = new Game(200, 200, 10);
    }

    @Test
    public void testSetPlayer() {
        Player p = new Player();
        p.setScore(100);
        game.setPlayer(p);
        assertEquals(p, game.getPlayer());
    }

    @Test
    public void testSetLevelCounter() {
        assertEquals(game.getLevelCounter(), 0);
        game.setLevelCounter(100);
        assertEquals(game.getLevelCounter(), 100);
    }

    @Test
    public void testSetIncorrectlyTypedWords() {
        assertEquals(game.getIncorrectlyTypedWords(), new ArrayList<>());
        ArrayList myList = new ArrayList<String>();
        myList.add("eric");
        myList.add("bob");
        game.setIncorrectlyTypedWords(myList);
        assertEquals(game.getIncorrectlyTypedWords(), myList);
    }

    @Test
    public void testSetCodeSnippets() {
        assertEquals(game.getCodeSnippets(), new ArrayList<>());
        ArrayList myList = new ArrayList<String>();
        myList.add(new CodeSnippet(1,1,1,"eric",2));
        game.setCodeSnippets(myList);
        assertEquals(game.getCodeSnippets(), myList);
    }


    @Test
    public void testIncrementDifficulty() {
        game.incrementDifficulty();
        game.incrementDifficulty();
        game.incrementDifficulty();
        game.incrementDifficulty();
        game.incrementDifficulty();
        game.incrementDifficulty();
        game.incrementDifficulty();
        game.incrementDifficulty();
        game.incrementDifficulty();
        assertEquals(game.getTickSpeed(), 10L);
        game.incrementDifficulty();
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
    public void testCheckGameOverNotOver() {
        assertFalse(game.getGameOver());
        game.checkGameOver();
        assertFalse(game.getGameOver());
    }

    @Test
    public void testGenerateCodeSnippet() {
        CodeSnippet reference = game.generateCodeSnippet(0, 0, 0, 0);
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
    public void testGenerateCodeSnippetRandomlyNoGenerate() {
        assertTrue(game.getCodeSnippets().isEmpty());
        game.generateCodeSnippetRandomly(2147483647);
        assertTrue(game.getCodeSnippets().isEmpty());
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
        CodeSnippet c = new CodeSnippet(10, 10, 10, "eric", 1);
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
        assertEquals("eric", game.getOutputString());
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
        list.add("if (x > y) return x;");
        list.add("int sum = a + b;");
        list.add("list.add(1);");
        list.add("for (int i=0; i<5; i++) {}");
        list.add("stack.push(10);");
        list.add("boolean flag = false;");
        list.add("list.remove(0);");
        list.add("n/=3;");
        list.add("x%=4;");
        assertEquals(list, game.initializeCodeSnippets());
    }

    @Test
    public void testTickMoveNoneReachedEnd() {
        CodeSnippet c = new CodeSnippet(10, 10, 10, "eric", 1);
        game.addCodeSnippet(c);
        int originalPosition = c.getPositionY();
        assertEquals(100, game.getPlayer().getHealth());
        game.tick(1);
        assertEquals(c.getPositionY(), originalPosition + c.getSpeed());
    }

    @Test
    public void testTickMoveOneReachedEnd() {
        CodeSnippet c = new CodeSnippet(10, 210, 10, "eric", 1);
        game.addCodeSnippet(c);
        game.tick(1);
        assertEquals(game.getPlayer().getHealth(), 90);
        assertEquals(game.getCodeSnippets().size(), 1);
        assertFalse(game.getCodeSnippets().contains(c));
    }

    @Test
    public void testTickGenerateSnippet() {
        game.tick(1);
        assertEquals(1, game.getCodeSnippets().size());
    }

    @Test
    public void testFreezeCodeSnippets() {
        CodeSnippet testCodeSnippet = new CodeSnippet(0, 0, 10, "eric", 1);
        CodeSnippet testCodeSnippet2 = new CodeSnippet(0, 0, 100, "eric2", 1);
        game.addCodeSnippet(testCodeSnippet);
        game.addCodeSnippet(testCodeSnippet2);
        assertEquals(10, testCodeSnippet.getSpeed());
        assertEquals(100, testCodeSnippet2.getSpeed());
        game.freezeCodeSnippets();
        assertEquals(0, testCodeSnippet.getSpeed());
        assertEquals(0, testCodeSnippet2.getSpeed());
    }

    @Test
    public void testSetAndGetScreen() {
        Screen test = null;
        try {
            test = new DefaultTerminalFactory()
                    .setPreferTerminalEmulator(false)
                    .setInitialTerminalSize(new TerminalSize(100, 40))
                    .createScreen();
        } catch (IOException e) {
            fail();
        }
        game.setScreen(test);
        assertEquals(test, game.getScreen());
    }

    @Test
    public void testToJson() {
        game.addIncorrectWord("example");
        JSONObject json = game.toJson();
        assertEquals(200, json.getInt("x"));
        assertEquals(200, json.getInt("y"));
        assertEquals(10, json.getInt("tickSpeed"));
        assertEquals(0, json.getInt("levelCounter"));
        JSONObject jsonPlayer = json.getJSONObject("player");
        assertEquals(100, jsonPlayer.getInt("health"));
        assertEquals(0, jsonPlayer.getInt("score"));
        JSONArray jsonSnippets = json.getJSONArray("codeSnippets");
        JSONArray jsonIncorrectWords = json.getJSONArray("incorrectSnippet");
        assertTrue(jsonIncorrectWords.toList().contains("example"));
    }

    @Test
    public void testCodeSnippetsToJson() {
        game.addCodeSnippet(new CodeSnippet(1,1,1,"eric", 1));
        game.addCodeSnippet(new CodeSnippet(1,1,1,"eric2", 1));
        game.addCodeSnippet(new CodeSnippet(1,1,1,"eric3", 1));
        JSONArray json = game.codeSnippetsToJson();
        assertEquals(3, json.length());
    }
}



