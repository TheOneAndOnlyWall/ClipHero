package client

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class ServerFinderKotlin(val address: String = "230.0.0.0", val port: Int = 50111): Thread() {

    var searchMessage = "FindClipHeroServer"
    val socket = DatagramSocket()
    val group = InetAddress.getByName(address)
    var running = true
    var buf = searchMessage.toByteArray()


    override fun run() {

        while (running) {
            val packet = DatagramPacket(buf, buf.size, group, port)
            socket.send(packet)

            Thread.sleep(1000)
        }

    }

    fun stopListening() {
        running = false
    }

}