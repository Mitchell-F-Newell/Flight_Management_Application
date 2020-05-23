// CustomPanel.java
import java.awt.*;
import javax.swing.*;

/**
 *This class contains the CustomPanel logic and construction.
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class CustomPanel extends JPanel {
	
	/**
	 * The image/background that is to be placed within the panel.
	 */
	ImageIcon image;
	
	/**
	 * The constructor for the CustomPanel which initializes the local image and sets the layout of the panel.
	 * @param layout refers to the layout of the panel.
	 * @param picture refers to the file name of the image.
	 */
	public CustomPanel (LayoutManager layout, String picture) {
		super(layout);
		image = new ImageIcon(picture);
	}
	
	/**
	 * This method sets the background of the panel to the new image.
	 * @param g the graphics of the panel.
	 */
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.drawImage(image.getImage(), 0, 0, null);
	}
}