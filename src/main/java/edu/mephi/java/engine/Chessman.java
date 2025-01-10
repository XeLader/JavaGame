package edu.mephi.java.engine;

import java.awt.*;

public class Chessman{
    private Color color;
    private boolean chosen = false;
    private static Chessman chosenChess = null;
    public enum Color
    {
        WHITE(0),
        BLACK(6);
        private final int value;
        private Color(int value)
        {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    private Type type;
    public enum Type
    {
        KING(0),
        QUEEN(1),
        ROOK(2),
        BISHOP(3),
        KNIGHT(4),
        PAWN(5);
        private final int value;
        private Type(int value)
        {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }


    private GameField.Position position;
    char[] unicodeSymbol = {'?'};

    Chessman(GameField.Position pos, Color col, Type typ)
    {
        position = pos;
        color = col;
        type = typ;
        //calculate symbol in unicode to draw it later on field
        unicodeSymbol[0] = (char)('\u2654' + color.getValue() + type.getValue());
    }

    public static void initializeChessmen(Chessman[][] chessmen)
    {
        //fill lines 1 and 8 with white and black pieces
        for (int i = 0; i <= 1; i++)
        {
            Chessman.Color color = (i == 0) ? Chessman.Color.WHITE : Chessman.Color.BLACK;
            int y = 1 + i*7;
            chessmen[0][i*7] = new Chessman(new GameField.Position( 1, y), color, Chessman.Type.ROOK);
            chessmen[1][i*7] = new Chessman(new GameField.Position(2, y), color, Chessman.Type.KNIGHT);
            chessmen[2][i*7] = new Chessman(new GameField.Position(3, y), color, Chessman.Type.BISHOP);
            chessmen[3][i*7] = new Chessman(new GameField.Position(4, y), color, Chessman.Type.QUEEN);
            chessmen[4][i*7] = new Chessman(new GameField.Position(5, y), color, Chessman.Type.KING);
            chessmen[5][i*7] = new Chessman(new GameField.Position(6, y), color, Chessman.Type.BISHOP);
            chessmen[6][i*7] = new Chessman(new GameField.Position(7, y), color, Chessman.Type.KNIGHT);
            chessmen[7][i*7] = new Chessman(new GameField.Position(8, y), color, Chessman.Type.ROOK);
        }
        //file lines 2 and 7 with black and white pawns
        for (int i = 1; i <=8; i++)
        {
            Chessman.Color white = Chessman.Color.WHITE;
            Chessman.Color black = Chessman.Color.BLACK;
            chessmen[i - 1][1] = new Chessman(new GameField.Position(i, 2), white, Chessman.Type.PAWN);
            chessmen[i - 1][6] = new Chessman(new GameField.Position(i, 7), black, Chessman.Type.PAWN);
        }

    }

    public void paint(Graphics g, int originX, int originY, int width, int height)
    {
        //calculate position of chess piece on field
        int titleWidth = width / GameField.TILES_X;
        int titleHeight = height / GameField.TILES_Y;
        int realX = originX + titleWidth * position.x - titleWidth * 3 / 4;
        int realY = originY + titleHeight * (9 - position.y) - titleHeight / 4;

        java.awt.Color color1 = g.getColor();
        if (chosen)
        {
            g.setFont( new Font("Serif", Font.PLAIN, 30));
            g.setColor(java.awt.Color.DARK_GRAY);
        }
        else
        {
            g.setFont( new Font("Serif", Font.PLAIN, 35));
            g.setColor(java.awt.Color.BLACK);
        }
        g.drawChars(unicodeSymbol, 0, 1, realX, realY);
        g.setColor(color1);
    }

    GameField.Position getPosition()
    {
        return position;
    }

    public void Choose()
    {
        if (chosenChess != null)
            chosenChess.chosen = false;
        chosenChess = this;
        chosen = true;
    }

    public boolean isChosen()
    {
        return chosen;
    }

    public static void ClearChoice()
    {
        if (chosenChess != null)
        {
            chosenChess.chosen = false;
            chosenChess = null;
        }
    }

    public void setPosition(GameField.Position position)
    {
        this.position = position;
    }

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public static Chessman getChosenChess(){
        return chosenChess;
    }
}
