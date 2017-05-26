package com.baron.model;

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

    public Proxy(String ip, Integer port, String protocolType) {
        this.ip = ip;
        this.port = port;
        this.protocolType = protocolType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Proxy) {
            return ((Proxy) o).ip.equals(this.ip) && ((Proxy) o).port.equals(this.port);
        }

        return false;
    }
}












