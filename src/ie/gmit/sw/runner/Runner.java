package ie.gmit.sw.runner;

import javax.swing.JFrame;
import resources.Constants;

public class Runner {

	// variables
	public static Scene scene;
	public static PauseScene pauseScene;
	public static boolean game = true;

	// Jframe windows
	JFrame gameWindow = new JFrame("GUI Project");
	JFrame pauseWindow = new JFrame("Game Paused");
	JFrame newGame = new JFrame("GUI Project");

	// speech recognition for starting the game and pausing
	public void speech() throws InterruptedException {

		// load in the grammar for speech
		voce.SpeechInterface.init("./resources", false, true, "./resources", "start");

		// simple menu for game
		System.out.println("-------------------------------------------------------------");
		System.out.println("Welcome to Space Game!!");
		System.out.println("In order to start the game please say 'Start'!!");
		System.out.println("In order to pause the game please say 'pause'!!");
		System.out.println("In order to continue the game please say 'continue'!!");
		System.out.println("In order to restart the game please say 'restart'!!");
		System.out.println("In order to quit the game please say 'exit'!!");
		System.out.println("-------------------------------------------------------------");

		boolean start = false;
		while (!start) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
			while (voce.SpeechInterface.getRecognizerQueueSize() > 0) {
				String s = voce.SpeechInterface.popRecognizedString();

				// check if s is equal to start
				if (s.contentEquals("start") || s.contentEquals("play")) {
					
					start();
					System.out.println("Game Started");
					System.out.println("Rotate your arm left and right to move!");
					System.out.println("Make a fist to shoot!");
					
				} else if (s.contentEquals("stop") || s.contentEquals("pause")) {
					
					pause();
					System.out.println("Game is currently paused - say Continue to unpause");
					
				} else if (s.contentEquals("continue") || s.contentEquals("resume")) {
					
					pauseWindow.setVisible(false);
					gameWindow.setVisible(true);
					System.out.println("Game is unpaused - say Stop to pause");
					
				} else if (s.contentEquals("restart")) {
					
					gameWindow.dispose();
					gameWindow.repaint();
					start();
					System.out.println("Starting a new Game");

				} else if (s.contentEquals("exit")) {
					
					System.out.println("Exiting Game....");
					System.exit(0);
				} else {
					s = "Keyword Not Detected";
				}
				System.out.println("You said: " + s);
			}
		}
		voce.SpeechInterface.destroy();
	}

	public void start() {
		// Create the application
		gameWindow = new JFrame("GUI Project");
		gameWindow.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		gameWindow.setResizable(false);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setAlwaysOnTop(true);

		// Associating the Scene panel with the window
		scene = new Scene();
		gameWindow.setContentPane(scene);

		gameWindow.setVisible(true);
	}

	public void pause() {
		gameWindow.setVisible(false);

		// pause panel
		pauseWindow = new JFrame("Game Paused");
		pauseWindow.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		pauseWindow.setResizable(false);
		pauseWindow.setLocationRelativeTo(null);
		pauseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pauseWindow.setAlwaysOnTop(true);

		// Associating the Scene panel with the window
		pauseScene = new PauseScene();
		pauseWindow.setContentPane(pauseScene);

		pauseWindow.setVisible(true);
	}

	public static void main(String[] args) throws InterruptedException {
		Runner r = new Runner();
		r.speech();
	}
}
