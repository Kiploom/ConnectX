package cpsc2150.extendedConnectX.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brianna Cherry (brihcherry)
 * @author Grant Benson (gbensoon)
 * @author Daniel Chaput (Dchaputs)
 * <p></p>
 * GameBoardMem represents the board of a ConnectX game. The board is represented internally
 * by a numRows*numCols big HashMap. It contains methods to add tokens to the board, check for
 * wins and ties, and verify whether a certain column is available for placing a token.
 *
 * @correspondence num_rows = numRows
 * @correspondence num_cols = numCols
 * @correspondence num_to_win = numToWin
 *
 * @invariant MIN_ROWS <= numRows <= MAX_ROWS
 * @invariant MIN_COLUMNS <= numCols <= MAX_COLUMNS
 * @invariant MIN_NUM_TO_WIN <= numToWin <= MAX_NUM_TO_WIN
 * @invariant [no blank space between two characters in a column of the game board]
 */
public class GameBoardMem extends AbsGameBoard{

    private HashMap<Character, List<BoardPosition>> gameBoard;
    private int numRows;
    private int numCols;
    private int numToWin;

    /**
     * Constructor that initializes all private variables and creates an instance
     * of GameBoard with an empty r*c sized HashMap to represent the empty board.
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
     * @post
     * numToWin == n AND numRows == r AND numCols == c
     * AND [gameBoard will be initialized as a new HashMap]
     */
    public GameBoardMem(int r, int c, int n) {
        // Initializing the game board
        gameBoard = new HashMap<>();

        numRows = r;
        numCols = c;
        numToWin = n;
    }



    public void dropToken(char p, int c) {
        // Check if the player exists in the gameBoard
        if (!gameBoard.containsKey(p)) {
            gameBoard.put(p, new ArrayList<>());
        }

        // Finding the lowest empty row
        int row = 0;
        BoardPosition tempPos = new BoardPosition(row,c);
        while (row < numRows && whatsAtPos(new BoardPosition(row, c)) != ' ') {
            row++;
            tempPos.setRow(row);
        }
        //"drop" the token
        gameBoard.get(p).add(tempPos);
    }



    public char whatsAtPos(BoardPosition pos) {
        // Goes through each character and checks if it contains the position pos
        for (Map.Entry<Character, List<BoardPosition>> entry : gameBoard.entrySet()) {
           if (entry.getValue().contains(pos)) {
               return entry.getKey();
           }
        }
        return ' ';
    }


    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numCols;
    }

    public int getNumToWin() {
        return numToWin;
    }

    /**
     * Checks if a position pos is occupied by a specific player token p. Overridden because this
     * is more efficient for a hashmap because you only need to check one key instead of everything on the board
     * using whatsAtPos().
     *
     * @param pos BoardPosition that we're checking
     * @param p char that represents the player token
     *
     * @pre
     *  [pos is a valid position for this board]
     *  AND p != NULL
     *
     * @post
     *  [will only return true if p is at position pos in this game board]
     *  AND self = #self
     *
     * @return true IFF token p is occupying pos, false OW
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char p) {
        // Returns true if p is a key and pos is a value within key p
        return gameBoard.get(p) != null && gameBoard.get(p).contains(pos);
    }
}
