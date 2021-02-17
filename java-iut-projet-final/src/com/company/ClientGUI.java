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

    static PrintWriter pr = null;
    static Socket s = null;
    static int port = 5000;
    static InputStreamReader in = null;
    static BufferedReader bf = null;
    static String msgin;
    static DataInputStream dataInputStream;

    public ClientGUI() {

        //DefaultCaret caret = (DefaultCaret)display_msg.getCaret();
        //caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String msgout = "";
                    msgout = msg.getText().trim();

                    if(msgout!=null) {
                       display_msg.append("Client : " + msgout + "\n");
                       ClientGUI monClientGUI = new ClientGUI();
                        //assert pr != null;
                        monClientGUI.pr.println(msgout);
                        monClientGUI.pr.flush();
                    }
                    int attempts = 0;

                    while(dataInputStream.available() == 0 && attempts < 1000)
                    {
                        display_msg.append("En attente d'une réponse du serveur..\n");
                        attempts++;
                        Thread.sleep(1000);
                    }

                    if (msgin != null) {
                        display_msg.append("Server : " + msgin + "\n");
                    }
                }
                catch(Exception a){
                    a.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) {
        //IHM
        JFrame frame = new JFrame("IHM");
        frame.setContentPane(new ClientGUI().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        try {
            s = new Socket("localhost", port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            assert s != null;
            pr = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in = new InputStreamReader(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataInputStream = new DataInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert in != null;
        bf = new BufferedReader(in);

        //String str = null;
        try {
            msgin = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server: " + msgin);
    }

}
