package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JPanel cardPanel;
    private JPanel loginPanel;
    private JTextField textFieldMulticastIp;
    private JButton connectButton;
    private JCheckBox reconnectNextTimeCheckBox;

    public MainGui(){
        super("ClipHero");
        this.setSize(350, 500);
        this.getContentPane().add(mainPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Set Location
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        this.setLocation(width - this.getWidth(), height - this.getHeight() - 25);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
