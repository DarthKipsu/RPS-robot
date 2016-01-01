package rpc;

import lejos.nxt.Motor;

/**
 * Moves robot hand for a game.
 */
public class HandMover {
	public HandMover() {
		Motor.C.setSpeed(150);
	}

	public void play() {
		shakeHand(3);
	}

	public void liftHand() {
		Motor.C.rotate(-100);
	}

	private void shakeHand(int times) {
		for	(int i = 0; i < times; i++) {
			raiseHand();
			if (i != times - 1) {
				liftHand();
			}
		}
	}

	private void raiseHand() {
		Motor.C.rotate(100);
	}
}
