package pij.main;

import java.util.LinkedList;
import java.util.List;

/**
 * A game of ScraBBKle to be played on the console.
 * A Game should be instantiated with the Board and Dictionary already loaded.
 *
 * @author Roland Crompton
 */
public class Game {

	/**
	 * The Board on which the Game will be played.
	 */
	private final Board board;
	/**
	 * The list of Players playing the game.
	 */
	private final LinkedList<Player> players;

	/**
	 * Constructor for the Game. Takes a List of Players playing the Game and a Board
	 * on which the Game is to be played. The first Player in players should be a HumanPlayer.
	 *
	 * @param players a List of Players playing the Game
	 * @param board a Board on which the Game will be played
	 */
	public Game(List<Player> players, Board board){
		this.players = new LinkedList<>(players);
		this.board = board;
	}

	/**
	 * Checks if any Player has available moves.
	 *
	 * @return whether it's possible for any Player to make a move
	 */
	private boolean checkAvailableMoves(){
		boolean movesPossible = false;
		for (Player p : this.players) {
			if (MoveFinder.findMoves(this.board, p.getRack(), true).size() != 0)
				movesPossible = true;
			break;
		}
		return movesPossible;
	}

	/**
	 * Checks who the Game and returns a String according to the result.
	 * The Player with the highest score wins. If this is neither Player, it is a draw.
	 *
	 * @return a String stating which Player, if any, won
	 */
	public String checkVictory() {
		String returnString = this.players.get(0).getName();
		int highestScore = this.players.get(0).getScore() - 1;

		for (Player p : this.players) {
			int score = p.getScore();
			String playerName = p.getName().toLowerCase();
			System.out.println("The " + playerName
					+ " player scored " + score + " points.");
			if (score == highestScore) {
				returnString = "It's a draw!";
			}
			else if (score > highestScore) {
				highestScore = score;
				returnString = "The " + playerName + " player wins!";
			}
		}
		return returnString;
	}

	/**
	 * Runs the game. Players will take it in turns to make moves, the console displaying
	 * the details of each move between each move.
	 * <p></p>
	 * <p></p>
	 * The game will be played until one of the following conditions is met:
	 * <p></p>
	 * The Bag is empty and a player has no Tiles in their rack; All players have passed twice;
	 * There are no moves available for either Player.
	 */
	public void run() {
		// have each player draw tiles
		Bag bag = new Bag();
		for (Player p : this.players) {
			p.draw(bag);
		}
		boolean go = true;
		int passes = 0;
		// Main loop of the game as it runs
		while (go) {
			Player activePlayer = this.players.poll();
			this.players.add(activePlayer);
			Move move;
			// Loop ensures the HumanPlayer cannot pass on the first move
			do {
				move = activePlayer.turn();
			} while (this.board.getStartState());

			// Remove the active player's played tiles and have them draw
			activePlayer.removeTiles(move.getTiles());
			activePlayer.draw(bag);

			// Display the result of the move
			System.out.println();
			if (move.isPass()) {
				passes++;
				System.out.println(activePlayer.getName() + " player passed their turn.");
			} else {
				passes = 0;
				activePlayer.updateScore(move.getWord().getScore());
				System.out.println(move);
				System.out.println("The result is: ");
			}

			// Check for the end of the Game
			boolean emptyRack = false;
			for (Player p : this.players) {
				System.out.println(p.getName() + " player score: " + p.getScore());
				if (p.getRack().isEmpty())
					emptyRack = true;
			}

			if ((passes >= this.players.size() * 2) || (bag.isEmpty() && emptyRack) || !checkAvailableMoves()) {
				System.out.println("Game Over!");
				// Deduct points equal to the value of all Tiles in each Player's rack
				for (Player p : this.players) {
					int deduction = 0;
					for (CharacterTile l : p.getRack()) {
						deduction += l.getValue();
					}
					p.updateScore(-deduction);
				}
				go = false;
			}
		}
		System.out.println(checkVictory());
	}

}
