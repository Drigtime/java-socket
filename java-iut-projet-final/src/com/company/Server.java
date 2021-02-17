package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {

    static ServerSocket ss = null;
    static int port = 5000;
    static Socket s = null;
    static InputStreamReader in = null;
    static PrintWriter pr = null;

    static String nomDriver = "com.mysql.cj.jdbc.Driver";
    static String urlBD = "jdbc:mysql://localhost:3306/java";
    static String user = "root";
    static String password = "";

    static Connection maConnexion = null;
    static Statement monStatement = null;

    static int clientCount = 0;


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

        try {
            assert s != null;
            in = new InputStreamReader(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert in != null;
        BufferedReader bf = new BufferedReader(in);
        String str = null;
        try {
            str = bf.readLine();
            System.out.println("Le client veut insérer dans la bdd : " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            insertMessage(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            pr = new PrintWriter(s.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert pr != null;
        pr.println("Insertion réussi de : " + str);
        pr.flush();
        try {
            pr = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertMessage(String message) {

        if(maConnexion == null) {
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
        }

        String sql = "INSERT INTO `socket_message` (`message`) VALUES ('" + message + "')";

        try {
            assert monStatement != null;
            int e = monStatement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(-61);
        }

        if(s.isClosed()) {
            try {
                monStatement.close();
                maConnexion.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
