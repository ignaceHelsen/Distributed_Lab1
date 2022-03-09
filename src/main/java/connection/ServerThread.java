package connection;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ServerThread extends Thread {
    private Socket socket;

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println(socket + " connected.");

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Random random = new Random();
            receiveFile(String.format("test_received_%d.txt", random.nextInt()));

            dataInputStream.close();
            dataOutputStream.close();
            socket.close();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveFile(String fileName) throws Exception {
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = dataInputStream.readLong();     // read file size
        byte[] buffer = new byte[4 * 1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
    }
}