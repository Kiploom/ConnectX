package cpsc2150.extendedConnectX.models;

import java.util.Arrays;

/**
 * @author Brianna Cherry (brihcherry)
 * @author Grant Benson (gbensoon)
 * @author Daniel Chaput (Dchaputs)
 * <p></p>
 * GameBoard represents the board of a ConnectX game. The board is represented internally
 * by a numRows x numCols 2D char array. It contains methods to add tokens to the board, check for wins and
 * ties, and verify whether a certain column is available for placing a token.
 *
 * @correspondence num_rows = numRows
 * @correspondence num_cols = numCols
 * @correspondence num_to_win = numToWin
 *
 * @invariant MIN_ROWS <= numRows <= MAX_ROWS
 * @invariant MIN_COLUMNS <= numCols <= MAX_COLUMNS
 * @invariant MIN_NUMTOWIN <= numToWin <= MAX_NUMTOWIN
 * @invariant [no blank space between two characters in a column of the game board]
 */

public class GameBoard extends AbsGameBoard {

    private char[][] gameBoard;
    private int numRows;
    private int numCols;
    private int numToWin;

    /**
     * Constructor that initializes all private variables and creates an instance
     * of GameBoard with an empty r x c array to represent the empty board.
     *
     * @param r the number of rows in the gameBoard
     * @param c the number of columns in the gameBoard
     * @param n the number that the player needs to get in a row to win the game
     *
     * @pre
     *  r != NULL
     *  AND c != NULL
     *  AND n != NULL
     *  AND MIN_ROWS <= r <= MAX_ROWS
     *  AND MIN_COLUMNS <= c <= MAX_COLUMNS
     *  AND MIN_NUM_TO_WIN <= n <= MAX_NUM_TO_WIN
     * 
     * @post [all characters within the 2D array gameBoard will be set to ' ']
     */
    public GameBoard(int r, int c, int n)
    {
        numRows = r;
        numCols = c;
        numToWin = n;
        gameBoard = new char[numRows][numCols];

        // Sets all positions in this GameBoard's board to contain ' '
        for (int i = 0; i < r; i++) {

            Arrays.fill(gameBoard[i], ' ');

        }
    }



    public void dropToken(char p, int c) {
        // Places the character p in column c. The token will be placed in the lowest available row in column c.        
        for (int i = 0; i < this.getNumRows(); i++) {
            if (gameBoard[i][c] == ' ') {
                gameBoard[i][c] = p;
                return;
            }
        }
    }



    public char whatsAtPos(BoardPosition pos) {
        return gameBoard[pos.getRow()][pos.getColumn()];
    }


    public int getNumRows()
    {
        return numRows;
    }


    public int getNumColumns()
    {
        return numCols;
    }


    public int getNumToWin()
    {
        return numToWin;
    }
}
