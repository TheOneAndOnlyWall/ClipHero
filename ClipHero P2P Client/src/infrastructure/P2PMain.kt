package infrastructure

import client.Client
import com.formdev.flatlaf.FlatDarculaLaf
import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatIntelliJLaf
import com.formdev.flatlaf.FlatLightLaf
import gui.MainGui

fun main(){

//    val client = Client()
//    client.start()
//    client.broadcastClipboard()

//    FlatIntelliJLaf.setup()
    FlatDarkLaf.setup()

    val testGui = MainGui()
}