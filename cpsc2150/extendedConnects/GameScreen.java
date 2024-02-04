package cpsc2150.extendedConnects;

import java.util.*;
import cpsc2150.extendedConnectX.models.*;

/**
 * @author Brianna Cherry (brihcherry)
 * @author Grant Benson (gbensoon)
 * @author Daniel Chaput (Dchaputs)
 * <p></p>
 * The main class for the connectX game that contains the main function,
 * gets the user input, and calls various other functions to run the game
 */
public class GameScreen {

    public static final int MIN_ROWS = 3;
    public static final int MAX_ROWS = 100;
    public static final int MIN_COLUMNS = 3;
    public static final int MAX_COLUMNS = 100;
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 10;
    public static final int MIN_NUM_TO_WIN = 3;
    public static final int MAX_NUM_TO_WIN = 25;

    private static List<Character> playerTokens;
    private static int turn = 0;
    private static int playerChoice = 0;
    private static String anotherGame = "Y";
    private static boolean someOneWins = false;
    private static boolean isTie = false;
    private static int numberPlayers = 0;
    private static int rows = 0;
    private static int cols = 0;
    private static int numToWin = 0;


    public static void main(String[] args) {
        Scanner ss = new Scanner(System.in);
        System.out.println("Welcome to ConnectX! To win the game, you must place enough\n" +
                "tokens in a row diagonally, horizontally, or vertically. If every\n" +
                "place on the board is taken without a win, the game will end in a tie.\n");

        while (anotherGame.equalsIgnoreCase("Y")) {
            turn = 0;
            numberPlayers = inputNumPlayers(ss);
            playerTokens = saveTokens(ss);
            rows = inputRows(ss);
            cols = inputCols(ss);
            numToWin = inputNumToWin(ss);

            System.out.println("Which version of ConnectX would you like to play? Enter F for fast or M for memory-efficient: ");
            String type = ss.next();

            while (!type.equalsIgnoreCase("F") && !type.equalsIgnoreCase("M")) {
                System.out.println("Invalid choice. Please enter F for fast or M for memory-efficient: ");
                type = ss.next();
            }

            IGameBoard myBoard;
            if (type.equalsIgnoreCase("F")) {
                myBoard = new GameBoard(rows, cols, numToWin);
            }
            else {
                myBoard = new GameBoardMem(rows, cols, numToWin);
            }

            while (!someOneWins && !isTie) {
                System.out.println(myBoard);

                playerChoice = getTurnCol(myBoard, ss);
                
                myBoard.dropToken(playerTokens.get(turn % numberPlayers), playerChoice);

                someOneWins = myBoard.checkForWin(playerChoice);
                isTie = myBoard.checkTie();

                turn++;
            }
            
            System.out.println(myBoard);

            if(isTie) {
                System.out.println("The game is a tie!");
                isTie = false;
            }
            else if (someOneWins) {
                System.out.println("Player " + playerTokens.get((turn - 1) % numberPlayers ) + " won!\n");
                someOneWins = false;
            }

            System.out.println("Would you like to play again? Y/N");
            anotherGame = ss.next();
        }

        ss.close();
    }

