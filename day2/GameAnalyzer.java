package day2;
import java.io.*;
import java.util.*;

public class GameAnalyzer {

    public static void main(String[] args) {
        String fileName = "day2/in.txt";
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            Map<Integer, List<Map<String, Integer>>> games = parseInput(scanner);
            
            int sum = 0;
            for (Map.Entry<Integer, List<Map<String, Integer>>> entry : games.entrySet()) {
                if (isGamePossible(entry.getValue()))
                    sum += entry.getKey();
            }
            System.out.println(sum);
            
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    private static Map<Integer, List<Map<String, Integer>>> parseInput(Scanner scanner) {
        Map<Integer, List<Map<String, Integer>>> games = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(": ");
            int gameId = Integer.parseInt(parts[0].split(" ")[1]);
            String[] setsRaw = parts[1].split("; ");
            List<Map<String, Integer>> sets = new ArrayList<>();

            for (String setRaw : setsRaw) {
                Map<String, Integer> setFin = new HashMap<>();
                String[] draws = setRaw.split(", ");
                for (String draw : draws) {
                    String[] s = draw.split(" ");
                    String color = s[1].trim();
                    int num = Integer.parseInt(s[0].trim());
                    setFin.put(color, num);
                }
                sets.add(setFin);
            }
            games.put(gameId, sets);
        }
        return games;
    }

    private static boolean isGamePossible(List<Map<String, Integer>> game) {
        for (Map<String, Integer> draw : game) {
            if (draw.containsKey("red") && draw.get("red") > 12)
                return false;
            if (draw.containsKey("green") && draw.get("green") > 13)
                return false;
            if (draw.containsKey("blue") && draw.get("blue") > 14)
                return false;
        }
        return true;
    }
}
