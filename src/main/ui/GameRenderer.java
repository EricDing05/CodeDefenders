package ui;

import model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.CodeSnippet;


// Represents the game renderer
public class GameRenderer extends JPanel {

    private Game game;
    private Image alien;
    private boolean takeDamage;

    // REQUIRES: game is not null
    // EFFECTS: creates an instance of the game renderer
    public GameRenderer(Game g) {
        this.game = g;
        setPreferredSize(new Dimension(800, 600));
        try {
            this.alien = ImageIO.read(new File("./lib/338435.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGame(Game g) {
        this.game = g;
    }

    // EFFECTS: draws all elements of the game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCodeSnippets(g);
        drawCurrentlyTypedWord(g);
        drawHealthAndScore(g);
        drawGameOver(g);
        drawDamage(g);
    }

    // EFFECTS: Draws the game over graphics when the game is over
    private void drawGameOver(Graphics g) {
        if (game.getGameOver()) {
            drawGameOverMessage(g);
            drawGameOverIncorrectSnippets(g);
        }
    }

    // EFFECTS: Draws the game over message
    private void drawGameOverMessage(Graphics g) {
        int x = (getWidth() - g.getFontMetrics().stringWidth("GAME OVER")) / 2;
        int y = getHeight() / 2;
        g.drawString("GAME OVER", x, y);
    }

    // EFFECTS: Draws all the incorrectly typed snippets
    private void drawGameOverIncorrectSnippets(Graphics g) {
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        int y = getHeight() - (getHeight() - 120);
        int x = getWidth() - 200;
        g.drawString("Incorrect Words:", x, y);
        for (String s : this.game.getIncorrectlyTypedWords()) {
            y = y + 30;
            g.drawString(s, x, y);
        }
    }

    // EFFECTS: Draws the word that the user is currently typing
    private void drawCurrentlyTypedWord(Graphics g) {
        String currentTypedWord = "Currently Typing: " + game.getOutputString();
        g.setColor(Color.pink);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        int x = (getWidth() - g.getFontMetrics().stringWidth(currentTypedWord)) / 2;
        int y = getHeight() - 30;
        g.drawString(currentTypedWord, x, y);
    }

    // EFFECTS: Draws all the code snippets
    private void drawCodeSnippets(Graphics g) {
        ArrayList<CodeSnippet> snippets = game.getCodeSnippets();
        for (CodeSnippet snippet : snippets) {
            drawOneSnippet(g, snippet);
        }
    }

    // EFFECTS: Draws one snippets, considering its powerUp status
    private void drawOneSnippet(Graphics g, CodeSnippet c) {
        int x = c.getPositionX();
        int y = c.getPositionY();
        String text = c.getString();
        if (c.getPowerUpStatus() == 5) {
            g.setColor(Color.RED);
            drawSnippetAndAlien(g, text, x, y);
            g.setColor(Color.BLACK);
            return;
        }
        if (c.getPowerUpStatus() == 4) {
            g.setColor(Color.BLUE);
            drawSnippetAndAlien(g, text, x, y);
            g.setColor(Color.BLACK);
            return;
        }
        drawSnippetAndAlien(g, text, x, y);
    }

    // EFFECTS: Draws a single snippet with an alien centered above it
    private void drawSnippetAndAlien(Graphics g, String s, int x, int y) {
        g.drawString(s, x, y);
        g.drawImage(alien, (x + (g.getFontMetrics().stringWidth(s) / 2) - 40), y - 50, 80, 40, null);
    }

    // EFFECTS: Draws the player health and score
    private void drawHealthAndScore(Graphics g) {
        int y = getHeight() - (getHeight() - 50);
        int x = getWidth() - (getWidth() - 40);
        g.drawString("Health: " + game.getPlayer().getHealth(), x, y);
        g.drawString("Score: " + game.getPlayer().getScore(), x, y + 30);
    }

    // EFFECTS: Draws red and plays sound if player takes damage
    private void drawDamage(Graphics g) {
        if (takeDamage == true) {
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
            takeDamage = false;
            SoundEffect.playSound("./lib/classic_hurt.wav");
        }
    }

    // EFFECTS: Calls drawDamage() whenever the player takes damage
    public void takeDamage() {
        this.takeDamage = true;
        repaint();
    }


}
