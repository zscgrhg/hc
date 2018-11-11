package com.example.hc.core;

import java.io.Closeable;

public interface CloseableObject extends Closeable {
    boolean isOpen();
}
