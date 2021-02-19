package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class ClientGUI {

    private JTextField msg;
    private JButton btn_send;
    private JPanel MainPanel;
    private JTextArea display_msg;

    static DataOutputStream out;
    static DataInputStream in;
    static Socket s;
    static int port = 5000;
    static String msgin;

    public ClientGUI() {

        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msgout = "";
                msgout = msg.getText().trim();
                msg.setText("");
                msg.requestFocus();
                if (msgout != null && msgout != "" && !msgout.isEmpty() && !msgout.isBlank()) {
                    try {
                        display_msg.append("Client : " + msgout + "\n");
                        out.writeUTF(msgout);
                        out.flush();
                    } catch (Exception a) {
                        System.out.println("ERROR - impossible d'envoyer le message dans le stream");
                    }
                } else {
                    display_msg.append("ERROR : message nul - Veuillez rentrer quelque chose..");
                    System.out.println("ERROR - message nul");
                    return;
                }
                int compteur = 0;
                while (msgin == null && msgout != null && msgout != "" && !msgout.isEmpty() && !msgout.isBlank()) {
                    System.out.println("ERROR - msgin null in the while");
                    display_msg.append("En attente d'une réponse du serveur..\n");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    compteur++;
                    if(compteur > 360){
                        display_msg.append("ERROR : le serveur ne repond pas..");
                        System.out.println("ERROR : le serveur ne repond pas..");
                        return;
                    }
                }
                display_msg.append("Server : " + msgin + "\n\n");
                System.out.println("Server : " + msgin + "\n\n");

                msgin = null;
                return;
            }
        });
    }

    public static void main(String[] args) throws IOException {
        //IHM
        JFrame frame = new JFrame("Client");
        frame.setContentPane(new ClientGUI().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        try {
            s = new Socket("localhost", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
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
            }
        }
    }


}
