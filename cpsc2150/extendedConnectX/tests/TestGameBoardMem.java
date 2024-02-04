package cpsc2150.extendedConnectX.tests;

import cpsc2150.extendedConnectX.models.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestGameBoardMem {
    
    private IGameBoard IGameBoardFactory(int r, int c, int n) {
        return new GameBoardMem(r, c, n);
    }

    /**
     * Helper function that creates an empty 2D array which will represent the game board.
     *
     * @param rows the number of rows of the gameBoard
     * @param cols the number of columns in the gameBoard
     *
     * @return 2D char array consisting of all "empty" cells, represented by a whitespace character
     */
    private char[][] emptyBoardArray(int rows, int cols) {
        char[][] board = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = ' ';
            }
        }

        return board;
    }

    /**
     * Helper function that adds a token to the lowest available spot in the given
     * column of the 2D board array.
     *
     * @param board the existing 2D array that represents the game board
     * @param col the column index of the token to be placed
     * @param token the character token to be placed
     *
     * @return the 2D char array board with the addition of the token specified by col and token
     */
    private char[][] addToBoardArray(char[][] board, int col, char token) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == ' ') {
                board[i][col] = token;
                return board;
            }
        }

        return board;
    }

    /**
     * Creates a string representation of the 2D array that represents the test game board,
     * using the same format as the string representation created by the toString() method in
     * the GameBoard class.
     *
     * @param board the 2D array to be converted to a string
     *
     * @return a string representation of the test gameBoard
     */
    private String testString(char[][] board) {
        String str = "|";
        int rows = board.length;
        int cols = board[0].length;
        int doubleDigits = 10;

        for (int i = 0; i < cols; i++) {
            if (i >= doubleDigits) {
                str += (i + "|");
            }
            else {
                str += (" " + i + "|");
            }
        }

        str += "\n";

        for (int i = rows - 1; i >= 0; i--) {
            str += "|";
            for (int j = 0; j < cols; j++) {
                str += (board[i][j] + " |");
            }
            str += "\n";
        }

        return str;
    }

    /**
     * Ensures checkIfFree returns true for a column that is almost full.
     */
    @Test
    public void testCheckIfFree_empty_first_almost_full_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        //Test variables
        int checkCol = 0;

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',0);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',0);
        testBoard.dropToken('o',0);

        assertTrue(testBoard.checkIfFree(checkCol));
    }

    /**
     * Ensures checkIfFree returns true when it encounters a free column that is also the final column of the board.
     */
    @Test
    public void testCheckIfFree_empty_last_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        //Test variables
        int checkCol = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        assertTrue(testBoard.checkIfFree(checkCol));
    }

    /**
     * Ensures checkIfFree returns false when it encounters a full column.
     */
    @Test
    public void testCheckIfFree_full_middle_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        //Test variables
        int checkCol = 2;

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',2);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);

        assertFalse(testBoard.checkIfFree(checkCol));
    }

    /**
     * Ensures checkHorizWin works correctly when it is given the very top right position of the board.
     */
    @Test
    public void testCheckHorizWin_top_right_true_char_X() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(4,4);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);
        char[][] charTestBoard = emptyBoardArray(5, 5);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',4);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',4);



        assertTrue(testBoard.checkHorizWin(checkPos,checkChar));
    }

    /**
     * Ensures checkHorizWin works correctly when it is given the very bottom left position of the board.
     */
    @Test
    public void testCheckHorizWin_bottom_left_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(0,0);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',1);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',0);

        assertTrue(testBoard.checkHorizWin(checkPos,checkChar));
    }

    /**
     * Ensures checkHorizWin works correctly when it is given a scenario where there are one less than numToWin matching chars in a row.
     */
    @Test
    public void testCheckHorizWin_middle_oneLessThenNumToWin_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(0,3);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',1);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',3);
        assertFalse(testBoard.checkHorizWin(checkPos,checkChar));
    }

    /**
     * Ensures checkHorizWin works correctly when it is given a position where it has to check both sides of the position.
     */
    @Test
    public void testCheckHorizWin_bottom_middle_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(0,2);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',0);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',2);
        assertTrue(testBoard.checkHorizWin(checkPos,checkChar));
    }

    /**
     * Ensures checkVertWin works correctly when it is given the very top right position of the board.
     */
    @Test
    public void testCheckVertWin_top_right_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(4,4);
        char checkChar = 'o';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',4);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',4);

        assertTrue(testBoard.checkVertWin(checkPos,checkChar));
    }

    /**
     * Ensures checkVertWin works correctly when it is given the very top left position of the board.
     */
    @Test
    public void testCheckVertWin_top_left_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(4,0);
        char checkChar = 'o';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',0);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',0);

        assertTrue(testBoard.checkVertWin(checkPos,checkChar));
    }

    /**
     * Ensures checkVertWin works correctly when it is given a scenario where there are one less than numToWin matching chars in a row.
     */
    @Test
    public void testCheckVertWin_middle_oneLessThanNumToWin_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(2,2);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',2);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',2);

        assertFalse(testBoard.checkVertWin(checkPos,checkChar));
    }

    /**
     * Ensures checkVertWin works correctly when it is checking a position that has both a horizontal and vertical win, but not a vertical win.
     */
    @Test
    public void testCheckVertWin_other_win_cases_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(3,3);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',0);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',0);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',0);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',3);

        assertFalse(testBoard.checkVertWin(checkPos,checkChar));
    }

    /**
     * Ensures the SW/NE part of checkDiagWin works correctly when given the top right position of the game board.
     */
    @Test
    public void testCheckDiagWin_SW_NE_top_right_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(4,4);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',2);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',4);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',4);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',4);

        assertTrue(testBoard.checkDiagWin(checkPos,checkChar));
    }

    /**
     * Ensures the SW/NE part of checkDiagWin works correctly when given the bottom left position of the game board.
     */
    @Test
    public void testCheckDiagWin_SW_NE_bottom_left_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(0,0);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',2);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',0);

        assertTrue(testBoard.checkDiagWin(checkPos,checkChar));
    }

    /**
     * Ensures the SW/NE part of checkDiagWin works correctly when given a position in the middle of the board (checkDiagWin must check both directions).
     */
    @Test
    public void testCheckDiagWin_SW_NE_middle_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(2,2);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',0);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',2);

        assertTrue(testBoard.checkDiagWin(checkPos,checkChar));
    }

    /**
     * Ensures the NW/SE part of checkDiagWin works correctly when given the top left position of the game board.
     */
    @Test
    public void testCheckDiagWin_NW_SE_top_left_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(4,0);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',0);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',0);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',0);

        assertTrue(testBoard.checkDiagWin(checkPos,checkChar));
    }

    /**
     * Ensures the NW/SE part of checkDiagWin works correctly when given the bottom right position of the game board.
     */
    @Test
    public void testCheckDiagWin_NW_SE_bottom_right_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(0,4);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',4);

        assertTrue(testBoard.checkDiagWin(checkPos,checkChar));
    }

    /**
     * Ensures the NW/SE part of checkDiagWin works correctly when given a position in the middle of the board (checkDiagWin must check both directions).
     */
    @Test
    public void testCheckDiagWin_NW_SE_middle_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(2,2);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',2);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',4);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',2);

        assertTrue(testBoard.checkDiagWin(checkPos,checkChar));
    }

    /**
     * Ensures checkDiagWin works when the given position has one less than numToWin mactching characters in a row on either diagonal from it, meaning it should return false.
     */
    @Test
    public void testCheckDiagWin_oneLessThanNumToWin_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(2,2);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',1);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);

        assertFalse(testBoard.checkDiagWin(checkPos,checkChar));
    }

    /**
     * Ensures checkTie correctly return true when the board is full and there is no tie.
     */
    @Test
    public void testCheckTie_full_noWin_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',0);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',0);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',0);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',4);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',4);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',4);

        assertTrue(testBoard.checkTie());
    }

    /**
     * Ensures checkTie correctly returns false when the game board is full and there is a win.
     */
    @Test
    public void testCheckTie_full_win_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',0);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',0);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',4);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',1);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',0);

        assertFalse(testBoard.checkTie());
    }

    /**
     * Ensures checkTie correctly returns false when the entire game board is full except for the top right corner and there is no win.
     * AND Ensures that checkTie checks the top right corner when checking for a tie.
     */
    @Test
    public void testCheckTie_notFull_noWin_topRightEmpty_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',0);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',0);
        testBoard.dropToken('o',0);
        testBoard.dropToken('x',0);

        testBoard.dropToken('o',2);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',2);

        testBoard.dropToken('x',1);
        testBoard.dropToken('o',1);
        testBoard.dropToken('x',1);

        testBoard.dropToken('o',1);
        testBoard.dropToken('x',2);
        testBoard.dropToken('o',1);

        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);
        testBoard.dropToken('x',3);
        testBoard.dropToken('o',3);

        testBoard.dropToken('x',2);
        testBoard.dropToken('o',3);

        testBoard.dropToken('x',4);
        testBoard.dropToken('o',4);
        testBoard.dropToken('x',4);
        testBoard.dropToken('o',4);

        assertFalse(testBoard.checkTie());
    }

    /**
     * Ensures checkTie correctly returns false when the board is empty.
     */
    @Test
    public void testCheckTie_empty_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        assertFalse(testBoard.checkTie());
    }

    /**
     * Ensures isPlayerAtPos correctly returns true when the player it's checking for in the top right corner is present in the top right corner.
     */
    @Test
    public void testIsPlayerAtPos_top_right_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        char dropTokenOne = 'x';
        char dropTokenTwo = 'o';

        // Test variables
        BoardPosition checkPos = new BoardPosition(4,4);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken(dropTokenOne,4);
        testBoard.dropToken(dropTokenTwo,4);
        testBoard.dropToken(dropTokenOne,4);
        testBoard.dropToken(dropTokenTwo,4);
        testBoard.dropToken(dropTokenOne,4);

        assertTrue(testBoard.isPlayerAtPos(checkPos,checkChar));
    }

    /**
     * Ensures isPlayerAtPos correctly returns true when the player it's checking for in the bottom left corner is present in the bottom left corner.
     */
    @Test
    public void testIsPlayerAtPos_bottom_left_true() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(0,0);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',0);

        assertTrue(testBoard.isPlayerAtPos(checkPos,checkChar));
    }

    /**
     * Ensures isPlayerAtPos correctly returns false when the player it's checking for is not present at the position it's checking.
     */
    @Test
    public void testIsPlayerAtPos_bottom_middle_wrongToken_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(0,2);
        char checkChar = 'o';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        testBoard.dropToken('x',2);

        assertFalse(testBoard.isPlayerAtPos(checkPos,checkChar));
    }

    /**
     * Ensures isPlayerAtPos correctly returns false when checking for a player token at a position that's empty.
     */
    @Test
    public void testIsPlayerAtPos_middle_empty_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(4,4);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        assertFalse(testBoard.isPlayerAtPos(checkPos,checkChar));
    }

    /**
     * Ensures isPlayerAtPos correctly returns false when checking the top right position for a player token when it's empty.
     */
    @Test
    public void testIsPlayerAtPos_top_right_empty_false() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition checkPos = new BoardPosition(4,4);
        char checkChar = 'x';

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        assertFalse(testBoard.isPlayerAtPos(checkPos,checkChar));
    }

    /**
     * Ensures GameBoard constructor correctly sets the parameters
     * row, column, and number to win when a board is created.
     */
    @Test
    public void test_constructor_setParameters() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows,numCols,numToWin);

        assertEquals(numRows, testBoard.getNumRows());
        assertEquals(numCols, testBoard.getNumColumns());
        assertEquals(numToWin, testBoard.getNumToWin());

    }

    /**
     * Tests that constructor correctly creates a board of the smallest
     * allowable size and number to win.
     */
    @Test
    public void test_constructor_smallestBoard() {
        // Board constructor variables
        int numRows = 3;
        int numCols = 3;
        int numToWin = 3;

        IGameBoard actualBoard = IGameBoardFactory(numRows, numCols, numToWin);
        char[][] expectedBoard = emptyBoardArray(numRows, numCols);

        assertEquals(actualBoard.toString(), testString(expectedBoard));
    }

    /**
     * Tests that constructor correctly creates a board of the largest
     * allowable size and number to win.
     */
    @Test
    public void test_constructor_biggestBoard() {
        // Board constructor variables
        int numRows = 100;
        int numCols = 100;
        int numToWin = 25;

        IGameBoard actualBoard = IGameBoardFactory(numRows, numCols, numToWin);
        char[][] expectedBoard = emptyBoardArray(numRows, numCols);

        assertEquals(actualBoard.toString(), testString(expectedBoard));

    }

    /**
     *  Ensures that dropToken correctly places a token in an empty column,
     *  before using dropToken() in more complicated tests.
     */
    @Test
    public void testDropToken_ValidEmptyColumn() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        char checkTokenOne = 'x';

        IGameBoard actualBoard = IGameBoardFactory(numRows,numCols,numToWin);
        char[][] expectedBoard = emptyBoardArray(numRows, numCols);

        actualBoard.dropToken(checkTokenOne, 0);
        expectedBoard = addToBoardArray(expectedBoard, 0, checkTokenOne);

        assertEquals(actualBoard.toString(), testString(expectedBoard));
    }

    /**
     * Ensures that dropToken correctly places a token in a
     * non-empty, valid column, before using dropToken() in more complicated tests
     */
    @Test
    public void testDropToken_ValidNonEmptyColumn() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        char checkTokenOne = 'x';
        char checkTokenTwo = 'o';

        IGameBoard actualBoard = IGameBoardFactory(numRows,numCols,numToWin);
        char[][] expectedBoard = emptyBoardArray(numRows, numCols);

        actualBoard.dropToken(checkTokenOne, 3);
        expectedBoard = addToBoardArray(expectedBoard, 3, checkTokenOne);
        actualBoard.dropToken(checkTokenTwo, 3);
        expectedBoard = addToBoardArray(expectedBoard, 3, checkTokenTwo);

        // Verifies that the GameBoard method places token in the same way as the 2D test array
        assertEquals(actualBoard.toString(), testString(expectedBoard));
    }

    /**
     * Ensures that the dropToken() method can correctly place a token in the highest
     * row of the indicated column, again building off previous tests
     */
    @Test
    public void testDropToken_top_right() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;
        char checkTokenOne = 'x';
        char checkTokenTwo = 'o';

        IGameBoard actualBoard = IGameBoardFactory(numRows, numCols, numToWin);
        char[][] expectedBoard = emptyBoardArray(numRows, numCols);

        // Fills the entire column almost to the top
        for (int i = 0; i < numRows - 1; i++) {
            actualBoard.dropToken(checkTokenOne, 4);
            expectedBoard = addToBoardArray(expectedBoard, 4, checkTokenOne);
        }

        // Drops a token in the final spot in the column
        actualBoard.dropToken(checkTokenTwo, 4);
        expectedBoard = addToBoardArray(expectedBoard, 4, checkTokenTwo);

        // Verify that the board remains unchanged
        assertEquals(actualBoard.toString(), testString(expectedBoard));
    }

    /**
     * Ensures that when a token is attempted to drop in a full column, with other
     * columns open, the GameBoard object is unchanged.
     */
    @Test
    public void testDropToken_OnlyFullCol() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        char checkTokenOne = 'x';
        char checkTokenTwo = 'o';

        IGameBoard actualBoard = IGameBoardFactory(numRows,numCols,numToWin);
        char[][] expectedBoard = emptyBoardArray(numRows, numCols);

        actualBoard.dropToken(checkTokenOne, 3);
        expectedBoard = addToBoardArray(expectedBoard, 3, checkTokenOne);
        actualBoard.dropToken(checkTokenTwo, 3);
        expectedBoard = addToBoardArray(expectedBoard, 3, checkTokenTwo);
        actualBoard.dropToken(checkTokenOne, 3);
        expectedBoard = addToBoardArray(expectedBoard, 3, checkTokenOne);
        actualBoard.dropToken(checkTokenTwo, 3);
        expectedBoard = addToBoardArray(expectedBoard, 3, checkTokenTwo);
        actualBoard.dropToken(checkTokenOne, 3);
        expectedBoard = addToBoardArray(expectedBoard, 3, checkTokenOne);

        // Drop token in column 3 which should be full
        actualBoard.dropToken(checkTokenTwo, 3);

        // Verifies that the test Board is unchanged from it's identical board which has
        // not had a token "dropped" in a full column
        assertEquals(actualBoard.toString(), testString(expectedBoard));
    }

    /**
     * Verifies that dropToken() method correctly places a token in a valid column
     * on a board that has other tokens but is not completely full.
     */
    @Test
    public void testDropToken_validCol_nonEmptyBoard() {
        // Board constructor variables
        int numRows = 4;
        int numCols = 4;
        int numToWin = 3;

        IGameBoard actualBoard = IGameBoardFactory(numRows,numCols,numToWin);
        char[][] expectedBoard = emptyBoardArray(numRows, numCols);

        // Fills each board with identical tokens
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (r+c % 2 == 0) {
                    actualBoard.dropToken('a', c);
                    expectedBoard = addToBoardArray(expectedBoard, c, 'a');
                }
                else {
                    actualBoard.dropToken('b', c);
                    expectedBoard = addToBoardArray(expectedBoard, c, 'b');
                }
            }
        }

        // For the test: drop a token in column that is not full-- should place token
        actualBoard.dropToken('c',1);
        expectedBoard = addToBoardArray(expectedBoard, 1, 'c');

        // Asserts that dropping the token did not modify the board besides the column it dropped in
        assertEquals(actualBoard.toString(), testString(expectedBoard));
    }

    /**
     * Verifies that whatsAtPos() correctly returns token values in the upper left
     * row of a board.
     */
    @Test
    public void testWhatsAtPos_checking_NW_corner() {
        // Board constructor variables
        int numRows = 4;
        int numCols = 4;
        int numToWin = 3;

        // Test variables
        BoardPosition testPos = new BoardPosition(3, 0);
        char testChar = 'd';

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        // Fills the leftmost row to reach NW corner
        testBoard.dropToken('a', 0);
        testBoard.dropToken('b', 0);
        testBoard.dropToken('c', 0);
        testBoard.dropToken('d', 0);

        assertEquals(testChar, testBoard.whatsAtPos(testPos));
    }

    /**
     * Verifies that whatsAtPos() correctly returns token values in the lower
     * left cell of a board.
     */
    @Test
    public void testWhatsAtPos_checking_SW_corner() {
        // Board constructor variables
        int numRows = 4;
        int numCols = 4;
        int numToWin = 3;

        // Test variables
        BoardPosition testPos = new BoardPosition(0, 0);
        char testChar = 'a';

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        // Fills the SW corner as well as some other cells
        testBoard.dropToken('a', 0);
        testBoard.dropToken('b', 0);
        testBoard.dropToken('c', 0);
        testBoard.dropToken('d', 0);

        assertEquals(testChar, testBoard.whatsAtPos(testPos));
    }

    /**
     * Verifies that whatsAtPos() correctly returns token values in the upper right
     * cell of a board.
     */
    @Test
    public void testWhatsAtPos_checking_NE_corner() {
        // Board constructor variables
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        // Test variables
        BoardPosition testPos = new BoardPosition(4, 4);
        char testChar = 'e';

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('a', 4);
        testBoard.dropToken('b', 4);
        testBoard.dropToken('c', 4);
        testBoard.dropToken('d', 4);
        testBoard.dropToken('e', 4);

        assertEquals(testChar, testBoard.whatsAtPos(testPos));
    }

    /**
     * Verifies that whatsAtPos() correctly returns token values in the lower
     * right cell of a board.
     */
    @Test
    public void testWhatsAtPos_checking_SE_corner() {
        // Board constructor variables
        int numRows = 4;
        int numCols = 4;
        int numToWin = 3;

        // Test variables
        BoardPosition testPos = new BoardPosition(0, 3);
        char testChar = 'a';

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        // Fills lower right corner
        testBoard.dropToken('a', 3);
        testBoard.dropToken('b', 3);
        testBoard.dropToken('c', 3);
        testBoard.dropToken('d', 3);

        assertEquals(testChar, testBoard.whatsAtPos(testPos));
    }

    /**
     * Ensure that whatsAtPos correctly returns a value stored in the center
     * of the board when many tokens have been placed.
     */
    @Test
    public void testWhatsAtPos_center_cell_NonEmptyBoard() {
        // Board constructor variables
        int numRows = 4;
        int numCols = 4;
        int numToWin = 3;

        // Test variables
        BoardPosition testPos = new BoardPosition(2, 2);
        char testChar = 'o';

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('x', 0);
        testBoard.dropToken('o', 0);
        testBoard.dropToken('x', 1);
        testBoard.dropToken('o', 2);
        testBoard.dropToken('x', 2);
        testBoard.dropToken('o', 2);

        assertEquals(testChar,testBoard.whatsAtPos(testPos));
    }
}
