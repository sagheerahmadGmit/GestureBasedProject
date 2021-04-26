package ie.gmit.sw.runner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ie.gmit.sw.game.AlienGroup;
import ie.gmit.sw.game.Obstacle;
import ie.gmit.sw.game.Saucer;
import ie.gmit.sw.game.Player;
import ie.gmit.sw.game.AlienShot;
import ie.gmit.sw.game.PlayerShot;
import resources.Time;
import resources.Keyboard;
import resources.Constants;

public class Scene extends JPanel {
	private static final long serialVersionUID = 1L;
	// variables
	public Player ship = new Player();
	public AlienGroup ag = new AlienGroup();
	public PlayerShot ss = new PlayerShot();

	// Create an array containing the 4 obstacles
	public Obstacle obstacle[] = new Obstacle[4];

	public AlienShot alien1, alien2, alien3;

	public Saucer saucer;
	private boolean paused = false;

	private Font scoreFont = new Font("Arial", Font.PLAIN, 20);
	private Font textFont = new Font("Arial", Font.PLAIN, 80);

	public boolean playing;

	public int score = 0;

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	// constructor
	public Scene() {
		super();

		// Instantiate the obstacle
		for (int colonne = 0; colonne < 4; colonne++) {
			this.obstacle[colonne] = new Obstacle(Constants.WINDOW_MARGIN + Constants.X_POS_INIT_OBSTACLE
					+ colonne * (Constants.OBSTACLE_WIDTH + Constants.OBSTACLE_GAP));
		}

		// Instantiate the keyboard
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(new Keyboard());

		// Instantiate the stop watch
		Thread chrono = new Thread(new Time());
		chrono.start();
	}

	public boolean stillPlaying() {
		// check if player is still alive
		if (this.ship.isAlive() == false) {
			playing = false;
		}
		return playing;
	}

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

		// Display the score
		g.setFont(scoreFont);
		g.drawString("SCORE : " + score, 400, 25);

		// Draw the ships
		this.ship.drawShip(g2);

		// Draw the aliens
		this.ag.drawAliens(g2);

		// Draw the shpaceship shooting
		this.ss.drawShipBullet(g2);

		// Detect the contact between the ship and the alien
		this.ag.alienShootsPlayer(this.ss);

		// draw the obstacles
		for (int i = 0; i < 4; i++) {
			this.obstacle[i].drawObstacle(g2);
		}

		// message for the game
		if (Time.counter < 500) {
			g.setFont(textFont);
			g.drawString("Good Luck!!", 95, 100);
		}

		// Game ended
		if (this.ship.isAlive() == false) {
			g.setFont(textFont);
			g.drawString("GAME OVER!!", 50, 100);
		}

		// Detect contact between ship and the obstacles
		this.ss.playerdestroysObstacle(obstacle);

		// Draw the alien shot
		if (Time.counter % 500 == 0) {
			alien1 = new AlienShot(this.ag.chooseAlien());
		}
		if (this.alien1 != null) {
			this.alien1.drawAlienBullet(g2);
			// Detect contact between alien and the obstacles
			this.alien1.alienHitsObstcale(obstacle);
			if (this.alien1.touchShip(ship) == true) {
				this.ship.setIsAlive(false);
			}
		}
		if (Time.counter % 750 == 0) {
			alien2 = new AlienShot(this.ag.chooseAlien());
		}
		if (this.alien2 != null) {
			this.alien2.drawAlienBullet(g2);
			// Detect contact between alien and the obstacles
			this.alien2.alienHitsObstcale(obstacle);
			if (this.alien2.touchShip(ship) == true) {
				this.ship.setIsAlive(false);
			}
		}
		if (Time.counter % 900 == 0) {
			alien3 = new AlienShot(this.ag.chooseAlien());
		}
		if (this.alien3 != null) {
			this.alien3.drawAlienBullet(g2);
			// Detect contact between alien and the obstacles
			this.alien3.alienHitsObstcale(obstacle);
			if (this.alien3.touchShip(ship) == true) {
				this.ship.setIsAlive(false);
			}
		}
		// Draw the saucer
		if (Time.counter % 2500 == 0) {
			saucer = new Saucer();
		}
		if (this.saucer != null) {
			if (this.saucer.getxPos() > 0) {
				// Detect contact between ship and saucer
				if (this.ss.destroySaucer(this.saucer) == true) {
					if (this.saucer.getDx() != 0) {
						this.score = this.score + Constants.SAUCER_VALUE;
					}
					this.saucer.setDx(0);
					this.saucer.setIsAlive(false);
					this.saucer.saucerMusic.stop();
					this.saucer.saucerDestroyed.play();
				}
				this.saucer.drawSaucer(g2);
			} else {
				this.saucer = null;
			}
		}

		if (this.ag.getNumOfAliens() == 0) {
			ag = new AlienGroup();
		}

		if (this.ag.alienPosition() > Constants.Y_POS_SHIP) {
			this.ship.destroShip();
		}
	}
}
