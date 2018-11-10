package org.apache.http.client.fluent;

import com.example.hc.core.HcExecutor;
import com.example.hc.core.HcListenerChain;
import com.example.hc.core.SyncHcListener;
import org.apache.http.*;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.Future;

public class HcRequest extends Request {
    private final InternalHttpRequest internalHttpRequest;
    private final HcListenerChain chain = new HcListenerChain();

    HcRequest(InternalHttpRequest request) {
        super(request);
        this.internalHttpRequest = request;
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

    public Future<HttpResponse> aexec() {
        Future<HttpResponse> execute = HcExecutor.HTTP_ASYNC_CLIENT.execute(internalHttpRequest, chain);
        return execute;
    }

    public HcRequest addListener(FutureCallback<HttpResponse> listener){
        if(listener instanceof SyncHcListener){
            chain.addSync(listener);
        }else {
            chain.addAsync(listener);
        }
        return this;
    }

    @Override
    public HcRequest addHeader(Header header) {
        super.addHeader(header);
        return this;
    }

    @Override
    public HcRequest setHeader(Header header) {
        super.setHeader(header);
        return this;
    }

    @Override
    public HcRequest addHeader(String name, String value) {
        super.addHeader(name, value);
        return this;
    }

    @Override
    public HcRequest setHeader(String name, String value) {
        super.setHeader(name, value);
        return this;
    }

    @Override
    public HcRequest removeHeader(Header header) {
        super.removeHeader(header);
        return this;
    }

    @Override
    public HcRequest removeHeaders(String name) {
        super.removeHeaders(name);
        return this;
    }

    @Override
    public HcRequest setHeaders(Header... headers) {
        super.setHeaders(headers);
        return this;
    }

    @Override
    public HcRequest setCacheControl(String cacheControl) {
        super.setCacheControl(cacheControl);
        return this;
    }

    @Override
    public HcRequest setDate(Date date) {
        super.setDate(date);
        return this;
    }

    @Override
    public HcRequest setIfModifiedSince(Date date) {
        super.setIfModifiedSince(date);
        return this;
    }

    @Override
    public HcRequest setIfUnmodifiedSince(Date date) {
        super.setIfUnmodifiedSince(date);
        return this;
    }

    @Override
    public HcRequest config(String param, Object object) {
        super.config(param, object);
        return this;
    }

    @Override
    public HcRequest removeConfig(String param) {
        super.removeConfig(param);
        return this;
    }

    @Override
    public HcRequest version(HttpVersion version) {
        super.version(version);
        return this;
    }

    @Override
    public HcRequest elementCharset(String charset) {
        super.elementCharset(charset);
        return this;
    }

    @Override
    public HcRequest useExpectContinue() {
        super.useExpectContinue();
        return this;
    }

    @Override
    public HcRequest userAgent(String agent) {
        super.userAgent(agent);
        return this;
    }

    @Override
    public HcRequest socketTimeout(int timeout) {
        super.socketTimeout(timeout);
        return this;
    }

    @Override
    public HcRequest connectTimeout(int timeout) {
        super.connectTimeout(timeout);
        return this;
    }

    @Override
    public HcRequest staleConnectionCheck(boolean b) {
        super.staleConnectionCheck(b);
        return this;
    }

    @Override
    public HcRequest viaProxy(HttpHost proxy) {
        super.viaProxy(proxy);
        return this;
    }

    @Override
    public HcRequest viaProxy(String proxy) {
        super.viaProxy(proxy);
        return this;
    }

    @Override
    public HcRequest body(HttpEntity entity) {
        super.body(entity);
        return this;
    }

    @Override
    public HcRequest bodyForm(Iterable<? extends NameValuePair> formParams, Charset charset) {
        super.bodyForm(formParams, charset);
        return this;
    }

    @Override
    public HcRequest bodyForm(Iterable<? extends NameValuePair> formParams) {
        super.bodyForm(formParams);
        return this;
    }

    @Override
    public HcRequest bodyForm(NameValuePair... formParams) {
        super.bodyForm(formParams);
        return this;
    }

    @Override
    public HcRequest bodyString(String s, ContentType contentType) {
        super.bodyString(s, contentType);
        return this;
    }

    @Override
    public HcRequest bodyFile(File file, ContentType contentType) {
        super.bodyFile(file, contentType);
        return this;
    }

    @Override
    public HcRequest bodyByteArray(byte[] b) {
        super.bodyByteArray(b);
        return this;
    }

    @Override
    public HcRequest bodyByteArray(byte[] b, ContentType contentType) {
        super.bodyByteArray(b, contentType);
        return this;
    }

    @Override
    public HcRequest bodyByteArray(byte[] b, int off, int len) {
        super.bodyByteArray(b, off, len);
        return this;
    }

    @Override
    public HcRequest bodyByteArray(byte[] b, int off, int len, ContentType contentType) {
        super.bodyByteArray(b, off, len, contentType);
        return this;
    }

    @Override
    public HcRequest bodyStream(InputStream instream) {
        super.bodyStream(instream);
        return this;
    }

    @Override
    public HcRequest bodyStream(InputStream instream, ContentType contentType) {
        super.bodyStream(instream, contentType);
        return this;
    }
}
