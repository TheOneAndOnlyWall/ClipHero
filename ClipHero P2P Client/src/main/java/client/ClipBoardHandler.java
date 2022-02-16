package client;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClipBoardHandler extends Thread implements ClipboardOwner {
    private Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

    private ClipboardChangeListener listener;


    public void setClipChangeListener(ClipboardChangeListener listener){
        this.listener = listener;
    }

    @Override
    public void run() {
        Transferable trans = sysClip.getContents(this);
        TakeOwnership(trans);
    }

    @Override
    public void lostOwnership(Clipboard c, Transferable t) {

        try {
            ClipBoardHandler.sleep(250);  //waiting e.g for loading huge elements like word's etc.
        } catch(Exception e) {
            System.out.println("Exception: " + e);
        }
        Transferable contents = sysClip.getContents(this);
        try {
            process_clipboard(contents, c);
        } catch (Exception ex) {
            Logger.getLogger(ClipBoardHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        TakeOwnership(contents);

    }

    private void TakeOwnership(Transferable t) {
        sysClip.setContents(t, this);
    }

    private void process_clipboard(Transferable t, Clipboard c) {
        String tempText;
        Transferable trans = t;

        try {
            if (trans != null?trans.isDataFlavorSupported(DataFlavor.stringFlavor):false) {
                tempText = (String) trans.getTransferData(DataFlavor.stringFlavor);
                if(listener != null){
                    listener.clipboardChanged(tempText);
                }
            }

        } catch (Exception e) {
        }
    }

    public void setClipboard(String newContent){
        sysClip.setContents(new StringSelection(newContent), this);
    }

    public String getClipboardMessage() throws IOException, UnsupportedFlavorException {return (String)sysClip.getData(DataFlavor.stringFlavor);}

}