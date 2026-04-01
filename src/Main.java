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
public class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(BookingRequestQueue bookingQueue, RoomInventory inventory, RoomAllocationService allocationService) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation = null;
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            if (reservation != null) {
                synchronized (inventory) {
                    allocationService.allocateRoom(reservation, inventory);
                }
            }
        }
import java.util.*;

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 10);
        inventory.put("Double", 10);
    }

    public void restoreInventory(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }
}

class CancellationService {
    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {
        if (reservationRoomTypeMap.containsKey(reservationId)) {
            String roomType = reservationRoomTypeMap.get(reservationId);
            inventory.restoreInventory(roomType);
            releasedRoomIds.push(reservationId);
            reservationRoomTypeMap.remove(reservationId);
            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        } else {
            System.out.println("Invalid cancellation: Reservation ID not found.");
        }
    }

    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No history found.");
            return;
        }
        Stack<String> tempStack = new Stack<>();
        while (!releasedRoomIds.isEmpty()) {
            String id = releasedRoomIds.pop();
            System.out.println("Released Reservation ID: " + id);
            tempStack.push(id);
        }
        while (!tempStack.isEmpty()) {
            releasedRoomIds.push(tempStack.pop());
        }
import java.util.Scanner;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {}

class ReservationValidator {
    public void validate(String guestName, String roomType, RoomInventory inventory) throws InvalidBookingException {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        if (!(roomType.equals("Single") || roomType.equals("Double") || roomType.equals("Suite"))) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
import java.util.ArrayList;
import java.util.List;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

class BookingReportService {
    public void generateReport(BookingHistory history) {
        System.out.println("Booking History Report");
        for (Reservation res : history.getConfirmedReservations()) {
            System.out.println("Guest: " + res.getGuestName() + ", Room Type: " + res.getRoomType());
        }
import java.util.*;

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

class AddOnServiceManager {
    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        this.servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        servicesByReservation.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        List<Service> services = servicesByReservation.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
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
        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println("\nRemaining Inventory:");
        inventory.displayRemaining();
        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        service.registerBooking("Single-1", "Single");

        service.cancelBooking("Single-1", inventory);

        service.showRollbackHistory();
        System.out.println("Booking Validation");
        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();

        try {
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            validator.validate(guestName, roomType, inventory);
            System.out.println("Booking successful for " + guestName);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
        System.out.println("Booking History and Reporting\n");

        BookingHistory history = new BookingHistory();
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
        AddOnServiceManager manager = new AddOnServiceManager();
        String reservationId = "Single-1";

        manager.addService(reservationId, new Service("Spa", 1000.0));
        manager.addService(reservationId, new Service("Breakfast", 500.0));

        System.out.println("Add-On Service Selection");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + manager.calculateTotalServiceCost(reservationId));
    }
}