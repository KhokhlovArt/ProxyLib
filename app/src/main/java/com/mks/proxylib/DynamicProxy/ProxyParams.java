package com.mks.proxylib.DynamicProxy;

public class ProxyParams {
    private String host;
    private String port;
    private String Username;
    private String Password;
    private int Timeout = 0;

    public String getHost() {
        return host;
    }

    public ProxyParams setHost(String host) {
        this.host = host;
        return this;
    }

    public String getPort() {
        return port;
    }

    public ProxyParams setPort(String port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return Username;
    }

    public ProxyParams setUsername(String username) {
        Username = username;
        return this;
    }

    public String getPassword() {
        return Password;
    }

    public ProxyParams setPassword(String password) {
        Password = password;
        return this;
    }

    public int getTimeout() {
        return Timeout;
    }

    public ProxyParams setTimeout(int Timeout) {
        this.Timeout = Timeout;
        return this;
    }
}
