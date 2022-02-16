package infrastructure

import com.formdev.flatlaf.FlatIntelliJLaf
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme
import gui.MainGui

fun main(){

    FlatIntelliJLaf.setup()
//    FlatOneDarkIJTheme.setup()

    val testGui = MainGui()
}