package day3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SchematicAnalyzer {

    public static List<List<Character>> readSchematic(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        List<List<Character>> schematic = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().strip();
            List<Character> charList = new ArrayList<>();
            for (char ch : line.toCharArray()) {
                charList.add(ch);
            }
            schematic.add(charList);
        }
        scanner.close();
        return schematic;
    }

    public static boolean isSymbol(char ch) {
        return !Character.isDigit(ch) && ch != '.';
    }

    public static boolean hasAdjacentSymbol(List<List<Character>> schematic, int row, int col) {
        int top = Math.max(0, row - 1);
        int bottom = Math.min(schematic.size() - 1, row + 1);
        int left = Math.max(0, col - 1);
        int right = Math.min(schematic.get(0).size() - 1, col + 1);

        for (int i = top; i <= bottom; i++) {
            for (int j = left; j <= right; j++) {
                if (isSymbol(schematic.get(i).get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int sumPartNumbers(List<List<Character>> schematic) {
        int totalSum = 0;

        for (int row = 0; row < schematic.size(); row++) {
            StringBuilder curr = new StringBuilder();
            boolean hasSymbol = false;

            for (int col = 0; col < schematic.get(row).size(); col++) {
                char ch = schematic.get(row).get(col);

                if (Character.isDigit(ch)) {
                    curr.append(ch);
                    if (hasAdjacentSymbol(schematic, row, col)) {
                        hasSymbol = true;
                    }
                }

                if (!Character.isDigit(ch) || col == schematic.get(row).size() - 1) {
                    if (hasSymbol && curr.length() > 0) {
                        totalSum += Integer.parseInt(curr.toString());
                    }
                    curr = new StringBuilder();
                    hasSymbol = false;
                }
            }
        }

        return totalSum;
    }

    public static void main(String[] args) {
        try {
            List<List<Character>> schematic = readSchematic("day3/in.txt");
            int result = sumPartNumbers(schematic);
            System.out.println(result);

        } catch (FileNotFoundException e) {}
    }
}
