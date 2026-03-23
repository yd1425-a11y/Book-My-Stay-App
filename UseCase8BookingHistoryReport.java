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

// BookingHistory class (Stores data)
class BookingHistory {

    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    // Add reservation
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

// Reporting Service (Read-only)
class BookingReportService {

    public void generateReport(BookingHistory history) {

        System.out.println("\n=== Booking History Report ===");

        List<Reservation> list = history.getConfirmedReservations();

        for (Reservation r : list) {
            System.out.println("Guest: " + r.getGuestName()
                    + ", Room Type: " + r.getRoomType());
        }
    }
}

// MAIN CLASS
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=== Booking History and Reporting ===");

        // Create booking history
        BookingHistory history = new BookingHistory();

        // Add confirmed bookings (simulate Use Case 6 output)
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}