package ie.gmit.sw.game;

import javax.swing.ImageIcon;

//import resources.Constants;

public class Alien extends Entity {
	
	private static final int ALIEN_WIDTH = 33;
	private static final int ALIEN_HEIGHT= 25;
	
	//constructor
	public Alien(int xPos, int yPos, String strImg1, String strImg2) {
		// Initialisation of the variables
		super.xPos = xPos;
		super.yPos = yPos;
		super.width = ALIEN_WIDTH;
		super.height = ALIEN_HEIGHT;
		super.dx = 0;
		super.dy = 0;
		super.isAlive = true;
		// images of the alien
		super.strImg1 = strImg1;
		super.strImg2 = strImg2;
		super.strImg3 = "/images/alienDies.png";
		// Loading the alien image
		super.icon = new ImageIcon(getClass().getResource(super.strImg1));
		super.img = this.icon.getImage();
	}
	
	public void imageChoice(boolean pos1) {
		// Method that loads the image of the alien according to its state and position (1 or 2)
		if(this.isAlive == true) {
		 if(pos1 == true) {super.icon = new ImageIcon(getClass().getResource(strImg1));} 
		  else {super.icon = new ImageIcon(getClass().getResource(strImg2));}
		}
		else {super.icon = new ImageIcon(getClass().getResource(strImg3));}		
		// reload the image
		super.img = this.icon.getImage();
	}
}
