package edu.mephi.java.engine;

public class GameSteps {

    public static void posibleSteps(Chessman [][]chessmen, GameField.Position position, boolean [][]ways)
    {
        int x = position.getX() - 1;
        int y = position.getY() - 1;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                ways[i][j] = false;

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
        if (y + direction > 0 && y + direction < 8)
            ways[x][y + direction] = (chessmen[x][y + direction] == null);
        if (y + direction*2 > 0 && y + direction*2 < 8)
            ways[x][y + direction] = (chessmen[x][y + direction] == null);

    }

    private static void possibleStepsKNIGHT (Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        Chessman.Color thisColor = chessmen[x][y].getColor();
        if (x - 1 > 0)
        {
            if (y - 2 > 0)
                ways[x-1][y-2] = true;
            if (y + 2 < 8)
                ways[x-1][y+2] = true;

            if (x - 2 > 0)
            {
                if (y - 1 > 0)
                    ways[x-2][y-1] = true;
                if (y + 1 < 8)
                    ways[x-2][y+1] = true;
            }
        }

        if (x + 1 < 8)
        {
            if (y - 2 > 0)
                ways[x+1][y-2] = true;
            if (y + 2 < 8)
                ways[x+1][y+2] = true;

            if (x + 2 < 8)
            {
                if (y - 1 > 0)
                    ways[x+2][y-1] = true;
                if (y + 1 < 8)
                    ways[x+2][y+1] = true;
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
        if (x - 1 > 0)
        {
            if (y - 1 > 0)
                ways[x-1][y-1] = checkVacation(chessmen,x, y, x-1, y-1);
            if (y + 1 < 8)
                ways[x-1][y+1] = checkVacation(chessmen,x, y, x-1, y+1);
            ways[x-1][y] = checkVacation(chessmen,x, y, x-1, y);
        }

        if (x + 1 < 8)
        {
            if (y - 1 > 0)
                ways[x+1][y-1] = checkVacation(chessmen,x, y, x+1, y-1);
            if (y + 1 < 8)
                ways[x+1][y+1] = checkVacation(chessmen,x, y, x+1, y+1);
            ways[x+1][y] = checkVacation(chessmen,x, y, x+1, y);
        }

        if (y -1 > 0)
            ways[x][y - 1] = checkVacation(chessmen,x, y, x, y-1);
        if (y + 1 < 8)
            ways[x][y - 1] = checkVacation(chessmen,x, y, x, y-1);
    }

    private static void possibleStepsLINES(Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        int i = 1;
        while(x + i < 8)
        {
            ways[x + i][y] = chessmen[x + i][y] == null;
            if (chessmen[x + i][y].getColor() != chessmen[x][y].getColor())
            {
                ways[x + i][y] = true;
                break;
            }
            i++;
        }
        i = -1;
        while(x + i > 0)
        {
            ways[x + i][y] = chessmen[x + i][y] == null;
            if (chessmen[x + i][y].getColor() != chessmen[x][y].getColor())
            {
                ways[x + i][y] = true;
                break;
            }
            i--;
        }
        i = 1;
        while(y + i < 8)
        {
            ways[x][y + i] = chessmen[x][y + i] == null;
            if (chessmen[x + i][y].getColor() != chessmen[x][y].getColor())
            {
                ways[x + i][y] = true;
                break;
            }
            i++;
        }
        i = -1;
        while(y + i > 0)
        {
            ways[x][y + i] = chessmen[x][y + i] == null;
            if (chessmen[x][y + i].getColor() != chessmen[x][y].getColor())
            {
                ways[x][y + i] = true;
                break;
            }
            i--;
        }
    }



    private static void possibleStepsDIAGONALLES(Chessman [][]chessmen, int x, int y, boolean [][]ways)
    {
        int i = 1;
        while(x + i < 8 && y + i < 8)
        {
            if(chessmen[x + i][y + i] == null)
                ways[x + i][y + i] = true;
            else if (chessmen[x + i][y + i].getColor() != chessmen[x][y].getColor())
            {
                ways[x + i][y + i] = true;
                break;
            }
            i++;
        }
        i = -1;
        while(x + i > 0 && y + i > 0)
        {
            if(chessmen[x + i][y + i] == null)
                ways[x + i][y + i] = true;
            else if (chessmen[x + i][y + i].getColor() != chessmen[x][y].getColor())
            {
                ways[x + i][y + i] = true;
                break;
            }
            i--;
        }
        i = 1;
        while(x - i > 0 && y + i < 8)
        {
            if(chessmen[x - i][y + i] == null)
                ways[x - i][y + i] = true;
            else if (chessmen[x - i][y + i].getColor() != chessmen[x][y].getColor())
            {
                ways[x - i][y + i] = true;
                break;
            }
            i++;
        }
        i = -1;
        while(x - i < 8 && y + i > 0)
        {
            if(chessmen[x - 1][y + i] == null)
                ways[x - 1][y + i] = true;
            else if (chessmen[x - 1][y + i].getColor() != chessmen[x][y].getColor())
            {
                ways[x - 1][y + i] = true;
                break;
            }
            i--;
        }
    }

    private static boolean checkVacation(Chessman [][]chessmen, int xCur, int yCur, int x, int y)
    {
        if(chessmen[x][y] == null)
            return true;
        else return chessmen[x][y].getColor() != chessmen[xCur][yCur].getColor();
    }
}
