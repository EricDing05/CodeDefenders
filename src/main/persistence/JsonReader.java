package persistence;

import com.googlecode.lanterna.screen.Screen;
import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// Credit to JsonSerializationDemo for most of the methods
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        int x = jsonObject.getInt("x");
        int y = jsonObject.getInt("y");
        int tickSpeed = jsonObject.getInt("tickSpeed");
        Game g = new Game(x, y, tickSpeed);
        initializeGame(g, jsonObject);
        return g;
    }

    // EFFECTS: Parses data from the given JSONObject and sets the fields of the game accordingly
    private void initializeGame(Game g, JSONObject j) {
        g.setCodeSnippets(getCodeSnippets(j));
        g.setLevelCounter(j.getInt("levelCounter"));
        g.setPlayer(getPlayer(j));
        g.setIncorrectlyTypedWords(getIncorrectWords(j));
    }

    // EFFECTS: Parses the player from the given JSONObject and returns it
    private Player getPlayer(JSONObject j) {
        Player p = new Player();
        int score = j.getJSONObject("player").getInt("score");
        int health = j.getJSONObject("player").getInt("health");
        p.setScore(score);
        p.setHealth(health);
        return p;
    }

    // EFFECTS: Parses the incorrect words from the JSONObject and returns the list
    private ArrayList<String> getIncorrectWords(JSONObject j) {
        ArrayList<String> list = new ArrayList<String>();
        JSONArray jsonArray = j.getJSONArray("incorrectSnippet");
        for (int i = 0; i < jsonArray.length(); i++) {
            String word = jsonArray.getString(i);
            list.add(word);
        }
        return list;
    }

    // EFFECTS: Parses the CodeSnippets from the JSONObject and returns the ArrayList of them
    private ArrayList<CodeSnippet> getCodeSnippets(JSONObject j) {
        ArrayList<CodeSnippet> codeArray = new ArrayList<>();
        JSONArray array = j.getJSONArray("codeSnippets");
        for (Object json : array) {
            codeArray.add(getCodeSnippet((JSONObject) json));
        }
        return codeArray;
    }

    // EFFECTS: Parses a single CodeSnippet from the JSONObject and returns it
    private CodeSnippet getCodeSnippet(JSONObject json) {
        int x = json.getInt("x");
        int y = json.getInt("y");
        String s = json.getString("snippet");
        int speed = json.getInt("speed");
        int powerUp = json.getInt("powerUpStatus");
        CodeSnippet c = new CodeSnippet(x,y,speed,s,powerUp);
        return c;
    }

}
