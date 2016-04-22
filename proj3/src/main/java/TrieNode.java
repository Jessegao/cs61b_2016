import java.util.HashMap;

/**
 * Created by Jesse on 4/18/2016.
 */
public class TrieNode {
    public char getCharacter() {
        return character;
    }

    public HashMap<Character, TrieNode> getBranches() {
        return branches;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    private char character;
    private HashMap<Character, TrieNode> branches = new HashMap<Character, TrieNode>();
    private boolean isLeaf;

    public TrieNode() {}

    public TrieNode(char c){
        character = c;
    }
}
