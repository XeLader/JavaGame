package edu.mephi.java.engine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.security.PrivateKey;


public class GameField extends JComponent{
    private final static int BORDER = 20; //border size in px
    private final static int TILE_SIZE = 40;
    public final static int TILES_X = 8; //count of
    public final static int TILES_Y = 8;
    private final char[] LETTERS = {'A'};
    private final char[] NUMBERS = {'8'};
    private final Color Black = Color.GRAY;
    private final Color BlackHovered = Color.DARK_GRAY;
    private final Color White = Color.WHITE;
    private final Color WhiteHovered = Color.LIGHT_GRAY;
    private boolean [][] ways = new boolean[8][8];

    public static class Position{
        int x;
        int y;
        Position(int x, int y)
        {
            this.x = (x > 0 && x <= 8) ? x : 1;
            this.y = (y > 0 && y <= 8) ? y : 1;
        }

        Position()
        {
            x = -1;
            y = -1;
        }
        public int getX()
        {
            return x;
        }
        public int getY()
        {
            return y;
        }
        @Override
        public String toString() {
            return "x: " + x + " y: " + y;
        }

        public boolean equals(Position position)
        {
            return x == position.x && y == position.y;
        }
    }

    public GameField(){
        setPreferredSize(new Dimension(TILE_SIZE* TILES_X + BORDER*2, TILE_SIZE* TILES_Y + BORDER*2));
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseMotionListener);
        Chessman.initializeChessmen(chessmen);

    }

    private Chessman[][] chessmen = new Chessman[8][8];
    private Position hoveredSquare = new Position();


    @Override
    public void paint(Graphics g)
    {
        //calculate size of squares, based on real size of field
        int heightOfSquare = (this.getHeight() - BORDER*2)/ TILES_Y;
        int widthOfSquare = (this.getWidth() - BORDER*2)/ TILES_X;
        drawSquares(g, widthOfSquare, heightOfSquare);
        drawLetters(g, widthOfSquare);
        drawNumbers(g, heightOfSquare);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (chessmen[i][j] != null)
                    chessmen[i][j].paint(g, BORDER, BORDER, (this.getWidth() - BORDER*2), (this.getHeight() - BORDER*2));
    }


    //draw white and black squares with width and height
    private void drawSquares(Graphics g, int width, int height)
    {
        //fill all field with squares
        for (int i = 1; i <= TILES_X; i++)
            for  (int j = 1; j <= TILES_Y; j++)
            {
                if ((i + j) % 2 == 0)
                {
                    if (i == hoveredSquare.getX() && j == hoveredSquare.getY())
                        g.setColor(BlackHovered);
                    else
                        g.setColor(Black);
                }
                else
                {
                    if (i == hoveredSquare.getX() && j == hoveredSquare.getY())
                        g.setColor(WhiteHovered);
                    else
                        g.setColor(White);
                }
                g.fillRect(BORDER + (i - 1) * width, BORDER+ (8 - j) * height, width, height);

                if(ways[i-1][j-1])
                {
                    g.setColor(Color.BLUE);
                    g.fillOval(BORDER + (i) * width - width/2, BORDER+ (9 - j) * height - height/2, 10, 10 );
                }
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
            NUMBERS[0]--;
        }
        NUMBERS[0]='8';
    }

    private Position getClickedSquare(int x, int y)
    {
         if ((x < BORDER || x > getWidth()-BORDER) ||
                (y <  BORDER || y > getHeight() - BORDER))
             return new Position();
         else
         {
             int title_size_x = (getWidth() - BORDER*2) / TILES_X;
             int title_size_y = (getHeight() - BORDER*2) / TILES_Y;
             return new Position((x - BORDER) / title_size_x + 1,
                    8 - (y- BORDER) / title_size_y);
         }
    }

    private Chessman findChessmanOnPosition(Position position)
    {
        return chessmen[position.getX() - 1][position.getY() - 1];
    }

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            Position clickPosition = getClickedSquare(x, y);
            if (GameSteps.nextStep(chessmen, clickPosition, ways))
                return;

            Chessman chess = findChessmanOnPosition(clickPosition);
            if (chess != null)
            {
                chess.Choose();
                GameSteps.posibleSteps(chessmen, chess.getPosition(), ways);
            }
            else
            {
                Chessman.ClearChoice();
                GameSteps.ClearWays(ways);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    MouseMotionListener mouseMotionListener = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            hoveredSquare = getClickedSquare(x, y);
            repaint();
        }
    };
}
