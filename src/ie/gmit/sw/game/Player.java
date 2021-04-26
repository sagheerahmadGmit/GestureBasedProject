package ie.gmit.sw.game;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import ie.gmit.sw.runner.Runner;
import resources.Time;
import resources.Constants;

public class Player extends Entity {

	private int counter = 0;

	public Player() {
		// initialise the variables of the super class
		super.xPos = Constants.X_POS_OF_SHIP;
		super.yPos = Constants.Y_POS_SHIP;
		super.width = Constants.SHIP_WIDTH;
		super.height = Constants.SHIP_HEIGHT;
		super.dx = 0;
		super.dy = 0;
		// Address of the images of the ship
		super.strImg1 = "/images/ship.png";
		super.strImg2 = "/images/shipDestroyed1.png";
		super.strImg3 = "/images/shipDestroyed2.png";
		// Load the image of the ship
		super.icon = new ImageIcon(getClass().getResource(super.strImg1));
		super.img = this.icon.getImage();
		super.isAlive = true;
	}

	public int shipMovement() {
		// Return the new position of the ship after any movement
		if (this.dx < 0) {
			if (this.xPos > Constants.LIMIT_LEFT) {
				this.xPos = this.xPos + this.dx;
			}
		} else if (dx > 0) {
			if (this.xPos + this.dx < Constants.LIMIT_RIGHT) {
				this.xPos = this.xPos + this.dx;
			}
		}
		return this.xPos;
	}

	// destroy the ship
	public void drawShip(Graphics g) {
		if (this.isAlive == false) {
			this.destroShip();
		}
		g.drawImage(this.img, this.shipMovement(), this.yPos, null);
	}

	public void destroShip() {
		if (counter < 300) {
			if (Time.counter % 2 == 0) {
				super.icon = new ImageIcon(getClass().getResource(super.strImg2));
			} else {
				super.icon = new ImageIcon(getClass().getResource(super.strImg3));
			}
			counter++;
		} else {
			Runner.game = false;
		}
		super.img = this.icon.getImage();
	}
}
