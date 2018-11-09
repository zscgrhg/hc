package com.example.hc;

import org.apache.http.Consts;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.ManagedNHttpClientConnectionFactory;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.ManagedNHttpClientConnection;
import org.apache.http.nio.conn.NHttpConnectionFactory;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import java.nio.charset.CodingErrorAction;

public class HcExecutor {
    public static final CloseableHttpAsyncClient HTTP_ASYNC_CLIENT = custom();

    public static CloseableHttpAsyncClient custom() {
        NHttpConnectionFactory<ManagedNHttpClientConnection> connFactory = new ManagedNHttpClientConnectionFactory();


        ConnectingIOReactor ioReactor = ioReactor();


        // Create a connection manager with custom configuration.
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(
                ioReactor, connFactory);


        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig
                .custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .build();
        // Configure the connection manager to use connection configuration either
        // by default or for a specific host.
        connManager.setDefaultConnectionConfig(connectionConfig);

        // Configure total max or per route limits for persistent connections
        // that can be kept in the pool or leased by the connection manager.
        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(10);

        // Use custom cookie store if necessary.

        // Create global request configuration
        RequestConfig defaultRequestConfig = RequestConfig
                .custom()
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .setExpectContinueEnabled(true)
                .build();

        // Create an HttpClient with the given custom dependencies and configuration.
        CloseableHttpAsyncClient httpclient = HttpAsyncClients
                .custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
        return httpclient;
    }

    private static ConnectingIOReactor ioReactor() {
        IOReactorConfig ioReactorConfig = IOReactorConfig
                .custom()
                .setIoThreadCount(Runtime
                        .getRuntime()
                        .availableProcessors())
                .setConnectTimeout(30000)
                .setSoTimeout(30000)
                .build();
        try {
            return new DefaultConnectingIOReactor(ioReactorConfig);
        } catch (IOReactorException e) {
            throw new RuntimeException(e);
        }
    }
}
