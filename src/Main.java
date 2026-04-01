import java.io.*;
import java.util.*;

class RoomInventory {
    private Map<String, Integer> inventory = new LinkedHashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getRooms() {
        return inventory;
    }

    public void printInventory() {
        System.out.println("Current Inventory:");
        inventory.forEach((type, count) -> System.out.println(type + ": " + count));
    }
}

class FilePersistenceService {
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
                writer.println(entry.getKey() + "-" + entry.getValue());
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("-");
                if (parts.length == 2) {
                    inventory.addRoom(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();
        String filePath = "inventory.txt";

        persistenceService.loadInventory(inventory, filePath);

        if (inventory.getRooms().isEmpty()) {
            inventory.addRoom("Single", 5);
            inventory.addRoom("Double", 3);
            inventory.addRoom("Suite", 2);
        }

        inventory.printInventory();
        persistenceService.saveInventory(inventory, filePath);
    }
}