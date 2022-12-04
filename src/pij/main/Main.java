package pij.main;

import java.io.*;

public class Main {
	
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	
	public void run() {
		boolean go = true;
		String input = "";
		Bag bag = new Bag();
		
		System.out.println("Welcome to ScraBBKle!");
		while (go) {
			System.out.println("Would you like to _l_oad a board or use the _d_efault board?");
			System.out.print("Please enter your choice (l/d): ");
			input = System.console().readLine();
			if (!Validator.inputValidation(input, new String[]{"d", "l"})) {
				System.out.println("That is not a valid input.");
				System.out.println();
				continue;
			}
			go = false;
			System.out.println();
		}
		String file = "";
		Board board = null;
		
		do {
			if (input.equals("l")) {
				System.out.print("Please enter the file name of the board: ");
				file = System.console().readLine();
			} else {
				file = "defaultBoard.txt";
			}
			file = "../resources/" + file;
			board = Validator.loadFile(file);
		} while (board == null);
		
		HumanPlayer human = new HumanPlayer();
		ComputerPlayer computer = new ComputerPlayer();
		Player[] players = new Player[] {human, computer};
		int currentPlayer = 0;
		Player activePlayer;
		go = true;
		while (go) {
			activePlayer = players[currentPlayer];
			board.print();
			Move move;
			do {
				move = activePlayer.turn(bag);
			} while (!board.placeWord(move));
			
			activePlayer.removeTiles(move.getTiles());
			
			System.out.println();
			if (move.isPass()) {
				System.out.println("Player passed their turn.");
			} else {
			
				String direction = "right";	
				if (move.getDirection() == 'd')
					direction = "down";
				 
				System.out.println("The move is:	Word: " + move.getTiles().toString() + " at position "
						+ (char) (move.getX() + 97) + (move.getY() + 1) + ", direction: " + direction);
				System.out.println();
				System.out.println("The result is: ");
				
			}
			
			System.out.println("Human player score:	" + human.getScore());
			System.out.println("Computer player score: " + computer.getScore());
			System.out.println();
			
			currentPlayer = Math.abs(currentPlayer - 1);
		}
			
	}
	

	
}
