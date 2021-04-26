package ie.gmit.sw.game;

import java.awt.Graphics;
import javax.swing.ImageIcon;

import ie.gmit.sw.runner.Runner;
import resources.Audio;
import resources.Constants;

public class PlayerShot extends Entity {

	private boolean ship = false;

	public PlayerShot() {
		// Initialize the variables of the super class
		super.xPos = 0;
		super.yPos = Constants.Y_POS_SHIP - Constants.BULLET_HEIGHT;
		super.width = Constants.BULLET_WIDTH;
		super.height = Constants.BULLET_HEIGHT;
		super.dx = 0;
		super.dy = Constants.DY_SHIP_BULLET;
		// Images for the ship
		super.strImg1 = "/images/shipBullet.png";
		super.strImg2 = "";
		super.strImg3 = "";
		// get the images
		super.icon = new ImageIcon(getClass().getResource(super.strImg1));
		super.img = this.icon.getImage();
	}

	public boolean isShipBullet() {
		return ship;
	}

	public void setShipBullet(boolean shipBullet) {
		this.ship = shipBullet;
	}

	public int moveshipBullet() {
		if (this.ship == true) {
			if (this.yPos > 0) {
				this.yPos = this.yPos - Constants.DY_SHIP_BULLET;
			} else {
				this.ship = false;
			}
		}
		return yPos;
	}

	public void drawShipBullet(Graphics g) {
		if (this.ship == true) {
			g.drawImage(this.img, this.xPos, this.moveshipBullet(), null);
		}
	}

	public boolean tueAlien(Alien alien) {
		// the shooting of the ship destroys an alien
		if (this.yPos < alien.getyPos() + alien.getHeight() && this.yPos + this.height > alien.getyPos()
				&& this.xPos + this.width > alien.getxPos() && this.xPos < alien.getxPos() + alien.getWidth()) {
			Audio.playSound("/sounds/alienDied.wav");
			return true;
		} else {
			return false;
		}
	}

	private boolean shipShootsObstcale() {
		// Returns true if the ship's shot is at obstacle
		if (this.yPos < Constants.Y_POS_OBSTACLE + Constants.OBSTACLE_HEIGHT
				&& this.yPos + this.height > Constants.Y_POS_OBSTACLE) {
			return true;
		} else {
			return false;
		}
	}

	private int closestObstacle() {
		// Returns the number of the obstacle (0,1,2 or 3) in the firing zone of the
		// ship
		int obstacleNo = -1;
		int column = -1;
		while (obstacleNo == -1 && column < 4) {
			column++;
			if (this.xPos + this.width > Constants.WINDOW_MARGIN + Constants.X_POS_INIT_OBSTACLE
					+ column * (Constants.OBSTACLE_WIDTH + Constants.OBSTACLE_GAP)
					&& this.xPos < Constants.WINDOW_MARGIN + Constants.X_POS_INIT_OBSTACLE + Constants.OBSTACLE_WIDTH
							+ column * (Constants.OBSTACLE_WIDTH + Constants.OBSTACLE_GAP)) {
				obstacleNo = column;
			}
		}
		return obstacleNo;
	}

	private int playershootsObstacle(Obstacle obstacle) {
		// Returns the distance of the ship's shot upon contact with a nearby obstacle
		int xPosTirVaisseau = -1;
		if (this.xPos + this.width > obstacle.getxPos() && this.xPos < obstacle.getxPos() + Constants.OBSTACLE_WIDTH) {
			xPosTirVaisseau = this.xPos;
		}
		return xPosTirVaisseau;
	}

	public int[] playerBulletHitsObstacle() {
		// Returns the number of the obstacle affected and the distance of the shot
		int[] arrDir = { -1, -1 };
		// The ship shot is at the height of the obstacle
		if (this.shipShootsObstcale() == true) {
			// save the number of the obstacle touched in arrDir [0]
			arrDir[0] = this.closestObstacle();
			if (arrDir[0] != -1) {
				// enregistre l'abscisse du tir du vaisseau lors du contact avec le château dans
				// tabRep[1]
				arrDir[1] = this.playershootsObstacle(Runner.scene.obstacle[arrDir[0]]);
			}
		}
		return arrDir;
	}

	public void playerdestroysObstacle(Obstacle tabChateaux[]) {
		// Contains (-1, -1) or the number of the obstacle hit and the distance of the
		// shot
		int[] tab = this.playerBulletHitsObstacle();
		// obstacle is hit
		if (tab[0] != -1) {
			if (tabChateaux[tab[0]].trouveBrique(tabChateaux[tab[0]].findCastleColumn(tab[1])) != -1) {
				// Destroy the bricks of the affected obstacle
				tabChateaux[tab[0]].breakBricks(tab[1]);
				// destroy alien bullet
				this.yPos = -1;
			}
		}
	}

	public boolean destroySaucer(Saucer saucer) {
		// ship shoots saucer
		if (this.yPos < saucer.getyPos() + saucer.getHeight() && this.yPos + this.height > saucer.getyPos()
				&& this.xPos + this.width > saucer.getxPos() && this.xPos < saucer.getxPos() + saucer.getWidth()) {
			// destroy the bullet
			this.ship = false;
			return true;
		} else {
			return false;
		}
	}
}
