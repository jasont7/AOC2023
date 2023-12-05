package day1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SumOfDigits {

    public static void main(String[] args) {
        try {
            File inputFile = new File("day1/in.txt");
            Scanner fileScanner = new Scanner(inputFile);
            int sum = 0;
    
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                int i = 0, j = line.length() - 1;
                Character num1 = null, num2 = null;
    
                while (i <= j) {
                    if (Character.isDigit(line.charAt(i)) && num1 == null)
                        num1 = line.charAt(i);
                    if (Character.isDigit(line.charAt(j)) && num2 == null)
                        num2 = line.charAt(j);
    
                    if (num1 != null && num2 != null)
                        break;
    
                    if (num1 == null) i++;
                    if (num2 == null) j--;
                }
    
                int val = 0;
                if (num1 != null && num2 != null)
                    val = Integer.parseInt("" + num1 + num2);
                else if (num1 != null)
                    val = Integer.parseInt("" + num1 + num1);
                else if (num2 != null)
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
}
