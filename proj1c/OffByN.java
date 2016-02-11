/**
 * Created by jesse on 2/10/16.
 */
public class OffByN implements CharacterComparator {

    private int N;
    public OffByN(int n){
        N = n;
    }
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == N;
    }
}
