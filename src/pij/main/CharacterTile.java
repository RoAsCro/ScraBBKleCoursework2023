package pij.main;

public abstract class CharacterTile extends Tile {
    public CharacterTile(String tileText, int value) {
        super(tileText, value);
    }

    public abstract boolean matchChar(char c);

}
