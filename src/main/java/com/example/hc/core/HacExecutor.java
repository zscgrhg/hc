package com.example.hc.core;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import java.util.concurrent.ExecutorService;

public class HacExecutor {
    public final CloseableHttpAsyncClient hac;
    public final ExecutorService EXECUTOR_SERVICE;

    public HacExecutor(HacExecutorCustom factory) {
        this.hac = factory.createHac();
        this.EXECUTOR_SERVICE = factory.createExecutorService();
    }

    public HacExecutor(CloseableHttpAsyncClient http_async_client, ExecutorService executor_service) {
        hac = http_async_client;
        EXECUTOR_SERVICE = executor_service;
    }

    public CloseableHttpAsyncClient getHac() {
        return hac;
    }

    public ExecutorService getEXECUTOR_SERVICE() {
        return EXECUTOR_SERVICE;
    }
}