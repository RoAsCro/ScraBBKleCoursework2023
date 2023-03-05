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
        testWordsFindCombos();
        return this.moves;
    }

    private void testWordsFindCombos() {

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

    private void allCombos(LinkedList<LetterTile> lettersInput, StringBuilder currentWordInput, ArrayList<TreeSet<String>> array, int depth){
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

    private void testWordsWithCombos(Coordinate c, ArrayList<TreeSet<String>> list) {

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
                ArrayList<TreeSet<String>> listTwo = new ArrayList<>(list);
                tile = reader.previous();
                if ((!(tile instanceof LetterTile))) {
                    reader.next();
                } else {
                    reader.conditionalPrevious(LetterTile.class::isInstance, (x) -> {
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

                            Move move = new Move(player, board);

                            if (move.validateInput(builderTwo.toString()) && move.checkPlacable()) {
                                String moveWord = move.getWord().toString();
                                if (Dictionary.lookupWord(moveWord)) {
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
            } while (offset < list.size());
        }
    }
}
