package day3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SchematicAnalyzer2 {

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

    public static List<Integer> adjacentPositions(List<List<Character>> mat, int row, int col) {
        List<Integer> pos = new ArrayList<>(); // 0:left, 1:right, 2:top, 3:bottom, 4:tl, 5:tr, 6:bl, 7: br
    
        if (col > 0 && Character.isDigit(mat.get(row).get(col - 1))) // left
            pos.add(0);
        if (col < mat.get(row).size() - 1 && Character.isDigit(mat.get(row).get(col + 1))) // right
            pos.add(1);
    
        boolean hasTop = false, hasBottom = false;
        if (row > 0 && Character.isDigit(mat.get(row - 1).get(col))) { // top
            pos.add(2);
            hasTop = true;
        }
        if (row < mat.size() - 1 && Character.isDigit(mat.get(row + 1).get(col))) { // bottom
            pos.add(3);
            hasBottom = true;
        }
    
        if (row > 0 && col > 0 && Character.isDigit(mat.get(row - 1).get(col - 1)) && !hasTop)
            pos.add(4); // top-left
        if (row > 0 && col < mat.get(row).size() - 1 && Character.isDigit(mat.get(row - 1).get(col + 1)) && !hasTop)
            pos.add(5); // top-right
        if (row < mat.size() - 1 && col > 0 && Character.isDigit(mat.get(row + 1).get(col - 1)) && !hasBottom)
            pos.add(6); // bottom-left
        if (row < mat.size() - 1 && col < mat.get(row).size() - 1 && Character.isDigit(mat.get(row + 1).get(col + 1)) && !hasBottom)
            pos.add(7); // bottom-right
    
        return pos;
    }
    
    public static int getNum(List<List<Character>> mat, int row, int col) {
        StringBuilder n = new StringBuilder();
        n.append(mat.get(row).get(col));

        int c = col - 1;
        while (c >= 0) {
            if (Character.isDigit(mat.get(row).get(c)))
                n.insert(0, mat.get(row).get(c));
            else
                break;
            c--;
        }

        c = col + 1;
        while (c < mat.get(row).size()) {
            if (Character.isDigit(mat.get(row).get(c)))
                n.append(mat.get(row).get(c));
            else
                break;
            c++;
        }

        return Integer.parseInt(n.toString());
    }

    public static List<Integer> getAdjacentNums(List<List<Character>> mat, int row, int col, List<Integer> pos) {
        List<Integer> nums = new ArrayList<>();
    
        for (int position : pos) {
            switch (position) {
                case 0: // Left
                    nums.add(getNum(mat, row, col - 1));
                    break;
                case 1: // Right
                    nums.add(getNum(mat, row, col + 1));
                    break;
                case 2: // Top
                    nums.add(getNum(mat, row - 1, col));
                    break;
                case 3: // Bottom
                    nums.add(getNum(mat, row + 1, col));
                    break;
                case 4: // Top-left
                    nums.add(getNum(mat, row - 1, col - 1));
                    break;
                case 5: // Top-right
                    nums.add(getNum(mat, row - 1, col + 1));
                    break;
                case 6: // Bottom-left
                    nums.add(getNum(mat, row + 1, col - 1));
                    break;
                case 7: // Bottom-right
                    nums.add(getNum(mat, row + 1, col + 1));
                    break;
                default:
                    // Invalid position
                    break;
            }
        }
    
        return nums;
    }

    public static int sumPartNumbers(List<List<Character>> mat) {
        int total = 0;
        for (int row = 0; row < mat.size(); row++) {
            for (int col = 0; col < mat.get(row).size(); col++) {
                if (mat.get(row).get(col) == '*') {
                    List<Integer> pos = adjacentPositions(mat, row, col);
                    if (pos.size() == 2) {
                        List<Integer> adjacentNums = getAdjacentNums(mat, row, col, pos);
                        int n1 = adjacentNums.get(0), n2 = adjacentNums.get(1);
                        int ratio = n1 * n2;
                        total += ratio;
                    }
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        try {
            List<List<Character>> schematic = readSchematic("day3/in.txt");
            int result = sumPartNumbers(schematic);
            System.out.println(result);

        } catch (FileNotFoundException e) {}
    }
}
