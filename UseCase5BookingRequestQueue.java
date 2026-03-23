import java.util.*;

// Reservation class (Represents booking request)
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

// Booking Queue class (FIFO)
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Added Request: " + reservation.getGuestName() +
                " -> " + reservation.getRoomType());
    }

    // Get next request (FIFO)
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    // Check if queue has requests
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

// MAIN CLASS
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=== Booking Request Queue ===");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        System.out.println("\nProcessing Requests (FIFO Order):");

        // Process queue
        while (bookingQueue.hasPendingRequests()) {
            Reservation r = bookingQueue.getNextRequest();

            System.out.println("Processing: " + r.getGuestName() +
                    " requested " + r.getRoomType());
        }
    }
}