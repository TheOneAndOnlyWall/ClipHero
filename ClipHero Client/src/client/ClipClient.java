package client;

import netzklassen.Client;

public class ClipClient extends Client {

    public ClipClient(String pServerIP, int pServerPort) {
        super(pServerIP, pServerPort);
    }

    @Override
    public void processMessage(String pMessage) {

    }
}
