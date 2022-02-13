package infrastructure

import client.ServerFinderKotlin
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.FlavorEvent
import java.awt.datatransfer.FlavorListener
import java.awt.datatransfer.StringSelection

fun main(){
    var finder = ServerFinderKotlin()
    finder.start()

    //Beispiel um Clipboard zu setzen
    val myString = "Test"
    val stringSelection = StringSelection(myString)
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(stringSelection, null)

    //Beispiel um Clipboard zu lesen
    Toolkit.getDefaultToolkit().systemClipboard.addFlavorListener(object: FlavorListener{
        override fun flavorsChanged(e: FlavorEvent){
            println("Clipboard updated to: ${e.source} ${e.toString()}")
            println(Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor))
        }
    })
}