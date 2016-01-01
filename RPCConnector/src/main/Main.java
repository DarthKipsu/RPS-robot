package main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import lejos.pc.comm.NXTCommException;

/**
 * Main program used to connect to Lego robot and initiate commands.
 */
public class Main {
	private static NxtConnector nxtConnector;
	private static RandomAccessFile pipe;

	public static void main(String[] args) throws NXTCommException, IOException {
		// Test moving the entire arm.
		nxtConnector = new NxtConnector("NXT", "00:16:53:08:D4:4D");
		pipe = new RandomAccessFile("nxtpipe", "rw");

		nxtConnector.testConnection();

		closeConnections();
	}

	private static void closeConnections() throws IOException {
		nxtConnector.closeConnection();
		pipe.close();
		new File("nxtpipe").delete();
	}
}
