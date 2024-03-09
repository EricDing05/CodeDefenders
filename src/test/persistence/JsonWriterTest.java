package persistence;

import model.CodeSnippet;
import model.Game;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    // tests for JsonWriter

    @Test
    void testWriterInvalidFile() {
        try {
            Game g = new Game(1,1,10);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriteAndReadBaseGame() throws IOException {
        // Create a base Game object with default or base properties
        Game game = new Game(800, 600, 100);
        game.setLevelCounter(1);

        // Write the Game to a JSON file
        JsonWriter writer = new JsonWriter("./data/baseGameWriterTest.json");
        writer.open();
        writer.write(game);
        writer.close();

        // Read the JSON file back into a Game object
        JsonReader reader = new JsonReader("./data/baseGameWriterTest.json");
        Game readGame = reader.read();

        // Assert that the properties match
        assertEquals(800, readGame.getMaxX());
        assertEquals(600, readGame.getMaxY());
        assertEquals(100, readGame.getTickSpeed());
        assertEquals(1, readGame.getLevelCounter());
    }

    public static Game createComplicatedGame() {
        // Game with custom dimensions and tick speed
        Game game = new Game(1024, 768, 60);
        game.setLevelCounter(5);

        // Setup player with specific score and health
        Player player = new Player();
        player.setScore(120);
        player.setHealth(80);
        game.setPlayer(player);

        // Add several code snippets with various properties
        ArrayList<CodeSnippet> snippets = new ArrayList<>();
        snippets.add(new CodeSnippet(100, 0, 5, "int i = 0;", 0));
        snippets.add(new CodeSnippet(200, 0, 5, "i++;", 1));
        snippets.add(new CodeSnippet(300, 0, 5, "System.out.println(i);", 2));
        game.setCodeSnippets(snippets);

        // Add some incorrectly typed words
        ArrayList<String> incorrectlyTypedWords = new ArrayList<>();
        incorrectlyTypedWords.add("intt");
        incorrectlyTypedWords.add("Sytem.out.println(i);");
        game.setIncorrectlyTypedWords(incorrectlyTypedWords);

        return game;
    }

    @Test
    public void testComplicatedGameSettings() throws IOException {
        Game game = createComplicatedGame(); // This method sets up a complicated game instance
        JsonWriter writer = new JsonWriter("./data/complexGameWriterTest.json");
        writer.open();
        writer.write(game);
        writer.close();

        JsonReader reader = new JsonReader("./data/complexGameWriterTest.json");
        Game readGame = reader.read();

        assertEquals(game.getMaxX(), readGame.getMaxX());
        assertEquals(game.getMaxY(), readGame.getMaxY());
        assertEquals(game.getTickSpeed(), readGame.getTickSpeed());
    }

    @Test
    public void testComplicatedGamePlayerProperties() throws IOException {
        Game game = createComplicatedGame(); // Setup complicated game
        JsonWriter writer = new JsonWriter("./data/complexGameWriterTest.json");
        writer.open();
        writer.write(game);
        writer.close();

        JsonReader reader = new JsonReader("./data/complexGameWriterTest.json");
        Game readGame = reader.read();

        assertEquals(game.getPlayer().getScore(), readGame.getPlayer().getScore());
        assertEquals(game.getPlayer().getHealth(), readGame.getPlayer().getHealth());
    }

    @Test
    public void testComplicatedGameCodeSnippets() throws IOException {
        Game game = createComplicatedGame(); // Setup complicated game
        JsonWriter writer = new JsonWriter("./data/complexGameWriterTest.json");
        writer.open();
        writer.write(game);
        writer.close();

        JsonReader reader = new JsonReader("./data/complexGameWriterTest.json");
        Game readGame = reader.read();

        assertEquals(game.getCodeSnippets().size(), readGame.getCodeSnippets().size());
    }

    @Test
    public void testComplicatedGameIncorrectlyTypedWords() throws IOException {
        Game game = createComplicatedGame(); // Setup complicated game
        JsonWriter writer = new JsonWriter("./data/complexGameWriterTest.json");
        writer.open();
        writer.write(game);
        writer.close();

        JsonReader reader = new JsonReader("./data/complexGameWriterTest.json");
        Game readGame = reader.read();

        assertEquals(game.getIncorrectlyTypedWords().size(), readGame.getIncorrectlyTypedWords().size());
    }



}
