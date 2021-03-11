package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {

    static int PORT = 5000;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        Database db = new Database("jdbc:mysql://localhost:3306/java", "root", "root");
        db.createTable("socket_message");

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }


        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println(e);
            }

            new SocketThread(socket, db).start();
        }
    }
}
