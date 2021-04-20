import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Socker extends JFrame {

    Socket socket = null;
     void view(){

        JTextArea textArea = new JTextArea();
        JTextField field = new JTextField();

        JButton startSocket = new JButton("Start Socket");
        startSocket.addActionListener( event -> {
            try {
                socket = new Socket("localhost", 3000);
                new Thread(()->{
                    while(socket.isConnected()){
                        try {
                            DataInputStream dis = new DataInputStream(socket.getInputStream());
                            if(dis.available() > 0){
                                byte[] b = new byte[dis.available()];
                                dis.readFully(b);
                                String msg = new String(b);
                                System.out.println(msg+"yes");
                                textArea.setText(msg);
                                textArea.setText("\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        JButton button = new JButton("Send");
        button.addActionListener((event) -> {
            String msg = field.getText();
            try {
                OutputStream ous = socket.getOutputStream();
                ous.write(msg.getBytes());
                ous.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

         JPanel panel = new JPanel(new BorderLayout());
         panel.add(field, BorderLayout.CENTER);
         panel.add(button, BorderLayout.LINE_END);

        add(textArea, BorderLayout.CENTER);
        add(startSocket, BorderLayout.PAGE_START);
        add(panel, BorderLayout.PAGE_END);
    }
    public static void main(String[] args) {
         Socker sock = new Socker();
         sock.view();
         sock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         sock.setSize(400, 300);
         sock.setLocationRelativeTo(null);
         sock.setVisible(true);
    }
}