    /**
     * Stores the players' choice for the column in playerChoice, using
     * input validation to make sure an appropriate choice is given.
     * 
     * @pre
     *  s != null
     *  AND myBoard != null
     *
     * @param myBoard IGameBoard of the current game of ConnectX
     * @param s Scanner object that will take user input
     * 
     * @post
     *  [getTurnCol = valid column choice given by player]
     *  AND self = #self
     * 
     * @return int that is the player's choice for a valid column
     */
    private static int getTurnCol(IGameBoard myBoard, Scanner s) {
        int choice;

        // Get the user's choice
        System.out.println("Player " + playerTokens.get(turn % numberPlayers) + ", what column do you want to place your marker in?");
        while (!s.hasNextInt()) {
            System.out.println("Player " + playerTokens.get(turn % numberPlayers) + ", what column do you want to place your marker in?");
            s.next();
        }
        choice = s.nextInt();

        // Until the user enters a valid choice:
        while (choice < 0 || choice >= cols || !myBoard.checkIfFree(choice)) {
            // Procedure for an out-of-bounds column
            if (choice < 0) {
                System.out.println("Column cannot be less than 0");
                System.out.println("Player " + playerTokens.get(turn % numberPlayers) + ", what column do you want to place your marker in?");
                while (!s.hasNextInt()) {
                    System.out.println("Player " + playerTokens.get(turn % numberPlayers) + ", what column do you want to place your marker in?");
                    s.next();
                }
                choice = s.nextInt();
            }
            else if (choice >= cols) {
                int maxCols = cols - 1;
                System.out.println("Column cannot be greater than " + maxCols);
                System.out.println("Player " + playerTokens.get(turn % numberPlayers) + ", what column do you want to place your marker in?");
                while (!s.hasNextInt()) {
                    System.out.println("Player " + playerTokens.get(turn % numberPlayers) + ", what column do you want to place your marker in?");
                    s.next();
                }
                choice = s.nextInt();
            }

            // Procedure for a column that is already full
            else if (!myBoard.checkIfFree(choice)) {
                System.out.println("Column is full");
                System.out.println("Player " + playerTokens.get(turn % numberPlayers) + ", what column do you want to place your marker in?");
                while (!s.hasNextInt()) {
                    System.out.println("Player " + playerTokens.get(turn % numberPlayers) + ", what column do you want to place your marker in?");
                    s.next();
                }
                choice = s.nextInt();
            }
        }

        return choice;
    }

    
    /**
     * Creates a list of character tokens for each player in the game in the order
     * that they will have their turns, ensuring that there are no duplicate 
     * tokens and that all letter tokens are uppercase.
     * 
     * @pre [numberPlayers must already contain a valid number given by players]
     * 
     * @param s Scanner object that takes input from the players
     * 
     * @post
     *  [saveTokens = a list of characters representing the token choice for each player.]
     *  AND self = #self
     * 
     * @return List<Character> containing the list of player tokens as characters
     */
    private static List<Character> saveTokens(Scanner s) {
        List<Character> players = new ArrayList<Character>(numberPlayers);

        for (int i  = 1; i <= numberPlayers; i++) {
            System.out.println("Enter the character to represent player " + i);
            String line = s.nextLine();
            while (line.isEmpty()) {
                line = s.nextLine();
            }

            char c;
            boolean duplicate = true;
            while (duplicate) {
                line = line.toUpperCase();
                c = line.charAt(0);
                if (!players.contains(c)) {
                    players.add(c);
                    duplicate = false;
                }
                else {
                    System.out.println(c + " is already taken as a player token!");
                    System.out.println("Enter the character to represent player " + i);
                    line = s.nextLine();
                }
            }
        }

        return players;
    }

    /**
     * Takes a choice from the player for the number of rows in the game board,
     * using input validation to make sure a valid choice is given.
     * 
     * @param s Scanner object used to accept input from players
     * 
     * @pre none
     * 
     * @post
     *  [inputRows = a choice for number of rows in the range [MIN_ROWS,MAX_ROWS]]
     *  AND self = #self
     * 
     * @return int containing the number of rows
     */
    private static int inputRows(Scanner s) {
        int rowIn;
        System.out.println("How many rows should be on the board?");
        while (!s.hasNextInt()) {
            System.out.println("How many rows should be on the board?");
            s.next();
        }
        rowIn = s.nextInt();

        // Until the user enters a valid choice:
        while (rowIn < MIN_ROWS || rowIn > MAX_ROWS) {
            if (rowIn < MIN_ROWS) {
                System.out.println("Must be at least " + MIN_ROWS + " rows");
            }
            else {
                System.out.println("Must be " + MAX_ROWS + " rows or fewer");
            }
            while (!s.hasNextInt()) {
                System.out.println("How many rows should be on the board?");
                s.next();
            }
            rowIn = s.nextInt();
        }

        return rowIn;
    }

