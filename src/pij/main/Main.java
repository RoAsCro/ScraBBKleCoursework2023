package pij.main;
import java.util.LinkedList;

public class Main {
	
	public static void main(String[] args) {
		Main main = new Main();

		boolean go = true;
		String input = "";
		Validator.loadDictionary();
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

		LinkedList<Player> players = new LinkedList<>();
		players.add(new HumanPlayer(board));
		players.add(new ComputerPlayer(board));

		Game game = new Game(players, board);
		game.run();
	}

}
