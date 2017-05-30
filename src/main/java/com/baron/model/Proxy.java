package com.baron.model;

import org.apache.http.HttpHost;
import org.aspectj.lang.annotation.Pointcut;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by Jason on 2017/5/18.
 */
public class Proxy {
    // 代理的IP地址
    private String ip;
    // 代理的端口号
    private Integer port;
    // 支持的协议的类型，1为http，2为https，3为http与https
    private String protocolType;
    private HttpHost httpHost;

    public Proxy(String ip, Integer port, String protocolType) throws UnknownHostException {
        this.ip = ip;
        this.port = port;
        this.protocolType = protocolType;

        toHttpHost();
    }

    public Proxy(HttpHost httpHost) {
        this.httpHost = httpHost;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    private void toHttpHost() throws UnknownHostException {
        httpHost = new HttpHost(Inet4Address.getByName(ip), port);
    }

    public HttpHost getHttpHost() {
        return httpHost;
    }

    public void setHttpHost(HttpHost httpHost) {
        this.httpHost = httpHost;
    }

    @Override
    public int hashCode() {
        return httpHost.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Proxy) {
            return (((Proxy) o).ip.equals(this.ip) && ((Proxy) o).port.equals(this.port)) ||
                    ((Proxy) o).httpHost == this.httpHost;
        } // else if

        return false;
    }
}












