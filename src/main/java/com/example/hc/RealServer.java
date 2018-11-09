package com.example.hc;

import java.util.concurrent.atomic.AtomicLong;

public class RealServer {
    public static final AtomicLong COUNTER=new AtomicLong(0);
    private final long mark = COUNTER.incrementAndGet();
    private final String host;
    private final int port;
    private final String ctxPath;
    private final boolean enableSSL;

    public RealServer(String host, int port, String ctxPath, boolean enableSSL) {

        this.host = host;
        this.port = port;
        this.ctxPath = ctxPath;
        this.enableSSL = enableSSL;
    }
}
