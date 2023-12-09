package day9;
import java.io.*;
import java.util.*;

public class SequenceCalculator {

    public static void main(String[] args) {
        try {
            File file = new File("day9/in.txt");
            Scanner scanner = new Scanner(file);
            int sum = 0;

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" ");
                List<Integer> seq = new ArrayList<>();
                for (String part : parts)
                    seq.add(Integer.parseInt(part));
                    
                // sum += nextVal(seq);  // part 1
                sum += prevVal(seq);  // part 2
            }
            scanner.close();

            System.out.println(sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> nextSeq(List<Integer> seq) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < seq.size(); i++)
            result.add(seq.get(i) - seq.get(i - 1));
        return result;
    }

    private static int nextVal(List<Integer> seq) {
        if (seq.stream().allMatch(v -> v == 0))
            return 0;
        return seq.get(seq.size() - 1) + nextVal(nextSeq(seq));
    }

    private static int prevVal(List<Integer> seq) {
        if (seq.stream().allMatch(v -> v == 0))
            return 0;
        return seq.get(0) - prevVal(nextSeq(seq));
    }
}
