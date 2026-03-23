import java.io.*;
import java.util.*;

// Inventory class
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void setRoomAvailability(String type, int count) {
        availability.put(type, count);
    }

    public Map<String, Integer> getAllRooms() {
        return availability;
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : availability.keySet()) {
            System.out.println(type + ": " + availability.get(type));
        }
    }
}

// Persistence Service
class FilePersistenceService {

    // Save inventory to file
    public void saveInventory(RoomInventory inventory, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry : inventory.getAllRooms().entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    // Load inventory from file
    public void loadInventory(RoomInventory inventory, String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");

                String type = parts[0];
                int count = Integer.parseInt(parts[1]);

                inventory.setRoomAvailability(type, count);
            }

            System.out.println("Inventory loaded successfully.");

        } catch (Exception e) {
            System.out.println("Error loading inventory. Starting fresh.");
        }
    }
}

// MAIN CLASS
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=== System Recovery ===");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistence = new FilePersistenceService();

        // Try loading existing data
        persistence.loadInventory(inventory, filePath);

        // If empty, initialize default values
        if (inventory.getAllRooms().isEmpty()) {
            inventory.setRoomAvailability("Single", 5);
            inventory.setRoomAvailability("Double", 3);
            inventory.setRoomAvailability("Suite", 2);
        }

        // Display inventory
        inventory.displayInventory();

        // Save inventory before exit
        persistence.saveInventory(inventory, filePath);
    }
}