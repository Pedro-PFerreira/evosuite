/*
 * Copyright (C) 2010-2018 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.runtime.mock.java.audio;

import org.evosuite.runtime.mock.OverrideMock;
import org.instancio.Instancio;
import org.instancio.settings.Settings;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of the Clip interface
 * <p>
 * This class is used to mock the Clip class from the javax.sound.sampled package.
 *
 * @author Pedro-PFerreira
 */
public class MockClip implements Clip, OverrideMock {

    private final AudioFormat audioFormat;
    private final AudioInputStream audioInputStream;
    private final List<LineListener> lineListeners = new ArrayList<>();

    private boolean open = false;
    private boolean running = false;
    private boolean looping = false;
    private int framePosition = 0;
    private int loopStart;
    private int loopEnd;

    public MockClip(){
        this.audioInputStream = new MockAudioInputStream();
        this.audioFormat = audioInputStream.getFormat();
        this.loopStart = 0;
        this.loopEnd = (int) audioInputStream.getFrameLength();
    }

    public MockClip(AudioInputStream audioInputStream){
        this.audioInputStream = audioInputStream;
        this.audioFormat = audioInputStream.getFormat();
        this.loopStart = 0;
        this.loopEnd = (int) this.audioInputStream.getFrameLength();
    }

    @Override
    public void open(AudioFormat audioFormat, byte[] bytes, int i, int i1) throws LineUnavailableException {

        if (open) {
            throw new LineUnavailableException("Clip is already open");
        }
        this.open = true;
    }

    @Override
    public void open(AudioInputStream audioInputStream) throws LineUnavailableException, IOException {
        if (open) {
            throw new LineUnavailableException("Clip is already open");
        }

        if (audioInputStream == null) {
            throw new NullPointerException("AudioInputStream is null");
        }

        byte[] data = new byte[(int)audioInputStream.getFrameLength() * audioInputStream.getFormat().getFrameSize()];
        int bytesRead = audioInputStream.read(data, 0, data.length);

        open(audioInputStream.getFormat(), data,0, bytesRead);
    }

    @Override
    public void open() throws LineUnavailableException {
        if (open) {
            throw new LineUnavailableException("Clip is already open");
        }

        byte[] data = new byte[(int)audioInputStream.getFrameLength() * audioInputStream.getFormat().getFrameSize()];
        int bytesRead;
        try {
            bytesRead = audioInputStream.read(data, 0, data.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        open(audioFormat, data,0, bytesRead);
    }

    @Override
    public void close() {
        stop();
        open = false;
        notifyListeners(LineEvent.Type.CLOSE);
    }

    @Override
    public void start() {
        if (!open) {
            throw new IllegalStateException("Clip is not open");
        }
        if (running) {
            throw new IllegalStateException("Clip is already running");
        }
        running = true;
        notifyListeners(LineEvent.Type.START);
    }

    @Override
    public void stop() {

        if (!open) {
            throw new IllegalStateException("Clip is not open");
        }
        if (!running) {
            throw new IllegalStateException("Clip is not running");
        }
        this.running = false;
        this.looping = false;
        notifyListeners(LineEvent.Type.STOP);
    }

    @Override
    public void loop(int count) {
        if (!open) { throw new IllegalStateException("Clip is not open"); }
        this.looping = true;
        notifyListeners(LineEvent.Type.START);
        start();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean isActive() {
        return running || looping;
    }

    @Override
    public int getFrameLength() {
        return (int) (audioInputStream.getFrameLength());
    }

    @Override
    public long getMicrosecondLength() {
        return (long) (audioInputStream.getFrameLength() / audioFormat.getFrameRate() * 1_000_000);
    }

    @Override
    public void setMicrosecondPosition(long microseconds) {
        int newFramePosition = (int) ((float) microseconds / 1_000_000 * audioFormat.getFrameRate());
        setFramePosition(newFramePosition);
    }


    @Override
    public void setFramePosition(int frames) {
        if (frames < 0 || frames >= getFrameLength()) {
            throw new IllegalArgumentException("Invalid frame position: " + frames);
        }
        this.framePosition = 0;
    }
    @Override
    public void setLoopPoints(int start, int end) {

        if (start < 0 || start >= getFrameLength() || end >= getFrameLength()) {
            throw new IllegalArgumentException("Invalid start frame position: " + start);
        }
        if (end == -1){
            end = getFrameLength() - 1;
        }
        if (start > end) {
            throw new IllegalArgumentException("Start frame position is greater than end frame position");
        }

        loopStart = start;
        loopEnd = end;
    }

    @Override
    public void drain() {

    }

    @Override
    public void flush() {

    }

    @Override
    public AudioFormat getFormat() {
        return audioFormat;
    }

    @Override
    public int getBufferSize() {

        if (audioInputStream == null || audioInputStream.getFormat() == null || audioInputStream.getFrameLength() == 0) {
            throw new IllegalStateException("AudioInputStream is null");
        }

        byte[] data = new byte[(int)audioInputStream.getFrameLength() * audioInputStream.getFormat().getFrameSize()];

        int bytesRead;
        try {
            bytesRead = audioInputStream.read(data, 0, data.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (bytesRead == -1) {
            throw new RuntimeException("Error reading audio data");
        }

        return bytesRead;
    }

    @Override
    public int available() {
        try {
            return audioInputStream.available();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getFramePosition() {
        return framePosition;
    }

    @Override
    public long getLongFramePosition() {
        return framePosition;
    }

    @Override
    public long getMicrosecondPosition() {
        return (long) (framePosition / audioFormat.getFrameRate() * 1_000_000);
    }

    @Override
    public float getLevel() {
        return Instancio.create(float.class);
    }

    @Override
    public Line.Info getLineInfo() {
        return new MockLineInfo(this.getClass());
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public Control[] getControls() {

        Settings settings = Settings.create()
                .mapType(Control.class, MockControl.class);

        return Instancio.of(Control[].class)
                .withSettings(settings)
                .create();
    }

    @Override
    public boolean isControlSupported(Control.Type type) {

        for (Control control : getControls()) {
            if (control.getType().equals(type)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Control getControl(Control.Type type) {

        for (Control control : getControls()) {
            if (control.getType().equals(type)) {
                return control;
            }
        }
        return null;
    }

    @Override
    public void addLineListener(LineListener lineListener) {
        lineListeners.add(lineListener);
    }

    @Override
    public void removeLineListener(LineListener lineListener) {
        lineListeners.remove(lineListener);
    }

    private void notifyListeners(LineEvent.Type eventType) {
        LineEvent event = new LineEvent(this, eventType, getFramePosition());
        for (LineListener listener : lineListeners) {
            listener.update(event);
        }
    }
}
