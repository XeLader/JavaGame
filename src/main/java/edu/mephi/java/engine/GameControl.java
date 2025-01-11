package edu.mephi.java.engine;

import javax.swing.*;

public class GameControl {
    private Chessman.Color currentPlayerColor;
    private final JLabel informLabel;
    private String informText;
    private boolean isGameOver;

    public GameControl()
    {
        currentPlayerColor = Chessman.Color.WHITE;
        informLabel = new JLabel();
        informLabel.setText("White moves first!");
    }

    public void stepControl(Chessman[][] chessmen, GameField.Position clickPosition, boolean[][] ways)
    {
        if (isGameOver)
            return;
        Chessman clickedChess = chessmen[clickPosition.getX()-1][clickPosition.getY()-1];
        if (clickedChess != null)
        {
            if (clickedChess.getColor() == currentPlayerColor) {
                clickedChess.Choose();
                GameSteps.posibleSteps(chessmen, clickedChess.getPosition(), ways);
                return;
            }
        }
        if (GameSteps.nextStep(chessmen, clickPosition, ways))
        {
            changePlayerColor();
            checkCheckMate(chessmen);
            informLabel.setText(informText);
        }
        else
        {
            Chessman.ClearChoice();
        }
    }

    public JLabel getInformLabel() {
        return informLabel;
    }

    public void changePlayerColor()
    {
        if (currentPlayerColor == Chessman.Color.WHITE)
        {
            currentPlayerColor = Chessman.Color.BLACK;
            informText = "Black moves";
        }
        else
        {
            currentPlayerColor = Chessman.Color.WHITE;
            informText = "White moves";
        }
    }

    private void checkCheckMate(Chessman[][] chessmen) {
        boolean[][] whiteWays = new boolean[8][8];
        boolean[][] whiteKingWays = new boolean[8][8];
        boolean[][] blackWays = new boolean[8][8];
        boolean[][] blackKingWays = new boolean[8][8];
        boolean[][] tempWays = new boolean[8][8];
        Chessman blackKing = null;
        Chessman whiteKing = null;

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (chessmen[i][j] != null) {
                    if (chessmen[i][j].getType() == Chessman.Type.KING) {
                        if (chessmen[i][j].getColor() == Chessman.Color.WHITE) {
                            whiteKing = chessmen[i][j];
                            GameSteps.posibleSteps(chessmen, new GameField.Position(i + 1, j + 1), whiteKingWays);
                            whiteKingWays[i][j] = true;
                        } else {
                            blackKing = chessmen[i][j];
                            GameSteps.posibleSteps(chessmen, new GameField.Position(i + 1, j + 1), blackKingWays);
                            blackKingWays[i][j] = true;
                        }
                    } else if (chessmen[i][j].getColor() == Chessman.Color.WHITE) {
                        GameSteps.posibleSteps(chessmen, new GameField.Position(i + 1, j + 1), tempWays);
                        addWays(whiteWays, tempWays);
                    } else {
                        GameSteps.posibleSteps(chessmen, new GameField.Position(i + 1, j + 1), tempWays);
                        addWays(blackWays, tempWays);
                    }
                }
            }

        if (whiteKing == null) {
            informText = "Black WINS!!!";
            isGameOver = true;
            return;
        } else if (blackWays[whiteKing.getPosition().getX() - 1][whiteKing.getPosition().getY() - 1])
            informText = "Check!" + informText;

        if (blackKing == null) {
            informText = "White WINS!!!";
            isGameOver = true;
            return;
        } else if (whiteWays[blackKing.getPosition().getX() - 1][blackKing.getPosition().getY() - 1])
            informText = "Check!" + informText;


        if (multiplyWays(whiteKingWays, blackWays)) {
            informLabel.setText("Checkmate! Black wins!!!");
            return;
        }
        if (multiplyWays(blackKingWays, whiteWays)) {
            informLabel.setText("Checkmate! White wins!!!");
        }

    }



    static void addWays(boolean[][] main, boolean[][] added)
    {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                main[i][j] = added[i][j] || main[i][j];
    }

    static boolean multiplyWays(boolean[][] king, boolean[][] m)
    {
        boolean check = true;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (king[i][j])
                    check = check && m[i][j];

        return check;
    }
}
