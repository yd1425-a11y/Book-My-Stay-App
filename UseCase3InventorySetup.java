import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates how a HashMap can be used as a centralized
 * inventory system for managing room availability.
 *
 * @author Yash
 * @version 3.1
 */

/* Inventory class responsible for managing room availability */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    /* Constructor initializes room inventory */
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    /* Retrieve availability of a specific room */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /* Update room availability */
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    /* Display complete inventory */
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " available");
        }
    }
}

/* Main Application */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     Book My Stay - Version 3.1");
        System.out.println("=================================");

        // Initialize inventory system
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating availability for Single Room...");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}