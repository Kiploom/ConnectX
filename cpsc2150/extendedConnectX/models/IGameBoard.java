package cpsc2150.extendedConnectX.models;

/**
 * @author Brianna Cherry (brihcherry)
 * @author Grant Benson (gbensoon)
 * @author Daniel Chaput (Dchaputs)
 * <p></p>
 * This game board is represented by a 2D char array or a hashmap with char keys paired with BoardPosition lists
 * Indexing starts at positon (0,0)
 * <p></p>
 * Initialization ensures:
 *      Each spot on the board contains a ' ' character to represent an empty spot.
 *      AND is self.getNumRows() x self.getNumColumns() big
 * <p></p>
 * Defines:
 *      num_cols: Z
 *      num_rows: Z
 *      num_to_win: Z
 * <p></p>
 * Constraints:
 *      MIN_ROWS <= num_rows <= MAX_ROWS
 *      MIN_COLUMNS <= num_cols <= MAX_COLUMNS
 *      MIN_NUMTOWIN <= num_to_win <= MAX_NUMTOWIN
 *      [No empty tokens between player tokens in the same column]
 */
public interface IGameBoard {

    /**
     * Checks if there is a free spot in a chosen column.
     * @param c is an integer that represents the column that we are checking to see is full or not
     *
     * @pre 0 <= c < num_cols
     *
     * @return true IFF column c has space for another player token, false OW
     *
     * @post
     *  [returns true IFF column c has space for another player token, false OW]
     *  AND self = #self
     */
    default public boolean checkIfFree(int c) {
        //returns true if the column can accept another token; false OW.
        return whatsAtPos(new BoardPosition(this.getNumRows() - 1,c)) == ' ';
    }

    /**
     * Checks if the most recent token has resulted in a win. The method works by calling other
     * methods that check for specific types of wins.
     *
     * @param c the column position that the most recent token was placed in
     *
     * @pre 0 <= c < num_cols
     *
     * @return true IFF checkHorizWin()==true OR checkVertWin()==true OR checkDiagWin()==true
     *
     * @post
     *  [checkForWin is true IFF checkHorizWin()==true OR checkVertWin()==true OR checkDiagWin()==true]
     *  AND self = #self
     */
    default public boolean checkForWin(int c) {
        // Finds the last token dropped position and sets pos to that position
        BoardPosition pos = new BoardPosition(0,c);
        for (int i = 0; i < this.getNumRows(); i++) {
            pos.setRow(i);

            if (this.whatsAtPos(pos) == ' ' || i == this.getNumRows() - 1) {
                pos.setRow(i - 1);
                i = this.getNumRows();
            }
        }

        return this.checkHorizWin(pos, this.whatsAtPos(pos)) || this.checkVertWin(pos, this.whatsAtPos(pos)) || this.checkDiagWin(pos,this.whatsAtPos(pos));
    }

