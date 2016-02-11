import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDeque1B{

	@Test
	public void testRandom(){
		Integer repeat = 500;
		Integer random;

		StudentArrayDeque<Integer> ints = new StudentArrayDeque<Integer>();
		ArrayDequeSolution<Integer> realInts = new ArrayDequeSolution<Integer>();
		FailureSequence sequence = new FailureSequence();
		for(Integer i = 0; i<repeat; i++){
			random = StdRandom.uniform(100);
			if(ints.isEmpty()){

				boolean solution = realInts.isEmpty();
				boolean student = ints.isEmpty();
				assertEquals(sequence.toString(), solution, student);
			}
			if(random > 75){
				ints.addFirst(random);
				realInts.addFirst(random);
				sequence.addOperation(new DequeOperation("addFirst", random));

				Integer solution = realInts.get(0);
				Integer student = ints.get(0);
				assertEquals(sequence.toString(), solution, student);

				int s = realInts.size();
				int s2 = ints.size();
				sequence.addOperation(new DequeOperation("size"));
				assertEquals(sequence.toString(), s, s2);
			}
			else if(random > 50 && random <= 75){
				ints.addLast(random);
				realInts.addLast(random);
				sequence.addOperation(new DequeOperation("addLast", random));

				Integer solution = realInts.get(realInts.size()-1);
				Integer student = ints.get(ints.size()-1);
				assertEquals(sequence.toString(), solution, student);

				int s = realInts.size();
				int s2 = ints.size();
				sequence.addOperation(new DequeOperation("size"));
				assertEquals(sequence.toString(), s, s2);
			}
			else if(random > 25 && random <= 50){

				Integer solution = realInts.removeFirst();
				Integer student = ints.removeFirst();
				sequence.addOperation(new DequeOperation("removeFirst"));
				assertEquals(sequence.toString(), solution, student);

				int s = realInts.size();
				int s2 = ints.size();
				sequence.addOperation(new DequeOperation("size"));
				assertEquals(sequence.toString(), s, s2);
			}
			else{
				Integer solution = realInts.removeLast();
				Integer student = ints.removeLast();
				sequence.addOperation(new DequeOperation("removeLast"));
				assertEquals(sequence.toString(), solution, student);

				int s = realInts.size();
				int s2 = ints.size();
				sequence.addOperation(new DequeOperation("size"));
				assertEquals(sequence.toString(), s, s2);
			}

		}
	}
	public static void main(String[] args){
		jh61b.junit.TestRunner.runTests(TestArrayDeque1B.class);
	}
}