package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JPanel cardPanel;
    private JPanel loginPanel;
    private JTextField textFieldMulticastIp;

    public MainGui(){
        this.setSize(300, 500);
        this.getContentPane().add(mainPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
