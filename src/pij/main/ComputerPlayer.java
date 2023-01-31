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
		private int difficulty = 1000;
		public ComputerPlayer(Board board) {
			super(board);
		}

		@Override
	public Move turn(Bag bag) {
		draw(bag);
		moves.clear();

		for (LetterTile lt : getRack()) {
			System.out.print(lt.getChar() + ", ");
		}
		System.out.println();
		long startTime = System.nanoTime();
//		parseBoardBreadth();
		testWordsFindCombos();
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
		bestMove.tryMove();

		//		if (parseBoard(move)) {
		//			getBoard().placeWord(move);
		//			return move;
		//		}

		return bestMove;
	}

//	/**
//	 * Goes through every tile on the board and checks if a word can be made from it, then places the word there if valid.
//	 *
//	 * @return true if a word is successfully placed. False if not.
//	 */
//	public boolean parseBoard(Move move) {
//		BoardReader reader = new BoardReader(getBoard(), 'r');
//		if (reader.depthFirstSearch((x, y) -> {
//			if (testWords(new LinkedList<>(getRack()), new LinkedList<LetterTile>(),
//					new BoardReader(getBoard(), x, y, reader.getDirection()), move)) {
//				return true;
//			}
//			return false;
//		}))
//			return true;
//
//		return false;
//	}

//	public void parseBoardTwo() {
//		BoardReader reader = new BoardReader(getBoard(), 'r');
//		reader.depthFirstSearch((x, y) -> {
//			testWordsTwo(new LinkedList<>(getRack()), new LinkedList<LetterTile>(),
//					new BoardReader(getBoard(), x, y, reader.getDirection()));
//			return false;
//		});
//	}

//	public void parseBoardThree() {
//		BoardReader reader = new BoardReader(getBoard(), 'r');
//		StringBuilder builder = new StringBuilder();
//		for (LetterTile l : getRack()) {
//			builder.append(l.getChar());
//		}
//
//		reader.depthFirstSearch((x, y) -> {
//			testWordsThree(new LinkedList<>(getRack()), new LinkedList<LetterTile>(),
//					new BoardReader(getBoard(), x, y, reader.getDirection()), Validator.lookupRack(builder.toString()));
//			return false;
//		});
//	}



//	/**
//	 * Attempts to create a word at the target location using every combination of the tiles in the input rack.
//	 * Step 1: Check rack is empty. If so, return false.
//	 * Step 2: For each letter in the rack:
//	 * 	-add that letter to the currentWord
//	 * 	-see if that word can be placed on the board, and construct a Word object from it. If the word cannot be placed, stop looking in that direction.
//	 * 	-see if that word is in the dictionary, if so, place it on the board and return true
//	 * 	-if not in the dictionary, create a new rack that is the same is the old rack except the letter in question
//	 * 	-call testWords again
//	 * 	-move the reader backwards
//	 * 	-call testWords again
//	 * 	-if that returns false, move the reader forwards
//	 * 	-remove the letter in question from the currentWord
//	 * Step 3: return false
//	 *
//	 * @param rack
//	 * @param currentWord
//	 * @param reader
//	 * @return
//	 */
//	private boolean testWords(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader, Move move) {
//		if (rack.isEmpty())
//			return false;
//		BoardReader readerTwo = new BoardReader(reader);
//
//		//If the reader is currently in the middle of a word on the board, it will reverse until it reaches the beginning of the word
//		if ((!LetterTile.class.isInstance(readerTwo.previous()))) {
//			readerTwo.next();
//		} else {
//			readerTwo.conditionalPrevious((tile) -> {return LetterTile.class.isInstance(tile);}, (x, y) -> {});
//			readerTwo.next();
//		}
//
//		for (LetterTile l : rack) {
//			if (WildTile.class.isInstance(l)) {
//				WildTile w = (WildTile) l;
//				for (String[] letter : Bag.getAlphabet()) {
//					w.setTempText(letter[0].charAt(0));
//				}
//			}
//
//			Word word = new Word();
//			currentWord.push(l);
//			//////////////
////			StringBuilder builder = new StringBuilder();
////			String wordString = currentWord.toString();
////			wordString = wordString.replace("[", "");
////			wordString = wordString.replace(",", "");
////
////			builder.append(wordString);
////			builder.append(",");
////			builder.append((char)(readerTwo.getX() + 97));
////			builder.append((readerTwo.getY() + 1));
////			builder.append(",");
////			builder.append(readerTwo.getDirection());
////
////			if (move.validateInput(builder.toString())){
////
////			}
////				return true;
//			//////////////
//
//			//Attempt to make a word with at the current location with the current tiles
//			//If the word doesn't meet the criteria for word placement, return false
//			//If the word does meet the criteria, but is not in the dictionary, continue the loop.
//			if (getBoard().constructWord(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(),
//					new LinkedList<LetterTile>(currentWord), word)) {
//
//
//				boolean inDictionary = false;
//				//Unless I'm mistaken, the current implementation will not allow for multiple WildTile's in a word
//				if (WildTile.class.isInstance(l)) {
//					WildTile w = (WildTile) l;
//					for (String[] letter : Bag.getAlphabet()) {
//						w.setTempText(letter[0].toLowerCase().charAt(0));
//						if (Validator.lookupWord(word.toString())) {
//							inDictionary = true;
//							break;
//						} else
//							w.setTempText(' ');
//					}
//				} else
//				if (Validator.lookupWord(word.toString()))
//					inDictionary = true;
//				if (inDictionary) {
//					//activePlayer.removeTiles(currentWord);
//					move.setAll(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(), currentWord.toArray(new LetterTile[0]));
//					return true;
//				}
//			}
//			else {
//				return false;
//			}
//			//Tries placing new tiles in front of the currently constructed word, then behind
//			for (int i = 0; i < 2; i++) {
//				LinkedList<LetterTile> newRack = new LinkedList<LetterTile>(rack);
//				newRack.remove(l);
//				if (testWords(newRack, new LinkedList<LetterTile>(currentWord), reader, move)) {
//					return true;
//				}
//				reader.previous();
//			}
//			//The first next() undoes the previous() above, the pop() removes l added at the start of the loop.
//			reader.next();
//			reader.next();
//			currentWord.pop();
//		}
//		return false;
//	}


