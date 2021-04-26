package ie.gmit.sw.game;

import java.awt.Graphics;
import java.util.Random;

import ie.gmit.sw.runner.Runner;
import resources.*;

public class AlienGroup {
	
	// Array containing all the aliens (50)
	private Alien aliens[][] = new Alien[5][10];
	private boolean goRight, pos1;
	private int speed;

	// Location of dead alien in aliens array
	private int[] deadAliens = { -1, -1 };
	
	Random rand = new Random();
	private int numAliens = Constants.NUMBER_OF_ALIENS;
	private int alienCounter = 0;

	public AlienGroup() {
		this.initAlienTable();
		this.goRight = true;
		this.pos1 = true;
		this.speed = Constants.ALIEN_SPEED;
	}

	private void initAlienTable() {
		// Method which fills the complete array of aliens
		for (int col = 0; col < 10; col++) {
			this.aliens[0][col] = new Alien(
					Constants.X_POS_INIT_ALIEN + (Constants.ALIEN_WIDTH + Constants.ALIEN_GAP_COLUMN) * col,
					Constants.ALT_INIT_ALIEN, "/images/strongAlien1.png", "/images/strongAlien2.png");
			for (int ligne = 1; ligne < 3; ligne++) {
				this.aliens[ligne][col] = new Alien(
						Constants.X_POS_INIT_ALIEN + (Constants.ALIEN_WIDTH + Constants.ALIEN_GAP_COLUMN) * col,
						Constants.ALT_INIT_ALIEN + Constants.ALIEN_GAP_LINE * ligne, "/images/middleAlien1.png",
						"/images/middleAlien2.png");
			}
			for (int ligne = 3; ligne < 5; ligne++) {
				this.aliens[ligne][col] = new Alien(
						Constants.X_POS_INIT_ALIEN + (Constants.ALIEN_WIDTH + Constants.ALIEN_GAP_COLUMN) * col,
						Constants.ALT_INIT_ALIEN + Constants.ALIEN_GAP_LINE * ligne, "/images/basicAlien1.png",
						"/images/basicAlien2.png");
			}
		}
	}

	public void drawAliens(Graphics g) {
		if (Time.counter % (100 - 10 * this.speed) == 0) {
			this.moveAliens();
		}
		// Draw the aliens contained in the tabAlien array
		for (int col = 0; col < 10; col++) {
			for (int row = 0; row < 5; row++) {
				if (this.aliens[row][col] != null) {
					this.aliens[row][col].imageChoice(pos1);
					g.drawImage(this.aliens[row][col].getImg(), this.aliens[row][col].getxPos(),
							this.aliens[row][col].getyPos(), null);
				}
			}
		}
	}

	private boolean leftEdgeKey() {
		// Method which detects the left edge of the window
		boolean reponse = false;
		for (int col = 0; col < 10; col++) {
			for (int row = 0; row < 5; row++) {
				if (this.aliens[row][col] != null) {
					if (this.aliens[row][col].getxPos() < Constants.WINDOW_MARGIN) {
						reponse = true;
						break;
					}
				}
			}
		}
		return reponse;
	}

	private boolean rightEdgeKey() {
		// Method which detects the right edge of the window
		boolean reponse = false;
		for (int col = 0; col < 10; col++) {
			for (int row = 0; row < 5; row++) {
				if (this.aliens[row][col] != null) {
					if (this.aliens[row][col].getxPos() > Constants.WINDOW_WIDTH - Constants.ALIEN_WIDTH
							- Constants.DX_ALIEN - Constants.WINDOW_MARGIN) {
						reponse = true;
						break;
					}
				}
			}
		}
		return reponse;
	}

	public void alienTurnAndDescend() {
		// Method which changes the direction of movement of the alien and brings it down a notch
		if (this.rightEdgeKey() == true) {
			for (int col = 0; col < 10; col++) {
				for (int row = 0; row < 5; row++) {
					if (this.aliens[row][col] != null) {
						this.aliens[row][col].setyPos(this.aliens[row][col].getyPos() + Constants.DY_ALIEN);
					}
				}
			}
			this.goRight = false;
			if (this.speed < 9) {
				this.speed++;
			}
		} else {
			if (this.leftEdgeKey() == true) {
				for (int col = 0; col < 10; col++) {
					for (int row = 0; row < 5; row++) {
						if (this.aliens[row][col] != null) {
							this.aliens[row][col].setyPos(this.aliens[row][col].getyPos() + Constants.DY_ALIEN);
						}
					}
				}
				this.goRight = true;
				if (this.speed < 9) {
					this.speed++;
				}
			}
		}
	}

