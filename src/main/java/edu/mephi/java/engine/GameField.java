package edu.mephi.java.engine;
import javax.swing.*;
import java.awt.*;
import java.security.PrivateKey;

public class GameField extends JComponent{
    private final static int BORDER = 20;
    private final static int TILE_SIZE = 40;
    public final static int TILES_X = 8;
    public final static int TILES_Y = 8;
    private final char[] LETTERS = {'A'};
    private final char[] NUMBERS = {'1'};

    public GameField(){
        setPreferredSize(new Dimension(TILE_SIZE* TILES_X + BORDER*2, TILE_SIZE* TILES_Y + BORDER*2));
        initializeChessmans();
    }

    private Chessman[] chessmans = new Chessman[32];
    private void initializeChessmans()
    {
        //fill lines 1 and 8 with white and black pieces
        for (int i = 0; i <= 1; i++)
        {
            Chessman.Color color = (i == 0) ? Chessman.Color.WHITE : Chessman.Color.BLACK;
            int y = 1 + i*7;
            chessmans[i*8] = new Chessman(new Chessman.Position( 1, y), color, Chessman.Type.ROOK);
            chessmans[i*8 + 1] = new Chessman(new Chessman.Position(2, y), color, Chessman.Type.KNIGHT);
            chessmans[i*8 + 2] = new Chessman(new Chessman.Position(3, y), color, Chessman.Type.BISHOP);
            chessmans[i*8 + 3] = new Chessman(new Chessman.Position(4, y), color, Chessman.Type.QUEEN);
            chessmans[i*8 + 4] = new Chessman(new Chessman.Position(5, y), color, Chessman.Type.KING);
            chessmans[i*8 + 5] = new Chessman(new Chessman.Position(6, y), color, Chessman.Type.BISHOP);
            chessmans[i*8 + 6] = new Chessman(new Chessman.Position(7, y), color, Chessman.Type.KNIGHT);
            chessmans[i*8 + 7] = new Chessman(new Chessman.Position(8, y), color, Chessman.Type.ROOK);
        }
        //file lines 2 and 7 with black and white pawns
        for (int i = 1; i <=8; i++)
        {
            Chessman.Color white = Chessman.Color.WHITE;
            Chessman.Color black = Chessman.Color.BLACK;
            chessmans[15 + i] = new Chessman(new Chessman.Position(i, 2), white, Chessman.Type.PAWN);
            chessmans[23 + i] = new Chessman(new Chessman.Position(i, 7), black, Chessman.Type.PAWN);
        }

    }

    @Override
    public void paint(Graphics g)
    {
        //calculate size of squares, based on real size of field
        int heightOfSquare = (this.getHeight() - BORDER*2)/ TILES_Y;
        int widthOfSquare = (this.getWidth() - BORDER*2)/ TILES_X;
        drawSquares(g, widthOfSquare, heightOfSquare);
        drawLetters(g, widthOfSquare);
        drawNumbers(g, heightOfSquare);
        Font font = g.getFont();
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        for (int i = 0; i < 32; i++)
            chessmans[i].paint(g, BORDER, BORDER, (this.getWidth() - BORDER*2), (this.getHeight() - BORDER*2));
        g.setFont(font);
    }


    //draw white and black squares with width and height
    private void drawSquares(Graphics g, int width, int height)
    {
        //fill all field with squares
        for (int i = 0; i < TILES_X; i++)
            for  (int j = 0; j < TILES_Y; j++)
            {
                if ((i + j) % 2 == 0)
                    g.setColor(Color.GRAY);
                else
                    g.setColor(Color.WHITE);
                g.fillRect(BORDER + i * width, BORDER+ j * height, width, height);
            }
        g.setColor(Color.BLACK);
    }

    //draw Letters A - H on game field
    private void drawLetters(Graphics g, int width)
    {
        for (int i = 0; i < TILES_X; i++)
        {
            g.drawChars(LETTERS, 0, 1, BORDER+ i * width + width/2, BORDER -3);
            LETTERS[0]++;
        }
        LETTERS[0] = 'A';
    }

    //draw Numbers 0 - 8 on game field
    private void drawNumbers(Graphics g, int height)
    {
        for (int j = 0; j < TILES_Y; j++)
        {
            g.drawChars(NUMBERS, 0, 1, BORDER - 15, BORDER+ j * height + height/2);
            NUMBERS[0]++;
        }
        NUMBERS[0]='1';
    }
}
