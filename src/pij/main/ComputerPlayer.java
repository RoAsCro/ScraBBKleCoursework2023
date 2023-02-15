package pij.main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

/**
	 * The computer player
	 * 
	 * @author Roland Crompton
	 *
	 */
public class ComputerPlayer extends Player {
		private LinkedList<Move> moves = new LinkedList<>();
		private int difficulty = 100000;
		public ComputerPlayer(Board board) {
			super(board);
		}

		@Override
	public Move turn(Bag bag) {
		moves.clear();

		for (LetterTile lt : getRack()) {
			System.out.print(lt.getChar() + ", ");
		}
		System.out.println();
		long startTime = System.nanoTime();
//		parseBoardBreadth();
//		testWordsFindCombos();
		moves = new BoardParser(getBoard(), this, false).findMoves();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println(duration);

		Move bestMove = new Move(this, getBoard());
		bestMove.validateInput(",,");
		for (Move m : moves) {
			if (m.getWord().getScore() > bestMove.getWord().getScore()) {

				bestMove = m;
			}
		}
		System.out.println(bestMove.getWord());
		System.out.println(bestMove.getWord().getScore());
		System.out.println(moves.size());
		bestMove.tryMove();

		draw(bag);
		return bestMove;
	}

//	public void parseBoardBreadth() {
//		BoardReader reader = new BoardReader(getBoard(), 'r');
//		TreeSet<ScraBBKleCoordinate> coordinates = reader.breadthFirstSearch();
//		testWordsBreadthInit(coordinates);
//	}
//
//	public void testWordsBreadthInit(TreeSet<ScraBBKleCoordinate> coordinates) {
//			BoardReader reader = new BoardReader(getBoard(), 'r');
//			while (!coordinates.isEmpty()){
//				ScraBBKleCoordinate currentCoord = coordinates.pollFirst();
//				reader.set(currentCoord);
//				for (int i = 0 ; i < 2 ; i++) {
//					//System.out.println(currentCoord);
//					Tile tile = reader.previous();
//					if (!(tile instanceof LetterTile)) {
//						reader.next();
//						testWordsBreadth(reader, new LinkedList<>(getRack()), new LinkedList<>());
//					}
//					reader.turn();
//				}
//			}
//	}
//
//
//
//	public void testWordsBreadth(BoardReader reader, LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord) {
//		//System.out.println(reader.getX() + ", " + reader.getY());
//		if (rack.isEmpty() || (moves.size() >= difficulty))
//			return;
//		BoardReader readerTwo = new BoardReader(reader);
//
//		//If the reader is currently in the middle of a word on the board, it will reverse until it reaches the beginning of the word
//		StringBuilder letters = new StringBuilder();
//
//
//
//
//
//		ScraBBKleCoordinate coordinate = new ScraBBKleCoordinate(readerTwo.getX(), readerTwo.getY());
//		for (LetterTile l : rack) {
//			//System.out.println(l);
//			LinkedList<LetterTile> newWord = new LinkedList<>(currentWord);
//			newWord.add(l);
//
//			for (int i = 0; i <= currentWord.size() + 1 ; i++) {
//				///////////////
//				Tile tile = readerTwo.previous();
//				if ((!(tile instanceof LetterTile))) {
//					readerTwo.next();
//				} else {
//					//letters.append(((LetterTile) tile).getChar());
//					readerTwo.conditionalPrevious(LetterTile.class::isInstance, (x, y) -> {});
//					readerTwo.next();
//				}
//				//////////////////
//				Move newMove = new Move(this, getBoard());
//				//System.out.println(l.getChar());
//				//HAVE THIS NOT BE INITIALISED EACH TIME
//				StringBuilder builder = new StringBuilder();
//				for (LetterTile lt : newWord) {
//					builder.append(lt.getChar());
//				}
//				builder.append(",");
//				builder.append((char) (readerTwo.getX() + 97));
//				builder.append((readerTwo.getY() + 1));
//				builder.append(",");
//				builder.append(readerTwo.getDirection());
//				//System.out.println(builder);
//
//
//				if (newMove.validateInput(builder.toString()) && newMove.checkPlacable()) {
//					//System.out.println(newMove.getWord());
//					if (Validator.lookupWord(newMove.getWord().toString())) {
//						//System.out.println("Found");
//						moves.add(newMove);
//
//					};
//
//				}
////				else
////					return;
//				readerTwo.previous();
//			}
//			readerTwo.set(coordinate);
//
//			LinkedList<LetterTile> newRack = new LinkedList<>(rack);
//			newRack.remove(l);
//			testWordsBreadth(readerTwo, newRack, newWord);
//			//
//
//		}
//		readerTwo.previous();
//
//	}
	public void testWordsFindCombos() {

		ArrayList<TreeSet<String>> words = new ArrayList<>();
		for (int i = 0; i < getRack().size() ; i++)
			words.add(new TreeSet<String>());

		allCombos(new LinkedList<>(getRack()), new StringBuilder(), words, 0);

		BoardReader reader = new BoardReader(getBoard(), 'r');
		TreeSet<ScraBBKleCoordinate> coordinates = reader.breadthFirstSearch();
		for (ScraBBKleCoordinate c : coordinates) {
			if (this.moves.size() > this.difficulty) {
				return;
			}
			testWordsWithCombos(c, words);
		}
	}

