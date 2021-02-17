package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    static PrintWriter pr = null;
    static Socket s = null;
    static int port = 5000;
    static InputStreamReader in = null;


    public static void main(String[] args) {

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

        assert pr != null;
        pr.println("Hello");
        pr.flush();

        try {
            in = new InputStreamReader(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert in != null;
        BufferedReader bf = new BufferedReader(in);
        String str = null;
        try {
            str = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server: " + str);
    }
}
