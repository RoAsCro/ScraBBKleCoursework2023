package pij.main;

import pij.main.players.ComputerPlayer;
import pij.main.players.HumanPlayer;

import java.util.LinkedList;

/**
 * Main class for ScraBBKle. Initialises a standard Game based on user inputs.
 *
 * @author Roland Crompton
 */
public class Main {

    /**
     * Initialises the Game setup.
     *
     * @param args there should be no args.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to ScraBBKle!");
        String input = "";
        boolean getInput = true;
        // Get the user input on whether to load a board or use the default
        while (getInput) {
            System.out.println("Would you like to _l_oad a board or use the _d_efault board?");
            System.out.print("Please enter your choice (l/d): ");
            input = System.console().readLine();
            if (!input.equals("d") && !input.equals("l")) {
                System.out.println("That is not a valid input.");
                System.out.println();
                continue;
            }
            getInput = false;
            System.out.println();
        }
        String file;
        Board board;
        // Load the Board
        do {
            if (input.equals("l")) {
                System.out.print("Please enter the file name of the board: ");
                file = System.console().readLine();
            } else {
                file = "defaultBoard.txt";
            }
            file = "../resources/" + file;
            board = ScraBBKleUtil.loadFile(file);
            if (board == null) {
                System.out.print("This is not a valid file. ");
            }
        } while (board == null);

        // Instantiate players
        LinkedList<Player> players = new LinkedList<>();
        players.add(new HumanPlayer(board));
        players.add(new ComputerPlayer(board));
        // Load the dictionary
        Dictionary.loadDictionary();
        // Run the game
        Game game = new Game(players, board);
        game.run();
    }

}
