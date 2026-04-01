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
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        service.registerBooking("Single-1", "Single");

        service.cancelBooking("Single-1", inventory);

        service.showRollbackHistory();
    }
}