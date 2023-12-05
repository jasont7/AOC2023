package day5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Seeds {

    public static void main(String[] args) {
        try {
            File inputFile = new File("day5/in.txt");
            Scanner scanner = new Scanner(inputFile);

            // get seeds
            String[] seedStrings = scanner.nextLine().split(" ");
            List<Long> seeds = new ArrayList<>();
            for (int i = 1; i < seedStrings.length; i++)
                seeds.add(Long.parseLong(seedStrings[i]));

            List<Long> locations = new ArrayList<>();
            for (long seed : seeds) {
                scanner = new Scanner(inputFile); // reopen the file for each seed
                for (int i = 0; i < 3; i++) // skip to the first mapping line
                    scanner.nextLine();

                for (int i = 0; i < 6; i++) { // process 6 different maps
                    boolean skip = false;
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (skip && !line.contains("map:"))
                            continue;
                        else if (line.contains("map:"))
                            break;

                        try {
                            String[] vals = line.split(" ");
                            long y = Long.parseLong(vals[0]);
                            long x = Long.parseLong(vals[1]);
                            long n = Long.parseLong(vals[2]);
                            if (seed >= x && seed <= x + n) {
                                long offset = seed - x;
                                seed = y + offset;
                                skip = true;
                            }
                        } catch (NumberFormatException e) {
                            // skip if the line cannot be parsed into integers
                        }
                    }
                }
                locations.add(seed);
            }
            scanner.close();

            long minLocation = locations.stream().min(Long::compare).orElse(Long.MAX_VALUE);
            System.out.println(minLocation);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
