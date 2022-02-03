package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServiceReply extends Thread{

    DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public ServiceReply() throws SocketException {
        socket = new DatagramSocket(50111);
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
