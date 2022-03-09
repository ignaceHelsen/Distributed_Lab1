package connection;

import java.io.*;
import java.net.*;

public class ClientUDP {
    private DatagramSocket socket;
    private InetAddress address;

    public ClientUDP() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getLocalHost();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String sendFile(String filePath) {
        try {
            File file = new File(filePath);
            FileInputStream stream = new FileInputStream(file);
            byte[] buf = new byte[(int)file.length()];
            stream.read(buf);

            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4999);
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            return new String(packet.getData(), 0, packet.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void close() {
        socket.close();
    }
}
