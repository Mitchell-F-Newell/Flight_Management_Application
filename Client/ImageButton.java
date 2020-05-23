// ImageButton.java
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *This class contains the ImageButton class which constructs a button with an image on it
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class ImageButton extends CustomButton {
	
	/**
	 * The symbol that is to be displayed when the field is active.
	 */
	private Icon symbol1;
	
	/**
	 * The symbol that is to be displayed when the field is not active.
	 */
	private Icon symbol2;
	
	/**
	 * A boolean value that returns true if the button is active and false if not active
	 */
	private Boolean active;
	
	/**
	 * The constructor for the interactable ImageButton object 
	 */
	public ImageButton (int size, String text) {
		super(size, text);
		setForeground(new Color(142, 132, 127));
		symbol1 = null;
		symbol2 = null;
		active = false;
		setPreferredSize(new Dimension(size * 10, 54));
	}
	
	
	/**
	 * This method sets the image that is to diplayed when the field is active.
	 * @param icon the image that will be displayed when the field is active
	 */
	public void setSymbol1 (Icon icon) {
		symbol1 = icon;
	}
	
	/**
	 * This method sets the image that is to be displayed when the field is not active.
	 * @param icon the image that will be displayed when the field is not active
	 */
	public void setSymbol2 (Icon icon) {
		symbol2 = icon;
	}
	
	/**
	 * Sets the value of the active data member
	 * @param a is the new true or false value of active
	 */
	public void setActive (boolean a) {
		active = a;
	}
	
	/**
	 * This method recolours the image button and all icons within the button.
	 * @param g refers to the graphics of the button.
	 */
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		if (symbol1 != null && symbol2 != null) {
			int symbolWidth = symbol1.getIconWidth();
			int symbolHeight = symbol1.getIconHeight();
			int x = (getWidth() - symbolWidth) / 2;
			int y = (getHeight() - symbolHeight) / 4;
			if (!active && !getModel().isPressed()) {
				symbol1.paintIcon(this, g, x, y);
			} else {
				symbol2.paintIcon(this, g, x, y);
			}
		}
		setMargin(new Insets(25, 0, 2, 2));
		if (active || getModel().isPressed()) {
			setForeground(new Color(79, 193, 0));
		} else {
			setForeground(new Color(53, 50, 48));
		}
	}
	
	/**
	 * This method changes the colour of the border of the button.
	 * @param g refers to the graphics of the button.
	 */
	protected void paintBorder (Graphics g) {
		if (active || getModel().isPressed()) {
			g.setColor(new Color(79, 193, 0));
		} else {
			g.setColor(new Color(53, 50, 48));
		}
		g.drawRoundRect(0, 0, getWidth() - 2, getHeight() - 1, 5, 5);
		g.drawRoundRect(1, 0, getWidth() - 2, getHeight() - 1, 5, 5);
		g.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 5, 5);
	}
}