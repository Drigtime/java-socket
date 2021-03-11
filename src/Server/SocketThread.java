package Server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class SocketThread extends Thread {
    protected Socket socket;
    protected Database db;

    public SocketThread(Socket clientSocket, Database db) {
        this.socket = clientSocket;
        this.db = db;
    }

    public void run() {
        InputStream inp;
        DataInputStream brinp;
        DataOutputStream out;
        try {
            inp = socket.getInputStream();
            brinp = new DataInputStream(new BufferedInputStream(inp));
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Nouvelle connexion de client");
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                try {
                    line = brinp.readUTF();
                } catch (EOFException e) {
                    socket.close();
                    return;
                }
                if (line.equalsIgnoreCase("/exit")) {
                    socket.close();
                    return;
                } else {
                    db.insertMessage("socket_message", line);
                    System.out.println("un client a enregistré '" + line + "' dans la base de données\n");
                    out.writeUTF(line + "\n\r");
                    out.flush();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
