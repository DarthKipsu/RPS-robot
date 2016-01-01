package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTConnector;

/**
 * Moves robot hand for a game.
 */
public class NxtConnector {
	private final NXTConnector connector;
    private final DataOutputStream output;
    private final DataInputStream input;

    public NxtConnector(String nxtName, String nxtAddr) throws NXTCommException, IOException {
		connector = new NXTConnector();
		connector.connectTo(nxtName, nxtAddr, NXTCommFactory.BLUETOOTH);
		
		output = connector.getDataOut();
		input = connector.getDataIn();
    }

	public void testConnection() throws IOException {
		System.out.println("Ask to raise hand");
		output.writeInt(1);
		output.flush();
		System.out.println("Received " + input.readInt());
		wait(1000);

		System.out.println("Ask to play");
		output.writeInt(2);
		output.flush();
		System.out.println("Received " + input.readInt());
		wait(1000);
	}

	public void closeConnection() throws IOException {
		System.out.println("Close");
		output.writeInt(0);
		output.flush();
		System.out.println("Received " + input.readInt());
		wait(1000);

		output.close();
		input.close();
		connector.close();
	}
	
	private static void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