    /**
     * Takes a choice from the player for the number of columns in the game board,
     * using input validation to make sure a valid choice is given.
     * 
     * @param s Scanner object used to accept input from players
     * 
     * @pre none
     * 
     * @post
     *  [inputCols = a choice for number of columns in the range [MIN_COLUMNS,MAX_COLUMNS]]
     *  AND self = #self
     * 
     * @return int containing the number of columns
     */
    private static int inputCols(Scanner s) {
        int columnIn;
        System.out.println("How many columns should be on the board?");
        while (!s.hasNextInt()) {
            System.out.println("How many columns should be on the board?");
            s.next();
        }
        columnIn = s.nextInt();

        // Until the user enters a valid choice:
        while (columnIn < MIN_COLUMNS || columnIn > MAX_COLUMNS) {
            if (columnIn < MIN_COLUMNS) {
                System.out.println("Must be at least " + MIN_COLUMNS + " columns");
            }
            else {
                System.out.println("Must be " + MAX_COLUMNS + " columns or fewer");
            }
            while (!s.hasNextInt()) {
                System.out.println("How many columns should be on the board?");
                s.next();
            }
            columnIn = s.nextInt();
        }    

        return columnIn;
    }

    /**
     * Takes a choice from the player for the number of players in the game,
     * using input validation to make sure a valid choice is given.
     * 
     * @param s Scanner object used to accept input from players
     * 
     * @pre none
     * 
     * @post
     *  [inputNumPlayers = a choice for number of players within the range [MIN_PLAYERS,MAX_PLAYERS]]
     *  AND self = #self
     * 
     * @return int containing the number of players playing
     */
    private static int inputNumPlayers(Scanner s) {
        int playerIn;
        System.out.println("How many players?");
        while (!s.hasNextInt()) {
            System.out.println("How many players?");
            s.next();
        }
        playerIn = s.nextInt();

        // Until the user enters a valid choice:
        while (playerIn < MIN_PLAYERS || playerIn > MAX_PLAYERS) {
            if (playerIn < MIN_PLAYERS) {
                System.out.println("Must be at least " + MIN_PLAYERS + " players");
            }
            else {
                System.out.println("Must be " + MAX_PLAYERS + " players or fewer");
            }
            while (!s.hasNextInt()) {
                System.out.println("How many players?");
                s.next();
            }
            playerIn = s.nextInt();
        }    

        return playerIn;
    }

    /**
     * Takes a choice from the player for the number of tokens in a row to
     * win a game, using input validation to make sure a valid choice is given.
     * 
     * @param s Scanner object used to take input from the players
     * 
     * @pre [rows and cols must already contain valid values given by user]
     * 
     * @post
     *  [inputNumToWin = a valid number to win given by the player]
     *  AND self = #self
     * 
     * @return int containing the number of tokens in a row required to win the game
     */
    private static int inputNumToWin(Scanner s) {
        int numToWinIn;
        System.out.println("How many in a row to win?");
        while (!s.hasNextInt()) {
            System.out.println("How many in a row to win?");
            s.next();
        }
        numToWinIn = s.nextInt();

        // Until the user enters a valid choice:
        while (numToWinIn < MIN_NUM_TO_WIN || numToWinIn > cols || numToWinIn > rows || numToWinIn > MAX_NUM_TO_WIN) {
            int max = rows;
            if (cols < rows) {
                max = cols;
            }
            if (MAX_NUM_TO_WIN < max) {
                max = MAX_NUM_TO_WIN;
            }

            if(numToWinIn < MIN_NUM_TO_WIN) {
                System.out.println("Must be at least " + MIN_NUM_TO_WIN + " in a row to win");
            }
            else {
                System.out.println("Must be " + max + " or fewer in a row to win");
            }
            while (!s.hasNextInt()) {
                System.out.println("How many in a row to win?");
                s.next();
            }
            numToWinIn = s.nextInt();
        }  

        return numToWinIn;
    }
}

