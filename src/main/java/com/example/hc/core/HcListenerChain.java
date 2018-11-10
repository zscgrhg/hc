package com.example.hc.core;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class HcListenerChain extends HcListener {
    private final List<FutureCallback<HttpResponse>> syncTasks = new ArrayList<FutureCallback<HttpResponse>>();
    private final List<FutureCallback<HttpResponse>> asyncTasks = new ArrayList<FutureCallback<HttpResponse>>();

    public void addSync(FutureCallback<HttpResponse> listener) {
        syncTasks.add(listener);
    }

    public void addAsync(FutureCallback<HttpResponse> listener) {
        asyncTasks.add(listener);
    }

    @Override
    public void completed(final HttpResponse result) {
        for (FutureCallback<HttpResponse> hcListener : syncTasks) {
            hcListener.completed(result);
        }
        for (final FutureCallback<HttpResponse> hcListener : asyncTasks) {
            getExecutor().execute(new Runnable() {
                public void run() {
                    hcListener.completed(result);
                }
            });
        }

    }

    @Override
    public void failed(final Exception ex) {
        for (FutureCallback<HttpResponse> hcListener : syncTasks) {
            hcListener.failed(ex);
        }
        for (final FutureCallback<HttpResponse> hcListener : asyncTasks) {
            getExecutor().execute(new Runnable() {
                public void run() {
                    hcListener.failed(ex);
                }
            });
        }
    }

    @Override
    public void cancelled() {
        for (FutureCallback<HttpResponse> hcListener : syncTasks) {
            hcListener.cancelled();
        }
        for (final FutureCallback<HttpResponse> hcListener : asyncTasks) {
            getExecutor().execute(new Runnable() {
                public void run() {
                    hcListener.cancelled();
                }
            });
        }
    }

    private ExecutorService getExecutor() {
        return HcExecutor.EXECUTOR_SERVICE;
    }
}
