package client;

import java.io.IOException;
import java.net.*;

public class ServerFinder extends Thread{

    private DatagramSocket socket;
    private boolean running;

    private byte[] buf;

    private InetAddress localhost;

    private String searchMessage = "FindClipHeroServer";

    public ServerFinder() throws UnknownHostException, SocketException {
        localhost = InetAddress.getByName("255.255.255.255");
        socket = new DatagramSocket();
        socket.setBroadcast(true);
        buf = searchMessage.getBytes();
    }

    public void run(){
        running = true;

        while(running){
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length, localhost, 50111);
                socket.send(packet);

                Thread.sleep(1000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopSearch(){
        running = false;
    }

}
