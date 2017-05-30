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

    public static void main(String [] args) {
        System.out.println(validate("1.w1.1.1"));
        System.out.println(validate("1.111.1.1"));
        System.out.println(validate("11.21.1.1"));
        System.out.println(validate("123.21.1.1"));
        System.out.println(validate("12.1111.1.1"));
    }
}
