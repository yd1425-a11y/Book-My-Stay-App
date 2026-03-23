import java.util.*;

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Queue (shared resource)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory (shared resource)
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void setRoomAvailability(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailableRooms(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrementRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String type : availability.keySet()) {
            System.out.println(type + ": " + availability.get(type));
        }
    }
}

// Allocation Service
class RoomAllocationService {

    private Map<String, Integer> roomCounter = new HashMap<>();

    public void allocateRoom(Reservation r, RoomInventory inventory) {

        String type = r.getRoomType();

        if (inventory.getAvailableRooms(type) <= 0) {
            System.out.println("No room available for " + r.getGuestName());
            return;
        }

        int count = roomCounter.getOrDefault(type, 0) + 1;
        roomCounter.put(type, count);

        String roomId = type + "-" + count;

        inventory.decrementRoom(type);

        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName() + ", Room ID: " + roomId);
    }
}

// THREAD CLASS
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            // Critical section → queue access
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            // Critical section → inventory update
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

// MAIN CLASS
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=== Concurrent Booking Simulation ===");

        // Setup queue
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Double"));
        queue.addRequest(new Reservation("Kural", "Suite"));
        queue.addRequest(new Reservation("Subha", "Single"));

        // Setup inventory
        RoomInventory inventory = new RoomInventory();
        inventory.setRoomAvailability("Single", 4);
        inventory.setRoomAvailability("Double", 2);
        inventory.setRoomAvailability("Suite", 1);

        // Allocation service
        RoomAllocationService service = new RoomAllocationService();

        // Create threads
        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));

        // Start threads
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        // Final inventory
        inventory.displayInventory();
    }
}