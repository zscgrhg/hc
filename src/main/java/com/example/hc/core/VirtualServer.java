package com.example.hc.core;

import org.apache.http.client.fluent.HcRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


public class VirtualServer {
    private final String name;
    private final DiscoveryClient discoveryClient;
    private final LoadBlancer loadBlancer;
    private final HacExecutor hacExecutor;

    private volatile List<RealServer> realRealServers = Collections.emptyList();

    VirtualServer(String name, DiscoveryClient discoveryClient, LoadBlancer loadBlancer, HacExecutor hacExecutor) {
        this.name = name;
        this.discoveryClient = discoveryClient;
        this.loadBlancer = loadBlancer;
        this.hacExecutor = hacExecutor;
    }

    public void initRealServers() {
        refreshRealServers();
    }

    public void refreshRealServers() {
        List<RealServer> realServers = discoveryClient.seek(name);
        if (realServers == null || realServers.isEmpty()) {
            return;
        }
        this.realRealServers = new CopyOnWriteArrayList<RealServer>(realServers);
    }

    public void remove(RealServer realServer) {
        realRealServers.remove(realServer);
    }


    public class ServerFailedListener extends HcListener implements SyncHcListener {
        private final RealServer watched;

        public ServerFailedListener(RealServer watched) {
            this.watched = watched;
        }

        @Override
        public void failed(Exception ex) {
            if (ex instanceof ConnectException
                    || ex instanceof UnknownHostException) {
                remove(watched);
            }
        }
    }

    public HcRequestBuilder PATH(String path) {
        return new HcRequestBuilder(path);
    }

    public class HcRequestBuilder {
        private final String path;
        private final Map<String, String> queryParams = new HashMap<String, String>();

        public HcRequestBuilder(String path) {
            this.path = path;
        }

        public HcRequestBuilder addParam(String name, String value) {
            queryParams.put(name, value);
            return this;
        }

        public HcRequestBuilder addParams(Map<String, String> pm) {
            queryParams.putAll(pm);
            return this;
        }

        private URI expand() {
            RealServer realServer = loadBlancer.select(realRealServers);
            StringBuilder sb = new StringBuilder(realServer.getBasePath()).append(path);
            char c = '?';
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                sb
                        .append(c)
                        .append(encode(entry.getKey()))
                        .append('=')
                        .append(encode(entry.getValue()));
                c = '&';
            }

            return URI
                    .create(sb.toString())
                    .normalize();
        }

        private String encode(String str) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        public HcRequest Get() {
            return HcRequest.Get(hacExecutor, expand());
        }

        public HcRequest Post() {
            return HcRequest.Post(hacExecutor, expand());
        }
    }

    public static void main(String[] args) throws IOException {

    }
}
