package com.ys168.zhanhb.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

/**
 *
 * @author zhanhb
 */
public class NginxFilterTest {

    private static final String seperator = "\\s*,\\s*";

    @Test(timeout = 5000)
    public void testA() throws Exception {
        Set<String> set = new HashSet<String>(Arrays.asList("61.153.34.35, 10.7.18.100".trim().split(seperator)));
        // retry once
        for (boolean retry = true;; retry = false) {
            try {
                InetAddress[] addrs = InetAddress.getAllByName("localhost");
                for (InetAddress addr : addrs) {
                    set.add(addr.getHostAddress());
                }
                Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
                while (nifs.hasMoreElements()) {
                    Enumeration<InetAddress> ias = nifs.nextElement().getInetAddresses();
                    while (ias.hasMoreElements()) {
                        set.add(ias.nextElement().getHostAddress());
                    }
                }
                break;
            } catch (IOException ex) {
                if (!retry) {
                    break;
                }
            } catch (SecurityException ex) {
                break;
            }
        }
    }
}
