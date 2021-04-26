package resources;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ie.gmit.sw.runner.Runner;

public class Keyboard implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		if (Runner.scene.ship.isAlive() == true) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				Runner.scene.ship.setDx(Constants.DX_SHIP);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				Runner.scene.ship.setDx(-Constants.DX_SHIP);
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (Runner.scene.ss.isShipBullet() == false) {
					Audio.playSound("/sounds/playerBullet.wav");
					Runner.scene.ss.setyPos(Constants.Y_POS_SHIP - Constants.BULLET_HEIGHT);
					Runner.scene.ss.setxPos(Runner.scene.ship.getxPos() + Constants.SHIP_WIDTH / 2 - 1);
					Runner.scene.ss.setShipBullet(true);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Runner.scene.ship.setDx(0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
