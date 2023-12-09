package day8;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Navigator2 {
    
    public static Map<String, Tuple<String, String>> parseNetwork(BufferedReader reader) throws IOException {
        Map<String, Tuple<String, String>> network = new HashMap<>();
        String line;
        
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                String[] parts = line.split("=");
                String node = parts[0].trim();
                String connections = parts[1].trim().replaceAll("[()]", "");
                String[] nodes = connections.split(",");
                network.put(node, new Tuple<String, String>(nodes[0].trim(), nodes[1].trim()));
            }
        }
        
        return network;
    }

    public static Long navigateToZ(Map<String, Tuple<String, String>> network, String instructions, String startNode) {
        String currNode = startNode;
        Long stepCount = 0L;

        while (!currNode.endsWith("Z")) {
            char nextInstruction = instructions.charAt((int)(stepCount % instructions.length()));
            if (nextInstruction == 'L')
                currNode = network.get(currNode).x;
            else if (nextInstruction == 'R')
                currNode = network.get(currNode).y;
            
            stepCount++;
        }

        return stepCount;
    }

    public static long calculateTotalSteps(Map<String, Tuple<String, String>> network, String instructions) {
        List<String> startingNodes = network.keySet().stream().filter(node -> node.endsWith("A")).collect(Collectors.toList());
        List<Long> allSteps = startingNodes.stream().map(node -> navigateToZ(network, instructions, node)).collect(Collectors.toList());
        return lcm(allSteps);
    }

    private static Long lcm(List<Long> input) {
        Long result = input.get(0);
        for(int i = 1; i < input.size(); i++)
            result = result * (input.get(i) / gcd(result, input.get(i)));
        return result;
    }

    private static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        String fileName = "day8/in.txt";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String instructions = reader.readLine().trim();
            reader.readLine();
            Map<String, Tuple<String, String>> network = parseNetwork(reader);

            Long steps = calculateTotalSteps(network, instructions);
            System.out.println(steps);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
