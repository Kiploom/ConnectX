package cpsc2150.extendedConnectX.models;

/**
 * @author Brianna Cherry (brihcherry)
 * @author Grant Benson(gbensoon)
 * @author Daniel Chaput (Dchaputs)
 * <p></p>
 * The BoardPosition class handles each individual cell of the ConnectX
 * game board. Each instance (equivalent to a cell on the board)
 * contains the row and column of the cell. The class also overrides the
 * equals() and toString() methods for BoardPosition objects.
 *
 * @invariant MIN_ROWS <= row <= MAX_ROWS
 * @invariant MIN_COLUMNS <= column <= MAX_COLUMNS
 */
public class BoardPosition {
    private int row;
    private int column;

    /**
     * Parameterized constructor creates an instance of BoardPosition and
     * sets the row and column of the object.
     *
     * @param r int that contains the row value being assigned
     * @param c int that contains the column value being assigned
     *
     * @pre
     *  r != NULL
     *  c != NULL
     *
     * @post
     *  row = r
     *  AND column = c
     */
    public BoardPosition(int r, int c) {
        //parameterized constructor for BoardPosition
        row = r;
        column = c;
    }

    /**
     * Gets the row of the current instance of BoardPosition
     *
     * @pre none
     *
     * @return int containing the row position of this BoardPostion
     * 
     * @post
     *  getRow = row
     *  AND row = #row
     *  AND column = #column
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column of the current instance of BoardPosition.
     *
     * @pre none
     *
     * @return int containing the column position of this BoardPostion
     * 
     * @post
     *  getColumn = column
     *  AND column = #column
     *  AND row = #row
     */
    public int getColumn() {
        return column;
    }

    /**
     * sets the row of the current BoardPosition object
     * 
     * @param r will be saved as the row
     * 
     * @pre r != NULL
     * 
     * @post
     *  row = r
     *  AND column = #column
     */
    public void setRow(int r) {
        row = r;
    }

    /**
     *sets the column of the current BoardPosition object
     * 
     * @param c will be saved as the column
     * 
     * @pre c != NULL
     * 
     * @post
     *  column = c
     *  AND row = #row
     */
    public void setColumn(int c) {
        column = c;
    }

    /**
     * equals() method is overridden so that two BoardPosition objects are equal
     * if they have the same row and column.
     *
     * @param obj is the current board position
     * 
     * @pre obj != null
     *
     * @return true if the objects are equal and false otherwise
     * 
     * @post
     *  [returns true if the two BoardPosition objects contain the same position and false otherwise]
     *  AND row = #row
     *  AND column = #column
     */
    @Override
    public boolean equals(Object obj) {
        // Ensuring obj is a BoardPosition object
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        BoardPosition this2 = (BoardPosition) obj;
        return this2.row == this.row && this2.column == this.column;
    }

    /**
     * toString() method is overridden so that a BoardPosition object's string
     * is its row and column separated by a comma; "<row>,<column>".
     *
     * @pre none
     *
     * @return String containing the board position
     * 
     * @post
     *  [The returned string will be in the format "<row>,<column>"]
     *  AND row = #row
     *  AND column = #column
     */
    @Override
    public String toString()
    {
        String out = "";
        out += (this.row + "," + this.column);

        return out;

    }
}