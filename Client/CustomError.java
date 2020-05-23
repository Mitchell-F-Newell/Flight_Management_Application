// CustomError.java
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 *This class contains the CustomError logic and construction
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class CustomError {
	/**
	 * The main jframe for the gui on which the error will be displayed.
	 */
	private JFrame main;
	
	/**
	 * This class acts as a tagging interface, waiting for an event to occur.
	 */
	private class EventListener implements ActionListener {
		/**
		 * the CustomError object that contains the display/gui.
		 */
		CustomError display;
		
		/**
		 * The constructor for the class, that sets the CustomError object display.
		 * @param d the new display of type CustomError.
		 */
		public EventListener (CustomError d) {
			display = d;
		}
		
		/**
		 * When an action occurs, this method will direct/complete the desired action.
		 * @param e the ActionEvent that occured within the gui.
		 */
		public void actionPerformed (ActionEvent e) {
			display.main.dispatchEvent(new WindowEvent(display.main, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	/**
	 * The constructor for class CustomError which creates/initializes the gui on which the error will be displayed.
	 * @param message is the error message that is to be displayed.
	 */
	public CustomError (String message) {
		main = new JFrame();
		main.setTitle("Error Message");
		main.setIconImage(new ImageIcon("Icons/Travel.png").getImage());
		main.setSize(400, 135);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		main.setLayout(new BorderLayout());
		
		CustomPanel background = new CustomPanel(new BorderLayout(), "Images/passport_background.png");
		
		TabPanel disp = new TabPanel(new Color(128, 201, 177, 205));
		disp.setLayout(new BoxLayout(disp, BoxLayout.Y_AXIS));
		JPanel tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		JLabel messages = new JLabel(message);
		messages.setFont(new Font("Candara", Font.BOLD, 6));
		messages.setForeground(new Color (53, 50, 48, 0));
		tempPanel.add(messages);
		disp.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		messages = new JLabel(message);
		messages.setFont(new Font("Candara", Font.BOLD, 14));
		messages.setForeground(new Color (53, 50, 48));
		tempPanel.add(messages);
		disp.add(tempPanel);
		tempPanel = new JPanel(new FlowLayout());
		tempPanel.setOpaque(false);
		JLabel tempLabel = new JLabel("H");
		tempLabel.setFont(new Font("Candara", Font.PLAIN, 35));
		tempLabel.setForeground(new Color (53, 50, 48, 0));
		tempPanel.add(tempLabel);
		CustomButton okay = new CustomButton(9, "Okay");
		okay.addActionListener(new EventListener(this));
		tempPanel.add(okay);
		tempLabel = new JLabel("H");
		tempLabel.setFont(new Font("Candara", Font.PLAIN, 35));
		tempLabel.setForeground(new Color (53, 50, 48, 0));
		tempPanel.add(tempLabel);
		disp.add(tempPanel);
		background.add("Center", disp);
		main.add("Center", background);
		main.setVisible(true);
	}
}