package day5;
import day5.Tuple;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SeedRanges {

    public static void main(String[] args) {
        List<Tuple<Long, Long>> ranges = getRanges();

        List<Long> locations = new ArrayList<>(); // will contain the min location for each range
        for (Tuple<Long, Long> range : ranges) {
            long rangeStart = range.x;
            long rangeEnd = range.x + range.y;
            locations.add(getMinLocationInRange(rangeStart, rangeEnd, 0));
        }
        System.out.println(locations);

        long minLocation = Collections.min(locations);
        System.out.println(minLocation);
    }

    public static List<Tuple<Long, Long>> getRanges() {
        List<Tuple<Long, Long>> ranges = new ArrayList<>();

        try {
            File inputFile = new File("day5/in.txt");
            Scanner scanner = new Scanner(inputFile);
    
            // get seeds
            String[] seedStrings = scanner.nextLine().split(" ");
            List<Long> seeds = new ArrayList<>();
            for (int i = 1; i < seedStrings.length; i++)
                seeds.add(Long.parseLong(seedStrings[i]));
            
            for (int i = 0; i < seeds.size(); i += 2) {
                long start = seeds.get(i);
                long end = seeds.get(i + 1);
                ranges.add(new Tuple<Long, Long>(start, end));
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return ranges;
    }

    public static long getNextMapping(long seed, int mapNum) {
        long nextMapping = seed;
        
        try {
            File inputFile = new File("day5/in.txt");
            Scanner scanner = new Scanner(inputFile);
            for (int i = 0; i < 3; i++) // skip to the first mapping line
                scanner.nextLine();
            
            for (int i = 0; i < 7; i++) { // process 7 different maps
                boolean skip = false;
                if (i != mapNum) skip = true;

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
                            nextMapping = y + offset;
                            skip = true;
                        }
                    } catch (NumberFormatException e) {
                        // skip if the line cannot be parsed into integers
                    }
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return nextMapping;
    }

    public static List<Tuple<Long, Long>> rangeSplits(long rangeStart, long rangeEnd, int mapNum) {
        List<Tuple<Long, Long>> splits = new ArrayList<>();

        try {
            File inputFile = new File("day5/in.txt");
            Scanner scanner = new Scanner(inputFile);
            for (int i = 0; i < 3; i++) // skip to the first mapping line
                scanner.nextLine();
            
            for (int i = 0; i < 7; i++) { // process 7 different maps
                boolean skip = false;
                if (i != mapNum) skip = true;

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
                        if (x >= rangeStart && x <= rangeEnd) {
                            long splitStart = x;
                            long splitEnd = Math.min(x + n, rangeEnd);
                            splits.add(new Tuple<Long, Long>(splitStart, splitEnd));
                        }
                    } catch (NumberFormatException e) {
                        // skip if the line cannot be parsed into integers
                    }
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Collections.sort(splits, (a, b) -> Long.compare(a.x, b.x));

        // add splits for unchanged regions
        List<Tuple<Long, Long>> tmpSplits = new ArrayList<>();
        long lastEnd = rangeStart;
        for (Tuple<Long, Long> split : splits) {
            if (split.x > lastEnd) {
                tmpSplits.add(new Tuple<Long, Long>(lastEnd, split.x - 1));
            }
            lastEnd = split.y + 1;
        }
        if (lastEnd < rangeEnd)
            tmpSplits.add(new Tuple<Long, Long>(lastEnd, rangeEnd));
        splits.addAll(tmpSplits);

        Collections.sort(splits, (a, b) -> Long.compare(a.x, b.x));

        return splits;
    }

    public static long getMinLocationInRange(long rangeStart, long rangeEnd, int mapNum) {
        if (mapNum > 6)
            return rangeStart;

        List<Tuple<Long, Long>> splits = rangeSplits(rangeStart, rangeEnd, mapNum);
        long minLocation = Long.MAX_VALUE;
        for (Tuple<Long, Long> split : splits) {
            long splitStartNext = getNextMapping(split.x, mapNum);
            long splitEndNext = getNextMapping(split.y, mapNum);
            long splitMinLocation = getMinLocationInRange(splitStartNext, splitEndNext, mapNum + 1);
            if (splitMinLocation < minLocation)
                minLocation = splitMinLocation;
        }
        return minLocation;
    }

}
