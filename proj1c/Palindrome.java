/**
 * Created by jesse on 2/10/16.
 */
public class Palindrome {

    public static Deque<Character> wordToDeque(String word){
        LinkedListDequeSolution<Character> characters = new LinkedListDequeSolution<Character>();
        for(int i = 0; i<word.length(); i++){
            characters.addLast(word.charAt(i));
        }
        return characters;
    }

    public static boolean isPalindrome(String word){
        if(word.length()<=1){
            return true;
        }
        else{
            char c1 = word.charAt(0);
            char c2 = word.charAt(word.length()-1);
            return (c1 == c2) && isPalindrome(word.substring(1,word.length()-1));
        }
    }

    public static boolean isPalindrome(String word, CharacterComparator cc){
        if(word.length()<=1){
            return true;
        }
        else{
            char c1 = word.charAt(0);
            char c2 = word.charAt(word.length()-1);
            return cc.equalChars(c1, c2) && isPalindrome(word.substring(1,word.length()-1), cc);
        }
    }
}
