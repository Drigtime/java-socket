package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {

    static ServerSocket ss;
    static int port = 5000;
    static Socket s;
    static DataOutputStream out;
    static DataInputStream in;

    static String nomDriver = "com.mysql.cj.jdbc.Driver";
    static String urlBD = "jdbc:mysql://localhost:3306/java";
    static String user = "root";
    static String password = "";

    static Connection maConnexion = null;
    static Statement monStatement = null;

    public static void main(String[] args) {

        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            assert ss != null;
            s = ss.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){

            try {
                assert s != null;
                in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                assert s != null;
                out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String str = null;
            try {
                str = in.readUTF();
                System.out.println("Le client veut insérer dans la bdd : " + str);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(str != null) {
                try {
                    insertMessage(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    out.writeUTF("Insertion réussi de : " + str);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void insertMessage(String message) {


        try {
            Class.forName(nomDriver);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
            System.exit(-3);
        }

        try {
            maConnexion = DriverManager.getConnection(urlBD, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(-4);
        }

        try {
            monStatement = maConnexion.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String sql = "INSERT INTO `socket_message` (`message`) VALUES ('" + message + "')";

        try {
            assert monStatement != null;
            int e = monStatement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(-61);
        }

        try {
            monStatement.close();
            maConnexion.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
