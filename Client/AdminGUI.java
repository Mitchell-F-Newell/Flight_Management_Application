// AdminGUI.java
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *This class contains the AdminGUI object which creates a user interface for the administrators
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class AdminGUI extends ClientGUI {
	
	/**
	 * deleteTicket refers to the button that will delete a ticket
	 * addFile refers to the button that will add the flights from a file
	 * addLight refers to the button that will add a flight
	 */
	private CustomButton deleteTicket, addFile, addFlight;
	
	/**
	 * These tickets allow the user to switch tabs between the different admin windows
	 */
	private ImageButton flights2, tickets2, add0, add1, add2;
	
	/**
	 * This is a TabPanel that adds a decorative background to the GUI
	 */
	private TabPanel topUpper2;
	
	/**
	 * This custom panel allows a background image to be added to the GUI
	 */
	private CustomPanel background2;
	
	/**
	 * These text fields all represent or allow a user to input information into the program
	 */
	private CustomTextField textFile, flightSource, flightDest, duration, date, time, price, numSeat;
	
	/**
	 * This class acts as a tagging interface, waiting for an event to occur.
	 */
	private class AdminEventListener implements ActionListener {
		
		/**
		 * the AdminGui object that contains the display/gui.
		 */
		AdminGUI display;
		
		/**
		 * The constructor for the class, that sets the AdminGUI object display.
		 * @param d the new display of type AdminGUI.
		 */
		public AdminEventListener (AdminGUI d) {
			display = d;
		}
		
		/**
		 * When an action occurs, this method will direct/complete the desired action.
		 * @param e the ActionEvent that occured within the gui.
		 */
		public void actionPerformed (ActionEvent e) {
			if (e.getSource() == flights0 || e.getSource() == flights1 || e.getSource() == flights2) {
				display.displayTab0();
			} else if (e.getSource() == tickets0 || e.getSource() == tickets1 || e.getSource() == tickets2) {
				display.displayTab1();
			} else if (e.getSource() == add0 || e.getSource() == add1 || e.getSource() == add2) {
				display.displayTab2();
			} else if (e.getSource() == addFlight) {
				display.addOneFlight();
			} else if (e.getSource() == addFile) {
				display.addManyFlights();
			} else if (e.getSource() == deleteTicket) {
				display.cancelTicket();
			}
		}
	}
	
	/**
	 * The constructor for the AdminGui class which initializes and creates the administrator user interface
	 * @param user refers to the username of the user
	 * @param sock is the socket connecting the GUI to the server
	 * @param out is the output stream that writes to the server
	 * @param in is the input stream from the server.
	 */
	public AdminGUI (String user, Socket sock, ObjectOutputStream out, ObjectInputStream in) {
		super(user, sock, out, in);
		isAdmin = true;
		
		// Sets the panel navigation buttons to the new version of the event listener.
		flights0.removeActionListener(buttons);
		flights1.removeActionListener(buttons);
		tickets0.removeActionListener(buttons);
		tickets1.removeActionListener(buttons);
		buttons = new AdminEventListener(this);
		flights0.addActionListener(buttons);
		flights1.addActionListener(buttons);
		tickets0.addActionListener(buttons);
		tickets1.addActionListener(buttons);
		
		// Creates a button to switch between to the add panel. 
		add0 = new ImageButton(10, "Add Flights");
		add0.setSymbol1(new ImageIcon("Icons/Add2.png"));
		add0.setSymbol2(new ImageIcon("Icons/Add1.png"));
		add0.addActionListener(buttons);
		topUpper0.add(add0);
		
		// Creates a button to switch between to the add panel and adds a button to delete tickets.
		add1 = new ImageButton(10, "Add Flights");
		add1.setSymbol1(new ImageIcon("Icons/Add2.png"));
		add1.setSymbol2(new ImageIcon("Icons/Add1.png"));
		add1.addActionListener(buttons);
		topUpper1.add(add1);
		JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
		tempPanel.setOpaque(false);
		deleteTicket = new CustomButton(18, "Delete Ticket");
		deleteTicket.addActionListener(buttons);
		tempPanel.add(deleteTicket);
		bottomRight.add(tempPanel);
		
		// ***Start of the Add Flight panel construction.***
		background2 = new CustomPanel(new BorderLayout(), "Images/beaches.png");
		
		// *Start of top of Add Flight panel construction.*
		// This block creates a section with buttons to switch between panels.
		topUpper2 = new TabPanel(new FlowLayout(FlowLayout.LEFT, 10, 10), new Color (128, 201, 177, 205));
		flights2 = new ImageButton(10, "Flights");
		flights2.setSymbol1(new ImageIcon("Icons/Plane2.png"));
		flights2.setSymbol2(new ImageIcon("Icons/Plane1.png"));
		flights2.addActionListener(buttons);
		topUpper2.add(flights2);
		tickets2 = new ImageButton(10, "Tickets");
		tickets2.setSymbol1(new ImageIcon("Icons/Ticket2.png"));
		tickets2.setSymbol2(new ImageIcon("Icons/Ticket1.png"));
		tickets2.addActionListener(buttons);
		topUpper2.add(tickets2);
		add2 = new ImageButton(10, "Add Flights");
		add2.setSymbol1(new ImageIcon("Icons/Add2.png"));
		add2.setSymbol2(new ImageIcon("Icons/Add1.png"));
		add2.addActionListener(buttons);
		topUpper2.add(add2);
		background2.add("North", topUpper2);
		// *End of top of Add Flight panel construction.*
		
		// *Start of bottom of Add Flight panel construction.*
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setOpaque(false);
		
		// This block creates an interface for adding flights from a file.
		TabPanel bottomUpper = new TabPanel(new GridLayout(0, 1), new Color (128, 201, 177, 205));
		JLabel tempLabel = new JLabel("   Add Multiple Flights");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		bottomUpper.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempLabel = new JLabel("   Text File Name: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		textFile = new CustomTextField(25, "filename.txt");
		textFile.setSymbol1(new ImageIcon("Icons/File2.png"));
		textFile.setSymbol2(new ImageIcon("Icons/File1.png"));
		tempPanel.add(textFile);
		bottomUpper.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempLabel = new JLabel("H");
		tempLabel.setFont(new Font("Candara", Font.PLAIN, 35));
		tempLabel.setForeground(new Color (53, 50, 48, 0));
		tempPanel.add(tempLabel);
		addFile = new CustomButton(15, "Add Flights" );
		addFile.addActionListener(buttons);
		tempPanel.add(addFile);
		tempLabel = new JLabel("H");
		tempLabel.setFont(new Font("Candara", Font.PLAIN, 35));
		tempLabel.setForeground(new Color (53, 50, 48, 0));
		tempPanel.add(tempLabel);
		bottomUpper.add(tempPanel);
		bottom.add("North", bottomUpper);
		
		// This block creates an interface for adding a flight from data fields.
		TabPanel bottomLower = new TabPanel(new GridLayout(0, 1), new Color (128, 201, 177, 205));
		tempLabel = new JLabel("   Add One Flight");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		bottomLower.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempLabel = new JLabel("   Flight Source: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		flightSource = new CustomTextField(20, "Start Location");
		flightSource.setSymbol1(new ImageIcon("Icons/Location2.png"));
		flightSource.setSymbol2(new ImageIcon("Icons/Location1.png"));
		tempPanel.add(flightSource);
		tempLabel = new JLabel("        Flight Destination: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		flightDest = new CustomTextField(20, "End Location");
		flightDest.setSymbol1(new ImageIcon("Icons/Location2.png"));
		flightDest.setSymbol2(new ImageIcon("Icons/Location1.png"));
		tempPanel.add(flightDest);
		bottomLower.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempLabel = new JLabel("   Flight Date: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		date = new CustomTextField(10, "dd/mm/yyyy");
		date.setSymbol1(new ImageIcon("Icons/Calendar2.png"));
		date.setSymbol2(new ImageIcon("Icons/Calendar1.png"));
		tempPanel.add(date);
		tempLabel = new JLabel("   Flight Time: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		time = new CustomTextField(10, "hh:mm");
		time.setSymbol1(new ImageIcon("Icons/Time2.png"));
		time.setSymbol2(new ImageIcon("Icons/Time1.png"));
		tempPanel.add(time);
		tempLabel = new JLabel("   Flight Duration: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		duration = new CustomTextField(10, "hh:mm");
		duration.setSymbol1(new ImageIcon("Icons/Time2.png"));
		duration.setSymbol2(new ImageIcon("Icons/Time1.png"));
		tempPanel.add(duration);
		bottomLower.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempLabel = new JLabel("   Flight Seats: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		numSeat = new CustomTextField(20, "Number of Seats");
		numSeat.setSymbol1(new ImageIcon("Icons/Number2.png"));
		numSeat.setSymbol2(new ImageIcon("Icons/Number1.png"));
		tempPanel.add(numSeat);
		tempLabel = new JLabel("        Flight Cost: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		price = new CustomTextField(20, "Price");
		price.setSymbol1(new ImageIcon("Icons/Money2.png"));
		price.setSymbol2(new ImageIcon("Icons/Money1.png"));
		tempPanel.add(price);
		bottomLower.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempLabel = new JLabel("H");
		tempLabel.setFont(new Font("Candara", Font.PLAIN, 35));
		tempLabel.setForeground(new Color (53, 50, 48, 0));
		tempPanel.add(tempLabel);
		addFlight = new CustomButton(15, "Add Flight" );
		addFlight.addActionListener(buttons);
		tempPanel.add(addFlight);
		tempLabel = new JLabel("H");
		tempLabel.setFont(new Font("Candara", Font.PLAIN, 35));
		tempLabel.setForeground(new Color (53, 50, 48, 0));
		tempPanel.add(tempLabel);
		bottomLower.add(tempPanel);
		JPanel empty = new JPanel(new BorderLayout());
		empty.setOpaque(false);
		empty.add("North", bottomLower);
		bottom.add("Center", empty);
		background2.add("Center", bottom);
		// *End of bottom of Add Flight panel construction.*
		// ***End of the Add Flight panel construction.***
		
	}
	
	/**
	 * This method allows the administrator to enter tab0 when the flights tab is pressed 
	 */
	public void displayTab0 () {
		super.displayTab0();
		add0.setActive(false);
		add1.setActive(false);
		flights2.setActive(true);
		tickets2.setActive(false);
		add2.setActive(false);
		main.setVisible(true);
	}
	
	/**
	 * This method allows the administrator to enter the tickets tab if tab button is pressed
	 */
	public void displayTab1 () {
		super.displayTab1();
		add0.setActive(false);
		add1.setActive(false);
		flights2.setActive(false);
		tickets2.setActive(false);
		add2.setActive(false);
		main.setVisible(true);
	}
	
	/**
	 * This methow allows the administrator to enter the add flight tab.
	 */
	public void displayTab2 () {
		flights0.setActive(false);
		tickets0.setActive(false);
		add0.setActive(true);
		flights1.setActive(false);
		tickets1.setActive(false);
		add1.setActive(true);
		flights2.setActive(false);
		tickets2.setActive(false);
		add2.setActive(true);
		main.setContentPane(background2);
		main.setVisible(true);
	}
	
	/**
	 * This method allows the administrator to add a singular flight by filling in the text fields and pressing the add flight button
	 */
	public void addOneFlight () {
		LocalDate localDate = LocalDate.now();
        DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate);
		String temp = date.getText();
		String[] newDate = temp.split("/");
		temp = localDate.toString();
		String[] currentDate = temp.split("-");
		System.out.println(currentDate[1]);
		if (Integer.parseInt(newDate[2]) < Integer.parseInt(currentDate[0])) {
			CustomError invalid = new CustomError("Date must be after the current date.");
		} else if (Integer.parseInt(newDate[2]) == Integer.parseInt(currentDate[0]) && Integer.parseInt(newDate[1]) < Integer.parseInt(currentDate[1])) {
			CustomError invalid = new CustomError("Date must be after the current date.");
		} else if (Integer.parseInt(newDate[2]) == Integer.parseInt(currentDate[0]) && Integer.parseInt(newDate[1]) == Integer.parseInt(currentDate[1]) && Integer.parseInt(newDate[0]) < Integer.parseInt(currentDate[2])) {
			CustomError invalid = new CustomError("Date must be after the current date.");
		} else {
			Flight newFlight = new Flight(0, flightSource.getText(), flightDest.getText(), date.getText(), time.getText(), duration.getText(),
											Integer.parseInt(numSeat.getText()), Integer.parseInt(numSeat.getText()), Double.parseDouble(price.getText()));
			try {
				socketOut.writeObject(new String("ADD"));
				socketOut.writeObject(newFlight);
			} catch (IOException e) {
				e.printStackTrace();
			}
			CustomError success = new CustomError("Flight successfully added.");
		}
	}
	
	/**
	 * This method allows the administrator to add many flights through a text file (using the add flights button)
	 */
	public void addManyFlights () {
		FlightStorage myFlights = new FlightStorage();
		Scanner inFile = null;
		try {
			inFile = new Scanner(new FileReader(textFile.getText()));
		} catch (IOException e) {
			CustomError invalid = new CustomError("File does not exist.");
			return;
		}
		int added = 0;
		while (inFile.hasNextLine()) {
			String temp = inFile.nextLine();
			String[] tempSplit = temp.split("\\s+");
			LocalDate localDate = LocalDate.now();
			DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate);
			String temp2 = tempSplit[2];
			String[] newDate = temp2.split("/");
			temp2 = localDate.toString();
			String[] currentDate = temp2.split("-");
			System.out.println(currentDate[1]);
			if (Integer.parseInt(newDate[2]) < Integer.parseInt(currentDate[0])) {
			} else if (Integer.parseInt(newDate[2]) == Integer.parseInt(currentDate[0]) && Integer.parseInt(newDate[1]) < Integer.parseInt(currentDate[1])) {
			} else if (Integer.parseInt(newDate[2]) == Integer.parseInt(currentDate[0]) && Integer.parseInt(newDate[1]) == Integer.parseInt(currentDate[1]) && Integer.parseInt(newDate[0]) < Integer.parseInt(currentDate[2])) {
			} else {
				Flight newFlight = new Flight(0, tempSplit[0], tempSplit[1], tempSplit[2], tempSplit[3], tempSplit[4],
											Integer.parseInt(tempSplit[5]), Integer.parseInt(tempSplit[5]), Double.parseDouble(tempSplit[6]));
				myFlights.addFlight(newFlight);
				added++;
			}
		}
		try {
			socketOut.writeObject(new String("ADDS"));
			socketOut.writeObject(myFlights);
		} catch (IOException e) {
			e.printStackTrace();
		}
		CustomError success = new CustomError(added + " Flights successfully added.");
	}
	
	/**
	 * This method allows the administrator to delete/cancel a ticket when the cancel ticket button is pressed
	 */
	public void cancelTicket () {
		int selected = ticketResults.getSelectedIndex();
		Vector <Ticket> myTickets = new Vector <Ticket> (ticketStore.gettickets());
		if (selected != -1) {
			try {
				socketOut.writeObject(new String("CANCEL " + myTickets.elementAt(selected).ticketID()));
				String message = (String)socketIn.readObject();
				if (message.equals("true")) {
					CustomError booked = new CustomError("Ticket successfully cancelled.");
				} else {
					CustomError booked = new CustomError("This ticket has already been cancelled.");
				}
				super.getTickets();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}