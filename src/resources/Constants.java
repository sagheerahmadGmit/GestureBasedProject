package resources;

public abstract class Constants {
	
	// Dimensions for the game window
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_MARGIN = 50;
	
	// Dimensions for the player ship
	public static final int SHIP_WIDTH = 39;
	public static final int SHIP_HEIGHT = 24;
	
	// position of the player ship
	public final static int X_POS_OF_SHIP = (WINDOW_WIDTH - SHIP_WIDTH)/ 2;
	public final static int Y_POS_SHIP = 490;	
	
	// Ship movement unit
	public final static int DX_SHIP = 1;
	
	// Ship movement limit
	public final static int LIMIT_LEFT = 60;
	public final static int LIMIT_RIGHT = 500;	
	
	// Dimensions of the alien
	public static final int ALIEN_WIDTH = 33;
	public static final int ALIEN_HEIGHT= 25;	
	
	// Alien Position Settings
	public final static int ALT_INIT_ALIEN = 120;
	public final static int X_POS_INIT_ALIEN = 29 + WINDOW_MARGIN;
	public final static int ALIEN_GAP_LINE = 40;
	public final static int ALIEN_GAP_COLUMN = 10;
	
	// Alien movement unit
	public final static int DX_ALIEN = 2;
	public final static int DY_ALIEN = 20;
	public final static int ALIEN_SPEED = 1;	
	
	// Number of aliens
	public final static int NUMBER_OF_ALIENS = 50;
	
	// Bullet dimensions
	public static final int BULLET_WIDTH = 3;
	public static final int BULLET_HEIGHT = 13;	
	
	// Bullet Movement
	public final static int DY_SHIP_BULLET = 2;
	
	// Brick dimensions
	public static final int BRICK_DIMENSIONS = 2;
	
	// Dimensions of the obstacles
	public static final int OBSTACLE_WIDTH = 72;
	public static final int OBSTACLE_HEIGHT = 54;
		
	// Parameters for the position of the obstacles
	public final static int Y_POS_OBSTACLE = 400;
	public final static int X_POS_INIT_OBSTACLE = 39;
	public final static int OBSTACLE_GAP = 42;
	
	// Dimensions for bullet
	public static final int ALIEN_BULLET_WIDTH = 5;
	public static final int ALIEN_BULLET_HEIGHT = 15;	
	
	// alien bullet
	public final static int DY_BULLET_ALIEN = 3;

	// Dimensions for the saucer
	public static final int SAUCER_WIDTH = 42;
	public static final int SAUCER_HEIGHT = 22;

	// Position of the saucer
	public final static int X_POS_INIT_SAUCER = WINDOW_WIDTH;
	public final static int Y_POS_SAUCER = 50;	

	// Saucer displacement unit
	public final static int DX_SAUCER = 1;
	
	// Points awarded for destroying aliens
	public static final int HIGH_VALUE = 50;
	public static final int MIDDLE_VALUE = 40;
	public static final int BASIC_VALUE = 20;
	public static final int SAUCER_VALUE = 100;
}


