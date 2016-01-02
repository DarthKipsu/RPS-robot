package rpc;

import lejos.nxt.Motor;

/**
 * Moves robot hand for a game.
 */
public class HandMover {
	private HandPosition handPosition;
	private FingerMover fingers;

	public HandMover() {
		Motor.C.setSpeed(150);
		handPosition = HandPosition.DOWN;
		fingers = new FingerMover();
	}

	public HandPosition getHandPosition() {
		return handPosition;
	}

	public void play(int sign) {
		liftHand();
		shakeHand(3);
		fingers.playSign(sign);
	}

	public void liftHand() {
		if (handPosition == HandPosition.DOWN) {
			fingers.closeFist();
			Motor.C.rotate(-100);
			handPosition = HandPosition.UP;
		}
	}

	public void lowerHand() {
		if (handPosition == HandPosition.UP) {
			fingers.closeFist();
			Motor.C.rotate(100);
			handPosition = HandPosition.DOWN;
		}
	}

	private void shakeHand(int times) {
		for	(int i = 0; i < times; i++) {
			lowerHand();
			if (i != times - 1) {
				liftHand();
			}
		}
	}
}
