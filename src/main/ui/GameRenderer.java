package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import model.CodeSnippet;

public class GameRenderer extends JPanel {

    private Game game;

    public GameRenderer(Game g) {
        this.game = g;
        setPreferredSize(new Dimension(800, 600));
    }

    public void setGame(Game g) {
        this.game = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCodeSnippets(g);
        drawCurrentlyTypedWord(g);
        drawHealthAndScore(g);
        drawGameOver(g);
    }

    private void drawGameOver(Graphics g) {
        if (game.getGameOver()) {
            drawGameOverMessage(g);
            drawGameOverIncorrectSnippets(g);
        }
    }

    private void drawGameOverMessage(Graphics g) {
        int x = (getWidth() - g.getFontMetrics().stringWidth("GAME OVER")) / 2;
        int y = getHeight() / 2;
        g.drawString("GAME OVER", x, y);
    }

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

    private void drawCurrentlyTypedWord(Graphics g) {
        String currentTypedWord = "Currently Typing: " + game.getOutputString();
        g.setColor(Color.pink);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        int x = (getWidth() - g.getFontMetrics().stringWidth(currentTypedWord)) / 2;
        int y = getHeight() - 30;
        g.drawString(currentTypedWord, x, y);
    }

    private void drawCodeSnippets(Graphics g) {
        ArrayList<CodeSnippet> snippets = game.getCodeSnippets();
        for (CodeSnippet snippet : snippets) {
            drawOneSnippet(g, snippet);
        }
    }

    private void drawOneSnippet(Graphics g, CodeSnippet c) {
        int x = c.getPositionX();
        int y = c.getPositionY();
        String text = c.getString();
        if (c.getPowerUpStatus() == 5) {
            g.setColor(Color.RED);
            g.drawString(text, x, y);
            g.setColor(Color.BLACK);
            return;
        }
        if (c.getPowerUpStatus() == 4) {
            g.setColor(Color.BLUE);
            g.drawString(text, x, y);
            g.setColor(Color.BLACK);
            return;
        }
        g.drawString(text, x, y);
    }

    private void drawHealthAndScore(Graphics g) {
        int y = getHeight() - (getHeight() - 50);
        int x = getWidth() - (getWidth() - 40);
        g.drawString("Health: " + game.getPlayer().getHealth(), x, y);
        g.drawString("Score: " + game.getPlayer().getScore(), x, y + 30);
    }


}
