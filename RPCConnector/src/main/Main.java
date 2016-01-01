package main;

import java.io.IOException;

import lejos.pc.comm.NXTCommException;

/**
 * Main program used to connect to Lego robot and initiate commands.
 */
public class Main {
	private static NxtConnector connector;

	public static void main(String[] args) throws NXTCommException, IOException {
		// Test moving the entire arm.
		connector = new NxtConnector("NXT", "00:16:53:08:D4:4D");
		connector.testConnection();
		connector.closeConnection();
	}
}
