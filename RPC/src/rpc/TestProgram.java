package rpc;

import lejos.nxt.Motor;

/**
 * Test program to demonstrate different hand functions
 */
public class TestProgram {

	public void run() {
		// Test finger moving functionality
		Motor.A.setSpeed(200);
		Motor.B.setSpeed(200);
		
		// scissors
		Motor.A.rotate(220);
		wait(1000);
		Motor.A.rotate(-220);
		
		wait(1000);
		
		// paper
		// TODO: simultaneous fingers movement
		Motor.A.rotate(220);
		Motor.B.rotate(180);
		wait(1000);
		Motor.A.rotate(-220);
		Motor.B.rotate(-180);
	}
	
	private void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
