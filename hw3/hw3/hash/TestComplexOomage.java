package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdRandom;


public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    public boolean haveNiceHashCodeSpread(Set<ComplexOomage> oomages) {
        /* TODO: Write a utility function that ensures that the oomages have
         * hashCodes that would distribute them fairly evenly across
         * buckets To do this, mod each's hashCode by M = 10,
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int M = 10;
        int[] buckets = new int[M];
        for (ComplexOomage oomage: oomages) {
            int position = (oomage.hashCode() & 0x7FFFFFFF) % M;
            buckets[position]++;
            if (buckets[position] < oomages.size() / 50 || buckets[position] > oomages.size() / 2.5) {
                return false;
            }
        }

        return true;
    }


    @Test
    public void testRandomItemsHashCodeSpread() {
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    @Test
    public void testWithDeadlyParams() {
        /* TODO: Create a Set that shows the flaw in the hashCode function.
         */
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();

        for (int j = 0; j < 50; j++) {
            oomages.add(new ComplexOomage(generateDangerousParams()));
        }

        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    private List<Integer> generateDangerousParams() {
        int N = StdRandom.uniform(1, 10);
        ArrayList<Integer> params = new ArrayList<Integer>(N);
        for (int i = 0; i < N; i++) {
            params.add(StdRandom.uniform(0, 1) * 255);
        }
        return params;
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
