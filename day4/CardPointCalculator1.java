package day4;
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class CardPointCalculator1 {

    public static int calculateCardPoints(Set<Integer> winningNums, Set<Integer> yourNums) {
        Set<Integer> matches = new HashSet<>(winningNums);
        matches.retainAll(yourNums);
        if (matches.size() > 0)
            return (int) Math.pow(2, matches.size() - 1);
        return 0;
    }

    public static int processFile(String fileName) {
        int totalPoints = 0;

        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.trim().split("\\|");
                String[] winningNumStrings = parts[0].split(":")[1].trim().split("\\h+");
                String[] yourNumStrings = parts[1].trim().split("\\h+");

                Set<Integer> winningNums = Arrays.stream(winningNumStrings).map(Integer::parseInt)
                    .collect(Collectors.toSet());
                Set<Integer> yourNums = Arrays.stream(yourNumStrings).map(Integer::parseInt)
                    .collect(Collectors.toSet());

                totalPoints += calculateCardPoints(winningNums, yourNums);
            }

        } catch (FileNotFoundException e) {}

        return totalPoints;
    }

    public static void main(String[] args) {
        String fileName = "day4/in.txt";
        System.out.println(processFile(fileName));
    }
}
