package com.example.hc.core;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

public class HcListener implements FutureCallback<HttpResponse> {
    public void completed(HttpResponse result) {

    }

    public void failed(Exception ex) {

    }

    public void cancelled() {

    }
}
