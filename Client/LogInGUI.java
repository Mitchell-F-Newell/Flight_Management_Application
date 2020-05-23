// LogInGUI.java
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

/**
 *This class contains the LoginGUI object which creates a user log in interface 
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class LogInGUI {
	/**
	 * The textfields in which the user will input their account information to log in
	 */
	private CustomTextField inUsername, upUsername;
	/**
	 * CustomPassword objects are textfields that hide the input/text.
	 */
	private CustomPassword inPassword, upPassword, upRePassword;
	
	/**
	 * The gui buttons that allow a user to sign in or sign up
	 */
	private CustomButton signIn, signUp;
	/**
	 * The checkbox that allows the user to declare their new account as an administrative account
	 */
	private JCheckBox admin;
	
	/**
	 * The main frame of the gui
	 */
	private JFrame main;
	
	/**
	 * Listens and waits for an event to occur which it will register
	 */
	private EventListener buttonPress;
	
	/**
	 * aSocket is the socket connecting the GUI to the server and client
	 */
	private Socket aSocket;
	
	/**
	 * in is the input stream from the server.
	 */
	private ObjectInputStream socketIn;
	
	/**
	 * socketOut is the output stream that writes to the server
	 */
	private ObjectOutputStream socketOut;
	
	/**
	 * This class acts as a tagging interface, waiting for an event to occur.
	 */
	private class EventListener implements ActionListener {
		LogInGUI display;
		public EventListener (LogInGUI d) {
			display = d;
		}
		public void actionPerformed (ActionEvent e) {
			if (e.getSource() == signIn) {
				display.signInNow();
			} else if (e.getSource() == signUp) {
				display.signUpNow();
			}
		}
	}
	
	/**
	 * The constructor for the LogInGui class which initializes and creates the log-in user interface
	 * @param serverName refers the servers name
	 * @param portNumber refers to the servers port number
	 */
	public LogInGUI (String serverName, int portNumber) {
		try {
			aSocket = new Socket(serverName, portNumber);
			socketOut = new ObjectOutputStream(aSocket.getOutputStream());
			socketIn = new ObjectInputStream(aSocket.getInputStream());
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}

		main = new JFrame();
		main.setTitle("Log In");
		main.setIconImage(new ImageIcon("Icons/Travel.png").getImage());
		main.setSize(425, 325);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		main.setLayout(new BorderLayout());
		main.addWindowListener(new WindowAdapter() {
			
			/**
			 * This method will quit the program if the window is closed
			 * @param e refers to the WindowEvent listener which triggers if a window event occures (i.e. closing the window)
			 */
			public void windowClosing(WindowEvent e)
				{
					quit();
				}
		});
		
		Font defaultFont = new Font("Candara", Font.BOLD, 14);
		buttonPress = new EventListener(this);
		
		CustomPanel background = new CustomPanel(new BorderLayout(), "Images/Flying_world.png");
		
		TabPanel northTarget = new TabPanel(new Color(128, 201, 177, 205));
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
		north.setOpaque(false);
		JPanel tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		JLabel tempLabel = new JLabel("Username:                        ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		inUsername = new CustomTextField(12, "");
		inUsername.setSymbol1(new ImageIcon("Icons/User2.png"));
		inUsername.setSymbol2(new ImageIcon("Icons/User1.png"));
		tempPanel.add(inUsername);
		north.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempLabel = new JLabel("Password:                        ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		inPassword = new CustomPassword(12, "");
		inPassword.setSymbol1(new ImageIcon("Icons/Lock2.png"));
		inPassword.setSymbol2(new ImageIcon("Icons/Lock1.png"));
		tempPanel.add(inPassword);
		north.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempPanel.add(new JLabel("                                                             "));
		signIn = new CustomButton(9, "Sign In");
		signIn.addActionListener(buttonPress);
		tempPanel.add(signIn);
		north.add(tempPanel);
		northTarget.add(north);
		background.add("North", northTarget);
		
		
		
		TabPanel southTarget = new TabPanel(new Color(128, 201, 177, 205));
		JPanel south = new JPanel();
		south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
		south.setOpaque(false);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempLabel = new JLabel("Username:                        ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		upUsername = new CustomTextField(12, "");
		upUsername.setSymbol1(new ImageIcon("Icons/User2.png"));
		upUsername.setSymbol2(new ImageIcon("Icons/User1.png"));
		tempPanel.add(upUsername);
		south.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempLabel = new JLabel("Password:                        ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		upPassword = new CustomPassword(12, "");
		upPassword.setSymbol1(new ImageIcon("Icons/Lock2.png"));
		upPassword.setSymbol2(new ImageIcon("Icons/Lock1.png"));
		tempPanel.add(upPassword);
		south.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		tempLabel = new JLabel("Re-Enter Password:      ");
		tempLabel.setForeground(new Color (53, 50, 48));
		tempLabel.setFont(defaultFont);
		tempPanel.add(tempLabel);
		upRePassword = new CustomPassword(12, "");
		upRePassword.setSymbol1(new ImageIcon("Icons/Lock2.png"));
		upRePassword.setSymbol2(new ImageIcon("Icons/Lock1.png"));
		tempPanel.add(upRePassword);
		south.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		admin = new JCheckBox("Admin Access");
		admin.setFocusPainted(false);
		admin.setOpaque(false);
		admin.setFont(defaultFont);
		admin.setForeground(new Color (53, 50, 48));
		tempPanel.add(admin);
		tempPanel.add(new JLabel("                       "));
		signUp = new CustomButton(9, "Sign Up");
		signUp.addActionListener(buttonPress);
		tempPanel.add(signUp);
		south.add(tempPanel);
		southTarget.add(south);
		background.add("South", southTarget);
		
		main.add(background);
		main.setVisible(true);
	}
	
	/**
	 * This method is the method incharge of the logic and implemetation behind signing in
	 */
	public void signInNow () {
		if (inUsername.getText().contains(" ")) {
			CustomError invalid = new CustomError("Username and/or password is incorrect.");
			return;
		}
		try {
			socketOut.writeObject(new String("SIGNIN " + inUsername.getText() + " " + inPassword.getText()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = "";
		try {
			message = (String)socketIn.readObject();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (message.equals("truea")) {
			AdminGUI a = new AdminGUI(inUsername.getText(), aSocket, socketOut, socketIn);
			main.setVisible(false);
		} else if (message.equals("truec")) {
			ClientGUI c = new ClientGUI(inUsername.getText(), aSocket, socketOut, socketIn);
			main.setVisible(false);
		} else {
			CustomError invalid = new CustomError("Username and/or password is incorrect.");
		}
	}
	
	/**
	 * This method incharge of the logic and implementation behind signing up
	 */
	public void signUpNow () {
		if (!upPassword.getText().equals(upRePassword.getText())) {
			CustomError invalid = new CustomError("Passwords do not match.");
		} else if (upUsername.getText().equals("") || upPassword.getText().equals("")) {
			CustomError invalid = new CustomError("Username and password fields are required.");
		} else if (upUsername.getText().contains(" ") || upPassword.getText().contains(" ") || upRePassword.getText().contains(" ")) {
			CustomError invalid = new CustomError("Username and password cannot contain spaces.");
		} else {
			try {
				if (admin.isSelected()) {
					socketOut.writeObject(new String("NEWUSER " + upUsername.getText() + " " + upPassword.getText() + " a"));
				} else {
					socketOut.writeObject(new String("NEWUSER " + upUsername.getText() + " " + upPassword.getText() + " c"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String message = "";
			try {
				message = (String)socketIn.readObject();
			} catch (IOException e) {
				System.err.println(e.getStackTrace());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (message.equals("true")) {
				if (admin.isSelected()) {
					AdminGUI a = new AdminGUI(upUsername.getText(), aSocket, socketOut, socketIn);
				} else {
					ClientGUI c = new ClientGUI(upUsername.getText(), aSocket, socketOut, socketIn);
				}
				main.setVisible(false);
			} else {
				CustomError invalid = new CustomError("Username is already taken.");
			}
		}
	}
	
	/**
	 * This method will quit the program and catch a couple errors
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
	
	public static void main(String[] args){
		LogInGUI l = new LogInGUI("localhost", 5454);
	}
	
}