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
			if (n == -1) {
				continueReading = false;
			} else if (n == 0 | n == 1 | n == 2) {
				hand.play(n);
			} else if (n == 3) {
				hand.liftHand();
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
