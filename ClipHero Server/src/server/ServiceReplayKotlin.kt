package server

import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket

class ServiceReplayKotlin(val address: String = "230.0.0.0", val port: Int = 50111): Thread() {

    val socket = MulticastSocket(port)
    var running = true
    var buf = ByteArray(256)

    init {
        socket.joinGroup(InetAddress.getByName(address))
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

    }

    fun stopServiceReply(){running = false}

}