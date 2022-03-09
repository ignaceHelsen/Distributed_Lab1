package connection.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("listening to port:5000");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Run in Thread
                new ServerThread(clientSocket).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}