	public void moveAliens() {
		// Method which manages the movement of aliens
		if (this.deadAliens[0] != -1) {
			// Eliminate the dead alien if necessary
			eliminateAlienDeath(deadAliens);
			// Reinitialise dead aliens
			deadAliens[0] = -1;
		}

		// Move to the right
		if (this.goRight == true) {
			for (int col = 0; col < 10; col++) {
				for (int row = 0; row < 5; row++) {
					if (this.aliens[row][col] != null) {
						this.aliens[row][col].setxPos(this.aliens[row][col].getxPos() + Constants.DX_ALIEN);
					}
				}
			}
		} else {
			// Move to the left
			for (int col = 0; col < 10; col++) {
				for (int row = 0; row < 5; row++) {
					if (this.aliens[row][col] != null) {
						this.aliens[row][col].setxPos(this.aliens[row][col].getxPos() - Constants.DX_ALIEN);
					}
				}
			}
		}
		// the aliens make a sound
		this.alienSound();
		// Increment the sound counter
		this.alienCounter++;
		// Change the image of the alien
		if (this.pos1 == true) {
			this.pos1 = false;
		} else {
			this.pos1 = true;
		}
		// Update the direction of movement if an alien reaches the edge of the window
		this.alienTurnAndDescend();
	}

	public void alienShootsPlayer(PlayerShot ss) {
		// Detection of ship fire contact with alien
		for (int col = 0; col < 10; col++) {
			for (int row = 0; row < 5; row++) {
				if (this.aliens[row][col] != null) {
					if (ss.tueAlien(this.aliens[row][col]) == true) {
						this.aliens[row][col].isAlive = false; // On tue l'alien
						// destroy the bulet
						ss.yPos = -1;
						// We record the position of the dead alien in the table
						this.deadAliens[0] = row;
						this.deadAliens[1] = col;
						if (row == 0) {
							Runner.scene.score = Runner.scene.score + Constants.HIGH_VALUE;
						} else if (row > 0 && row < 3) {
							Runner.scene.score = Runner.scene.score + Constants.MIDDLE_VALUE;
						} else {
							Runner.scene.score = Runner.scene.score + Constants.BASIC_VALUE;
						}
						break;
					}
				}
			}
		}
	}

	private void eliminateAlienDeath(int[] deadAlien) {
		// Method which removes the dead alien from the array (null case)
		this.aliens[deadAlien[0]][deadAlien[1]] = null;
		this.numAliens--;
	}

	public int[] chooseAlien() {
		// Return the position of an alien drawn at random in tabAlien at the bottom of its col
		int positionAlien[] = { -1, -1 };
		// We check that there are still living aliens
		if (this.numAliens != 0) {
			do {
				// We randomly draw a column
				int colonne = rand.nextInt(10);
				// table for aliens
				// We are looking for the 1st living alien
				for (int row = 4; row >= 0; row--) {
					// starting from the bottom
					if (aliens[row][colonne] != null) {
						positionAlien[0] = this.aliens[row][colonne].getxPos();
						positionAlien[1] = this.aliens[row][colonne].getyPos();
						break;
					}
				}
			} while (positionAlien[0] == -1);
		}
		return positionAlien;
	}

	// Method which plays the sound of the alien (4 possible sounds)
	private void alienSound() {
		int counter = this.alienCounter % 4;
		if (counter == 0) {
			Audio.playSound("/sounds/Alien1.wav");
		} else if (counter == 1) {
			Audio.playSound("/sounds/Alien2.wav");
		} else if (counter == 2) {
			Audio.playSound("/sounds/Alien3.wav");
		} else {
			Audio.playSound("/sounds/Alien4.wav");
		}
	}

	public int getNumOfAliens() {
		return numAliens;
	}

	public int alienPosition() {
		// Return the altitude of the feet of the lowest alien
		int pos = 0, finalPos = 0;
		for (int col = 1; col < 10; col++) {
			for (int row = 4; row >= 0; row--) {
				if (this.aliens[row][col] != null) {
					pos = this.aliens[row][col].yPos + this.aliens[row][col].height;
					break;
				}
			}
			if (pos > finalPos) {
				finalPos = pos;
			}
		}
		return finalPos;
	}
}
