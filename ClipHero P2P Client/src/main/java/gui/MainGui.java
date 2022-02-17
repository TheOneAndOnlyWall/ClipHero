package gui;

import client.Client;
import client.ClipBoardHandler;
import protocol.PROTOCOL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

public class MainGui extends JFrame implements ActionListener, KeyListener, GuiClipboardHandler {

    //Gui
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

    //Clipboard
    private ClipBoardHandler clipChangeListener;

    //System Tray
    private PopupMenu popup;
    private TrayIcon trayIcon;
    private SystemTray tray;
    private MenuItem settingsTray;
    private MenuItem exitTray;
    private CheckboxMenuItem autoPasteTray;
    private CheckboxMenuItem autoCopyTray;


    private static final ResourceBundle bundle = ResourceBundle.getBundle("GuiStrings");

    public MainGui(){
        super(bundle.getString("ProjectTitle"));
        this.setSize(450, 500);
        this.getContentPane().add(mainPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

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

        clipChangeListener = new ClipBoardHandler();
        clipChangeListener.start();

        ListDoubleClickCopy ldcc = new ListDoubleClickCopy(listClipHistory, clipChangeListener);
        listClipHistory.addMouseListener(ldcc);

        //System Tray
        createSystemTray();

        addClosingListener();

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

        //System Tray
        if(e.getSource() == trayIcon){
            this.setVisible(true);
        }

        if(e.getSource() == exitTray){
            tray.remove(trayIcon);
            System.exit(0);
        }

        if(e.getSource() == settingsTray){
            this.setVisible(true);
            cl.show(cardPanel, "Settings");
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
                clipClient = new Client(textFieldMulticastIp.getText(), 50111, this, clipChangeListener);
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

    private void createSystemTray(){
        if(SystemTray.isSupported()){
            popup = new PopupMenu();
            trayIcon = new TrayIcon(createImage(50, 50));
            tray = SystemTray.getSystemTray();

            // Create a pop-up menu components
            settingsTray = new MenuItem(bundle.getString("Settings"));
            exitTray = new MenuItem(bundle.getString("Exit"));
            autoPasteTray = new CheckboxMenuItem(bundle.getString("AutoPaste"));
            autoCopyTray = new CheckboxMenuItem(bundle.getString("AutoCopy"));

            //Add components to pop-up menu
            popup.add(settingsTray);
            popup.addSeparator();
            popup.add(autoPasteTray);
            popup.add(autoCopyTray);
            popup.addSeparator();
            popup.add(exitTray);

            trayIcon.setPopupMenu(popup);
            trayIcon.setToolTip(bundle.getString("ClipHeroRunning"));

            trayIcon.addActionListener(this);
            autoPasteTray.addActionListener(this);
            autoCopyTray.addActionListener(this);
            settingsTray.addActionListener(this);
            exitTray.addActionListener(this);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("TrayIcon could not be added.");
            }
        }
    }

    private void addClosingListener(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                trayIcon.displayMessage(bundle.getString("ProjectTitle"), bundle.getString("RunningInBackground"), TrayIcon.MessageType.INFO);
            }
        });
    }

    private void createUIComponents() {
        listModel = new DefaultListModel();
        listClipHistory = new JList(listModel);

    }
}
