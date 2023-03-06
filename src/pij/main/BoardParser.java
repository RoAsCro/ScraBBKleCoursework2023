package pij.main;

import java.util.*;
import java.util.TreeSet;

public class BoardParser {
    private final Board board;

    private final List<Move> moves = new LinkedList<>();

    private final Player player;

    private final boolean findAny;

    public BoardParser(Board board, Player player, boolean findAny) {
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
    private void allCombos(LinkedList<LetterTile> lettersInput, StringBuilder currentWordInput,
                           List<TreeSet<String>> combinations, int depth){
        if (lettersInput.isEmpty())
            return;

        for (LetterTile l : lettersInput) {

            LinkedList<LetterTile> letters = new LinkedList<>(lettersInput);

            letters.remove(l);

            StringBuilder currentWord = new StringBuilder(currentWordInput);
            currentWord.append(l.getChar());
            combinations.get(depth).add(currentWord.toString());
            allCombos(letters, currentWord, combinations, depth + 1);
        }
    }

    /**
     * Finds every possible way a set combinations of String can be placed at
     * a given coordinate on the board, if that coordinate holds a LetterTile that
     * is the left- or top-most letter in a word already on the board.
     *
     * @param c the coordinate of the tile to be tested
     * @param combinations a set of all combinations of Strings to be tested
     */
    private void testWordsWithCombos(Coordinate c, List<TreeSet<String>> combinations) {

        BoardReader reader = new BoardReader(board, c, 'r');

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
                ArrayList<TreeSet<String>> newCombinations = new ArrayList<>(combinations);
                tile = reader.previous();
                if ((!(tile instanceof LetterTile))) {
                    reader.next();
                } else {
                    reader.conditionalPrevious(LetterTile.class::isInstance, (x)->{});
                    reader.next();
                }
                StringBuilder builder = new StringBuilder();
                builder.append(",");
                builder.append((char) (reader.getCoord().getX() + Move.LOWER_A_CHAR_INT));
                builder.append(reader.getCoord().getY() + 1);
                builder.append(",");
                builder.append(reader.getDirection());

                loopBlock:
                {
                    ArrayList<TreeSet<String>> listThree = new ArrayList<>(newCombinations);
                    for (int i = offset; i < combinations.size(); i++) {
                        for (String s : listThree.get(i)) {

//                            if (s.equals("")){
//                                continue ;
//                            }
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
