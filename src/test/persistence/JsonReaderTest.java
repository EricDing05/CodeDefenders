package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {
    // Tests for JsonReader

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    public void testReadEmptyGame() throws IOException {
        String path = "./data/baseGameDataReader.json";
        JsonReader reader = new JsonReader(path);
        Game game = reader.read();

        assertNotNull(game);
        assertEquals(800, game.getMaxX());
        assertEquals(600, game.getMaxY());
        assertEquals(100, game.getTickSpeed());
        assertEquals(0, game.getLevelCounter());

        Player player = game.getPlayer();
        assertNotNull(player);
        assertEquals(0, player.getScore());
        assertEquals(100, player.getHealth());

        assertTrue(game.getCodeSnippets().isEmpty());
        assertTrue(game.getIncorrectlyTypedWords().isEmpty());
    }

    @Test
    public void testGameBaseProperties() throws IOException {
        JsonReader reader = new JsonReader("./data/validtestDataReader.json");
        Game game = reader.read();

        assertNotNull(game);
        assertEquals(1024, game.getMaxX());
        assertEquals(768, game.getMaxY());
        assertEquals(50, game.getTickSpeed());
        assertEquals(3, game.getLevelCounter());
    }

    @Test
    public void testPlayerProperties() throws IOException {
        JsonReader reader = new JsonReader("./data/validtestDataReader.json");
        Game game = reader.read();

        Player player = game.getPlayer();
        assertNotNull(player);
        assertEquals(150, player.getScore());
        assertEquals(80, player.getHealth());
    }

    @Test
    public void testIncorrectlyTypedWords() throws IOException {
        JsonReader reader = new JsonReader("./data/validtestDataReader.json");
        Game game = reader.read();

        ArrayList<String> incorrectWords = game.getIncorrectlyTypedWords();
        assertNotNull(incorrectWords);
        assertEquals(2, incorrectWords.size());
        assertTrue(incorrectWords.contains("wrong1"));
        assertTrue(incorrectWords.contains("wrong2"));
    }

    @Test
    public void testCodeSnippets() throws IOException {
        JsonReader reader = new JsonReader("./data/validtestDataReader.json");
        Game game = reader.read();

        ArrayList<CodeSnippet> codeSnippets = game.getCodeSnippets();
        assertNotNull(codeSnippets);
        assertEquals(2, codeSnippets.size());

        CodeSnippet snippet1 = codeSnippets.get(0);
        assertEquals(100, snippet1.getPositionX());
        assertEquals(200, snippet1.getPositionY());
        assertEquals(5, snippet1.getSpeed());
        assertEquals("System.out.println();", snippet1.getString());
        assertEquals(0, snippet1.getPowerUpStatus());

        CodeSnippet snippet2 = codeSnippets.get(1);
        assertEquals(300, snippet2.getPositionX());
        assertEquals(400, snippet2.getPositionY());
        assertEquals(3, snippet2.getSpeed());
        assertEquals("int i = 0;", snippet2.getString());
        assertEquals(1, snippet2.getPowerUpStatus());
    }


}



