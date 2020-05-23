// Server.java
import java.io.*;
import java.sql.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.InterruptedException;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *This class contains the Server object which is essentially the brains behind the program
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class Server implements Runnable {
	
	/**
	 * socketOut is the output stream that writes to the client
	 */
	private ObjectOutputStream socketOut;
	 
	/**
	 * socketIn is the input stream that reads from the client
	 */
	private ObjectInputStream socketIn;
	
	/**
	 * Is a system to make sure ticket is not cancelled twice
	 */
	private CancelOperator cancel;
	
	/**
	 * Is a system to make sure a flight is not double booked
	 */
	private BookingOperator book;
	
	/**
	 * Is a boolean that returns true if the server is running and false otherwise
	 */
	private boolean running;
	
	/**
	 * Is the connection to our database
	 */
	private Connection myConn;
	
	/**
	 * The constructor for the Server which contains the essential "brains" behind the program
	 * @param aSocket is the socket connecting the server to the client
	 * @param out is the output stream that writes to the client
	 * @param in is the input stream that reads from the client
	 * @param cancel is a system to make sure ticket is not cancelled twice
	 * @param book is a system to make sure a flight is not double booked
	 */
	public Server (Socket aSocket, ObjectOutputStream out, ObjectInputStream in, CancelOperator cancel, BookingOperator book) {
		socketOut = out;
		socketIn = in;
		running = true;
		myConn = null;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/flight_management", "root", "mysql");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.cancel = cancel;
		this.book = book;
	}
	
	/**  
	 * {@inheritDoc}  
	 */
	@Override
	public void run () {
		while (running) {
			try {
				String message = (String)socketIn.readObject();
				String[] messages = message.split("\\s+");
				if (messages[0].equals("SIGNIN")) {
					signIn(messages);
				} else if (messages[0].equals("NEWUSER")) {
					newUser(messages);
				} else if (messages[0].equals("FLIGHTS")) {
					retrieveFlights(messages);
				} else if (messages[0].equals("BOOK")) {
					bookTicket(messages);
				} else if (messages[0].equals("TICKETS")) {
					retrieveTickets(messages);
				} else if (messages[0].equals("CANCEL")) {
					cancelTicket(messages);
				} else if (messages[0].equals("ADD")) {
					addFlight();
				} else if (messages[0].equals("ADDS")) {
					addManyFlights();
				} else if (messages[0].equals("QUIT")) {
					running = false;
				}
			} catch (IOException e) {
				System.err.println(e.getStackTrace());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method verifies a users username and password
	 * @param messaged is the users input username and password
	 */
	public void signIn (String[] messaged) {
		if (messaged.length != 3) {
			try {
				socketOut.writeObject(new String ("false"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		PreparedStatement stmt = null;
		try {
			stmt = myConn.prepareStatement("SELECT * FROM users WHERE usernames = ? AND passwords = ?");
			stmt.setString(1, messaged[1]);
			stmt.setString(2, messaged[2]);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("admin").equals("a")) {
					socketOut.writeObject(new String ("truea"));
				} else {
					socketOut.writeObject(new String ("truec"));
				}
			} else {
				socketOut.writeObject(new String ("false"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will create a new user account
	 * @param messaged is the input information required to make the account
	 */
	public void newUser (String[] messaged) {
		if (messaged.length != 4) {
			try {
				socketOut.writeObject(new String ("false"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		PreparedStatement stmt = null;
		try {
			stmt = myConn.prepareStatement("SELECT * FROM users WHERE usernames = ?");
			stmt.setString(1, messaged[1]);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				socketOut.writeObject(new String ("false"));
			} else {
				stmt = myConn.prepareStatement("INSERT INTO users (usernames, passwords, admin) VALUES (?, ?, ?)");
				stmt.setString(1, messaged[1]);
				stmt.setString(2, messaged[2]);
				stmt.setString(3, messaged[3]);
				stmt.executeUpdate();
				socketOut.writeObject(new String ("true"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will add a new flight into the data base
	 */
	public void addFlight () {
		try {
			Flight newFlight = (Flight)socketIn.readObject();
			PreparedStatement stmt = myConn.prepareStatement("INSERT INTO flights (totalSeats, availableSeats, source, destination, date, time, duration, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setInt(1,  newFlight.gettotalSeats());
			stmt.setInt(2,  newFlight.getavailableSeats());
			stmt.setString(3,  newFlight.getsource());
			stmt.setString(4, newFlight.getdestination());
			stmt.setString(5, newFlight.getdate());
			stmt.setString(6, newFlight.gettime());
			stmt.setString(7, newFlight.getduration());
			stmt.setDouble(8, newFlight.getprice());
			stmt.executeUpdate();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will revieve a textfile of flights/many flight objects and add them all to the database
	 */
	public void addManyFlights () {
		try {
			FlightStorage newFlights = (FlightStorage)socketIn.readObject();
			Vector <Flight> myFlights = new Vector <Flight> (newFlights.getflights());
			for (int i = 0; i < myFlights.size(); i++) {
				PreparedStatement stmt = myConn.prepareStatement("INSERT INTO flights (totalSeats, availableSeats, source, destination, date, time, duration, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				stmt.setInt(1,  myFlights.elementAt(i).gettotalSeats());
				stmt.setInt(2,  myFlights.elementAt(i).getavailableSeats());
				stmt.setString(3,  myFlights.elementAt(i).getsource());
				stmt.setString(4, myFlights.elementAt(i).getdestination());
				stmt.setString(5, myFlights.elementAt(i).getdate());
				stmt.setString(6, myFlights.elementAt(i).gettime());
				stmt.setString(7, myFlights.elementAt(i).getduration());
				stmt.setDouble(8, myFlights.elementAt(i).getprice());
				stmt.executeUpdate();
			}
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will access the database and recieve flights based on what the user searched for
	 * @param messaged will be the flight criteria that the user searched for
	 */
	public void retrieveFlights (String[] messaged) {
		PreparedStatement stmt = null;
		try {
			if (!messaged[1].equals("0")) {
				if (!messaged[2].equals("0")) {
					if (!messaged[3].equals("0")) {
						stmt = myConn.prepareStatement("SELECT * FROM flights WHERE source = ? AND destination = ? AND date = ?");
						stmt.setString(1,  messaged[1]);
						stmt.setString(2,  messaged[2]);
						stmt.setString(3,  messaged[3]);
					} else {
						stmt = myConn.prepareStatement("SELECT * FROM flights WHERE source = ? AND destination = ?");
						stmt.setString(1,  messaged[1]);
						stmt.setString(2,  messaged[2]);
					}
				} else {
					if (!messaged[3].equals("0")) {
						stmt = myConn.prepareStatement("SELECT * FROM flights WHERE source = ? AND date = ?");
						stmt.setString(1,  messaged[1]);
						stmt.setString(2,  messaged[3]);
					} else {
						stmt = myConn.prepareStatement("SELECT * FROM flights WHERE source = ?");
						stmt.setString(1,  messaged[1]);
					}
				}
			} else {
				if (!messaged[2].equals("0")) {
					if (!messaged[3].equals("0")) {
						stmt = myConn.prepareStatement("SELECT * FROM flights WHERE destination = ? AND date = ?");
						stmt.setString(1,  messaged[2]);
						stmt.setString(2,  messaged[3]);
					} else {
						stmt = myConn.prepareStatement("SELECT * FROM flights WHERE destination = ?");
						stmt.setString(1,  messaged[2]);
					}
				} else {
					if (!messaged[3].equals("0")) {
						stmt = myConn.prepareStatement("SELECT * FROM flights WHERE date = ?");
						stmt.setString(1,  messaged[3]);
					} else {
						stmt = myConn.prepareStatement("SELECT * FROM flights");
					}
				}
			}
			FlightStorage myFlights = new FlightStorage();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Flight newFlight = new Flight(rs.getInt("flightNumber"), rs.getString("source"), rs.getString("destination"), rs.getString("date"), rs.getString("time"), rs.getString("duration"),
						rs.getInt("totalSeats"), rs.getInt("availableSeats"), rs.getDouble("price"));
				myFlights.addFlight(newFlight);
			}
			socketOut.writeObject(myFlights);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This lets the user know if the booking of a ticket was successful or not
	 */
	public void bookTicket (String[] messaged) {
		try{
			boolean isBooked = book.bookTicket(messaged[1], messaged[2], messaged[3], messaged[4], Integer.parseInt(messaged[5]));
			if (isBooked) {
				socketOut.writeObject(new String("true"));
			} else {
				socketOut.writeObject(new String("false"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will send the user all of their tickets they have signed up for
	 */
	public void retrieveTickets (String[] messaged) {
		try {
			PreparedStatement stmt = null;
			String state = new String("SELECT * FROM tickets");
			boolean first = true;
			if (!messaged[1].equals("0")) {
				if (first) {
					state += " WHERE user = ?";
				} else {
					state += " AND user = ?";
				}
			}
			if (!messaged[2].equals("0")) {
				if (first) {
					state += " WHERE flightNumber = ?";
				} else {
					state += " AND flightNumber = ?";
				}
			}
			if (!messaged[3].equals("0")) {
				if (first) {
					state += " WHERE source = ?";
				} else {
					state += " AND source = ?";
				}
			}
			if (!messaged[4].equals("0")) {
				if (first) {
					state += " WHERE destination = ?";
				} else {
					state += " AND destination = ?";
				}
			}
			if (!messaged[5].equals("0")) {
				if (first) {
					state += " WHERE date = ?";
				} else {
					state += " AND date = ?";
				}
			}
			if (!messaged[6].equals("0")) {
				if (first) {
					state += " WHERE price = ?";
				} else {
					state += " AND price = ?";
				}
			}
			if (!messaged[7].equals("0")) {
				if (first) {
					state += " WHERE lastname = ?";
				} else {
					state += " AND lastname = ?";
				}
			}
			stmt = myConn.prepareStatement(state);
			int j = 1;
			for (int i = 1; i < 8; i++)	{
				if (!messaged[i].equals("0")) {
					if (i == 6) {
						stmt.setDouble(j, Double.parseDouble(messaged[i]));
						j++;
					} else if (i == 2) {
						stmt.setInt(j,  Integer.parseInt(messaged[i]));
						j++;
					} else {
						stmt.setString(j, messaged[i]);
						j++;
					}
				}
			}
			TicketStorage myTickets = new TicketStorage();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Ticket newTicket = new Ticket(rs.getString("user"), rs.getString("name"), rs.getString("lastname"), rs.getString("DOB"), rs.getInt("ticketID"), rs.getInt("flightNumber"),
						rs.getString("source"), rs.getString("destination"), rs.getString("date"), rs.getString("time"), rs.getString("duration"), rs.getDouble("price"));
				myTickets.addTicket(newTicket);
			}
			socketOut.writeObject(myTickets);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will let the user know if the cancellation of their ticket was successful
	 */
	public void cancelTicket (String[] messaged) {
		try{
			boolean isCancelled = cancel.cancelFlight(Integer.parseInt(messaged[1]));
			if (isCancelled) {
				socketOut.writeObject(new String("true"));
			} else {
				socketOut.writeObject(new String("false"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method initializes a thread pool and initializes the server
	 */
	public static void main(String[] args) {
		CancelOperator cancel = new CancelOperator();
		BookingOperator book = new BookingOperator();
		ServerSocket aServerSocket = null;
		try {
			aServerSocket = new ServerSocket(5454);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
		System.out.println("Server is running...");
		
		ExecutorService executor = Executors.newFixedThreadPool(10); // Increase later?
		boolean running = true;
		while (running) {
			try {
				Socket aSocket = aServerSocket.accept();
				System.out.println("Client Connected...");
				ObjectInputStream socketIn = new ObjectInputStream(aSocket.getInputStream());
				ObjectOutputStream socketOut = new ObjectOutputStream(aSocket.getOutputStream());
				Runnable thread = new Server(aSocket, socketOut, socketIn, cancel, book);
				executor.execute(thread);
			} catch (IOException e) {
				System.err.println(e.getStackTrace());
			}
		}
		executor.shutdown();
		try {
			Thread.sleep(1000);
			aServerSocket.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		} catch (InterruptedException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
}