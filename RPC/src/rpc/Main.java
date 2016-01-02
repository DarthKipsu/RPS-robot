package rpc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

/**
 * Main program to start rock, paper & scissors program
 */
public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Waiting...");
		BTConnection btc = Bluetooth.waitForConnection();

		DataInputStream input = btc.openDataInputStream();
		DataOutputStream output = btc.openDataOutputStream();
		HandMover hand = new HandMover();

		System.out.println("Reading data");
		
		boolean continueReading = true;
		while (continueReading) {
			int n = input.readInt();
			if (n == 0) {
				continueReading = false;
			} else if (n == 1) {
				hand.liftHand();
			} else if (n == 2) {
				hand.play();
			}
			System.out.println("read " + n);
			output.writeInt(n);
			output.flush();
		}

		System.out.println("Closing connections");
		input.close();
		output.close();
		if (hand.getHandPosition() == HandPosition.UP) {
			hand.lowerHand();
		}
		wait(500); // wait for data to drain
		btc.close();
	}

	private static void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
