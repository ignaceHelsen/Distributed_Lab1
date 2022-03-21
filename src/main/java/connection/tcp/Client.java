package connection.tcp;

import java.io.*;
import java.net.Socket;

public class Client {
    // https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
    private static DataOutputStream dataOutputStream = null;

    public static void main(String[] args) {
        try(Socket socket = new Socket("127.0.0.1",5000)) {
            // Read file
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            sendFile("./files/test.txt");

            dataOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void sendFile(String path) throws Exception{
        int bytes;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        // send file size
        dataOutputStream.writeLong(file.length());
        // break file into chunks
        byte[] buffer = new byte[256];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }
}