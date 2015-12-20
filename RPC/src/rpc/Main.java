package rpc;

import lejos.nxt.*;

/**
 * Main program to start rock, paper & scissors program
 */
public class Main {

	public static void main(String[] args) {
		// Test finger moving functionality
		Motor.A.setSpeed(45);
		Motor.A.rotate(220);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Motor.A.rotate(-220);
	}
}
