import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            InetSocketAddress address = new InetSocketAddress("127.0.0.1", 23334);
            final SocketChannel channel = SocketChannel.open();
            Scanner scanner = new Scanner(System.in);
            channel.connect(address);
            final ByteBuffer input = ByteBuffer.allocate(2 << 10);
            String message;
            while (true) {
                System.out.println("Введите строку с пробелами или введите end для завершения работы программы");
                message = scanner.nextLine();
                if (message.equals("end")){
                    break;
                }
                channel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
                int count = channel.read(input);
                System.out.println(new String (input.array(), 0, count, StandardCharsets.UTF_8).trim());
                input.clear();
            }
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
}
