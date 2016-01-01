
package nxt;

import java.io.File;

/**
 * Handles communications between the main program and NXT brick connector.
 */
public class NxtConnector {

    public NxtConnector(String nxtpipe) {
        if (new File(nxtpipe).exists()) {
            System.out.println("Use robot");
        } else {
            System.out.println("Dont use robot");
        }
    }
}
