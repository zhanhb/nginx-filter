package com.ys168.zhanhb.filter;

import java.net.UnknownHostException;

public class NetTest {
    public static void main(String[] args) throws UnknownHostException {
        System.out.println(java.net.InetAddress.getByName("10.7.18.146").getHostName());
    }
}
