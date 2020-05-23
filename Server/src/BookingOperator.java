// BookingOperator.java
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *This class contains the BookingOperator object which connects to the database books a ticket and updates the number of seats
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class BookingOperator {
	
	/**
	 * This method obtains the number of seats for a certain flight then books the ticket and then updates the number of seats on the flight
	 */
	public synchronized boolean bookTicket (String user, String name, String lastname, String DOB, int flightNo) throws SQLException {
		Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/flight_management", "root", "mysql");
		PreparedStatement stmt = myConn.prepareStatement("SELECT * FROM flights WHERE flightNumber = ?");
		stmt.setInt(1, flightNo);
		ResultSet rs = stmt.executeQuery();
		int availableSeats = 0;
		if (rs.next()) {
			availableSeats = rs.getInt("availableSeats");
			if (availableSeats > 0) {
				availableSeats--;
				stmt = myConn.prepareStatement("INSERT INTO tickets (user, name, lastname, DOB, source, destination, date, time, duration, flightNumber, price) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
				stmt.setString (1, user);
				stmt.setString (2, name);
				stmt.setString (3, lastname);
				stmt.setString (4, DOB);
				stmt.setDouble (11, rs.getDouble("price"));
				stmt.setInt	 (10, flightNo);
				stmt.setString (5, rs.getString("source"));
				stmt.setString (6, rs.getString("destination"));
				stmt.setString (7, rs.getString("date"));
				stmt.setString (8, rs.getString("time"));
				stmt.setString (9, rs.getString("duration"));
				stmt.execute();
				
				stmt = myConn.prepareStatement("UPDATE flights SET availableSeats = ? WHERE flightNumber = ?");
				stmt.setInt (2, flightNo);
				stmt.setInt (1, availableSeats);
				stmt.executeUpdate();
				myConn.close();
				return true;
			}
		}
		myConn.close();
		return false;
	}
}