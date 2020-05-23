// Ticket.java
import java.io.Serializable;

/**
 *This class contains the Ticket object which stores all the characteristics of the flight tickets.
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class Ticket implements Serializable {

	/**
	 * user refers to the username of the ticket holder
	 * name refers to the ticket holders first name
	 * lastname refers to the ticket holders last name
	 * DOB refers to the ticket holders date of birth
	 * source refers to the place where the plane is taking off from
	 * destination refers to the location that the plane is heading
	 * date refers to the date that the plane flight occurs
	 * time refers to the time that the flight is taking off
	 * duration refers to the length of the plane flight.
	 */
	private String user, name, lastname, DOB, source, destination, date, time, duration;
	
	/**
	 * ticketID refers to the tickets identification number
	 * flightNo refers to the flights flight number
	 */
	int ticketID, flightNo;
	
	/**
	 * price refers to the price of the flight ticket
	 */
	double price;
	
	/**
	 * the id of the serialized information
	 */
	private static final long serialVersionUID = 131654831;
	
	
	/**
	 * the constructor for the ticket object that constructs and sets/initializes the data fields
	 * @param user refers to the username of the ticket holder
	 * @param name refers to the ticket holders first name
	 * @param lastname refers to the ticket holders last name
	 * @param DOB refers to the ticket holders date of birth
	 * @param ticketID refers to the tickets identification number
	 * @param flightNo refers to the flights flight number
	 * @param source refers to the location where the plane is taking off from
	 * @param destination refers to the location that the plane is heading
	 * @param date refers to the date that the plane flight occurs
	 * @param time refers to the time that the flight is taking off
	 * @param duration refers to the length of the plane flight.
	 * @param price refers to the price of the plane ticket
	 */
	public Ticket(String user, String name, String lastname, String DOB, int ticketID, 
			int flightNo, String source, String destination, String date, String time, String duration, double price){
		this.user = user;
		this.name = name;
		this.lastname = lastname;
		this.DOB = DOB;
		this.ticketID = ticketID;
		this.flightNo = flightNo;
		this.source = source;
		this.destination = destination;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.price = price;
	}
	
	/**
	 * This method sets the user data member to a new user data member
	 * @param the new value of the user data member
	 */
	public void setuser(String user){
		this.user = user;
	}
	
	/**
	 * This method sets the users name to a new name
	 * @param the new name of the user
	 */
	public void setname(String name){
		this.name = name;
	}
	
	/**
	 * This method sets the users last name to a new last name
	 * @param lastname refers to the new last name given to the user.
	 */
	public void setlastname(String lastname){
		this.lastname = lastname;
	}
	
	/**
	 * This method sets the recorded date of birth of the user to a new value
	 * @param DOB is the new value set as the users date of birth
	 */
	public void setDOB(String DOB){
		this.DOB = DOB;
	}
	
	/**
	 * This method sets the ticketID to a new ticketID
	 * @param ticketID is the new value of the tickets ticket id.
	 */
	public void setticketID(int ticketID){
		this.ticketID = ticketID;
	}
	
	/**
	 * This method sets the flights flight number to a new value.
	 * @param flightNo is the new value of the flights flight number
	 */
	public void setflightNo(int flightNo){
		this.flightNo = flightNo;
	}
	
	/**
	 * This method sets the flights source to a new source
	 * @param source is the flights new source
	 */
	public void setsource(String source){
		this.source = source;
	}
	
	/**
	 * This method sets the flights destination to a new destination
	 * @param destination is the flights new destinaiton
	 */
	public void setdestination(String destination){
		this.destination =  destination;
	}
	
	/**
	 * This method sets the date of the flight to a new date
	 * @param date is the flights new date
	 */
	public void setdate(String date){
		this.date = date;
	}
	
	/**
	 * This method sets the time of the flight to a new time
	 * @param time is the new time of the flight
	 */
	public void settime(String time){
		this.time = time;
	}
	
	/**
	 * This method sets the duration of the flight to a new duration
	 * @param duration is the new duraiton of the flight
	 */
	public void setduration(String duration){
		this.duration = duration;
	}
	
	/**
	 * This method sets the price of the flight ticket to a new price
	 * @param price is the new price of the flight ticket
	 */
	public void setprice(double price){
		this.price = price;
	}	
	
	/**
	 * This method returns the username of the user.
	 * @return the username of the user
	 */
	public String getuser(){
		return user;
	}
	
	/**
	 * This method returns the first name of the ticket holder.
	 * @return the first name of the ticket holder
	 */
	public String getname(){
		return name;
	}
	
	/**
	 * This method returns the last name of the ticket holder.
	 * @return the last name of the ticket holder
	 */
	public String getlastname(){
		return lastname;
	}
	
	/**
	 * This method returns the ticket holders date of birth
	 * @return the ticket holders date of birth
	 */
	public String getDOB(){
		return DOB;
	}
	
	/**
	 * This method returns the tickets identification number
	 * @return the tickets ID number
	 */
	public int ticketID(){
		return ticketID;
	}
	
	/**
	 * This method returns the flights flight number
	 * @return the flights flight number
	 */
	public int getflightNo(){
		return flightNo;
	}
	
	/**
	 * This method returns the flights source location
	 * @return the source location of the flight
	 */
	public String getsource(){
		return source;
	}
	
	/**
	 * This method returns the flights destinaiton location
	 * @return The destination of the flight
	 */
	public String getdestination(){
		return destination;
	}
	
	/**
	 * This method returns the date of the flight
	 * @return the date the flight occurs on
	 */
	public String getdate(){
		return date;
	}
	
	/**
	 * This method returns the time that the flight occurs on
	 * @return the time the flight occurs on
	 */
	public String gettime(){
		return time;
	}
	
	/**
	 * This method returns the duration of the flight
	 * @return the duration of the flight
	 */
	public String getduration(){
		return duration;
	}
	
	/**
	 * This method returns the price of a flight ticket
	 * @return the price of a flight ticket
	 */
	public double getprice(){
		return price;
	}
}
