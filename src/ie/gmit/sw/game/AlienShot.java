package ie.gmit.sw.game;

import java.awt.Graphics;
import java.util.Random;
import javax.swing.ImageIcon;

import ie.gmit.sw.runner.Runner;
import resources.Audio;
import resources.Time;
import resources.Constants;

public class AlienShot extends Entity {

	Random rand = new Random();

	public AlienShot(int[] tabPositionAlien) {

		// Initialise the variables of the super class
		super.xPos = tabPositionAlien[0] + Constants.ALIEN_WIDTH / 2 - 1;
		super.yPos = tabPositionAlien[1] + Constants.ALIEN_HEIGHT;
		super.width = Constants.ALIEN_BULLET_WIDTH;
		super.height = Constants.ALIEN_BULLET_HEIGHT;
		super.dx = 0;
		super.dy = Constants.DY_BULLET_ALIEN;
		// Address of the images of the ship
		super.strImg1 = "/images/alienBullet1.png";
		super.strImg2 = "/images/alienBullet2.png";
		super.strImg3 = "";
		// Load the image of the alien shot
		if (rand.nextInt(2) == 0) {
			super.icon = new ImageIcon(getClass().getResource(super.strImg1));
		} else {
			super.icon = new ImageIcon(getClass().getResource(super.strImg2));
		}
		super.img = this.icon.getImage();
	}

	public int moveAlienBullet() {
		if (Time.counter % 4 == 0) {
			if (this.yPos < 600) {
				this.yPos = this.yPos + Constants.DY_BULLET_ALIEN;
			}
		}
		return yPos;
	}

	public void drawAlienBullet(Graphics g) {
		g.drawImage(this.img, this.xPos, this.moveAlienBullet(), null);
	}

	private boolean alienShootsObstacle() {
		// Returns true if the ship's shot is at obstacle
		if (this.yPos < Constants.Y_POS_OBSTACLE + Constants.OBSTACLE_HEIGHT
				&& this.yPos + this.height > Constants.Y_POS_OBSTACLE) {
			return true;
		} else {
			return false;
		}
	}

	private int closestObstacke() {
		// Returns the number of the obstacle (0,1,2 or 3) in the firing zone of the
		// ship
		int obstacleNo = -1;
		int column = -1;
		while (obstacleNo == -1 && column < 4) {
			column++;
			if (this.xPos + this.width - 1 > Constants.WINDOW_MARGIN + Constants.X_POS_INIT_OBSTACLE
					+ column * (Constants.OBSTACLE_WIDTH + Constants.OBSTACLE_GAP)
					&& this.xPos + 1 < Constants.WINDOW_MARGIN + Constants.X_POS_INIT_OBSTACLE
							+ Constants.OBSTACLE_WIDTH + column * (Constants.OBSTACLE_WIDTH + Constants.OBSTACLE_GAP)) {
				obstacleNo = column;
			}
		}
		return obstacleNo;
	}

	private int alienshootsObstacle(Obstacle obstacle) {
		// Returns the distance of the ship's shot upon contact with a nearby obstacle
		int xPosBullet = -1;
		if (this.xPos + this.width > obstacle.getxPos() && this.xPos < obstacle.getxPos() + Constants.OBSTACLE_WIDTH) {
			xPosBullet = this.xPos;
		}
		return xPosBullet;
	}

	// Returns the number of the obstacle affected and the distance of the shot
	public int[] tirAlienToucheChateau() {
		int[] arrDir = { -1, -1 };
		// The alien shot is at the height of the obstacle
		if (this.alienShootsObstacle() == true) {
			// save the number of the obstacle touched in arrDir [0]
			arrDir[0] = this.closestObstacke();
			if (arrDir[0] != -1) {
				arrDir[1] = this.alienshootsObstacle(Runner.scene.obstacle[arrDir[0]]);
			}
		}
		return arrDir;
	}

	public void alienHitsObstcale(Obstacle tabObstacle[]) {
		// Contains (-1, -1) or the number of the obstacle hit and the distance of the
		// shot
		int[] tab = this.tirAlienToucheChateau();
		// obstacle is hit
		if (tab[0] != -1) {
			if (tabObstacle[tab[0]].findBrickTop(tabObstacle[tab[0]].findCastleColumn(tab[1])) != -1
					&& tabObstacle[tab[0]].findBrickTop(tabObstacle[tab[0]].findCastleColumn(tab[1])) != 27) {
				// Destroy the bricks of the affected obstacle
				tabObstacle[tab[0]].bricksTop(tab[1]);
				// destroy alien bullet
				this.yPos = 700;
			}
		}
	}

	public boolean touchShip(Player ship) {
		// Returns true if an Alien hits the ship
		if (this.yPos < ship.getyPos() + ship.getHeight() && this.yPos + this.height > ship.getyPos()
				&& this.xPos + this.width > ship.getxPos() && this.xPos < ship.getxPos() + ship.getWidth()) {
			this.yPos = 700;
			Audio.playSound("/sounds/shipDestroyed.wav");
			return true;
		} else {
			return false;
		}
	}
}
