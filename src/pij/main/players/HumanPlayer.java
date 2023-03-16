package pij.main.players;

import pij.main.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The human implementation of Player. Takes a console input to make a turn.
 *
 * @author Roland Crompton
 */
public class HumanPlayer extends Player {

    /**
     * A HumanPlayer's name is "Human".
     */
    private static final String NAME = "Human";

    /**
     * Constructs a new HumanPlayer with the given Board.
     *
     * @param board the Board the HumanPlayer will make moves on
     */
    public HumanPlayer(Board board) {
        super(board);
    }

    /**
     * Returns the HumanPlayer's name "Human".
     *
     * @return the HumanPlayer's name
     */
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Prints the Player's tile rack.
     */
    private void printRack() {
        System.out.println(getRack().stream()
                .map(t -> "[" + t.toString() + "]")
                .collect(Collectors.joining(", ")));
    }

	/**
	 * The HumanPlayer's turn. Prints the player's tile rack and the Board on the console
	 * then requests a move, explaining the format it needs to be in.
	 * Then takes the move as an input from the user and tries to translate that into a valid
	 * Move. If it cannot, requests the Move again.
	 *
	 * @return the valid Move the user makes
	 */
    @Override
    public Move turn() {
        String input;
        Move move = new Move(getBoard());
        do {
            getBoard().print();
            System.out.println("It's your turn! Your tiles:");
            printRack();
            System.out.println("Please enter your move with letter sequence, position, "
                    + "and direction(d for down, r for right) separated by commas. "
                    + "Entering just two commas passes.");
            input = System.console().readLine();

        } while (
                !(validateInput(input, move) &&
                        move.tryMove()));
        return move;
    }

    /**
     * Checks if the input String has been formatted correctly and uses Tiles the Player
     * actually has. Then sets this Move's fields accordingly.
     *
     * @param input the String representing the move the Player wants to make
     * @return true if the input String is valid, false otherwise
     */
    public boolean validateInput(String input, Move move) {
        if (input.equals(",,")) {
            return true;
        }

        String[] movesToTest = input.split(",");

        if (movesToTest.length != 3) {
            return false;
        }
        String letters = movesToTest[0];
        String location = movesToTest[1];
        String direction = movesToTest[2];

        if (location.length() < 2 || location.length() > 3) {
            return false;
        }

        String x = location.substring(0, 1);
        String y = location.substring(1);

        int yLength = y.length();
        // Check the x coordinate is a letter, th y coordinate is a number,
        // the direction is either right or down, and there is at least one letter being played
        if (!Character.isLetter(x.charAt(0)) || !Character.isDigit(y.charAt(0))
                || !Character.isDigit(y.charAt(yLength - 1))
                || (!direction.equals("r") && !direction.equals("d"))
                || letters.length() < 1) {
            return false;
        }
        char[] chars = letters.toCharArray();
        // Check the player has the required tiles
        ArrayList<CharacterTile> moveTiles = new ArrayList<>();
        ArrayList<CharacterTile> playerRack = new ArrayList<>(getRack());
        for (char c : chars) {
            CharacterTile characterTile = playerRack.stream().filter(t -> t.matchChar(c)).findFirst().orElse(null);
            if (characterTile == null) {
                System.out.println("X");
                return false;
            }
            playerRack.remove(characterTile);
            moveTiles.add(characterTile);
        }

        move.setAll(new Coordinate(x.charAt(0), Integer.parseInt(y)), direction.charAt(0), moveTiles);
        return true;
    }

}
