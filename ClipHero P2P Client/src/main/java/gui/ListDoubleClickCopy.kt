package gui

import client.ClipBoardHandler
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JList

class ListDoubleClickCopy(val list: JList<String>, val handler: ClipBoardHandler): MouseAdapter() {

    override fun mouseClicked(e: MouseEvent?) {

        if (e != null) {
            if(e.clickCount == 2){
                val index = list.locationToIndex(e.point)
                val listModel = list.model
                val item = listModel.getElementAt(index)
                list.ensureIndexIsVisible(index)
                handler.setClipboard(item)
            }
        }

    }

}