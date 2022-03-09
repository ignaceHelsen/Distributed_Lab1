import connection.ClientUDP;
import connection.ServerUDP;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class UdpTest {
    ClientUDP client;

    @Before
    public void setup(){
        new ServerUDP().start();
        client = new ClientUDP();
    }

    @Test
    public void whenReceived() {
        String echo = client.sendFile("./files/test.txt");
        assertEquals("zocket", echo);
    }

    @After
    public void tearDown() {
        client.close();
    }
}
