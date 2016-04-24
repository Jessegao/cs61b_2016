/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra
 * @version 1.4 - April 14, 2016
 *
 **/
public class RadixSort
{

    /**
     * Does Radix sort on the passed in array with the following restrictions:
     *  The array can only have ASCII Strings (sequence of 1 byte characters)
     *  The sorting is stable and non-destructive
     *  The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     **/
    public static String[] sort(String[] asciis)
    {
        String[] strings = asciis.clone();

        //find max length
        int stringLength = 0;
        for (String s : strings) {
            if (s.length() > stringLength) {
                stringLength = s.length();
            }
        }

        sortHelp(strings, stringLength);
        return strings;
    }

    /**
     * Radix sort helper function that recursively calls itself to achieve the sorted array
     *  destructive method that changes the passed in array, asciis
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelper(String[] asciis, int start, int end, int index)
    {
        //TODO use if you want to
    }

    public static void sortHelp(String[] a, int W) {
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];

        for (int d = 0; d < W; d++) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R+1];
            for (int i = 0; i < N; i++)
                count[getInt(a[i], d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // move data
            for (int i = 0; i < N; i++)
                aux[count[getInt(a[i], d)]++] = a[i];

            // copy back
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }

    private static int getInt(String s, int d) {
        if (d > s.length() - 1) {
            return 0;
        } else {
            return (int) s.charAt(d);
        }
    }

    public static void main(String[] strings) {
        String[] strings1 = new String[] {"awesome", "dunnard", "bootywarrior", "ham", "bolster", "nice"};
        String[] str = sort(strings1);
        for (String s : str) {
            System.out.println(s);
        }
    }
}