	public void allCombos(LinkedList<LetterTile> lettersInput, StringBuilder currentWordInput, ArrayList<TreeSet<String>> array, int depth){
		if (lettersInput.isEmpty())
			return;

		for (LetterTile l : lettersInput) {

			LinkedList<LetterTile> letters = new LinkedList<>(lettersInput);

			letters.remove(l);

			StringBuilder currentWord = new StringBuilder(currentWordInput);
			currentWord.append(l.getChar());
			array.get(depth).add(currentWord.toString());
			allCombos(letters, currentWord, array, depth + 1);
		}
	}

	public void testWordsWithCombos(ScraBBKleCoordinate c, ArrayList<TreeSet<String>> list) {

		BoardReader reader = new BoardReader(getBoard(), c.getX(), c.getY(), 'r');

		for (int k = 0 ; k < 2 ; k++) {

			reader.set(c);
			reader.turn();
			int offset = 0;
			int offsetInc = 0;
			Tile tile = reader.previous();

			if ((!(tile instanceof LetterTile))) {
				reader.next();
			}else
				continue;
			do {
				ArrayList<TreeSet<String>> listTwo = new ArrayList<>(list);
				tile = reader.previous();
				if ((!(tile instanceof LetterTile))) {
					reader.next();
				} else {
					reader.conditionalPrevious(LetterTile.class::isInstance, (x, y) -> {
					});
					reader.next();
				}
				StringBuilder builder = new StringBuilder();
				builder.append(",");
				builder.append((char) (reader.getX() + 97));
				builder.append(reader.getY() + 1);
				builder.append(",");
				builder.append(reader.getDirection());

				loopBlock:
				{
					ArrayList<TreeSet<String>> listThree = new ArrayList<>(listTwo);
					for (int i = offset; i < list.size(); i++) {
						for (String s : listThree.get(i)) {

							if (s.equals("")){
								continue ;
							}
							StringBuilder builderTwo = new StringBuilder(builder);
							builderTwo.insert(0, s);

							Move move = new Move(this, getBoard());

							if (move.validateInput(builderTwo.toString()) && move.checkPlacable()) {
								String moveWord = move.getWord().toString();
								if (Validator.lookupWord(moveWord)) {
									moves.add(move);
								}
							} else {
								break loopBlock;
							}
						}
					}
				}

				reader.previous();
				offset += offsetInc;
				offsetInc = 1;
			} while (offset < list.size());
		}
	}



}
