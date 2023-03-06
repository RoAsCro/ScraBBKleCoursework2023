package pij.main;

import java.util.LinkedList;
import java.util.List;

public class Game {


	Player activePlayer;
	Board board;
	LinkedList<Player> players;

	public Game(List<Player> players, Board board){
		this.players = new LinkedList<>(players);
		this.board = board;
	}

	
	public void run() {



		Bag bag = new Bag();
		for (Player p : this.players) {
			p.draw(bag);
		}
		boolean go = true;
		int passes = 0;
		while (go) {
			activePlayer = players.poll();
			players.add(activePlayer);
			//board.print();
			Move move;
			do {
				move = activePlayer.turn(bag);
			} while (board.getStartState());

			activePlayer.removeTiles(move.getTiles());
			activePlayer.draw(bag);
			
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
			
			if ((passes >= 4) || (bag.isEmpty() && emptyRack) || checkAvailableMoves()) {
				for (Player p : players) {
					int deduction = 0;
					for (LetterTile l : p.getRack()) {
						deduction += l.getValue();
					}
					System.out.println(deduction);
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
