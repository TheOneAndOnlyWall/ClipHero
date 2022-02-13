package client

import gui.GuiClipboardHandler
import protocol.PROTOCOL
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket

class Client (val address: String = "230.0.0.0", val port: Int = 50111, val clipboardHandler: GuiClipboardHandler): Thread() {

    private val socket = MulticastSocket(port)
    private val clipboard = Toolkit.getDefaultToolkit().systemClipboard

    private var running = true
    private var buf = ByteArray(256)
    private var group = InetAddress.getByName(address)

    var autoPaste = false
    var autoCopy = false

    init {
        socket.joinGroup(group)
    }

    override fun run() {

        while(running){

            val packet = DatagramPacket(buf, buf.size)
            socket.receive(packet)
            processMessage(packet.address, packet.port, String(packet.data, 0, packet.length))

        }

        socket.leaveGroup(group)

    }

    private fun processMessage(receivedAddress: InetAddress, receivedPort: Int, receivedString: String){
        val messageType = receivedString.substring(0 until receivedString.indexOf(PROTOCOL.seperator))
        val message = receivedString.substring(receivedString.indexOf(PROTOCOL.seperator) + 1)

        println("Message Type: $messageType \nMessage: $message")

        when(messageType){
            PROTOCOL.clipboardMessage -> {
                if(autoCopy){
                    clipboard.setContents(StringSelection(message), null)
                    println("Copy to Clipboard")
                }
                clipboardHandler.processIncomingClipboardMessage(message)
            }
        }
    }

    fun broadcastMessage(messageType: String, message: String){
        val fullMessage = messageType + PROTOCOL.seperator + message
        val packet = DatagramPacket(fullMessage.toByteArray(), fullMessage.length, group, port)
        socket.send(packet)
    }

    fun broadcastClipboard(){
        broadcastMessage(PROTOCOL.clipboardMessage, clipboard.getData(DataFlavor.stringFlavor) as String)
    }

    fun stopClient(){running = false}

}