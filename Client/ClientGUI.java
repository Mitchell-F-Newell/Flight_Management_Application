// ClientGUI.java
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.Socket;
/**
 *This class contains the ClientGUI object which creates a user interface for the user
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class ClientGUI {
	
	/**
	 * Is a FlightStorage object that contains many flights
	 */
	protected FlightStorage flightStore;
	
	/**
	 * Is a TicketStorage object that holds many flights
	 */
	protected TicketStorage ticketStore;
	
	/**
	 * socketOut is the output stream that writes to the server
	 */
	protected ObjectOutputStream socketOut;
	
	/**
	 * aSocket is the socket connecting the GUI to the server
	 */
	protected Socket aSocket;
	
	/**
	 * in is the input stream from the server.
	 */
	protected ObjectInputStream socketIn;
	
	/**
	 * Listens and waits for an event to occur which it will register
	 */
	protected ListListener listListen;
	
	/**
	 * The main frame of the GUI
	 */
	protected JFrame main;
	
	/**
	 * The various textFields which take or diplay information on the flight and or tickets
	 */
	private CustomTextField searchFrom, searchTo, searchDepart, fltNo, fltStart, fltDest, fltDate,
							fltTime, fltDur, fltSeats, fltAvailable, fltCost, searchTicketFrom, searchTicketTo,
							searchTicketDepart, searchNumber, searchCost, searchName;
	
	/**
	 * The interactable Buttons used in the GUI
	 */
	private CustomButton searchFlights, searchTickets, buyTicket, clearFlights, clearTickets, viewTicket;
	
	/**
	 * The default text used within the gui
	 */
	protected Font defaultFont;
	
	/**
	 * These tickets allow the user to switch tabs between the different user windows
	 */
	protected ImageButton flights0, tickets0, flights1, tickets1;
	
	/**
	 * These are TabPanels that add a decorative background to the GUI
	 */
	protected TabPanel topUpper0, topUpper1, bottomRight;
	
	/**
	 * The list of flights and tickets that will be displayed within the gui
	 */
	protected JList <String> flightResults, ticketResults;
	
	/**
	 * The following are a list to diplay the elements
	 */
	private ListSelectionModel flightSelect, ticketSelect;
	
	/**
	 * Actionlistener waits for either buttons or searchers to be interacted with and will follow through with the correct corresponding methods.
	 */
	protected ActionListener buttons, searchers;
	
	/**
	 * customPanel allows for the multiple backgrounds to be implemented to the client gui.
	 */
	private CustomPanel background0, background1;
	
	/**
	 * refers to the username of the user
	 */
	private String username;
	
	/** 
	 * boolean value that returns true if the user is an administrator and false otherwise
	 */
	protected boolean isAdmin;
	
	/**
	 * This class acts as a tagging interface, waiting for an event to occur.
	 */
	private class EventListener implements ActionListener {
		
		/**
		 * the ClientGui object that contains the display/gui.
		 */
		ClientGUI display;
		
		/**
		 * The constructor for the class, that sets the ClientGUI object display.
		 * @param d the new display of type ClientGUI.
		 */
		public EventListener (ClientGUI d) {
			display = d;
		}
		
		/**
		 * When an action occurs, this method will direct/complete the desired action.
		 * @param e the ActionEvent that occured within the gui.
		 */
		public void actionPerformed (ActionEvent e) {
			if (e.getSource() == flights0 || e.getSource() == flights1) {
				display.displayTab0();
			} else if (e.getSource() == tickets0 || e.getSource() == tickets1) {
				display.displayTab1();
			} else if (e.getSource() == clearFlights) {
				display.clearFlight();
			} else if (e.getSource() == clearTickets) {
				display.clearTicket();
			} else if (e.getSource() == searchFlights) {
				display.getFlights();
			} else if (e.getSource() == buyTicket) {
				display.buyTicket();
			} else if (e.getSource() == searchTickets) {
				display.getTickets();
			} else if (e.getSource() == viewTicket) {
				display.viewTick();
			}
		}
	}
	
	/**
	 * This class lets you interact with the list of Flights and Tickets
	 */
	private class ListListener implements ListSelectionListener {
		
		/**
		 * the ClientGui object that contains the display/gui.
		 */
		ClientGUI display;
		
		/**
		 * The constructor for the listlistener object sets the Client object display.
		 */
		public ListListener (ClientGUI d) {
			display = d;
		}
		
		/**
		 * This method takes into account what you object you are interacting with from the list
		 * @param e A listSelection event object that will trigger if you interact with an object within the list
		 */
		public void valueChanged (ListSelectionEvent e) {
			if (e.getSource() == flightSelect) {
				int selected = display.flightResults.getSelectedIndex();
				if (selected != -1) {
					Vector <Flight> tempFlights = flightStore.getflights();
					display.fltNo.setText("" + tempFlights.elementAt(selected).getflightNo());
					display.fltStart.setText(tempFlights.elementAt(selected).getsource());
					display.fltDest.setText(tempFlights.elementAt(selected).getdestination());
					display.fltDate.setText(tempFlights.elementAt(selected).getdate());
					display.fltTime.setText(tempFlights.elementAt(selected).gettime());
					display.fltDur.setText(tempFlights.elementAt(selected).getduration());
					display.fltSeats.setText("" + tempFlights.elementAt(selected).gettotalSeats());
					display.fltAvailable.setText("" + tempFlights.elementAt(selected).getavailableSeats());
					display.fltCost.setText("" + tempFlights.elementAt(selected).getprice());
				}
			}
		}
	}
	
	/**
	 * The constructor for the ClientGui class which initializes and creates the user interface
	 * @param user refers to the username of the user
	 * @param sock is the socket connecting the GUI to the server
	 * @param out is the output stream that writes to the server
	 * @param in is the input stream from the server.
	 */
	public ClientGUI (String user, Socket sock, ObjectOutputStream out, ObjectInputStream in) {
		aSocket = sock;
		socketOut = out;
		socketIn = in;
		
		// Initialize fonts and listeners for the GUI.
		defaultFont = new Font("Candara", Font.BOLD, 14);
		buttons = new EventListener(this);
		searchers = new EventListener(this);
		username = user;
		isAdmin = false;
		flightStore = null;
		
		// Set up main frame settings.
		main = new JFrame();
		main.setTitle("Flight Managment System");
		main.setIconImage(new ImageIcon("Icons/Travel.png").getImage());
		main.setSize(875, 700);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		main.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
				{
					quit();
				}
		});
		main.setLayout(new BorderLayout());
		
		// ***Start of the Flight Search panel construction.***
		background0 = new CustomPanel(new BorderLayout(), "Images/beaches.png");
		
		// *Start of top of Flight Search panel construction.*
		JPanel top = new JPanel(new BorderLayout());
		top.setOpaque(false);
		
		// This block creates a section with buttons to switch between panels.
		topUpper0 = new TabPanel(new FlowLayout(FlowLayout.LEFT, 10, 10), new Color (128, 201, 177, 205));
		flights0 = new ImageButton(10, "Flights");
		flights0.setSymbol1(new ImageIcon("Icons/Plane2.png"));
		flights0.setSymbol2(new ImageIcon("Icons/Plane1.png"));
		flights0.addActionListener(buttons);
		topUpper0.add(flights0);
		tickets0 = new ImageButton(10, "Tickets");
		tickets0.setSymbol1(new ImageIcon("Icons/Ticket2.png"));
		tickets0.setSymbol2(new ImageIcon("Icons/Ticket1.png"));
		tickets0.addActionListener(buttons);
		topUpper0.add(tickets0);
		top.add("North", topUpper0);
		
		// This block creates a section with fields to enter search parameters.
		TabPanel topLower = new TabPanel(new GridLayout(0, 2), new Color (128, 201, 177, 205));
		topLower.setOpaque(false);
		JLabel tempLabel = new JLabel("   Flying From");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		topLower.add(tempLabel);
		tempLabel = new JLabel("   Flying To");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		topLower.add(tempLabel);
		JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		searchFrom = new CustomTextField(31, "Anywhere");
		searchFrom.setSymbol1(new ImageIcon("Icons/Location2.png"));
		searchFrom.setSymbol2(new ImageIcon("Icons/Location1.png"));
		tempPanel.add(searchFrom);
		topLower.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		searchTo = new CustomTextField(31, "Anywhere");
		searchTo.setSymbol1(new ImageIcon("Icons/Location2.png"));
		searchTo.setSymbol2(new ImageIcon("Icons/Location1.png"));
		tempPanel.add(searchTo);
		topLower.add(tempPanel);
		tempLabel = new JLabel("   Departing");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		topLower.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		topLower.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		searchDepart = new CustomTextField(15, "dd/mm/yyyy");
		searchDepart.setSymbol1(new ImageIcon("Icons/Calendar2.png"));
		searchDepart.setSymbol2(new ImageIcon("Icons/Calendar1.png"));
		tempPanel.add(searchDepart);
		topLower.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		tempPanel.setOpaque(false);
		searchFlights = new CustomButton(13, "Search Flights");
		searchFlights.addActionListener(buttons);
		tempPanel.add(searchFlights);
		clearFlights = new CustomButton(13, "Clear Search");
		clearFlights.addActionListener(searchers);
		tempPanel.add(clearFlights);
		buyTicket = new CustomButton(13, "Buy Ticket");
		buyTicket.addActionListener(buttons);
		tempPanel.add(buyTicket);
		topLower.add(tempPanel);
		tempLabel = new JLabel("");
		tempLabel.setFont(new Font("Candara", Font.PLAIN, 2));
		topLower.add(tempLabel);
		top.add("Center", topLower);
		
		background0.add("North", top);
		// *End of top of Flight Search panel construction.*
		
		// *Start of bottom of Flight Search panel construction.*
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setOpaque(false);
		
		// This block creates a side section to display information from each flight.
		bottomRight = new TabPanel(new GridLayout(0, 1), new Color (128, 201, 177, 205));		
		tempLabel = new JLabel("   Flight Number:");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		bottomRight.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		fltNo = new CustomTextField(6, "");
		fltNo.setForeground(new Color(53, 50, 48));
		fltNo.setEditable(false);
		tempPanel.add(fltNo);
		bottomRight.add(tempPanel);
		tempLabel = new JLabel("   Flight Source:");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		bottomRight.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		fltStart = new CustomTextField(16, "");
		fltStart.setForeground(new Color(53, 50, 48));
		fltStart.setEditable(false);
		tempPanel.add(fltStart);
		bottomRight.add(tempPanel);
		tempLabel = new JLabel("   Flight Destination:");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		bottomRight.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		fltDest = new CustomTextField(16, "");
		fltDest.setForeground(new Color(53, 50, 48));
		fltDest.setEditable(false);
		tempPanel.add(fltDest);
		bottomRight.add(tempPanel);
		tempLabel = new JLabel("   Flight Date | Time | Duration:");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		bottomRight.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		fltDate = new CustomTextField(6, "");
		fltDate.setForeground(new Color(53, 50, 48));
		fltDate.setEditable(false);
		tempPanel.add(fltDate);
		fltTime = new CustomTextField(3, "");
		fltTime.setForeground(new Color(53, 50, 48));
		fltTime.setEditable(false);
		tempPanel.add(fltTime);
		fltDur = new CustomTextField(3, "");
		fltDur.setForeground(new Color(53, 50, 48));
		fltDur.setEditable(false);
		tempPanel.add(fltDur);
		bottomRight.add(tempPanel);
		tempLabel = new JLabel("   Seats Available | Total:");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		bottomRight.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		fltAvailable = new CustomTextField(4, "");
		fltAvailable.setForeground(new Color(53, 50, 48));
		fltAvailable.setEditable(false);
		tempPanel.add(fltAvailable);
		fltSeats = new CustomTextField(4, "");
		fltSeats.setForeground(new Color(53, 50, 48));
		fltSeats.setEditable(false);
		tempPanel.add(fltSeats);
		bottomRight.add(tempPanel);
		tempLabel = new JLabel("   Flight Price:");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		bottomRight.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		fltCost = new CustomTextField(16, "");
		fltCost.setForeground(new Color(53, 50, 48));
		fltCost.setEditable(false);
		tempPanel.add(fltCost);
		bottomRight.add(tempPanel);
		bottom.add("East", bottomRight);
		
		// This block creates a section to display a list of flights.
		//This part is the unchecked or unsafe operation
		TabPanel bottomLeft = new TabPanel(new FlowLayout(FlowLayout.CENTER, 10, 10), new Color (128, 201, 177, 205));
		flightResults = new JList();
		flightSelect = flightResults.getSelectionModel();
		flightSelect.addListSelectionListener(new ListListener(this));
		flightResults.setFont(new Font("Courier", Font.BOLD, 14));
		JScrollPane tempScroll = new JScrollPane(flightResults);
		flightResults.setBackground(new Color (223, 217, 226));
		tempScroll.setPreferredSize(new Dimension(643, 426));
		bottomLeft.add("Center", tempScroll);
		bottom.add("Center", bottomLeft);
		
		background0.add("Center", bottom);
		// *End of bottom of Flight Search panel construction.*
		// ***End of the Flight Search panel construction.***
		
		
		// ***Start of the Ticket Search panel construction.***
		background1 = new CustomPanel(new BorderLayout(), "Images/beaches.png");
		
		// *Start of top of Ticket Search panel construction.*
		top = new JPanel(new BorderLayout());
		top.setOpaque(false);
		
		// This block creates a section with buttons to switch between panels.
		topUpper1 = new TabPanel(new FlowLayout(FlowLayout.LEFT, 10, 10), new Color (128, 201, 177, 205));
		flights1 = new ImageButton(10, "Flights");
		flights1.setSymbol1(new ImageIcon("Icons/Plane2.png"));
		flights1.setSymbol2(new ImageIcon("Icons/Plane1.png"));
		flights1.addActionListener(buttons);
		topUpper1.add(flights1);
		tickets1 = new ImageButton(10, "Tickets");
		tickets1.setSymbol1(new ImageIcon("Icons/Ticket2.png"));
		tickets1.setSymbol2(new ImageIcon("Icons/Ticket1.png"));
		tickets1.addActionListener(buttons);
		topUpper1.add(tickets1);
		top.add("North", topUpper1);
		
		// This block creates a section with fields to enter search parameters.
		topLower = new TabPanel(new GridLayout(0, 3), new Color (128, 201, 177, 205));
		topLower.setOpaque(false);
		tempLabel = new JLabel("   Flight Number");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		topLower.add(tempLabel);
		tempLabel = new JLabel("   Ticket Source");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		topLower.add(tempLabel);
		tempLabel = new JLabel("   Ticket Destination");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		topLower.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		searchNumber = new CustomTextField(8, "Any Number");
		searchNumber.setSymbol1(new ImageIcon("Icons/Search2.png"));
		searchNumber.setSymbol2(new ImageIcon("Icons/Search1.png"));
		tempPanel.add(searchNumber);
		topLower.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		searchTicketFrom = new CustomTextField(20, "Anywhere");
		searchTicketFrom.setSymbol1(new ImageIcon("Icons/Location2.png"));
		searchTicketFrom.setSymbol2(new ImageIcon("Icons/Location1.png"));
		tempPanel.add(searchTicketFrom);
		topLower.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		searchTicketTo = new CustomTextField(20, "Anywhere");
		searchTicketTo.setSymbol1(new ImageIcon("Icons/Location2.png"));
		searchTicketTo.setSymbol2(new ImageIcon("Icons/Location1.png"));
		tempPanel.add(searchTicketTo);
		topLower.add(tempPanel);
		tempLabel = new JLabel("   Ticket Date");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		topLower.add(tempLabel);
		tempLabel = new JLabel("   Ticket Price");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		topLower.add(tempLabel);
		tempLabel = new JLabel("   Last Name");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		topLower.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		searchTicketDepart = new CustomTextField(10, "dd/mm/yyyy");
		searchTicketDepart.setSymbol1(new ImageIcon("Icons/Calendar2.png"));
		searchTicketDepart.setSymbol2(new ImageIcon("Icons/Calendar1.png"));
		tempPanel.add(searchTicketDepart);
		topLower.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		searchCost = new CustomTextField(10, "Any Price");
		searchCost.setSymbol1(new ImageIcon("Icons/Money2.png"));
		searchCost.setSymbol2(new ImageIcon("Icons/Money1.png"));
		tempPanel.add(searchCost);
		topLower.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		tempPanel.setOpaque(false);
		searchName = new CustomTextField(10, "Last Name");
		searchName.setSymbol1(new ImageIcon("Icons/Search2.png"));
		searchName.setSymbol2(new ImageIcon("Icons/Search1.png"));
		tempPanel.add(searchName);
		topLower.add(tempPanel);
		tempLabel = new JLabel("");
		tempLabel.setFont(new Font("Candara", Font.PLAIN, 2));
		topLower.add(tempLabel);
		top.add("Center", topLower);
		
		background1.add("North", top);
		// *End of top of Ticket Search panel construction.*
		
		// *Start of bottom of Ticket Search panel construction.*
		bottom = new JPanel(new BorderLayout());
		bottom.setOpaque(false);
		
		// This block creates a side section to display the buttons for interacting with tickets.
		bottomRight = new TabPanel(new GridLayout(0, 1), new Color (128, 201, 177, 205));
		tempLabel = new JLabel("");
		tempLabel.setFont(new Font("Candara", Font.PLAIN, 2));
		bottomRight.add(tempLabel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
		tempPanel.setOpaque(false);
		searchTickets = new CustomButton(18, "Search Tickets");
		searchTickets.addActionListener(buttons);
		tempPanel.add(searchTickets);
		bottomRight.add(tempPanel);		
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
		tempPanel.setOpaque(false);
		viewTicket = new CustomButton(18, "View Ticket");
		viewTicket.addActionListener(buttons);
		tempPanel.add(viewTicket);
		bottomRight.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
		tempPanel.setOpaque(false);
		clearTickets = new CustomButton(18, "Clear Search");
		clearTickets.addActionListener(searchers);
		tempPanel.add(clearTickets);
		bottomRight.add(tempPanel);
		bottom.add("East", bottomRight);
		
		// This block creates a section to display a list of tickets.
		//This part is the unchecked or unsafe operation
		bottomLeft = new TabPanel(new FlowLayout(FlowLayout.CENTER, 10, 10), new Color (128, 201, 177, 205));
		ticketResults = new JList();
		ticketSelect = ticketResults.getSelectionModel();
		ticketSelect.addListSelectionListener(new ListListener(this));
		ticketResults.setFont(new Font("Courier", Font.BOLD, 14));
		tempScroll = new JScrollPane(ticketResults);
		tempScroll.setPreferredSize(new Dimension(643, 426));
		ticketResults.setBackground(new Color (223, 217, 226));
		bottomLeft.add("Center", tempScroll);
		bottom.add("Center", bottomLeft);
		
		background1.add("Center", bottom);
		// *End of bottom of Ticket Search panel construction.*
		// ***End of the Ticket Search panel construction.***
		
		// Initialize Selected Panel.
		flights0.setActive(true);
		tickets0.setActive(false);
		flights1.setActive(true);
		tickets1.setActive(false);
		main.setContentPane(background0);
		main.setVisible(true);
	}
	
	/**
	 * This method allows the administrator to enter tab0 when the flights tab is pressed 
	 */
	public void displayTab0 () {
		flights0.setActive(true);
		tickets0.setActive(false);
		flights1.setActive(true);
		tickets1.setActive(false);
		main.setContentPane(background0);
		main.setVisible(true);
	}
	
	/**
	 * This method allows the administrator to enter the tickets tab if tab button is pressed
	 */
	public void displayTab1 () {
		flights0.setActive(false);
		tickets0.setActive(true);
		flights1.setActive(false);
		tickets1.setActive(true);
		main.setContentPane(background1);
		main.setVisible(true);
	}
	
	/**
	 * This method will clear the list of flights from the gui (if a list is currently active)
	 */
	public void clearFlight () {
		searchFrom.setText("");
		searchFrom.requestFocus();
		searchTo.setText("");
		searchTo.requestFocus();
		searchDepart.setText("");
		searchDepart.requestFocus();
		fltNo.setText("");
		fltStart.setText("");
		fltDest.setText("");
		fltDate.setText("");
		fltTime.setText("");
		fltDur.setText("");
		fltSeats.setText("");
		fltAvailable.setText("");
		fltCost.setText("");
		clearFlights.requestFocus();
		Vector resultsVec = new Vector();
		flightResults.setListData(resultsVec);
	}
	
	/**
	 * This method will clear the list of tickets from the gui (if a list is currently active)
	 */
	public void clearTicket () {
		searchTicketFrom.setText("");
		searchTicketFrom.requestFocus();
		searchTicketTo.setText("");
		searchTicketTo.requestFocus();
		searchTicketDepart.setText("");
		searchTicketDepart.requestFocus();
		searchNumber.setText("");
		searchNumber.requestFocus();
		searchCost.setText("");
		searchCost.requestFocus();
		searchName.setText("");
		searchName.requestFocus();					
		clearTickets.requestFocus();
		Vector resultsVec = new Vector();
		ticketResults.setListData(resultsVec);
	}
	
	/**
	 * This method will quit the program
	 */
	public void quit () {
		try {
			socketOut.writeObject(new String("QUIT"));
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.err.println(e.getStackTrace());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	/**
	 * This method will recieve all the flights from the server that satisfy the searched constraints
	 */
	public void getFlights () {
		fltNo.setText("");
		fltStart.setText("");
		fltDest.setText("");
		fltDate.setText("");
		fltTime.setText("");
		fltDur.setText("");
		fltSeats.setText("");
		fltAvailable.setText("");
		fltCost.setText("");
		String tempString = new String("FLIGHTS ");
		try {
			if (!searchFrom.getText().equals("Anywhere")) {
				tempString += searchFrom.getText() + " ";
			} else {
				tempString += "0 ";
			}
			if (!searchTo.getText().equals("Anywhere")) {
				tempString += searchTo.getText() + " ";
			} else {
				tempString += "0 ";
			}
			if (!searchDepart.getText().equals("dd/mm/yyyy")) {
				tempString += searchDepart.getText() + " ";
			} else {
				tempString += "0 ";
			}
			socketOut.writeObject(tempString);
			flightStore = (FlightStorage)socketIn.readObject();
			Vector <Flight> myFlights = new Vector <Flight> (flightStore.getflights());
			Vector <String> tempVector = new Vector <String>();
			for (int i = 0; i < myFlights.size(); i++) {
				tempString = new String("" + myFlights.elementAt(i).getflightNo());
				while (tempString.length() < 5) {
					tempString += " ";
				}
				tempString += " | From " + myFlights.elementAt(i).getsource();
				while (tempString.length() < 25) {
					tempString += " ";
				}
				tempString += " | To " + myFlights.elementAt(i).getdestination();
				while (tempString.length() < 45) {
					tempString += " ";
				}
				tempString += " | " + myFlights.elementAt(i).getdate();
				while (tempString.length() < 58) {
					tempString += " ";
				}
				tempString += " | Seats " + myFlights.elementAt(i).getavailableSeats() + " / " + myFlights.elementAt(i).gettotalSeats();
				while (tempString.length() < 68) {
					tempString += " ";
				}
				tempVector.add(tempString);
			}
			flightResults.setListData(tempVector);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will attempt to book the user a ticket
	 */
	public void buyTicket () {
		int selected = flightResults.getSelectedIndex();
		boolean view = false;
		if (selected != -1) {
			BookFlight book = new BookFlight(view, "", "", "", Integer.parseInt(fltNo.getText()), fltDur.getText(), fltDate.getText(),
												fltTime.getText(), fltCost.getText(), fltStart.getText(), fltDest.getText(), username, 
												socketOut, socketIn);
		}
	}
	
	/**
	 * This method will recieve all the tickets from the server that satisfy the searched constraints
	 */
	public void getTickets () {
		String tempString = "";
		if (!isAdmin) {
			tempString = new String("TICKETS " + username + " ");
		} else {
			tempString = new String("TICKETS 0 ");
		}
		try {
			if (!searchNumber.getText().equals("Any Number")) {
				tempString += searchNumber.getText() + " ";
			} else {
				tempString += "0 ";
			}
			if (!searchTicketFrom.getText().equals("Anywhere")) {
				tempString += searchTicketFrom.getText() + " ";
			} else {
				tempString += "0 ";
			}
			if (!searchTicketTo.getText().equals("Anywhere")) {
				tempString += searchTicketTo.getText() + " ";
			} else {
				tempString += "0 ";
			}
			if (!searchTicketDepart.getText().equals("dd/mm/yyyy")) {
				tempString += searchTicketDepart.getText() + " ";
			} else {
				tempString += "0 ";
			}
			if (!searchCost.getText().equals("Any Price")) {
				tempString += searchCost.getText() + " ";
			} else {
				tempString += "0 ";
			}
			if (!searchName.getText().equals("Last Name")) {
				tempString += searchName.getText() + " ";
			} else {
				tempString += "0";
			}
			socketOut.writeObject(tempString);
			ticketStore = (TicketStorage)socketIn.readObject();
			Vector <Ticket> myTickets = new Vector <Ticket> (ticketStore.gettickets());
			Vector <String> tempVector = new Vector <String>();
			for (int i = 0; i < myTickets.size(); i++) {
				tempString = new String("" + myTickets.elementAt(i).ticketID());
				while (tempString.length() < 5) {
					tempString += " ";
				}
				tempString += " | " + myTickets.elementAt(i).getname() + " " + myTickets.elementAt(i).getlastname();
				while (tempString.length() < 25) {
					tempString += " ";
				}
				tempString += " | From " + myTickets.elementAt(i).getsource();
				while (tempString.length() < 45) {
					tempString += " ";
				}
				tempString += " | To " + myTickets.elementAt(i).getdestination();
				while (tempString.length() < 65) {
					tempString += " ";
				}
				tempString += " | " + myTickets.elementAt(i).getdate();
				while (tempString.length() < 78) {
					tempString += " ";
				}
				tempVector.add(tempString);
			}
			ticketResults.setListData(tempVector);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method pulls up a visual display of the ticket for the user
	 */
	public void viewTick () {
		int selected = ticketResults.getSelectedIndex();
		Vector <Ticket> myTickets = new Vector <Ticket> (ticketStore.gettickets());
		boolean view = true;
		if (selected != -1) {
			BookFlight book = new BookFlight(view, myTickets.elementAt(selected).getname(), myTickets.elementAt(selected).getlastname(), myTickets.elementAt(selected).getDOB(),
												myTickets.elementAt(selected).getflightNo(), myTickets.elementAt(selected).getduration(), myTickets.elementAt(selected).getdate(),
												myTickets.elementAt(selected).gettime(), "" + myTickets.elementAt(selected).getprice(), myTickets.elementAt(selected).getsource(), myTickets.elementAt(selected).getdestination(), username, 
												socketOut, socketIn);
		}
	}
}