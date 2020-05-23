// FlightStorage.java
import java.util.Vector;
import java.io.Serializable;

/**
 *This class contains the FlightStorage object which stores the flights into a data type.
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class FlightStorage implements Serializable {

	/**
	 * The vector that all the flight information will be stored in.
	 */
	private Vector <Flight> flights;
	
	/**
	 * The number of flights that are in the vector
	 */
	private int length;
	
	/**
	 * the id of the serialized information
	 */
	private static final long serialVersionUID = 987654321;
	
	/**
	 * This is the constructor of the FlightStorage object which initializes the vector and length of the vector to 0/null.
	 */
	public FlightStorage(){
		flights = new Vector <Flight>();
		length = 0;	
	}
	
	/**
	 * This method adds a flight to the vector and increments length
	 * @param flight refers to the new flight object that is being added to the vector
	 */
	public void addFlight(Flight flight){
		flights.add(flight);
		length++;
	}
	
	/**
	 * This method sets the vector of flights to a new vecctor of flights.
	 * @param flights is the new vector that contians the flights
	 */
	public void setflights(Vector<Flight> flights){
		this.flights = flights;
	}
	
	/**
	 * This mehtod changes value of the length data member.
	 * @param length is the new length value
	 */
	public void setlength(int length){
		this.length = length;
	}
	
	/**
	 * This method returns the vector of flights
	 * @return the vector containing all the flights and their data
	 */
	public Vector<Flight> getflights(){
		return flights;
	}
	
	/**
	 * This method returns the value of the length data member
	 * @return the value of the length data member
	 */
	public int getlength(){
		return length;
	}
}
