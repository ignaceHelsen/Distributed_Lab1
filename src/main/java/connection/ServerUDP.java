package connection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.Random;

public class ServerUDP extends Thread {
    // inspiration from https://www.baeldung.com/udp-in-java
    private DatagramSocket socket;
    private byte[] buf = new byte[256]; //Change size when needed

    public ServerUDP() {
        try {
            socket = new DatagramSocket(4999);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received
                    = new String(packet.getData(), 0, packet.getLength());
            int zeroIndex = received.indexOf(0);
            received = received.substring(0, zeroIndex);
            System.out.println(received);
            File file = new File(String.format("test_received_%d.txt", new Random().nextInt()));
            FileWriter writer = new FileWriter(file);
            writer.write(received);
            writer.close();

            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Buffer may be to small: see line 11 in ServerUDP");
        }
        finally {
            socket.close();
        }
    }
}
