package ie.gmit.sw.game;

import java.awt.Color;
import java.awt.Graphics;
import resources.Audio;
import resources.Constants;

public class Obstacle extends Entity {

	private final int ROW_NUM = Constants.OBSTACLE_HEIGHT / Constants.BRICK_DIMENSIONS;
	private final int COLUMN_NUM = Constants.OBSTACLE_WIDTH / Constants.BRICK_DIMENSIONS;

	// array containing the bricks of the obstacle (the box contains true for a
	// brick and false for empty)
	boolean obstacles[][] = new boolean[ROW_NUM][COLUMN_NUM];

	public Obstacle(int xPos) {
		// distance of the leftmost point of the castle
		super.xPos = xPos;
		// Ordinate of the top of the obstacle
		super.yPos = Constants.Y_POS_OBSTACLE;

		this.initTabObstacle();
	}

	// Creation of the initial table associated with the obstacle without damage
	public void initTabObstacle() {
		// We fill all the boxes of the array with true
		for (int ligne = 0; ligne < ROW_NUM; ligne++) {
			for (int colonne = 0; colonne < COLUMN_NUM; colonne++) {
				obstacles[ligne][colonne] = true;
			}
		}
		// We fill all the boxes without brick in the table with false
		// Bevelling of the top of the obstacle
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 2; j++) {
				obstacles[j][i] = false;
				obstacles[j][COLUMN_NUM - i - 1] = false;
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j < 4; j++) {
				obstacles[j][i] = false;
				obstacles[j][COLUMN_NUM - i - 1] = false;
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 4; j < 6; j++) {
				obstacles[j][i] = false;
				obstacles[j][COLUMN_NUM - i - 1] = false;
			}
		}
		// Entrance to the obstacle
		for (int k = 18; k < ROW_NUM; k++) {
			for (int i = 10; i < COLUMN_NUM - 10; i++) {
				obstacles[k][i] = false;
			}
		}
		// Bevelling entrance to the obstacles
		for (int i = 12; i < COLUMN_NUM - 12; i++) {
			for (int j = 16; j < 18; j++) {
				obstacles[j][i] = false;
				obstacles[j][COLUMN_NUM - i - 1] = false;
			}
		}
		for (int i = 14; i < COLUMN_NUM - 14; i++) {
			for (int j = 14; j < 16; j++) {
				obstacles[j][i] = false;
				obstacles[j][COLUMN_NUM - i - 1] = false;
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 4; j < 6; j++) {
				obstacles[j][i] = false;
				obstacles[j][COLUMN_NUM - i - 1] = false;
			}
		}
	}

	// Drawing of the obstacles
	public void drawObstacle(Graphics g2) {
		for (int i = 0; i < ROW_NUM; i++) {
			for (int j = 0; j < COLUMN_NUM; j++) {
				if (obstacles[i][j] == true) {
					g2.setColor(Color.GRAY);
				} else {
					g2.setColor(Color.ORANGE);
				}
				g2.fillRect(this.xPos + Constants.BRICK_DIMENSIONS * j, this.yPos + Constants.BRICK_DIMENSIONS * i,
						Constants.BRICK_DIMENSIONS, Constants.BRICK_DIMENSIONS);
			}
		}
	}

	public int findCastleColumn(int xMissile) {
		// Find the column of the table associated with the obstacle hit by the bullet
		int colonne = -1;
		colonne = (xMissile - this.xPos) / Constants.BRICK_DIMENSIONS;
		return colonne;
	}

	public int trouveBrique(int colonne) {
		// Find the first brick starting from the bottom of the column of the table
		// associated with the obstacle or return -1
		int j = ROW_NUM - 1;
		while (j >= 0 && obstacles[j][colonne] == false) {
			j--;
		}
		return j;
	}

	private void bricks(int line, int column) {
		// Eliminate the first 6 bricks of the column starting from the bottom if they
		// exist
		for (int counter = 0; counter < 6; counter++) {
			if (line - counter >= 0) {
				obstacles[line - counter][column] = false;
				if (column < COLUMN_NUM - 1) {
					obstacles[line - counter][column + 1] = false;
				}
			}
		}
	}

	public void breakBricks(int xTir) {
		// play the sound
		Audio.playSound("/sounds/breakBrick.wav");
		int colonne = this.findCastleColumn(xTir);
		this.bricks(trouveBrique(colonne), colonne);
	}

	public int findBrickTop(int col) {
		// Find the first brick starting from the top of the table column
		// associated with the obstacle or return -1
		int line = 0;
		if (col != -1) {
			while (line < ROW_NUM && obstacles[line][col] == false) {
				line++;
			}
		}
		return line;
	}

	private void removeBricksTop(int line, int col) {
		// Elimination of the first 6 bricks of the column starting from the top if they
		// exist
		for (int compteur = 0; compteur < 6; compteur++) {
			if (line + compteur < ROW_NUM && col != -1) {
				obstacles[line + compteur][col] = false;
				if (col < COLUMN_NUM - 1) {
					obstacles[line + compteur][col + 1] = false;
				}
			}
		}
	}

	public void bricksTop(int xTir) {
		// play the sound
		Audio.playSound("/sounds/breakBrick.wav");
		int colonne = this.findCastleColumn(xTir);
		this.removeBricksTop(findBrickTop(colonne), colonne);
	}
}