    /**
     * Checks if the most recent token has resulted in a tie, which is when every column is full without any wins.
     *
     * @pre none
     *
     * @return true IFF the game board is full, false OW
     *
     * @post
     *  [returns true IFF all positions are full and !checkForWin, OW false ]
     *  AND self = #self
     */
    default public boolean checkTie() {
        for (int i = 0; i < this.getNumColumns(); i++) {
            if (this.checkIfFree(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for a win where there are num_to_win of the same token in a row horizontally.
     *
     * @param pos the position of the last token placed
     * @param p the token character that was just placed
     *
     * @pre [p contains a valid character]
     *
     * @return true IFF num_to_win of the same token line up horizontally, false OW
     *
     * @post
     *  [returns true IFF num_to_win of the same token line up horizontally, returns false OW]
     *  AND self = #self
     */
    default public boolean checkHorizWin(BoardPosition pos, char p) {
        int count = 0;

        BoardPosition tempPos = new BoardPosition(0,0);

        tempPos.setRow(pos.getRow());
        tempPos.setColumn(pos.getColumn());

        // Check row for a win
        for (int i = 0; i < this.getNumColumns(); i++) {
            tempPos.setColumn(i);
            if (isValidPos(tempPos) && this.isPlayerAtPos(tempPos, p)) {
                count++;
            }
            else {
                count = 0;
            }
            if (count == this.getNumToWin()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for a win where there are num_to_win of the same token in a row vertically.
     *
     * @param pos the position of the last token placed
     * @param p the token character that was just placed
     *
     * @pre [p contains a valid character]
     *
     * @return true IFF num_to_win of the same token line up vertically, false OW
     *
     * @post
     *  [returns true IFF num_to_win of the same token line up vertically, returns false OW]
     *  AND self = #self
     */
    default public boolean checkVertWin(BoardPosition pos, char p) {
        int count = 0;

        BoardPosition tempPos = new BoardPosition(0,0);

        tempPos.setRow(pos.getRow());
        tempPos.setColumn(pos.getColumn());

        // Check column for a win
        for (int i = 0; i < this.getNumRows(); i++) {
            tempPos.setRow(i);
            if (isValidPos(tempPos) && this.isPlayerAtPos(tempPos,p)) {
                count++;
            }
            else {
                count = 0;
            }
            if (count == this.getNumToWin()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for a win where there are num_to_win tokens in a row diagonally.
     *
     * @param pos the position of the last token placed
     * @param p the token character that was just placed
     *
     * @pre [p contains a valid character]
     *
     * @return true IFF num_to_win of the same token line up diagonally, false OW
     *
     * @post
     *  [returns true IFF num_to_win of the same token line up diagonally, returns false OW]
     *  AND self = #self
     */
    default public boolean checkDiagWin(BoardPosition pos, char p) {
        int count = 0;
        int extendScope = 2;

        BoardPosition tempPos = new BoardPosition(0,0);

        // Set position for NW corner of check
        tempPos.setRow(pos.getRow() + this.getNumToWin() - 1);
        tempPos.setColumn(pos.getColumn() - this.getNumToWin() + 1);

        // Check NW/SE diagonal
        for (int i = 0; i < this.getNumToWin()*extendScope + 1; i++) {
            if (isValidPos(tempPos) && this.isPlayerAtPos(tempPos,p)) {
                count++;
            }
            else {
                count = 0;
            }
            if (count == this.getNumToWin()) {
                return true;
            }

            tempPos.setRow(tempPos.getRow() - 1);
            tempPos.setColumn(tempPos.getColumn() + 1);
        }

        // Set position for SW corner of check
        tempPos.setRow(pos.getRow()-this.getNumToWin() + 1);
        tempPos.setColumn(pos.getColumn()-this.getNumToWin() + 1);
        count = 0;

        // Check SW/NE diagonal
        for (int i = 0; i < this.getNumToWin()*extendScope + 1; i++) {
            if (isValidPos(tempPos) && this.isPlayerAtPos(tempPos,p)) {
                count++;
            }
            else {
                count = 0;
            }
            if(count == this.getNumToWin()) {
                return true;
            }

            tempPos.setRow(tempPos.getRow() + 1);
            tempPos.setColumn(tempPos.getColumn() + 1);
        }

        return false;
    }

    /**
     * Checks if a BoardPosition is within this game board's bounds
     *
     * @param pos the BoardPosition that is being checked
     *
     * @pre
     *  pos.getRow() != null
     *  AND pos.getColumn() != null
     *
     * @post
     *  [will return true IFF pos is within BoardPositions bounds]
     *  AND self = #self
     *
     * @return true IFF pos is in the bounds of this game board, false OW
     */
    default public boolean isValidPos(BoardPosition pos) {
        return pos.getRow() >= 0 && pos.getRow() < this.getNumRows() && pos.getColumn() >= 0 && pos.getColumn() < this.getNumColumns();
    }

    /**
     * Check if a position pos is occupied by a specific player token p
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
    default public boolean isPlayerAtPos(BoardPosition pos, char p) {
        return this.whatsAtPos(pos) == p;
    }

    /**
     * Sets token p to the lowest unoccupied row of a chosen column c.
     *
     * @param p the token which depends on which player dropped the token
     * @param c the column that the player requested to drop their token in
     *
     * @pre
     *  p != NULL
     *  AND 0 <= c < num_cols
     *  AND checkIfFree(c) == TRUE
     *
     * @post [self = #self with token p placed at the lowest available row in column c of self]
     */
    public void dropToken(char p, int c);

    /**
     * Gets the contents of a chosen BoardPosition object-- that is, cell on the game board.
     *
     * @param pos the position on this game board that is being examined
     *
     * @pre isValidPos(pos) == true
     *
     * @post
     *  [returns char containing the character that is contained at pos]
     *  AND self = #self
     *
     * @return char containing whichever token exists at pos OR a whitespace character if none do
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * Gets the number of rows in this game board and returns that number
     *
     * @pre none
     *
     * @post
     *  [returns the number of rows in the game board]
     *  AND self = #self
     * 
     * @return int representing the number of rows in this game board
     */
    public int getNumRows();

    /**
     * Gets the number of columns in the game board and returns that number
     *
     * @pre none
     *
     * @post
     *  [returns the number of columns in the game board]
     *  AND self = #self
     * 
     * @return int representing the number of columns in this game board
     */
    public int getNumColumns();

    /**
     * Gets the number of tokens in a row needed to win the game and returns that number
     *
     * @pre none
     *
     * @post
     *  [returns number of tokens in a row required to win the game]
     *  AND self = #self
     *
     * @return int representing the number of tokens in a row needed to win the game
     */
    public int getNumToWin();
}