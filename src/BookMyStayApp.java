import java.util.LinkedHashMap;
import java.util.Map;

class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new LinkedHashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        System.out.println("Hotel Room Inventory Status\n");

        displayRoomDetails("Single Room", 1, 250, 1500.0, inventory);
        displayRoomDetails("Double Room", 2, 400, 2500.0, inventory);
        displayRoomDetails("Suite Room", 3, 750, 5000.0, inventory);
    }

    private static void displayRoomDetails(String name, int beds, int size, double price, RoomInventory inv) {
        System.out.println(name + ":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available Rooms: " + inv.getRoomAvailability().get(name));
        System.out.println();
    }
}
