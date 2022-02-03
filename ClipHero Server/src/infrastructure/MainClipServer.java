package infrastructure;

import server.ServiceReply;

import java.net.SocketException;

public class MainClipServer {

    public static void main(String[] args) {
        try {
            ServiceReply reply = new ServiceReply();
            reply.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
