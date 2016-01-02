package rpc;

import lejos.nxt.Motor;

/**
 * Moves robot fingers to play rock, paper or scissors.
 */
public class FingerMover {
	private FingerPosition fingerPosition;
	
	public FingerMover() {
		Motor.A.setSpeed(200);
		Motor.B.setSpeed(200);
		fingerPosition = FingerPosition.ROCK;
	}

	public void playSign(int sign) {
		if (sign == 1) {
			playPaper();
		} else if (sign == 2) {
			playScissors();
		} else {
			// no need to do anything, fingers already at rock
		}
	}

	public void closeFist() {
		if (fingerPosition == FingerPosition.PAPER) {
			closePaper();
		} else if (fingerPosition == FingerPosition.SCISSORS) {
			closeScissors();
		} else {
			// no need to do anything, fist already closed
		}
	}

	private void playPaper() {
		Motor.A.rotate(220, true);
		Motor.B.rotate(180);
		fingerPosition = FingerPosition.PAPER;
	}

	private void closePaper() {
		Motor.A.rotate(-220, true);
		Motor.B.rotate(-180);
		fingerPosition = FingerPosition.ROCK;
	}

	private void playScissors() {
		Motor.A.rotate(220);
		fingerPosition = FingerPosition.SCISSORS;
	}

	private void closeScissors() {
		Motor.A.rotate(-220);
		fingerPosition = FingerPosition.ROCK;
	}
}
