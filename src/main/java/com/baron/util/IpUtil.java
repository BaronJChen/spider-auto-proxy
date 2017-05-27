package com.baron.util;

import org.apache.http.annotation.ThreadSafe;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * Created by baron on 17-5-26.
 */
@ThreadSafe
public class IpUtil {
    private IpUtil() {}

    public static boolean validate(String ipString) {
        // use regex
        // ip address should be like this, 12.12.12.12
        return ipString.matches("(\\d{1,3}.){3}\\d{1,3}");
    }
}
