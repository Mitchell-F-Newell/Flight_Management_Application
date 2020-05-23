// CancelOperator.java
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *This class contains the CancelOperator object which connects to the database books a ticket and updates the number of seats
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class CancelOperator {
	/**
	 * This method obtains the flight number from the ticket database and updates the number of available seats for the flight and then 
	 * cancels/deletes the ticket from the data base
	 */
	public synchronized boolean cancelFlight (int ticketID) throws SQLException {
		Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/flight_management", "root", "mysql");
		PreparedStatement stmt = myConn.prepareStatement("SELECT * FROM tickets WHERE ticketID = ?");
		stmt.setInt(1, ticketID);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			int flNum = rs.getInt("flightNumber");
			stmt = myConn.prepareStatement("SELECT * FROM flights WHERE flightNumber = ?");
			stmt.setInt(1, flNum);
			ResultSet rs2 = stmt.executeQuery();
			rs2.next();
			stmt = myConn.prepareStatement("UPDATE flights SET availableSeats = ? WHERE flightNumber = ?");
			stmt.setInt (2, flNum);
			int temp = rs2.getInt("availableSeats");
			temp++;
			stmt.setInt (1, temp);
			stmt.executeUpdate();
			stmt = myConn.prepareStatement("DELETE FROM tickets WHERE ticketID = ?");
			stmt.setInt(1, ticketID);
			stmt.executeUpdate();
			myConn.close();
			return true;
		}
		myConn.close();
		return false;
	}
}