package cpsc2150.extendedConnectX.models;

/**
 * @author Brianna Cherry (brihcherry)
 * @author Grant Benson (gbensoon)
 * @author Daniel Chaput (Dchaputs)
 * <p></p>
 * Abstract class that implements the overriden toString() method
 */
public abstract class AbsGameBoard implements IGameBoard {
    int doubleDigits = 10;

    /**
     * toString() overridden such that it creates a visualization of the game board.
     * It contains markers for each player where they have placed their tokens, and
     * column numbers at the base of the board. It displays a whitespace for empty cells.
     *
     * @pre none
     *
     * @return a string that is a visual representation of the current GameBoard
     *
     * @post
     * [returns a visual representation of this GameBoard in a string]
     * AND self = #self
     */
    @Override
    public String toString()
    {
        String boardString = "|";
        BoardPosition position = new BoardPosition(0,0);

        // needs comment
        for (int i = 0; i < this.getNumColumns(); i++) {
            boardString += String.format("%2s|", i);
        }

        boardString += "\n";

        // needs comment
        for (int i = this.getNumRows() - 1; i >= 0; i--) {
            boardString += "|";
            for (int j = 0; j < this.getNumColumns(); j++) {
                position.setRow(i);
                position.setColumn(j);
                boardString += String.format("%-2s|", this.whatsAtPos(position));
            }
            boardString += "\n";
        }

        return boardString;
    }
}