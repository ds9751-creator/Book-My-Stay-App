import java.util.*;

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new LinkedHashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

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

class RoomAllocationService {
    private Map<String, Integer> roomTypeCounters;

    public RoomAllocationService() {
        roomTypeCounters = new HashMap<>();
    }

    public String allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();
        int currentAvailable = inventory.getRoomAvailability().getOrDefault(type, 0);

        if (currentAvailable > 0) {
            int nextNumber = roomTypeCounters.getOrDefault(type, 0) + 1;
            roomTypeCounters.put(type, nextNumber);

            inventory.updateAvailability(type, currentAvailable - 1);

            return type + "-" + nextNumber;
        }
        return "No Rooms Available";
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        List<Reservation> requests = new ArrayList<>();
        requests.add(new Reservation("Abhi", "Single"));
        requests.add(new Reservation("Subha", "Single"));
        requests.add(new Reservation("Vanmathi", "Suite"));

        for (Reservation res : requests) {
            String roomId = allocationService.allocateRoom(res, inventory);
            System.out.println("Booking confirmed for Guest: " + res.getGuestName() + ", Room ID: " + roomId);
        }
    }
}
