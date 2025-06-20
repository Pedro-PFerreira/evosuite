package org.evosuite.runtime.mock.java.io;

import org.evosuite.runtime.mock.OverrideMock;

import java.io.IOException;
import java.io.Reader;

public class MockReader extends Reader implements OverrideMock {

    int position;
    char[] contentRead;

    @Override
    public int read(char[] chars, int offset, int len) throws IOException {

        if (chars == null) return -1;

        if (offset < 0 || len < 0 || len > chars.length - offset) {
            return -1;
        }

        int bytesRead = Math.min(len, chars.length);
        System.arraycopy(chars, offset, contentRead, bytesRead, len);
        position += bytesRead;

        return 0;
    }

    @Override
    public void close() throws IOException {
        this.contentRead = null;
        this.position = 0;
    }
}
