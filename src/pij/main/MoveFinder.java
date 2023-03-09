package pij.main;

import java.util.*;
import java.util.TreeSet;

public class MoveFinder {
    private final Board board;

    private final List<Move> moves = new LinkedList<>();

    private final Player player;

    private final boolean findAny;

    public MoveFinder(Board board, Player player, boolean findAny) {
        this.board = board;
        this.player = player;
        this.findAny = findAny;
    }

    public List<Move> findMoves(){
        findAllMoves();
        return this.moves;
    }


    private void findAllMoves() {

        ArrayList<TreeSet<String>> words = new ArrayList<>();
        for (int i = 0; i < player.getRack().size() ; i++)
            words.add(new TreeSet<String>());

        allCombos(new LinkedList<>(player.getRack()), new StringBuilder(), words, 0);

        BoardReader reader = new BoardReader(board, 'r');
        TreeSet<Coordinate> coordinates = reader.breadthFirstSearch();
        for (Coordinate c : coordinates) {
            if (findAny && this.moves.size() > 0) {
                return;
            }
            testWordsWithCombos(c, words);
        }
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
     * Finds every possible way a set combinations of String can be placed at
     * a given coordinate on the board, if that coordinate holds a CharacterTile that
     * is the left- or top-most letter in a word already on the board.
     *
     * @param c the coordinate of the tile to be tested
     * @param combinations a set of all combinations of Strings to be tested
     */
    private void testWordsWithCombos(Coordinate c, List<TreeSet<String>> combinations) {

        BoardReader reader = new BoardReader(board, c, 'r');
        // For loop ensures both down and right directions are checked
        for (int k = 0 ; k < 2 ; k++) {

            reader.set(c);
            reader.turn();
            // Check letter at this coordinate has no letters behind it
            AbstractBoardTile tile = reader.previous();
            if ((!(tile instanceof CharacterTile))) {
                reader.next();
            }else
                continue;
            int offset = 0;
            int offsetInc = 0;
            do {
//                ArrayList<TreeSet<String>> newCombinations = new ArrayList<>(combinations);
                // Check there are no letters behind this one. If there are, go backwards until
                // it reaches the back-most letter
                tile = reader.previous();
                if ((!(tile instanceof CharacterTile))) {
                    reader.next();
                } else {
                    reader.conditionalPrevious(CharacterTile.class::isInstance, x->{});
                    reader.next();
                }
                StringBuilder builder = new StringBuilder();
                builder.append(",")
                        .append(reader.getCoord().toString())
                        .append(",")
                        .append(reader.getDirection());

                loopBlock:
                {
                    // Begin trying every combination of letters at the coordinate
//                    ArrayList<TreeSet<String>> listThree = new ArrayList<>(newCombinations);
                    for (int i = offset; i < combinations.size(); i++) {
                        for (String s : combinations.get(i)) {
                            StringBuilder builderTwo = new StringBuilder(builder);
                            builderTwo.insert(0, s);

                            Move move = new Move(player, board);
                            if (move.validateInput(builderTwo.toString()) && move.checkPlacable()) {
                                if (move.lookupWord()) {
                                    moves.add(move);
                                    if (findAny)
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
