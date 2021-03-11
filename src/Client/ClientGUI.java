package Client;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;


public class ClientGUI {

    private JTextField msg;
    private JButton btn_send;
    private JPanel MainPanel;
    private JTextArea display_msg;

    static JFrame frame;
    static DataOutputStream out;
    static DataInputStream in;
    static Socket s;
    static int port = 5000;
    static String msgin;

    public ClientGUI() {
        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msgout;
                msgout = msg.getText().trim();
                msg.setText("");
                msg.requestFocus();
                if (msgout != null && !msgout.equals("") && !msgout.isEmpty() && !msgout.isBlank()) {
                    if (msgout.length() >= 255) {
                        display_msg.append("ERROR : Le message entré est trop grand, veuillez s'il vous plait reduire la taille de votre message\n");
                        System.out.println("ERROR - message length exceeded max limit");
                        return;
                    } else {
                        try {
                            display_msg.append("Client : " + msgout + "\n");
                            out.writeUTF(msgout);
                            out.flush();
                        } catch (Exception a) {
                            System.out.println("ERROR - impossible d'envoyer le message dans le stream");
                        }
                    }
                } else {
                    display_msg.append("ERROR : message nul - Veuillez rentrer quelque chose..\n");
                    System.out.println("ERROR - message nul");
                    return;
                }
                if (msgout.equals("/exit")) {
                    frame.dispose();
                    System.exit(0);
                }
                int compteur = 0;
                while (msgin == null && msgout != null && !msgout.equals("") && !msgout.isEmpty() && !msgout.isBlank()) {
                    System.out.println("ERROR - msgin null in the while");
                    display_msg.append("En attente d'une réponse du serveur..\n");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    compteur++;
                    if (compteur > 360) {
                        display_msg.append("ERROR : le serveur ne repond pas..\n");
                        System.out.println("ERROR : le serveur ne repond pas..");
                        return;
                    }
                }
                display_msg.append("Server : Le message '" + msgin + "' a bien été sauvegardé dans la base de données\n\n");
                System.out.println("Server : " + msgin + "\n\n");

                msgin = null;
                return;
            }
        });
    }

    public static void main(String[] args) throws IOException {
        frame = new JFrame("Client");
        frame.setMinimumSize(new Dimension(650, 400));
        ClientGUI clientGUI = new ClientGUI();
        frame.setContentPane(clientGUI.MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        waitForConnectedToServer(clientGUI);

        while (true) {
            try {
                assert s != null;
                out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                assert s != null;
                in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                msgin = in.readUTF();
            } catch (IOException o) {
                o.printStackTrace();
                clientGUI.display_msg.append("Connexion au serveur perdu\n");
                s = null;
                waitForConnectedToServer(clientGUI);
            }
        }
    }

    private static void waitForConnectedToServer(ClientGUI clientGUI) {
        clientGUI.msg.setEnabled(false);
        clientGUI.btn_send.setEnabled(false);
        int counter = 0;
        while (s == null || counter > 320) {
            try {
                s = new Socket("localhost", port);
                clientGUI.display_msg.append("Connexion au serveur établi\n");
                clientGUI.msg.setEnabled(true);
                clientGUI.btn_send.setEnabled(true);
            } catch (IOException io) {
                io.printStackTrace();
                System.out.println("Echec connexion au serveur");
                if (counter == 1) {
                    clientGUI.display_msg.append("Echec connexion au serveur\n");
//                }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException i) {
                    i.printStackTrace();
                    System.out.println("Erreur sleep");
                }
                counter++;
            }
        }
    }
}
