package edu.mephi.java.engine;
import javax.swing.*;
import java.awt.*;

public class GameField extends JComponent{
    private final int BORDER = 20;
    private final int TILE_SIZE = 40;
    private final int WIDTH = 8;
    private final int HEIGHT = 8;
    private final char[] LETTERS = {'A'};
    private final char[] NUMBERS = {'1'};

    public GameField(){
        setPreferredSize(new Dimension(TILE_SIZE*WIDTH + BORDER*2, TILE_SIZE*HEIGHT + BORDER*2));
    }

    @Override
    public void paint(Graphics g)
    {
        //calculate size of squares, based on real size of field
        int heightOfSquare = (this.getHeight() - BORDER*2)/HEIGHT;
        int widthOfSquare = (this.getWidth() - BORDER*2)/WIDTH;
        drawSquares(g, widthOfSquare, heightOfSquare);
        drawLetters(g, widthOfSquare);
        drawNumbers(g, heightOfSquare);

    }


    //draw white and black squares with width and height
    private void drawSquares(Graphics g, int width, int height)
    {
        //fill all field with squares
        for (int i = 0; i < WIDTH; i++)
            for  (int j = 0; j < HEIGHT; j++)
            {
                if ((i + j) % 2 == 0)
                    g.setColor(Color.BLACK);
                else
                    g.setColor(Color.WHITE);
                g.fillRect(BORDER + i * width, BORDER+ j * height, width, height);
            }
    }

    //draw Letters A - H on game field
    private void drawLetters(Graphics g, int width)
    {
        for (int i = 0; i < WIDTH; i++)
        {
            g.drawChars(LETTERS, 0, 1, BORDER+ i * width + width/2, BORDER -3);
            LETTERS[0]++;
        }
        LETTERS[0] = 'A';
    }

    //draw Numbers 0 - 8 on game field
    private void drawNumbers(Graphics g, int height)
    {
        for (int j = 0; j < HEIGHT; j++)
        {
            g.drawChars(NUMBERS, 0, 1, BORDER - 15, BORDER+ j * height + height/2);
            NUMBERS[0]++;
        }
        NUMBERS[0]='1';
    }
}
