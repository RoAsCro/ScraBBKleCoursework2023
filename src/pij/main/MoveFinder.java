package pij.main;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Class for finding Moves on ScraBBKle Board.
 * It can be set to find any Move or find all Moves available to a given Player.
 * Assumes that all words placed on a Board intersect with one another according to the rules
 * of ScraBBKle.
 *
 * @author Roland Crompton
 */
public class MoveFinder {
    /**
     * The Board to search or available Moves.
     */
    private final Board board;

    /**
     * The MoveFinder's mode. If true, the MoveFinder will find exactly one Move. If false,
     * it will find all possible Moves.
     */
    private final boolean findAny;

    /**
     * A Linked List containing the Moves the MoveFinder finds.
     */
    private final List<Move> moves = new LinkedList<>();

    /**
     * The set of Tiles drawn on to search for available Moves
     */
    private final List<CharacterTile> playerTiles;


    public MoveFinder(Board board, List<CharacterTile> playerTiles, boolean findAny) {
        this.playerTiles = playerTiles;
        this.board = board;
//        this.player = player;
        this.findAny = findAny;
    }

    /**
     * Recursively finds all possible String combinations that can be constructed with the
     * player's tile rack.
     *
     * @param lettersInput initially a player's tile rack
     * @param currentWordInput initially an empty StringBuilder
     * @param combinations the List of TreeSets where the combinations found will be stored
     * @param depth the current depth of recursion. Initially 0. Should not exceed the size of
     *              combinations
     */
    private void allCombos(LinkedList<CharacterTile> lettersInput, StringBuilder currentWordInput,
                           List<TreeSet<String>> combinations, int depth){
        if (lettersInput.isEmpty())
            return;

        for (CharacterTile l : lettersInput) {

            LinkedList<CharacterTile> letters = new LinkedList<>(lettersInput);

            letters.remove(l);

            StringBuilder currentWord = new StringBuilder(currentWordInput);
            currentWord.append(l.getChar());
            combinations.get(depth).add(currentWord.toString());
            allCombos(letters, currentWord, combinations, depth + 1);
        }
    }

    /**
     * Finds one or all Moves playable on the Board with the given Player's Tiles.
     * Finds every combination of letter possible with a Player's rack,
     * then finds every letter on the Board and attempts to place every letter combination there.
     */
    public List<Move> findMoves() {

        ArrayList<TreeSet<String>> words = new ArrayList<>();
        for (int i = 0; i < this.playerTiles.size() ; i++)
            words.add(new TreeSet<>());

        allCombos(new LinkedList<>(this.playerTiles), new StringBuilder(), words, 0);

        BoardReader reader = new BoardReader(this.board, this.board.getCentre(), 'r');
        SortedSet<Coordinate> coordinates = reader.breadthFirstSearch();
        for (Coordinate c : coordinates) {
            if (this.findAny && this.moves.size() > 0) {
                break;
            }
            testWordsWithCombos(c, words);
        }
        return this.moves;
    }

    /**
     * Finds every possible way a set combinations of letters can be placed at
     * a given coordinate on the board, if that coordinate holds a CharacterTile that
     * is the left- or top-most letter in a word already on the board.
     *
     * @param c the Coordinate of the Tile to be tested
     * @param combinations a set of all combinations of letters to be tested
     */
    private void testWordsWithCombos(Coordinate c, List<TreeSet<String>> combinations) {

        BoardReader reader = new BoardReader(this.board, c, 'r');
        // For loop ensures both down and right directions are checked
        for (int k = 0 ; k < 2 ; k++) {

            reader.setCoordinate(c);
            reader.turn();
            // Check letter at this coordinate has no letters behind it
            BoardTile tile = reader.previous();
            if ((!(tile instanceof CharacterTile))) {
                reader.next();
            }else
                continue;
            // offset determines how far back from the initial Tile is currently being tested
            int offset = 0;
            int offsetInc = 0;
            do {
                // Check there are no letters behind this one. If there are, go backwards until
                // it reaches the back-most letter
                tile = reader.previous();
                if ((!(tile instanceof CharacterTile))) {
                    reader.next();
                } else {
                    reader.conditionalPrevious(CharacterTile.class::isInstance, x->{});
                    reader.next();
                }
                loopBlock:
                {
                    // Begin trying every combination of letters at the coordinate
                    for (int i = offset; i < combinations.size(); i++) {
                        for (String s : combinations.get(i)) {
                            Move move = new Move(this.board);
                            List<CharacterTile> moveTiles = new LinkedList<>();
                            IntStream.range(0, s.length())
                                    .forEach(sub->Bag.generateTiles(s.substring(sub, sub+1), moveTiles, 1));
                            move.setAll(reader.getCoord(), reader.getDirection(), moveTiles);
                            if (move.checkPlacable()) {
                                if (move.getWord().lookupWord()) {
                                    this.moves.add(move);
                                    if (this.findAny)
                                        return;
                                }
                            } else {
                                // If a combination of letters isn't placable, stop trying
                                // with anything the size of current combo or larger
                                break loopBlock;
                            }
                        }
                    }
                }
                reader.previous();
                offset += offsetInc;
                offsetInc = 1;
            } while (offset < combinations.size());
        }
    }
}
