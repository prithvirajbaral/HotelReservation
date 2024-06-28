# Hotel Reservation System

This is my first project using Java. It is a comprehensive Hotel Reservation System built with Java, JDBC, IntelliJ IDEA, and MySQL.

## Features
1. **Reserve a Room**: Book rooms with necessary details.
2. **View Reservations**: Retrieve and display all current reservations.
3. **Get Room Number**: Obtain the room number for a specific reservation.
4. **Update Reservations**: Modify existing reservations to reflect changes.
5. **Delete Reservations**: Remove reservations as needed.

## Setup Instructions
1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/HotelReservationSystem.git
2. **Navigate to the project directory:**
   ```bash
   cd HotelReservation
3. **Import the project into IntelliJ IDEA.**
4. **Update the database connection details in HotelReservationSystem.java:**
   ```java
   private static final String jdbcUrl = "jdbc:mysql://localhost:3306/your-database";
   private static final String username = "your-username";
   private static final String password = "your-password";
Replace your-password with your MySQL database password.

5. **Connect MySQL Connector/J:**

   * Download MySQL Connector/J from MySQL official website
   * Add the Connector/J JAR file to your IntelliJ IDEA project
      - Right-click on your project in IntelliJ IDEA.
      - Select "Open Module Settings".
      - Go to "Libraries" and click on the "+" sign to add the Connector/J JAR file.

6. **Run the project from IntelliJ IDEA.**


### Notes:
- Make sure to replace `your-username`, `your-password`, and `hotel_db` with your actual MySQL credentials and database name.
- Detailed instructions are provided for cloning the repository, setting up the project in IntelliJ IDEA, updating database connection details, and connecting MySQL Connector/J.

This `README.md` file will guide users on how to set up and run your Hotel Reservation System project effectively. Adjust the instructions as per your specific project requirements and preferences.
