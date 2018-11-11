package com.example.hc.core;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

public class VirtualServerKeeper {
    private static final Map<String, VirtualServer> SERVERS = new ConcurrentHashMap<String, VirtualServer>();
    private static final List<CloseableObject> closeables = new CopyOnWriteArrayList<CloseableObject>();

    public synchronized static VirtualServer create(String name,
                                                    DiscoveryClient discoveryClient,
                                                    LoadBlancer loadBlancer,
                                                    HacExecutorCustom haec) {
        if (SERVERS.containsKey(name)) {
            return SERVERS.get(name);
        } else {
            final CloseableHttpAsyncClient hac = haec.createHac();
            final ExecutorService executorService = haec.createExecutorService();
            closeables.add(new CloseableObject() {
                public boolean isOpen() {
                    return hac.isRunning();
                }

                public void close() throws IOException {
                    hac.close();
                }
            });
            closeables.add(new CloseableObject() {
                public boolean isOpen() {
                    return !executorService.isShutdown();
                }

                public void close() throws IOException {
                    executorService.shutdown();
                }
            });
            VirtualServer created = new VirtualServer(name, discoveryClient,
                    loadBlancer,
                    new HacExecutor(hac, executorService));
            SERVERS.put(name, created);
            return created;
        }
    }

    public synchronized static VirtualServer create(String name, DiscoveryClient discoveryClient) {
        return create(name, discoveryClient,
                new SimpleLB(),
                new SharedHacCustom());
    }

    private static void close(CloseableObject closeable) {
        if (closeable.isOpen()) {
            try {
                closeable.close();
            } catch (IOException e) {

            }
        }
    }

    static {
        Runtime
                .getRuntime()
                .addShutdownHook(new Thread(new Runnable() {
                    public void run() {
                        for (CloseableObject closeable : closeables) {
                            close(closeable);
                        }
                    }
                }));
    }


}
