package pij.main;
import pij.main.Players.ComputerPlayer;
import pij.main.Players.HumanPlayer;

import java.util.LinkedList;

public class Main {
	
	public static void main(String[] args) {

		boolean go = true;
		String input = "";
		Dictionary.loadDictionary();
		System.out.println("Welcome to ScraBBKle!");
		while (go) {
			System.out.println("Would you like to _l_oad a board or use the _d_efault board?");
			System.out.print("Please enter your choice (l/d): ");
			input = System.console().readLine();
			if (!input.equals("d") && !input.equals("l")) {
				System.out.println("That is not a valid input.");
				System.out.println();
				continue;
			}
			go = false;
			System.out.println();
		}
		String file;
		Board board;
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

		LinkedList<Player> players = new LinkedList<>();
		players.add(new HumanPlayer(board));
		players.add(new ComputerPlayer(board));

		Game game = new Game(players, board);
		Dictionary.loadDictionary();
		game.run();
	}

}
