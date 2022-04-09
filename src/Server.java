import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) {
        try {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 23334));
        while (true) {
            SocketChannel channel = serverChannel.accept(); {
                final ByteBuffer input = ByteBuffer.allocate(2 << 10);
                while (channel.isConnected()){
                    int count = channel.read(input);
                    if (count == -1) break;
                    String message = new String(input.array(), 0, count, StandardCharsets.UTF_8);
                    input.clear();
                    System.out.println("Сообщение от клиента " + message);
                    message = message.replace(" ", "");
                    channel.write(ByteBuffer.wrap(("Ответ сервера: " + message).getBytes(StandardCharsets.UTF_8)));
                }
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
