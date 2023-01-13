import java.util.Scanner;

//Need to use method printbattleship and pass in array of chars
//Need to pass scanner in and only have one
//Need to update the location and guess grids
// When enter invalid coodinates there is too much space when guessing hit

public class Battleship {
    public static void main(String[] args) {
        System.out.println("Welcome to Battleship!");
        Scanner input = new Scanner(System.in);

        //Build player 1 ship locations
        System.out.println("\nPLAYER 1, ENTER YOUR SHIPS' COORDINATES.");
        char[][] userOneShipLocations = buildShipLocations(input);
        for (int i = 0; i < 100; i++) {
            System.out.println("\n");
        }

        //Build player 2 ship locations
        System.out.println("\n\nPLAYER 2, ENTER YOUR SHIPS' COORDINATES.");
        char[][] userTwoShipLocations = buildShipLocations(input);
        for (int i = 0; i < 100; i++) {
            System.out.println("\n");
        }

        //Build player 1 guess board
        char[][] userOneGuesses = createUserBattleBoard();
        int userOneCorrectGuesses = 0;

        //Build player 2 guess board
        char[][] userTwoGuesses = createUserBattleBoard();
        int userTwoCorrectGuesses = 0;

        //Player rotating guesses
        while (userOneCorrectGuesses < 5 && userTwoCorrectGuesses < 5) {
            boolean playerOneGuess = guessing("1", userOneGuesses, userTwoShipLocations, input);
            if (playerOneGuess) {
                userOneCorrectGuesses++;
            }
            if (userOneCorrectGuesses == 5) {
                break;
            }
            boolean playerTwoGuess = guessing("2", userTwoGuesses, userOneShipLocations, input);
            if (playerTwoGuess) {
                userTwoCorrectGuesses++;
            }
        }

        if (userOneCorrectGuesses == 5) {
            System.out.println("\nPLAYER 1 WINS! YOU SUNK ALL OF YOUR OPPONENT'S SHIPS!");
            System.out.println("\nFinal boards:\n");
            printBattleShip(userOneShipLocations);
            System.out.println("\n");
            printBattleShip(userTwoShipLocations);
        } else {
            System.out.println("\nPLAYER 2 WINS! YOU SUNK ALL OF YOUR OPPONENT'S SHIPS!");
            System.out.println("\nFinal boards:");
            printBattleShip(userTwoShipLocations);
            System.out.println("\n\n");
            printBattleShip(userOneShipLocations);
        }
    }


    public static boolean guessing(String player, char[][] userGuesses, char[][] userBoard, Scanner inputGuesses) {
        boolean invalidGuess = true;
        boolean alreadyGuessed = true;
        int startGuess =0;
        int endGuess = 0;
        boolean hit = false;
        System.out.println("\n");
        while (invalidGuess || alreadyGuessed){
            System.out.println("Player " + player + ", enter hit row/column:");
            startGuess = inputGuesses.nextInt();
            endGuess = inputGuesses.nextInt();
            invalidGuess = (startGuess < 0 || startGuess > 4) || (endGuess < 0 || endGuess > 4);
            if (invalidGuess) {
                System.out.println("Invalid coordinates. Choose different coordinates.");
                continue;
            }
            char guessedChar = userGuesses[startGuess][endGuess];
            if (guessedChar != '-'){
                System.out.println("You already fired on this spot. Choose different coordinates.");
            } else {
                alreadyGuessed = false;
            }
        }
        char hitCharacter = userBoard[startGuess][endGuess];
        char guessCharacter = userGuesses[startGuess][endGuess];
        if (guessCharacter == '-') {
            if (hitCharacter == '@') {
                hit = true;
                String message = player.equals("1") ? "PLAYER 1 HIT PLAYER 2's SHIP!" : "PLAYER 2 HIT PLAYER 1's SHIP!";
                System.out.println(message);
                userGuesses[startGuess][endGuess] = 'X';
                userBoard[startGuess][endGuess] = 'X';
                printBattleShip(userGuesses);
            } else {
                System.out.println("PLAYER " + player + " MISSED!");
                userGuesses[startGuess][endGuess] = 'O';
                userBoard[startGuess][endGuess] = 'O';
                printBattleShip(userGuesses);
            }
        }
        return hit;
    }

    public static char[][] createUserBattleBoard() {
        char[][] userBoard = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                userBoard[i][j] = '-';
            }
        }
        return userBoard;
    }

    // Use this method to print game boards to the console.
    private static void printBattleShip(char[][] player) {
        System.out.print("  ");
        for (int row = -1; row < 5; row++) {
            if (row > -1) {
                System.out.print(row + " ");
            }
            for (int column = 0; column < 5; column++) {
                if (row == -1) {
                    System.out.print(column + " ");
                } else {
                    System.out.print(player[row][column] + " ");
                }
            }
            System.out.println("");
        }
    }

    public static char[][] buildShipLocations(Scanner input) {
        char[][] userBoard = createUserBattleBoard();
        for (int i = 1; i <= 5; i++) {
            int start;
            int end;
            do {
                System.out.println("Enter ship " + i + " location:");
                start = input.nextInt();
                end = input.nextInt();
                if ((start < 0 || end > 4) || (start > 4 || end < 0)) {
                    System.out.println("Invalid coordinates. Choose different coordinates.");
                } else if (userBoard[start][end] != '-'){
                    System.out.println("You already have a ship there. Choose different coordinates.");
                }
            } while ((start < 0 || end > 4) || (start > 4 || end < 0));
            userBoard[start][end] = '@';
        }
        printBattleShip(userBoard);
        return userBoard;
    }
}

// Errors
//Failed: 1st player's final board does not have corrent representation of hi
//t ships (X)Please check the homework .pdf expected:<0 [X X - -] -> but was:<0 [ @
//@  -  - ] ->

//Testing that it can handle positive integers that are out of bounds
//        Failed: Doesnt handle out of bound invalid inputs correctly. Please check t
//he homework .pdf expected:<...nvalid coordinates. []Choose different coo...> but wa
//s:<...nvalid coordinates. [ ]Choose different coo...>

//Failed: The line of the printed board is formatted incorrectly. Please chec
//k the homework .pdf expected:<0 [1 2 3] 4> but was:<0 [ 1  2  3 ] 4>

//Failed: Missed spaces are filled with 'O' character expected:<79> but was:<
//116>

//Failed: Missed spaces are filled with 'X' character expected:<88> but was:<
//108>

//Failed: Doesnt correctly handle when bad coordinates (out of bound integers
//) are fired.Please check the homework .pdf expected:<Player 1[,] enter hit row/colu
//m...> but was:<Player 1[] enter hit row/colum...>

//Failed: 1st player's final board does not have corrent representation of un
//fired coordinates (-)Please check the homework .pdf expected:<2 [- - - -] -> but wa
//s:<2 [ -  -  -  - ] ->

//Failed: 1st player's final board does not have corrent representation of mi
//ssed shots (O)Please check the homework .pdf expected:<1 [- O - -] -> but was:<1 [
//-  -  -  - ] ->

//Testing target history board to make sure empty spaces are '-'
//        Failed: Empty spaces are not filled with '-' character expected:<45> but wa
//s:<111>


//    public static void printBattleBoard(String[][] boardArr) {
//        System.out.printf("%s", "   0  1  2  3  4");
//        for (int i = 0; i < 5; i++) {
//            System.out.println("");
//            System.out.printf("%s ", i);
//            for (int j = 0; j < 5; j++) {
//                System.out.printf("%s", boardArr[i][j]);
//            }
//        }
//    }
