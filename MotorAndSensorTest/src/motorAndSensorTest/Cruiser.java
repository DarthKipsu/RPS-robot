package motorAndSensorTest;

import lejos.nxt.Motor;

public class Cruiser {
	
	private double tireSpacing;
	private double travelPerDegree;
	
	public Cruiser() {
		tireSpacing = 15.6;
		travelPerDegree = 9;
	}
	
	public void moveForward(int centimeters) {
		int movement = (int)Math.round(centimeters / travelPerDegree * 360.0);
		move(movement);
	}
	
	public void moveBackward(int centimeters) {
		int movement = (int)Math.round(centimeters / travelPerDegree * 360.0);
		move(-movement);
	}
	
	public void rotateLeft(int degrees) {
		int rotation = (int)Math.round(tireSpacing * Math.PI / (360 / degrees) / travelPerDegree * 360.0);
		rotate(-rotation);
	}
	
	public void rotateRight(int degrees) {
		int rotation = (int)Math.round(tireSpacing * Math.PI / (360 / degrees) / travelPerDegree * 360.0);
		rotate(rotation);
	}
	
	public void closeMotors() {
		Motor.B.stop();
		Motor.A.stop();
	}
	
	private void move(int movement) {
		Motor.B.rotate(movement, true);
		Motor.A.rotate(movement);
	}
	
	private void rotate(int rotation) {
		Motor.B.rotate(rotation, true);
		Motor.A.rotate(-rotation);
	}

}
