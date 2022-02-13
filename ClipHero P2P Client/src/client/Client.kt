package client

import protocol.PROTOCOL
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket

class Client (val address: String = "230.0.0.0", val port: Int = 50111): Thread() {

    private val socket = MulticastSocket(port)
    private var running = true
    private var buf = ByteArray(256)
    private var group = InetAddress.getByName(address)

    init {
        socket.joinGroup(group)
    }

    override fun run() {

        while(running){

            val packet = DatagramPacket(buf, buf.size)
            socket.receive(packet)
            val recievedAddress = packet.address
            val recievedPort = packet.port
            val recievedString = String(packet.data, 0, packet.length)

            println("IP: $address \nPort: $port \nMessage: $recievedString")

        }

        socket.leaveGroup(group)

    }

    fun broadcastMessage(message: String){
        val fullMessage = PROTOCOL.clipboardMessage + PROTOCOL.seperator + message
        val packet = DatagramPacket(fullMessage.toByteArray(), fullMessage.length, group, port)
        socket.send(packet)
    }

    fun stopClient(){running = false}

}