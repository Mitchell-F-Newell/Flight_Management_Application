// TicketStorage.java
import java.util.Vector;
import java.io.Serializable;

/**
 *This class contains the TicketStorage object which stores the tickets into a data type.
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class TicketStorage implements Serializable {
	
	/**
	 * The vector that all the ticket information will be stored in.
	 */
	private Vector <Ticket> tickets;
	
	/**
	 * The number of tickets that are in the vector
	 */
	private int length;
	
	/**
	 *  the id of the serialized information
	 */
	private static final long serialVersionUID = 578321954;
	
	/**
	 * This is the constructor of the TicketStorage object which initializes the vector and length of the vector to 0/null.
	 */
	public TicketStorage(){
		tickets = new Vector <Ticket>();
		length = 0;	
	}
	
	/**
	 * This method adds a ticket to the vector and increments length
	 * @param ticket refers to the new ticket object that is being added to the vector
	 */
	public void addTicket(Ticket ticket){
		tickets.add(ticket);
		length++;
	}
	
	/**
	 * This method sets the vector of tickets to a new vecctor of tickets.
	 * @param tickets is the new vector that contians the tickets
	 */
	public void settickets(Vector<Ticket> tickets){
		this.tickets = tickets;
	}
	
	/**
	 * This mehtod changes value of the length data member.
	 * @param length is the new length value
	 */
	public void setlength(int length){
		this.length = length;
	}
	
	/**
	 * This method returns the vector of tickets
	 * @return the vector containing all the tickets and their data
	 */
	public Vector<Ticket> gettickets(){
		return tickets;
	}
	
	/**
	 * This method returns the value of the length data member
	 * @return the value of the length data member
	 */
	public int getlength(){
		return length;
	}
}


