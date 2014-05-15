
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress[] addrs = java.net.InetAddress.getAllByName("localhost");
        System.out.println("addrs = " + Arrays.toString(addrs));
    }
}
