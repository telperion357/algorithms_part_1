import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/*
 <b>Using algs4.jar.</b>
 Write a program RandomWord.java that reads a sequence of words from standard input
 and prints one of those words uniformly at random. Do not store the words in an array or list.
 Instead, use Knuth’s method: when reading the ith word, select it with probability 1/i to be the champion,
 replacing the previous champion. After reading all of the words, print the surviving champion.
 */
public class RandomWord {
    public static void main(String[] args) {
        String championWord = "";
        int i = 1;
        while (!StdIn.isEmpty()) {
            String currentWord = StdIn.readString();
            double p = 1.0/i;

            // Substitute the champion with current word with probability 1/i
            if (StdRandom.bernoulli(p)) {
                championWord = currentWord;
            }
            i++;
        }
        System.out.println(championWord);
    }
}
