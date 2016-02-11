/**
 * Created by jesse on 2/10/16.
 */
public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char first, char last){
        return Math.abs(first - last) == 1;
    }
}
