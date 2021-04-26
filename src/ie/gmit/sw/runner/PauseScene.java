package ie.gmit.sw.runner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import resources.Constants;
import resources.Time;

public class PauseScene extends JPanel {

	private static final long serialVersionUID = 1L;
	private Font textFont = new Font("Arial", Font.PLAIN, 80);

	// paint the scene
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2 = (Graphics2D) g;

		// Draw the wallpaper
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

		// Green line drawing at the bottom of the screen
		g2.setColor(Color.GREEN);
		g2.fillRect(30, 530, 535, 5);

		g.setFont(textFont);
		g.drawString("Game Paused!!", 15, 300);
	}

}
