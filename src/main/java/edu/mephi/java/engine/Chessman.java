package edu.mephi.java.engine;

import javax.swing.*;
import java.awt.*;

public class Chessman{
    private Color color;
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

    public static class Position{
        int x;
        int y;
        Position(int x, int y)
        {
            this.x = (x > 0 && x <= 8) ? x : 1;
            this.y = (y > 0 && y <= 8) ? y : 1;
        }
    }
    private Position position;
    char[] unicodeSymbol = {'?'};

    Chessman(Position pos, Color col, Type typ)
    {
        position = pos;
        color = col;
        type = typ;
        //calculate symbol in unicode to draw it later on field
        unicodeSymbol[0] = (char)('\u2654' + color.getValue() + type.getValue());
    }

    public void paint(Graphics g, int originX, int originY, int width, int height)
    {
        //calculate position of chess piece on field
        int titleWidth = width / GameField.TILES_X;
        int titleHeight = height / GameField.TILES_Y;
        int realX = originX + titleWidth * position.x - titleWidth * 3 / 4;
        int realY = originY + titleHeight * position.y - titleHeight / 4;

        g.drawChars(unicodeSymbol, 0, 1, realX, realY);
    }
}
