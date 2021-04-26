package resources;

import ie.gmit.sw.runner.Runner;

public class Time implements Runnable {

	//variables
	// waiting time between 2 loop turns: 5 ms
	private final int PAUSE = 5;
	public static int counter = 0;
	
	@Override
	public void run() {		
		while(Runner.game == true){ 
			counter++;
			// Calling the PaintComponent method of the scene object
			Runner.scene.repaint();
			// pause for 5 secs
			try {Thread.sleep(PAUSE);}
			catch (InterruptedException e) {}
		}
	}	
		
}