// BookFlight.java
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

/**
 *This class contains the BookFlight object which creates a user interface for booking a flight
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class BookFlight {
	
	/**
	 * The main frame of the GUI
	 */
	private JFrame frame;
	
	/**
	 * The interactable Buttons used in the GUI
	 */
	private CustomButton bookF, closeF;
	
	/**
	 * The various textFields which take or diplay information on the flight
	 */
	private CustomTextField firstName, lastName, DOB, flight_num, duration, date, time, prce, source, destination;
	
	/**
	 * Listens and waits for an event to occur which it will register
	 */
	private eventListener listen;
	
	/**
	 * refers to the username of the user
	 */
	private String username;
	
	/**
	 * socketOut is the output stream that writes to the server
	 */
	private ObjectOutputStream socketOut;
	
	/**
	 * in is the input stream from the server.
	 */
	private ObjectInputStream socketIn;
	
	/**
	 * This class acts as a tagging interface, waiting for an event to occur.
	 */
	private class eventListener implements ActionListener {
		
		/**
		 * the BookFlight object that contains the display/gui.
		 */
		BookFlight display;
		
		/**
		 * The constructor for the class, that sets the BookFlight object display.
		 * @param disp the new display of type BookFlight.
		 */
		public eventListener (BookFlight disp) {
			display = disp;
		}
	
		/**
		 * When an action occurs, this method will direct/complete the desired action.
		 * @param e the ActionEvent that occured within the gui.
		 */
		public void actionPerformed (ActionEvent e){
			boolean close = true;
			if (e.getSource()== bookF) {
				close = display.book();
			}
			if (close) {
				display.frame.dispatchEvent(new WindowEvent(display.frame, WindowEvent.WINDOW_CLOSING));
			}
		}
	}
	
	/**
	 * The constructor for the BookFlight class which initializes and creates the BookFLight user interface
	 * @param view indicates weather or not you are booking a ticket
	 * @param firstS is the users first name
	 * @param lastS is the users last name
	 * @param DOBS is the users date of birth
	 * @param numberS is the number of seats on the flight
	 * @param durationS is the duration of the flight
	 * @param dateS is the date of the flight
	 * @param timeS is the time of the flight
	 * @param priceS is the proce of the palne itcket.
	 * @param sourceS is the place where the flight takes off
	 * @param destinationS is the destination of the flight
	 * @param user is the users username
	 * @param SocketOut is the input output stream that writes to the server
	 * @param SocketIn is the input stream from the server
	 */
	public BookFlight (boolean view, String firstS, String lastS, String DOBS, int numberS, String durationS, String dateS,
						String timeS, String priceS, String sourceS, String destinationS, String user, ObjectOutputStream socketOut, ObjectInputStream socketIn) {
		Font defaultFont = new Font("Candara", Font.BOLD, 14);
		username = user;
		this.socketOut = socketOut;
		this.socketIn = socketIn;
		listen = new eventListener(this);
		frame = new JFrame();
		frame.setTitle("View Ticket");
		frame.setIconImage(new ImageIcon("Icons/Travel.png").getImage());
		frame.setLayout(new BorderLayout());
		frame.setSize(700, 387);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		CustomPanel panel = new CustomPanel(new BorderLayout(), "Images/Ticket.png");
		
		JPanel ticketOverlay = new JPanel(new BorderLayout());
		ticketOverlay.setOpaque(false);
		
		JPanel subNorth = new JPanel(new BorderLayout());
		subNorth.setOpaque(false);
		
		JPanel ticket = new JPanel();
		ticket.setLayout(new GridLayout(0,1));
		ticket.setOpaque(false);
		
		JPanel zero = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
		zero.setOpaque(false);
		JLabel tempLabel = new JLabel("       Source: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		zero.add(tempLabel);
		source = new CustomTextField(12, "");
		source.setText(sourceS);
		source.setEditable(false);
		zero.add(source);
		tempLabel = new JLabel("          Destination: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		zero.add(tempLabel);
		destination = new CustomTextField(12, "");
		destination.setText(destinationS);
		destination.setEditable(false);
		zero.add(destination);
		subNorth.add("North", zero);
		
		if (!view) {
			TabPanel one = new TabPanel(new FlowLayout(FlowLayout.CENTER, 0, 15), new Color (128, 201, 177, 205));
			tempLabel = new JLabel("       First Name: ");
			tempLabel.setForeground(new Color (53, 50, 48));
			tempLabel.setFont(defaultFont);
			one.add(tempLabel);
			firstName = new CustomTextField(12, "Type First Name");
			one.add(firstName);
			tempLabel = new JLabel("       Last Name: ");
			tempLabel.setForeground(new Color (53, 50, 48));
			tempLabel.setFont(defaultFont);
			one.add(tempLabel);
			lastName = new CustomTextField(12, "Type Last Name");
			one.add(lastName);
			tempLabel = new JLabel("       D.O.B: ");
			tempLabel.setForeground(new Color (53, 50, 48));
			tempLabel.setFont(defaultFont);
			one.add(tempLabel);
			DOB = new CustomTextField(8, "dd/mm/yyyy");
			one.add(DOB);
			subNorth.add("South", one);
		} else {
			JPanel one = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
			one.setOpaque(false);
			tempLabel = new JLabel("       First Name: ");
			tempLabel.setForeground(new Color (53, 50, 48));
			tempLabel.setFont(defaultFont);
			one.add(tempLabel);
			firstName = new CustomTextField(12, "");
			firstName.setText(firstS);
			firstName.setEditable(false);
			one.add(firstName);
			tempLabel = new JLabel("       Last Name: ");
			tempLabel.setForeground(new Color (53, 50, 48));
			tempLabel.setFont(defaultFont);
			one.add(tempLabel);
			lastName = new CustomTextField(12, "");
			lastName.setText(lastS);
			lastName.setEditable(false);
			one.add(lastName);
			tempLabel = new JLabel("       D.O.B: ");
			tempLabel.setForeground(new Color (53, 50, 48));
			tempLabel.setFont(defaultFont);
			one.add(tempLabel);
			DOB = new CustomTextField(8, "");
			DOB.setText(DOBS);
			DOB.setEditable(false);
			one.add(DOB);
			subNorth.add("South", one);
		}
		
		ticketOverlay.add("North", subNorth);
		
		JPanel info = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 35));
		info.setOpaque(false);
		tempLabel = new JLabel("       Flight Number: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		info.add(tempLabel);
		flight_num = new CustomTextField(6, "");
		flight_num.setText(Integer.toString(numberS));
		flight_num.setEditable(false);
		info.add(flight_num);
		tempLabel = new JLabel("       Flight Duration: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		info.add(tempLabel);
		duration = new CustomTextField(6, "");
		duration.setText(durationS);
		duration.setEditable(false);
		info.add(duration); 
		tempLabel = new JLabel("       Date: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		info.add(tempLabel);
		date = new CustomTextField(6, "");
		date.setText(dateS);
		date.setEditable(false);
		info.add(date);
		tempLabel = new JLabel("       Time: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		info.add(tempLabel);
		time = new CustomTextField(6,"");
		time.setText(timeS);
		time.setEditable(false);
		info.add(time);
		ticket.add(info);
		
		JPanel price = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		price.setOpaque(false);
		tempLabel = new JLabel("   Price: ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		price.add(tempLabel);
		prce = new CustomTextField(10, "");
		prce.setText(priceS);
		prce.setEditable(false);
		price.add(prce);
		tempLabel = new JLabel("    ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		price.add(tempLabel);
		if (!view) {
			bookF = new CustomButton(12, "Book Ticket");
			bookF.addActionListener(listen);
			price.add(bookF);
		} else {
			closeF = new CustomButton(12, "Close Ticket");
			closeF.addActionListener(listen);
			price.add(closeF);
		}			
		ticketOverlay.add("South", price);
		
		ticketOverlay.add("Center", ticket);
		
		panel.add("South", ticketOverlay);
	    
		
		frame.add("Center", panel);
		
		frame.setVisible(true);
		
    }
	
	/**
	 * This method will book the user a ticket
	 */
	public boolean book () {
		if (firstName.getText().equals("Type First Name") || lastName.getText().equals("Type Last Name") || DOB.getText().equals("dd/mm/yyyy")) {
			CustomError invalid = new CustomError("Please fill all required fields.");
			return false;
		} else {
			try {
				socketOut.writeObject(new String("BOOK " + username + " " + firstName.getText() + " " + lastName.getText() + " " + DOB.getText() + " " + flight_num.getText()));
				String message = (String)socketIn.readObject();
				if (message.equals("true")) {
					CustomError booked = new CustomError("Ticket successfully booked.");
					frame.setVisible(false);
				} else {
					CustomError booked = new CustomError("This flight is already full.");
					frame.setVisible(false);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return true;
		}
	}
}
