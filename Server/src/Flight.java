// Flight.java
import java.io.Serializable;
/**
 *This class constructs the flight object which stores all characteristics of the plane flights.
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class Flight implements Serializable {
	/**
	 * flightNo referes to the flights flight number
	 * totalSeats refers to the total amount of seats on the plane
	 * availableSeats refers to the amount of seats left on the plane
	 */
	private int flightNo, totalSeats, availableSeats;
	/**
	 * source refers to the place where the plane is taking off from
	 * destination refers to the location that the plane is heading
	 * date refers to the date that the plane flight occurs
	 * time refers to the time that the flight is taking off
	 * duration refers to the length of the plane flight.
	 */
	private String source, destination, date, time, duration;
	
	/**
	 * refers to the price of the plane ticket
	 */
	private double price;
	
	/**
	 * the id of the serialized information
	 */
	private static final long serialVersionUID = 698741235;

	/**
	 * the constructor for the flight object that constructs and sets/initializes the data fields
	 * @param flightNo referes to the flights flight number
	 * @param source refers to the location where the plane is taking off from
	 * @param destination refers to the location that the plane is heading
	 * @param date refers to the date that the plane flight occurs
	 * @param time refers to the time that the flight is taking off
	 * @param duration refers to the length of the plane flight.
	 * @param totalSeats refers to the total amount of seats on the plane
	 * @param availableSeats refers to the amount of seats left on the plane
	 * @param price refers to the price of the plane ticket
	 */
	public Flight(int flightNo, String source, String destination, String date, String time,
	String duration, int totalSeats, int availableSeats, double price){
		this.flightNo = flightNo;
		this.source = source;
		this.destination = destination;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.totalSeats = totalSeats;
		this.availableSeats = availableSeats;
		this.price = price;
	}
	
	/**
	 * This method sets the flightNo to a new flightNo.
	 * @param is the new flightNo.
	 */
	public void setflightNo(int flightNo){
		this.flightNo = flightNo;
	}
	
	/**
	 * This method sets the source to a new source
	 * @param is the new flight source
	 */
	public void setsource(String source){
		this.source = source;
	}
	
	/**
	 * This method sets the destination of the flight to a new destination
	 * @param destination is the flights new destination
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
	 * @param time is the flights new time
	 */
	public void settime(String time){
		this.time = time;
	}
	
	/**
	 * This method chenges the duration of the flight to a new duration
	 * @param duration is the flights new duration
	 */
	public void setduration(String duration){
		this.duration = duration;
	}
	
	/**
	 * This method sets the total number of seats on the flight to a new number.
	 * @param totalSeats is the new number of total seats on the flight
	 */
	public void settotalSeats(int totalSeats){
		this.totalSeats = totalSeats;
	}
	
	/**
	 * This method sets the total number of available seats on the flight to a new number.
	 * @param availableSeats is the new number of available seats on the flight
	 */
	public void setavailableSeats(int availableSeats){
		this.availableSeats = availableSeats;
	}
	
	/**
	 * This method sets the price of the flight to a new value.
	 * @param price referes to the new price of the flight.
	 */
	public void setprice(double price){
		this.price = price;
	}	
	
	/**
	 * This method returns the flightNo.
	 * @return the flights number.
	 */
	public int getflightNo(){
		return flightNo;
	}
	
	/**
	 * This method returns the flights source.
	 * @return the flights source
	 */
	public String getsource(){
		return source;
	}
	
	/**
	 * This method refers to the flights destination
	 * @return the flights destination
	 */
	public String getdestination(){
		return destination;
	}
	
	/**
	 * This method returns the flights date
	 * @return the flights date
	 */
	public String getdate(){
		return date;
	}
	
	/**
	 * This method returns the flights time
	 * @return the flights time
	 */
	public String gettime(){
		return time;
	}
	
	/**
	 * This method returns the flights duration
	 * @return the flights duration.
	 */
	public String getduration(){
		return duration;
	}
	
	/**
	 * This method returns the total number of seats on the flight
	 * @return the flights total number of seats
	 */
	public int gettotalSeats(){
		return totalSeats;
	}
	
	/**
	 * This method returns the flights number of available seats.
	 * @return the flights number of available seats.
	 */
	public int getavailableSeats(){
		return availableSeats;
	}
	
	/**
	 * This method returns the price of the flight
	 * @return the price of the flight ticket
	 */
	public double getprice() {
		return price;
	}
}
