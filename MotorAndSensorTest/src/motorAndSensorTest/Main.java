package motorAndSensorTest;

import lejos.nxt.*;

/**
 * Test basic functionality of motors with NXT brick.
 */
public class Main {
	
	private static Cruiser cruiser = new Cruiser();

	public static void main(String[] args) {
		System.out.println("Hello World");
		Button.waitForPress();
		
		// Motors more in detail in Cruiser.java
		cruiser.moveForward(5);
		cruiser.rotateLeft(90);
		cruiser.moveForward(5);
		cruiser.moveBackward(5);
		cruiser.rotateLeft(90);
		cruiser.moveBackward(5);
	}
}