//	private void testWordsTwo(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader) {
//
//		if (rack.isEmpty() || (moves.size() >= difficulty))
//			return;
//		//This second reader is for finding where to start the word
//		BoardReader readerTwo = new BoardReader(reader);
//
//		//If the reader is currently in the middle of a word on the board, it will reverse until it reaches the beginning of the word
//		Tile tile = readerTwo.previous();
//		if ((!(tile instanceof LetterTile))) {
//			readerTwo.next();
//		} else {
//			readerTwo.conditionalPrevious(LetterTile.class::isInstance, (x, y) -> {});
//			readerTwo.next();
//		}
//
//		for (LetterTile l : rack) {
//			///_______________________________________________________________________________________________
//			//This sets it to Z for some reason?
////			if (l instanceof WildTile w) {
////				for (String[] letter : Bag.getAlphabet()) {
////					w.setTempText(letter[0].charAt(0));
////				}
////			}
//			///_______________________________________________________________________________________________
//			Move newMove = new Move(this, getBoard());
//
//			currentWord.push(l);
//
//			StringBuilder builder = new StringBuilder();
//			for (LetterTile lt : currentWord) {
//				builder.append(lt.getChar());
//			}
//			builder.append(",");
//			builder.append((char)(readerTwo.getX() + 97));
//			builder.append((readerTwo.getY() + 1));
//			builder.append(",");
//			builder.append(readerTwo.getDirection());
//			//System.out.println(builder);
//
//			if (newMove.validateInput(builder.toString()) && newMove.checkPlacable()){
//
//				if (Validator.lookupWord(newMove.getWord().toString())) {
//					moves.add(newMove);
//
//				}
//
//
//			} else {
//				//If move is not placable, stop searching here
//				return;
//			}
//
//			//Tries placing new tiles in front of the currently constructed word, then behind
//			for (int i = 0; i < 2; i++) {
//				//System.out.println("....");
//				LinkedList<LetterTile> newRack = new LinkedList<>(rack);
//				newRack.remove(l);
//				testWordsTwo(newRack, new LinkedList<LetterTile>(currentWord), reader);
//				reader.previous();
//			}
//			//The first next() undoes the previous() above, the pop() removes l added at the start of the loop.
//			reader.next();
//			reader.next();
//			currentWord.pop();
//		}
//	}

