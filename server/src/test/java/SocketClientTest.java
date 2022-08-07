import com.hitwh.face.socket.Client;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangrong
 * @date 2022/8/7 22:55
 */
public class SocketClientTest {
    public static void main(String[] args) {
        Map<String, String> header = new HashMap<>();
        header.put("privateKey", "123456");
        Client client = new Client(URI.create("ws://localhost:8080/"), header);
        client.connect();
    }
}
