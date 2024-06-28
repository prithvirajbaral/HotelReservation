import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/hotel_db"; // Update with your database name
    private static final String username = "root"; // Update with your MySQL username
    private static final String password = "Prithviritzz@0610"; // Update with your MySQL password

    public static void main(String[] args) {
        Connection connection = null;
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            while (true) {
                System.out.println();
                System.out.println("HOTEL RESERVATION SYSTEM:");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Clear the buffer
                switch (choice) {
                    case 1:
                        reserveRoom(connection, sc);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoom(connection, sc);
                        break;
                    case 4:
                        updateReservation(connection, sc);
                        break;
                    case 5:
                        deleteReservation(connection, sc);
                        break;
                    case 0:
                        exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    private static void reserveRoom(Connection connection, Scanner sc) {
        try {
            System.out.print("Enter Guest Name: ");
            String guestName = sc.nextLine();
            System.out.println();
            System.out.print("Enter Room Number: ");
            int roomNum = sc.nextInt();
            System.out.println();
            System.out.print("Enter Contact Number: ");
            long contNum = sc.nextLong();

            String sql = "INSERT INTO reservation (guestName, room_no, contact_no) VALUES ('"
                    + guestName + "', "
                    + roomNum + ", "
                    + contNum + ")";

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation Successful");
                } else {
                    System.out.println("Reservation Failed");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    private static void viewReservation(Connection connection) {
        String sql = "SELECT * FROM reservation";
        try (Statement statement = connection.createStatement(); ResultSet set = statement.executeQuery(sql)) {
            System.out.println("Current Reservations:");
            while (set.next()) {
                int id = set.getInt("reservation_id");
                String guestName = set.getString("guestName");
                int roomNo = set.getInt("room_no");
                long contactNo = set.getLong("contact_no");
                Timestamp time = set.getTimestamp("reservation_date");

                System.out.println("ID: " + id + ", Guest Name: " + guestName + ", Room Number: " + roomNo + ", Contact Number: " + contactNo + ", Time: " + time);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    private static void getRoom(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter Reservation ID: ");
            int reservationId = sc.nextInt();
            sc.nextLine(); // Clear the buffer
            System.out.println("Enter Guest Name: ");
            String name = sc.nextLine();

            String sql = "SELECT room_no FROM reservation WHERE reservation_id = " + reservationId + " AND guestName = '" + name + "'";
            try (Statement statement = connection.createStatement(); ResultSet set = statement.executeQuery(sql)) {
                if (set.next()) {
                    int roomNum = set.getInt("room_no");
                    System.out.println("Room number for Reservation ID: " + reservationId + " and Guest Name: " + name + " is: " + roomNum);
                } else {
                    System.out.println("Reservation not found for the given ID and Guest Name");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    private static void updateReservation(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter Reservation ID to Update: ");
            int reservationId = sc.nextInt();
            sc.nextLine(); // Clear the buffer

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID");
                return;
            }

            System.out.println("Enter new Guest Name: ");
            String newGuestName = sc.nextLine();
            System.out.println("Enter new Room Number: ");
            int newRoomNum = sc.nextInt();
            System.out.println("Enter new Contact Number: ");
            long newContNum = sc.nextLong();

            String sql = "UPDATE reservation SET guestName = '" + newGuestName +
                    "', room_no = " + newRoomNum +
                    ", contact_no = " + newContNum +
                    " WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation updated successfully");
                } else {
                    System.out.println("Reservation update failed");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    private static void deleteReservation(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter Reservation ID to Delete: ");
            int reservationId = sc.nextInt();
            sc.nextLine(); // Clear the buffer

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID");
                return;
            }

            String sql = "DELETE FROM reservation WHERE reservation_id = " + reservationId;
            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation deleted successfully");
                } else {
                    System.out.println("Reservation delete failed");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    private static boolean reservationExists(Connection connection, int id) {
        String sql = "SELECT reservation_id FROM reservation WHERE reservation_id = " + id;
        try (Statement statement = connection.createStatement(); ResultSet set = statement.executeQuery(sql)) {
            return set.next();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
        }
    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thanks for using Hotel Reservation System");
    }
}