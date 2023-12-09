package day4;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Scanner;

public class CardPointCalculator2 {

    static class Card {
        Set<Integer> winningNums;
        Set<Integer> yourNums;

        public Card(Set<Integer> winningNums, Set<Integer> yourNums) {
            this.winningNums = winningNums;
            this.yourNums = yourNums;
        }
    }

    public static List<Card> processFile(String fileName) {
        List<Card> cards = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                String[] parts = line.trim().split("\\|");
                String[] winningNumStrings = parts[0].split(":")[1].trim().split("\\s+");
                String[] yourNumStrings = parts[1].trim().split("\\s+");

                Set<Integer> winningNums = Arrays.stream(winningNumStrings).map(Integer::parseInt)
                    .collect(Collectors.toSet());
                Set<Integer> yourNums = Arrays.stream(yourNumStrings).map(Integer::parseInt)
                    .collect(Collectors.toSet());

                cards.add(new Card(winningNums, yourNums));
            }

        } catch (FileNotFoundException e) {}

        return cards;
    }

    public static int processCards(List<Card> cards) {
        int[] totalCards = new int[cards.size()];
        Arrays.fill(totalCards, 1);

        for (int c = 0; c < totalCards.length; c++) {
            int currCardCount = totalCards[c];
            Card card = cards.get(c);
            int matches = (int) card.winningNums.stream().filter(card.yourNums::contains).count();

            if (matches > 0 && c + matches < totalCards.length) {
                for (int j = 1; j <= matches; j++) {
                    totalCards[c + j] += currCardCount;
                }
            }
        }

        return Arrays.stream(totalCards).sum();
    }

    public static void main(String[] args) {
        String fileName = "day4/in.txt";
        List<Card> cards = processFile(fileName);
        System.out.println(processCards(cards));
    }
}
