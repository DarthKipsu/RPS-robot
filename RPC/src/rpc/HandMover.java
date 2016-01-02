package rpc;

import lejos.nxt.Motor;

/**
 * Moves robot hand for a game.
 */
public class HandMover {
	private HandPosition handPosition;

	public HandMover() {
		Motor.C.setSpeed(150);
		handPosition = HandPosition.DOWN;
	}

	public HandPosition getHandPosition() {
		return handPosition;
	}

	public void play() {
		liftHand();
		shakeHand(3);
	}

	private void shakeHand(int times) {
		for	(int i = 0; i < times; i++) {
			lowerHand();
			if (i != times - 1) {
				liftHand();
			}
		}
	}

	public void liftHand() {
		if (handPosition == HandPosition.DOWN) {
			Motor.C.rotate(-100);
		}
	}

	public void lowerHand() {
		if (handPosition == HandPosition.UP) {
			Motor.C.rotate(100);
		}
	}
}
