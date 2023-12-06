package day1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class SumOfDigits2 {

    public static void main(String[] args) {
        try {
            File inputFile = new File("day1/in.txt");
            Scanner fileScanner = new Scanner(inputFile);
            int sum = 0;
    
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                int num1 = -1, num2 = -1;
                int i = 0, j = line.length() - 1;
                while (i <= j) {
                    num1 = getDigit(line, i, "forward");
                    num2 = getDigit(line, j, "backward");
    
                    if (num1 != -1 && num2 != -1) break;
                    if (num1 == -1) i++;
                    if (num2 == -1) j--;
                }
    
                int val = 0;
                if (num1 != -1 && num2 != -1)
                    val = Integer.parseInt("" + num1 + num2);
                else if (num1 != -1)
                    val = Integer.parseInt("" + num1 + num1);
                else if (num2 != -1)
                    val = Integer.parseInt("" + num2 + num2);
                sum += val;
            }
    
            System.out.println(sum);
            fileScanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int getDigit(String line, int i, String direction) {
        Map<String, Integer> digitMap = Map.of(
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9
        );

        if (Character.isDigit(line.charAt(i)))
            return Character.getNumericValue(line.charAt(i));

        for (String dig : digitMap.keySet()) {
            int start = 0;
            int end = line.length();

            if (direction.equals("forward"))
                end = Math.min(i + dig.length(), line.length());
            else if (direction.equals("backward"))
                start = Math.max(i - dig.length() + 1, 0);

            String substring = line.substring(start, end);
            if (substring.contains(dig))
                return digitMap.get(dig);
        }

        return -1;
    }

}
