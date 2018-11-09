package com.example.hc;

import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class VirtualServer {
    private String name;
    private DiscoveryClient discoveryClient;
    private LoadBlancer loadBlancer;
    private List<RealServer> realRealServers;

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

    public class HcRequestBuilder {
        private String path;
        private Map<String,String> queryParams=new HashMap<String, String>();
    }

    public static void main(String[] args) throws IOException {
        String s = Request
                .Get("http://www.baidu.com")
                .execute()
                .returnContent()
                .asString();
        System.out.println(s);
    }
}
