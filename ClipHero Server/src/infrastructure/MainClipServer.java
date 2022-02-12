package infrastructure;

import server.ServiceReplayKotlin;
import server.ServiceReply;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainClipServer {

    public static void main(String[] args) {

        try {
            ServiceReply reply = new ServiceReply();
            reply.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
