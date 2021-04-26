package ie.gmit.sw.game;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import resources.Audio;
import resources.Time;
import resources.Constants;

public class Saucer extends Entity {

	// saucer audio
	public Audio saucerMusic = new Audio("/sounds/soundSaucerPass.wav");
	public Audio saucerDestroyed = new Audio("/sounds/saucerdestroyed.wav");

	private int counter = 0;

	//saucer constructor
	public Saucer() {
		super.xPos = Constants.X_POS_INIT_SAUCER;
		super.yPos = Constants.Y_POS_SAUCER;
		super.width = Constants.SAUCER_WIDTH;
		super.height = Constants.SAUCER_HEIGHT;
		super.dx = Constants.DX_SAUCER;
		super.dy = 0;

		// Images for the saucer
		this.strImg1 = "/images/saucer.png";
		this.strImg2 = "/images/saucer100.png";
		this.strImg3 = "";
		// Loading the ship image
		super.icon = new ImageIcon(getClass().getResource(strImg1));
		super.img = this.icon.getImage();
		super.isAlive = true;

		this.saucerMusic.play();
		this.saucerDestroyed.stop();
		this.counter = 0;
	}

	public int saucerMovement() {
		// Return the new position of the saucer after possible movement
		if (this.isAlive && Time.counter % 2 == 0) {
			if (this.xPos > 0) {
				this.xPos = this.xPos - this.dx;
			} else {
				this.xPos = Constants.X_POS_INIT_SAUCER;
			}
		}
		return this.xPos;
	}

	//draw the red saucer ship
	public void drawSaucer(Graphics g) {
		if (this.isAlive == false) {
			this.destroySaucer();
		}
		g.drawImage(this.img, this.saucerMovement(), this.yPos, null);
	}

	//destroy the saucer if the player hits it
	public void destroySaucer() {
		if (counter < 300) {
			super.icon = new ImageIcon(getClass().getResource(super.strImg2));
			super.img = this.icon.getImage();
			counter++;
		} else {
			this.xPos = Constants.X_POS_INIT_SAUCER;
		}
	}
}