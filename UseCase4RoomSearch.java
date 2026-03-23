import java.util.*;

// Room class (Domain Model)
class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Price: ₹" + price);
        System.out.println("Amenities: " + amenities);
        System.out.println("--------------------------");
    }
}

// Inventory class (State Holder)
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void setRoomAvailability(String type, int count) {
        availability.put(type, count);
    }

    public Map<String, Integer> getRoomAvailability() {
        return availability; // Read-only usage (no modification outside)
    }
}

// Service class (Search Logic ONLY)
class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Single Room
        if (availability.getOrDefault("Single", 0) > 0) {
            System.out.println("Single Room Available:");
            singleRoom.displayDetails();
        }

        // Double Room
        if (availability.getOrDefault("Double", 0) > 0) {
            System.out.println("Double Room Available:");
            doubleRoom.displayDetails();
        }

        // Suite Room
        if (availability.getOrDefault("Suite", 0) > 0) {
            System.out.println("Suite Room Available:");
            suiteRoom.displayDetails();
        }
    }
}

// MAIN CLASS
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Create room objects
        Room singleRoom = new Room("Single", 2000, "WiFi, AC");
        Room doubleRoom = new Room("Double", 3500, "WiFi, AC, TV");
        Room suiteRoom = new Room("Suite", 6000, "WiFi, AC, TV, Mini Bar");

        // Create inventory
        RoomInventory inventory = new RoomInventory();
        inventory.setRoomAvailability("Single", 3);
        inventory.setRoomAvailability("Double", 0); // Not available
        inventory.setRoomAvailability("Suite", 2);

        // Search service
        RoomSearchService service = new RoomSearchService();

        // Perform search
        service.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
    }
}