import java.util.*;

// Inventory class
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void setRoomAvailability(String type, int count) {
        availability.put(type, count);
    }

    public void incrementRoom(String type) {
        availability.put(type, availability.getOrDefault(type, 0) + 1);
    }

    public int getAvailableRooms(String type) {
        return availability.getOrDefault(type, 0);
    }
}

// Cancellation Service (CORE LOGIC)
class CancellationService {

    // Map reservationID -> roomType
    private Map<String, String> reservationRoomTypeMap;

    // Stack for rollback tracking
    private Stack<String> rollbackStack;

    public CancellationService() {
        reservationRoomTypeMap = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    // Register confirmed booking (from Use Case 6)
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    // Cancel booking
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        // Validation
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation: Reservation not found");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.incrementRoom(roomType);

        // Remove booking
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: "
                + roomType);
    }

    // Show rollback history (LIFO)
    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most Recent First):");

        Stack<String> temp = (Stack<String>) rollbackStack.clone();

        while (!temp.isEmpty()) {
            System.out.println("Released Reservation ID: " + temp.pop());
        }
    }
}

// MAIN CLASS
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=== Booking Cancellation ===");

        // Inventory setup
        RoomInventory inventory = new RoomInventory();
        inventory.setRoomAvailability("Single", 5);

        // Cancellation service
        CancellationService service = new CancellationService();

        // Register bookings (simulate confirmed bookings)
        service.registerBooking("Single-1", "Single");
        service.registerBooking("Single-2", "Single");

        // Cancel booking
        service.cancelBooking("Single-1", inventory);

        // Show rollback history
        service.showRollbackHistory();

        // Show updated inventory
        System.out.println("\nUpdated Single Room Availability: "
                + inventory.getAvailableRooms("Single"));
    }
}