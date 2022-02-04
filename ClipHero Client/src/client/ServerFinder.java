package client;

import java.io.IOException;
import java.net.*;

public class ServerFinder extends Thread{

    private DatagramSocket socket;
    private boolean running;

    private byte[] buf;

    private InetAddress group;

    private String searchMessage = "FindClipHeroServer";

    public ServerFinder() throws UnknownHostException, SocketException {
        socket = new DatagramSocket();
        group = InetAddress.getByName("230.0.0.0");
        buf = searchMessage.getBytes();
    }

    public void run(){
        running = true;

        while(running){
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 50111);
                socket.send(packet);

                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopSearch(){
        running = false;
    }

}
