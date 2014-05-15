package com.ys168.zhanhb.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

/**
 *
 * @author zhanhb
 */
public class NginxFilterTest {

    private static final String seperator = "[,\\s]+";
    private Set<String> excludes;

    @Test(timeout = 5000)
    public void testA() throws Exception {
        List<String> list = Arrays.asList("61.153.34.35,10.7.18.100");
        Set<String> set = new HashSet<String>(list.size() << 1);
        for (String string : list) {
            list.add(string.trim());
        }
        try {
            InetAddress[] addrs = InetAddress.getAllByName("localhost");
            for (InetAddress addr : addrs) {
                set.add(addr.getHostAddress());
            }

            // retry once
            boolean retry = true;
            for (Set<String> ipList = new HashSet<String>(8);; retry = false) {
                try {
                    Enumeration<NetworkInterface> ips = NetworkInterface.getNetworkInterfaces();
                    while (ips.hasMoreElements()) {
                        NetworkInterface ni = ips.nextElement();
                        Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
                        while (inetAddresses.hasMoreElements()) {
                            InetAddress ip = inetAddresses.nextElement();
                            ipList.add(ip.getHostAddress());
                        }
                    }
                    set.addAll(ipList);
                    break;
                } catch (SocketException ex) {
                    if (!retry) {
                        throw ex;
                    }
                }
            }
        } catch (IOException ex) {
        } catch (SecurityException ex) {
        }
        System.out.println(set);
    }
}
