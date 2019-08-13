import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

public class ClientGUI implements StringConsumer, StringProducer {
    private JFrame frame;
    private JButton bt1, bt2, bt3, bt4, bt5;
    private JButton btSend;
    private JButton btCenter;
    private JTextArea textAreaCenter;
    private JTextField tf1, tf2, input;
    private JPanel panelBottom, panelTop;

    private ConnectionProxy connection;
    private Socket socket;

    public ClientGUI() {
        connection = new ConnectionProxy(this);
        //creating GUI components
        frame = new JFrame("CHAT - JAVA COURSE");
        tf1 = new JTextField(10);
        tf2 = new JTextField(4);
        bt1 = new JButton("GO!");

        textAreaCenter = new JTextArea(5, 20);
        textAreaCenter.setEditable(true);
        JScrollPane scrollPanel = new JScrollPane(textAreaCenter);

        btSend = new JButton("Send");
        input = new JTextField(20);
        panelBottom = new JPanel();
        panelTop = new JPanel();

        //getting frame content pane
        Container container = frame.getContentPane();

        //assigning background colors for each one of the panels
        panelBottom.setBackground(Color.darkGray);
        panelTop.setBackground(Color.darkGray);

        //assigning layout managers for each one of the containers
        panelBottom.setLayout(new FlowLayout());
        panelTop.setLayout(new FlowLayout());
        container.setLayout(new BorderLayout());

        //placing the components above the containers
        panelBottom.add(input);
        panelBottom.add(btSend);
        panelTop.add(tf1);
        panelTop.add(tf2);
        panelTop.add(bt1);
        container.add("Center", textAreaCenter);
        container.add("North", panelTop);
        container.add("South", panelBottom);

        /**handling connection host and port*/
        ActionListener ConnectionListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String host = tf1.getText();
                tf1.setText("Connecting...");
                int port = Integer.parseInt(tf2.getText());
                try {
                    socket = new Socket(host, port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                connection.addSocket(socket);
                /**start readUTF from server*/
                connection.start();
                tf1.setText("Connected!");
            }
        };
        bt1.addActionListener(ConnectionListener);
        //
        /**handling user input*/
        ActionListener produceListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String str = input.getText();
                input.setText("");
                connection.consume(str);
            }
        };
        btSend.addActionListener(produceListener);

        //handling frame closing event
        frame.addWindowListener(new WindowAdapter() {
                                    public void windowClosing(WindowEvent event) {
                                        frame.setVisible(false);
                                        frame.dispose();
                                        //connection.Stop();
                                        //connection.removeConsumer(null);
                                        System.exit(0);
                                    }
                                }
        );

    }

    public void consume(String str) {
        textAreaCenter.append(str + '\n');
    }

    public void addConsumer(StringConsumer sc) {

    }

    public void removeConsumer(StringConsumer sc) {

    }

    public void go() {
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        ClientGUI GUI = new ClientGUI();
        GUI.go();
        /**Client GUI start*/
    }
}