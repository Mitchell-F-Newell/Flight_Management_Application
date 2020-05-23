// TabPanel.java
import java.awt.*;
import javax.swing.*;

/**
 *This class contains the Tabpanel object which is a decorative background panel.
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 * @since April 6th, 2017
 */
public class TabPanel extends JPanel {
	
	/**
	 * refers to the colour of the TabPanel
	 */
	Color color;
	
	/**
	 * The constructor for the TsabPanel object which sets the layout and colour of the TabPanel.
	 * @param layout is the layout that the TabPanel is to be set to
	 * @param col is the colour of the TabPanel
	 */
	public TabPanel (LayoutManager layout, Color col) {
		super(layout);
		setOpaque(false);
		color = col;
	}
	
	/**
	 * Construct the TabPanel to use the default layout and specified colour
	 * @para col is the colour of the TabPanel
	 */
	public TabPanel (Color col) {
		super();
		setOpaque(false);
		color = col;
	}
	
	/**
	 * This method recolours the TabPanel.
	 * @param g refers to the graphics of the panel.
	 */
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
	}
}