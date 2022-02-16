package gui;

import client.Client;
import protocol.PROTOCOL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ResourceBundle;

public class MainGui extends JFrame implements ActionListener, KeyListener, GuiClipboardHandler {
    private JPanel mainPanel;
    private JPanel cardPanel;
    private JPanel loginPanel;
    private JTextField textFieldMulticastIp;
    private JButton connectButton;
    private JCheckBox automaticReconnectCheckBox;
    private JPanel applicationPanel;
    private JScrollPane scrollPaneClipHistory;
    private JButton buttonDisconnect;
    private JCheckBox checkBoxAutoCopy;
    private JCheckBox checkBoxAutoPaste;
    private JButton buttonSettings;
    private JTextField textFieldSendMessage;
    private JButton sendButton;
    private JButton clipboardButton;
    private JPanel settlingsPanel;
    private JComboBox comboBoxTheme;
    private JCheckBox checkBoxStartup;
    private JCheckBox checkBoxStartMinimized;
    private JButton okayButton;
    private JButton cancelButton;
    private JButton applyButton;
    private JList listClipHistory;
    private CardLayout cl;
    private Client clipClient;
    private DefaultListModel listModel;

    private static final ResourceBundle bundle = ResourceBundle.getBundle("GuiStrings");

    public MainGui(){
        super("ClipHero");
        this.setSize(450, 500);
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
        sendButton.addActionListener(this);
        clipboardButton.addActionListener(this);
        buttonDisconnect.addActionListener(this);
        buttonSettings.addActionListener(this);

        checkBoxAutoCopy.addActionListener(this);
        checkBoxAutoPaste.addActionListener(this);

        textFieldSendMessage.addKeyListener(this);
        textFieldMulticastIp.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == connectButton){
            connectToMultiIp();
        }

        if(e.getSource() == sendButton){
            sendMessage();
        }

        if(e.getSource() == clipboardButton){
            if(clipClient != null){
                clipClient.broadcastClipboard();
            }
        }

        if(e.getSource() == buttonDisconnect){
            cl.show(cardPanel, "Login");
            clipClient.stopClient();
            clipClient = null;
        }

        if(e.getSource() == buttonSettings){
            cl.show(cardPanel, "Settings");
        }

        if(e.getSource() == checkBoxAutoCopy){
            clipClient.setAutoCopy(checkBoxAutoCopy.isSelected());
        }

        if(e.getSource() == checkBoxAutoPaste){
            clipClient.setAutoPaste(checkBoxAutoPaste.isSelected());
        }

    }

    @Override
    public void processIncomingClipboardMessage(String message) {
        listModel.addElement(message);
        listClipHistory.ensureIndexIsVisible(listModel.indexOf(listModel.lastElement()));
        listClipHistory.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            Object source = e.getSource();
            if (textFieldMulticastIp.equals(source)) {
                connectToMultiIp();
            } else if (textFieldSendMessage.equals(source)) {
                sendMessage();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void connectToMultiIp(){
        if(!textFieldMulticastIp.getText().isBlank()){
            try{

                //Test if entered IP is a valid Multicast IP
                String[] numbersOfIp = textFieldMulticastIp.getText().split("\\.");
                if(numbersOfIp.length != 4){
                    throw new NumberFormatException();
                }
                for(int i = 0; i < numbersOfIp.length; i++){
                    int partOfIp = Integer.parseInt(numbersOfIp[i]);
                    if(i == 0){
                        if(partOfIp < 224 || partOfIp > 239){
                            throw new NumberFormatException();
                        }
                    }else{
                        if(partOfIp < 0 || partOfIp > 255){
                            throw new NumberFormatException();
                        }
                    }
                }
                clipClient = new Client(textFieldMulticastIp.getText(), 50111, this);
                clipClient.start();
                cl.show(cardPanel, "Application");
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, bundle.getString("NotMulticastIP"));
            }
        }else{
            JOptionPane.showMessageDialog(this, bundle.getString("NoIPWarning"));
        }
    }

    private void sendMessage(){
        if(clipClient != null && !textFieldSendMessage.getText().isBlank()){
            clipClient.broadcastMessage(PROTOCOL.Companion.getClipboardMessage(), textFieldSendMessage.getText());
            textFieldSendMessage.setText("");
        }
    }

    private void createUIComponents() {
        listModel = new DefaultListModel();
        listClipHistory = new JList(listModel);

    }
}
