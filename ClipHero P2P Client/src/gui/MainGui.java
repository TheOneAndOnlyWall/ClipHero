package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class MainGui extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JPanel cardPanel;
    private JPanel loginPanel;
    private JTextField textFieldMulticastIp;
    private JButton connectButton;
    private JCheckBox automaticReconnectCheckBox;
    private JPanel applicationPanel;
    private JScrollPane scrollPaneClipHistory;
    private JTable tableClipHistory;
    private JButton buttonDisconnect;
    private JCheckBox checkBoxAutoCopy;
    private JCheckBox checkBoxAutoPaste;
    private JButton buttonSettings;
    private CardLayout cl;

    private static final ResourceBundle bundle = ResourceBundle.getBundle("gui.GuiStrings");

    public MainGui(){
        super("ClipHero");
        this.setSize(400, 500);
        this.getContentPane().add(mainPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Set Location
        GraphicsConfiguration config = this.getGraphicsConfiguration();
        Rectangle bounds = config.getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);

        int x = bounds.x + bounds.width - insets.right - this.getWidth();
        int y = bounds.y + bounds.height - this.getHeight() - 25;
        this.setLocation(x, y);

        cl = (CardLayout)(cardPanel.getLayout());

        connectButton.addActionListener(this);
        buttonDisconnect.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == connectButton){
            if(!textFieldMulticastIp.getText().isBlank()){
                cl.show(cardPanel, "Application");
            }else{
                JOptionPane.showMessageDialog(this, bundle.getString("NoIPWarning"));
            }
        }

        if(e.getSource() == buttonDisconnect){
            cl.show(cardPanel, "Login");
        }

    }
}
