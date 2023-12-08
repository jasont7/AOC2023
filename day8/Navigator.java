package day8;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Navigator {
    
    public static Map<String, String[]> parseNetwork(BufferedReader reader) throws IOException {
        Map<String, String[]> network = new HashMap<>();
        String line;
        
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                String[] parts = line.split("=");
                String node = parts[0].trim();
                String connections = parts[1].trim().replaceAll("[()]", "");
                String[] nodes = connections.split(",");
                network.put(node, new String[]{nodes[0].trim(), nodes[1].trim()});
            }
        }
        
        return network;
    }

    public static int navigateToZzz(String instructions, Map<String, String[]> network) {
        String current_node = "AAA";
        int instructionIndex = 0;
        int stepCount = 0;

        while (!current_node.equals("ZZZ")) {
            char nextInstruction = instructions.charAt(instructionIndex);
            current_node = nextInstruction == 'L' ? network.get(current_node)[0] : network.get(current_node)[1];
            instructionIndex = (instructionIndex + 1) % instructions.length();
            stepCount++;
        }

        return stepCount;
    }

    public static void main(String[] args) {
        String fileName = "day8/in.txt";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String instructions = reader.readLine().trim();
            reader.readLine();
            Map<String, String[]> network = parseNetwork(reader);

            int steps = navigateToZzz(instructions, network);
            System.out.println(steps);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