//	private void testWordsThree(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader, TreeSet<String> tree) {
//
//		if (rack.isEmpty() || (moves.size() >= difficulty))
//			return;
//		//This second reader is for finding where to start the word
//		BoardReader readerTwo = new BoardReader(reader);
//
//		//If the reader is currently in the middle of a word on the board, it will reverse until it reaches the beginning of the word
//
//		StringBuilder letters = new StringBuilder();
//		Tile tile = readerTwo.previous();
//		if ((!(tile instanceof LetterTile))) {
//			readerTwo.next();
//		} else {
//			//letters.append(((LetterTile) tile).getChar());
//			readerTwo.conditionalPrevious(LetterTile.class::isInstance, (x, y) -> {letters.append(((LetterTile) readerTwo.getCurrent()).getChar());});
//			readerTwo.next();
//		}
//		//System.out.println(letters);
//		TreeSet<String> newTree = Validator.lookupSet(letters.reverse().toString(), tree);
//		if (newTree.isEmpty())
//			return;
//
//		for (LetterTile l : rack) {
//			TreeSet<String> treeThree = new TreeSet<>(newTree);
//			//System.out.println(newTree.size());
//			///_______________________________________________________________________________________________
//			//This sets it to Z for some reason?
////			if (l instanceof WildTile w) {
////				for (String[] letter : Bag.getAlphabet()) {
////					w.setTempText(letter[0].charAt(0));
////				}
////			}
//			///_______________________________________________________________________________________________
//			Move newMove = new Move(this, getBoard());
//			//System.out.println(l.getChar());
//			currentWord.add(l);
//			//System.out.println(currentWord.toString());
//
//			StringBuilder builder = new StringBuilder();
//			for (LetterTile lt : currentWord) {
//				builder.append(lt.getChar());
//			}
//			builder.append(",");
//			builder.append((char)(readerTwo.getX() + 97));
//			builder.append((readerTwo.getY() + 1));
//			builder.append(",");
//			builder.append(readerTwo.getDirection());
//			//System.out.println(builder);
//
//			if (newMove.validateInput(builder.toString()) && newMove.checkPlacable()){
//
////				if (Validator.lookupWord(newMove.getWord().toString())) {
////					moves.add(newMove);
////
////				}
//				String word = newMove.getWord().toString();
//				//System.out.println(word);
//				if (treeThree.contains(word.toLowerCase())) {
//					//System.out.println("Yes");
//					moves.add(newMove);
//				}
//				treeThree = Validator.lookupSet(word, treeThree);
//				if ((treeThree.isEmpty())) {
//					//System.out.println("Continue");
//					reader.next();
//					currentWord.pollLast();
//					continue;
//				}
////				if (x == 0) {
////					System.out.println("S: " + newMove.getWord().toString());
////					moves.add(newMove);
////				} else if (x == -1) {
////					continue;
////				} else
////					System.out.println(newMove.getWord().toString());
//
//			} else {
//				//If move is not placable, stop searching here
//				return;
//			}
//
//
//
//			//Tries placing new tiles in front of the currently constructed word, then behind
//			for (int i = 0; i < 2; i++) {
//				//System.out.println("....");
//				LinkedList<LetterTile> newRack = new LinkedList<>(rack);
//				newRack.remove(l);
//				testWordsThree(newRack, new LinkedList<LetterTile>(currentWord), reader, treeThree);
//				reader.previous();
//			}
//			//The first next() undoes the previous() above, the pop() removes l added at the start of the loop.
//			reader.next();
//			reader.next();
//			currentWord.pollLast();
//		}
//	}


	public void parseBoardBreadth() {
		BoardReader reader = new BoardReader(getBoard(), 'r');
		TreeSet<ScraBBKleCoordinate> coordinates = reader.breadthFirstSearch();
		//long startTime = System.nanoTime();
		testWordsBreadthInit(coordinates);
//		long endTime = System.nanoTime();
//		long duration = (endTime - startTime);
		//System.out.println(duration);


	}

	public void testWordsBreadthInit(TreeSet<ScraBBKleCoordinate> coordinates) {
			BoardReader reader = new BoardReader(getBoard(), 'r');
			while (!coordinates.isEmpty()){
				ScraBBKleCoordinate currentCoord = coordinates.pollFirst();
				reader.set(currentCoord);
				for (int i = 0 ; i < 2 ; i++) {
					//System.out.println(currentCoord);
					Tile tile = reader.previous();
					if (!(tile instanceof LetterTile)) {
						reader.next();
						testWordsBreadth(reader, new LinkedList<>(getRack()), new LinkedList<>());
					}
					reader.turn();
				}
			}
	}



	public void testWordsBreadth(BoardReader reader, LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord) {
		//System.out.println(reader.getX() + ", " + reader.getY());
		if (rack.isEmpty() || (moves.size() >= difficulty))
			return;
		BoardReader readerTwo = new BoardReader(reader);

		//If the reader is currently in the middle of a word on the board, it will reverse until it reaches the beginning of the word
		StringBuilder letters = new StringBuilder();





		ScraBBKleCoordinate coordinate = new ScraBBKleCoordinate(readerTwo.getX(), readerTwo.getY());
		for (LetterTile l : rack) {
			//System.out.println(l);
			LinkedList<LetterTile> newWord = new LinkedList<>(currentWord);
			newWord.add(l);

			for (int i = 0; i <= currentWord.size() + 1 ; i++) {
				///////////////
				Tile tile = readerTwo.previous();
				if ((!(tile instanceof LetterTile))) {
					readerTwo.next();
				} else {
					//letters.append(((LetterTile) tile).getChar());
					readerTwo.conditionalPrevious(LetterTile.class::isInstance, (x, y) -> {});
					readerTwo.next();
				}
				//////////////////
				Move newMove = new Move(this, getBoard());
				//System.out.println(l.getChar());
				//HAVE THIS NOT BE INITIALISED EACH TIME
				StringBuilder builder = new StringBuilder();
				for (LetterTile lt : newWord) {
					builder.append(lt.getChar());
				}
				builder.append(",");
				builder.append((char) (readerTwo.getX() + 97));
				builder.append((readerTwo.getY() + 1));
				builder.append(",");
				builder.append(readerTwo.getDirection());
				//System.out.println(builder);


				if (newMove.validateInput(builder.toString()) && newMove.checkPlacable()) {
					//System.out.println(newMove.getWord());
					if (Validator.lookupWord(newMove.getWord().toString())) {
						//System.out.println("Found");
						moves.add(newMove);

					};

				}
//				else
//					return;
				readerTwo.previous();
			}
			readerTwo.set(coordinate);

			LinkedList<LetterTile> newRack = new LinkedList<>(rack);
			newRack.remove(l);
			testWordsBreadth(readerTwo, newRack, newWord);
			//

		}
		readerTwo.previous();

	}

	public void testWordsFindCombos() {

		ArrayList<TreeSet<String>> words = new ArrayList<>();
		for (int i = 0; i < getRack().size() ; i++)
			words.add(new TreeSet<String>());

		allCombos(new LinkedList<>(getRack()), new StringBuilder(), words, 0);

		BoardReader reader = new BoardReader(getBoard(), 'r');
		TreeSet<ScraBBKleCoordinate> coordinates = reader.breadthFirstSearch();
		//testWordsBreadthInit(coordinates);
		for (ScraBBKleCoordinate c : coordinates) {
//			System.out.println(this.moves.size());
//			System.out.println(c);
			if (this.moves.size() > this.difficulty) {
				return;
			}
			//System.out.println(c);
			testWordsWithCombos(c, words);
		}
		//System.out.println("SIZE= " + moves.size());
//		for (Move m : moves) {
//			System.out.print(m.);
//		}


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
				ArrayList<TreeSet<String>> listTwo = new ArrayList<TreeSet<String>>(list);
				//System.out.println( c + "::" + reader.getX() + ", " + reader.getY());
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

						//System.out.println(builderTwo);

							Move move = new Move(this, getBoard());

							if (move.validateInput(builderTwo.toString()) && move.checkPlacable()) {
								String moveWord = move.getWord().toString();
								if (Validator.lookupWord(moveWord)) {
									moves.add(move);
								}
//								else if (!Validator.lookupPrefix(moveWord)) {
//									for (int l = i + 1; l < list.size() - 4; l++) {
//										TreeSet<String> currentTree = listThree.get(l);
//										TreeSet<String> head = new TreeSet<>(currentTree.headSet(s));
//										TreeSet<String> tail = new TreeSet<>(currentTree.tailSet(s + "za"));
//										head.addAll(tail);
//										listThree.set(l, head);
//										//System.out.println(l + ": " + listThree.get(l).size());
//									}
//								}
//								if (Validator.lookupWord(moveWord)) {
//									moves.add(move);
//
//								}

							} else {
								//System.out.println(".........");
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
