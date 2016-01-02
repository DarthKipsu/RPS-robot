package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import lejos.pc.comm.NXTCommException;

/**
 * Main program used to connect to Lego robot and initiate commands.
 */
public class Main {
	private static NxtConnector nxtConnector;
	private static File pipe;

	public static void main(String[] args) throws NXTCommException, IOException {
		nxtConnector = new NxtConnector("NXT", "00:16:53:08:D4:4D");
		pipe = createPipe();

		nxtConnector.readPipeInstructions(
				new BufferedReader(new FileReader(pipe)));

		closeConnections();
	}

	private static File createPipe() throws IOException {
		File p = new File("nxtpipe");
		p.createNewFile();
		return p;
	}

	private static void closeConnections() throws IOException {
		nxtConnector.closeConnection();
		pipe.delete();
	}
}
