package infrastructure;

import client.ServerFinder;

import java.net.SocketException;
import java.net.UnknownHostException;

public class MainClipClient {

    public static void main(String[] args) {

        try {
            ServerFinder finder = new ServerFinder();
            finder.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

}
