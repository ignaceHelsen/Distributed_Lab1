package connection.tcp;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * This class will run the server application in a Threaded environment.
 */
public class ServerThread extends Thread {
    private Socket socket;

    private DataInputStream dataInputStream = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println(socket + " connected.");

            // Create input and output stream
            dataInputStream = new DataInputStream(socket.getInputStream());

            receiveFile(String.format("test_received_%d.txt", new Random().nextInt()));

            dataInputStream.close();

            socket.close();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            // Since we are in a testing environment, we print the stacktrace
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