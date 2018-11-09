package org.apache.http.client.fluent;

import java.net.URI;

public class HcRequest extends Request {
    HcRequest(InternalHttpRequest request) {
        super(request);
    }

    public static HcRequest Get(URI uri) {
        return new HcRequest(new InternalHttpRequest("GET", uri));
    }

    public static HcRequest Get(String uri) {
        return new HcRequest(new InternalHttpRequest("GET", URI.create(uri)));
    }


    public static HcRequest Post(URI uri) {
        return new HcRequest(new InternalEntityEnclosingHttpRequest("POST", uri));
    }

    public static HcRequest Post(String uri) {
        return new HcRequest(new InternalEntityEnclosingHttpRequest("POST", URI.create(uri)));
    }
}
