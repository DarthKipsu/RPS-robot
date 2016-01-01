
package nxt;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

/**
 * Handles communications between the main program and NXT brick.
 */
public class NxtConnector {
    private final NXTComm connection;
    private final NXTInfo info;

    public NxtConnector(String nxtName) throws NXTCommException {
        connection = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
        info = new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT", nxtName);
    }
}
