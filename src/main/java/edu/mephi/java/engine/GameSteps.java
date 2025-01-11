package edu.mephi.java.engine;

public class GameSteps {

    public static void posibleSteps(Chessman [][]chessmen, GameField.Position position, boolean [][]ways)
    {
        ClearWays(ways);
        int x = position.getX() - 1;
        int y = position.getY() - 1;

        switch (chessmen[x][y].getType())
        {
            case PAWN:
                possibleStepsPAWN(chessmen, x, y, ways);
                return;

            case KNIGHT:
                possibleStepsKNIGHT(chessmen, x, y, ways);
                return;

            case QUEEN:
                possibleStepsQUEEN(chessmen, x, y, ways);
                return;

            case BISHOP:
                possibleStepsBISHOP(chessmen, x, y, ways);
                return;

            case ROOK:
                possibleStepsROOK(chessmen, x, y, ways);
                return;

            case KING:
                possibleStepsKING(chessmen, x, y, ways);


        }
    }

    private static void possibleStepsPAWN (Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        int direction;
        //choose right direction
        if (chessmen[x][y].getColor() == Chessman.Color.WHITE)
            direction = 1;
        else
            direction = -1;

        //check if there is no board or other
        if (y + direction >= 0 && y + direction < 8)
        {
            ways[x][y + direction] = (chessmen[x][y + direction] == null);
            if (y + direction*2 >= 0 && y + direction*2 < 8)
                ways[x][y + direction*2] = (chessmen[x][y + direction*2] == null);

            if(x + 1 < 8)
                ways[x + 1][y + direction] = checkPawnCaprures(chessmen, x, y, x+1, y+direction);

            if(x - 1 >= 0)
                ways[x-1][y + direction] = checkPawnCaprures(chessmen, x, y, x-1, y+direction);
        }


    }

    private static void possibleStepsKNIGHT (Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        if (x - 1 >= 0)
        {
            if (y - 2 >= 0)
                ways[x-1][y-2] = checkVacation(chessmen, x-1, y-2);
            if (y + 2 < 8)
                ways[x-1][y+2] = checkVacation(chessmen, x-1, y+2);

            if (x - 2 >= 0)
            {
                if (y - 1 >= 0)
                    ways[x-2][y-1] = checkVacation(chessmen, x-2, y-1);
                if (y + 1 < 8)
                    ways[x-2][y+1] = checkVacation(chessmen, x-2, y+1);
            }
        }

        if (x + 1 < 8)
        {
            if (y - 2 >= 0)
                ways[x+1][y-2] = checkVacation(chessmen, x+1, y-2);
            if (y + 2 < 8)
                ways[x+1][y+2] = checkVacation(chessmen, x+1, y+2);

            if (x + 2 < 8)
            {
                if (y - 1 >= 0)
                    ways[x+2][y-1] = checkVacation(chessmen, x+1, y-1);
                if (y + 1 < 8)
                    ways[x+2][y+1] = checkVacation(chessmen, x+2, y+1);
            }
        }
    }

    private static void possibleStepsQUEEN (Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        possibleStepsLINES(chessmen, x, y, ways);
        possibleStepsDIAGONALLES(chessmen, x, y, ways);
    }

    private static void possibleStepsROOK(Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        possibleStepsLINES(chessmen, x, y, ways);
    }

    private static void possibleStepsBISHOP(Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        possibleStepsDIAGONALLES(chessmen, x, y, ways);
    }

    private static void possibleStepsKING(Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        if (x - 1 >= 0)
        {
            if (y - 1 > 0)
                isNotStopped(chessmen, ways, x, y, x-1, y-1);
            if (y + 1 < 8)
                isNotStopped(chessmen, ways, x, y, x-1, y+1);
            isNotStopped(chessmen, ways, x, y, x-1, y);
        }

        if (x + 1 < 8)
        {
            if (y - 1 > 0)
                isNotStopped(chessmen, ways, x, y, x+1, y-1);
            if (y + 1 < 8)
                isNotStopped(chessmen, ways, x, y, x+1, y+1);
            isNotStopped(chessmen, ways, x, y, x+1, y);
        }

        if (y -1 >= 0)
            isNotStopped(chessmen, ways, x, y, x, y-1);
        if (y + 1 < 8)
            isNotStopped(chessmen, ways, x, y, x, y+1);
    }

    private static void possibleStepsLINES(Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        for(int d = -1; d <= 1; d += 2)
        {
            int i = d;
            while(y + i >= 0 && y + i < 8)
            {
                if(isNotStopped(chessmen, ways, x, y, x, y+i))
                    break;
                i += d;
            }
            i = d;
            while(x + i >= 0 && x + i < 8)
            {
                if(isNotStopped(chessmen, ways, x, y, x+i, y))
                    break;
                i+=d;
            }
        }
    }



    private static void possibleStepsDIAGONALLES(Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        for(int d = -1; d <= 1; d += 2)
        {
            int i = d;
            while(x - i >= 0 && x - i < 8 && y + i >= 0 &&  y + i < 8)
            {
                if(isNotStopped(chessmen, ways, x, y, x-i, y+i))
                    break;
                i+=d;
            }
            i = d;
            while(x + i >= 0 && x + i < 8  && y + i >= 0 && y + i < 8)
            {
                if(isNotStopped(chessmen, ways, x, y, x+i, y+i))
                    break;
                i+=d;
            }
        }
    }

    private static boolean checkVacation(Chessman [][]chessmen, int x, int y)
    {
        return (chessmen[x][y] == null);
    }

    private static boolean checkCaptured(Chessman [][]chessmen, int xCur, int yCur, int x, int y)
    {
        return chessmen[x][y].getColor() != chessmen[xCur][yCur].getColor();
    }

    private static boolean isNotStopped(Chessman [][]chessmen, boolean[][] ways, int xCur, int yCur, int x, int y)
    {
        if(checkVacation(chessmen, x, y))
        {
            ways[x][y] = true;
            return false;
        }
        else if (checkCaptured(chessmen, xCur, yCur, x, y))
            ways[x][y] = true;
        return true;

    }

    private static boolean checkPawnCaprures(Chessman [][]chessmen, int xCur, int yCur, int x, int y)
    {
        if(chessmen[x][y] == null)
            return false;
        else return chessmen[x][y].getColor() != chessmen[xCur][yCur].getColor();
    }

    public static boolean nextStep(Chessman [][]chessmen, GameField.Position newPosition, boolean [][]way)
    {
        if (Chessman.getChosenChess() == null)
            return false;

        GameField.Position oldPosition = Chessman.getChosenChess().getPosition();
        int x = newPosition.getX() - 1;
        int y = newPosition.getY() - 1;
        if (way[x][y])
        {
            chessmen[x][y] = Chessman.getChosenChess();
            chessmen[x][y].setPosition(newPosition);
            chessmen[oldPosition.getX() - 1][oldPosition.getY() -1] = null;
            Chessman.ClearChoice();
            ClearWays(way);
            return true;
        }
        return false;
    }

    public static void ClearWays(boolean[][] ways)
    {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                ways[i][j] = false;
    }
}
