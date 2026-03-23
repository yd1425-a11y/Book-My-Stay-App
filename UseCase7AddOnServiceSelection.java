import java.util.*;

// Service class (Add-on service)
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// Manager class (Core logic)
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added Service: " + service.getServiceName()
                + " to Reservation: " + reservationId);
    }

    // Calculate total cost
    public double calculateTotalServiceCost(String reservationId) {

        List<Service> services = servicesByReservation.get(reservationId);

        if (services == null) return 0;

        double total = 0;

        for (Service s : services) {
            total += s.getCost();
        }

        return total;
    }
}

// MAIN CLASS
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=== Add-On Service Selection ===");

        // Assume reservation already exists (from Use Case 6)
        String reservationId = "Single-1";

        // Create manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa", 800);
        Service pickup = new Service("Airport Pickup", 200);

        // Add services
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);
        manager.addService(reservationId, pickup);

        // Calculate total cost
        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("\nReservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}