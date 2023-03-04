package pij.main;

import java.util.LinkedList;
import java.util.List;

public class Game {
	
	int passes = 0;
	Player activePlayer;
	Board board;
	Bag bag;
	LinkedList<Player> players;

	public Game(List<Player> players, Board board){
		this.players = new LinkedList<>(players);
		this.board = board;
	}

	
	public void run() {
		boolean go = true;
		bag = new Bag();

		for (Player p : this.players) {
			p.draw(bag);
		}

		while (go) {
			activePlayer = players.poll();
			players.add(activePlayer);
			//board.print();
			Move move;
			do {
				move = activePlayer.turn(this.bag);
			} while (board.getStartState());

			activePlayer.removeTiles(move.getTiles());
			activePlayer.draw(this.bag);
			
			System.out.println();
			if (move.isPass()) {
				passes++;
				System.out.println("Player passed their turn.");
			} else {
				passes = 0;
				System.out.println();
				System.out.println("The result is: ");
			}
			boolean emptyRack = false;
			for (Player p : players) {
				System.out.println(p.getName() + " player score: " + p.getScore());
				if (p.getRack().isEmpty())
					emptyRack = true;
			}
			
			if ((passes >= 4) || (this.bag.isEmpty() && emptyRack) || checkAvailableMoves()) {
				for (Player p : players) {
					int deduction = 0;
					for (LetterTile l : p.getRack()) {
						deduction += l.getValue();
					}
					p.updateScore(-deduction);
				}
				go = false;
			}
		}
		System.out.println(checkVictory());
	}

	private boolean checkAvailableMoves(){
		boolean noMoves = true;
		for (Player p : this.players) {
			if (new BoardParser(this.board, this.activePlayer, true).findMoves().size() != 0)
				noMoves = false;
			break;
		}
		return noMoves;
	}

	public String checkVictory() {
		Player playerOne = players.get(0);
		Player playerTwo = players.get(1);
		double scoreOne = playerOne.getScore();
		double scoreTwo = playerTwo.getScore();
		String winningPlayerName;

		if (scoreOne == scoreTwo) {
			return "It's a draw!";
		}
		else if (scoreOne > scoreTwo) {
			winningPlayerName = playerOne.getName().toLowerCase();
		}else
			winningPlayerName = playerTwo.getName().toLowerCase();
		return ("The " + winningPlayerName + " player wins!");
	}
}
