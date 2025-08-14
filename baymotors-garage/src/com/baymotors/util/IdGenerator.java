package com.baymotors.util;

import java.util.concurrent.atomic.AtomicLong;

public final class IdGenerator {
    private static final AtomicLong SEQ = new AtomicLong(1);

    private IdGenerator() {}

    public static String next(String prefix) {
        return prefix + SEQ.getAndIncrement();
    }
}
