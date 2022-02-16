package infrastructure

import client.ClipBoardListener
import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme
import gui.MainGui
import java.awt.Toolkit
import java.awt.datatransfer.FlavorEvent
import java.awt.datatransfer.FlavorListener

fun main(){

//    FlatIntelliJLaf.setup()
    FlatOneDarkIJTheme.setup()

    val testGui = MainGui()
}