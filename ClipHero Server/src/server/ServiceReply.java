package server;

import java.io.IOException;
import java.net.*;

public class ServiceReply extends Thread{

    MulticastSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public ServiceReply() throws IOException {
        socket = new MulticastSocket(50111);
        InetAddress group = InetAddress.getByName("230.0.0.0");
        socket.joinGroup(group);
    }

    public void run(){
        running = true;

        while(running){
            try {

                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                String recieved = new String(packet.getData(), 0, packet.getLength());

                System.out.println(recieved);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void stopServiceReply(){
        running = false;
    }

}
