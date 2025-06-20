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

import net.datafaker.Faker;
import org.evosuite.runtime.mock.OverrideMock;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of Java's SourceDataLine for testing purposes.
 * This ensures audio data is generated in memory without disk access.
 *
 * <p>All objects are created in memory, and no access to disk is ever done.</p>
 *
 * @author Pedro-PFerreira
 */
public class MockSourceDataLine implements SourceDataLine, OverrideMock {

    private boolean isOpen = false;
    private boolean isRunning = false;
    private boolean isActive = false;
    private byte[] dataBuffer;
    private List<LineListener> lineListeners = new ArrayList<>();

    @Override
    public void open(AudioFormat audioFormat, int i) throws LineUnavailableException {

        if (isOpen || audioFormat == null || i <= 0) {
            throw new LineUnavailableException("Line is already open or AudioFormat is null");
        }

        isOpen = true;
        isActive = true;
        dataBuffer = new byte[i];
    }

    @Override
    public void open(AudioFormat audioFormat) throws LineUnavailableException {

        if (isOpen || audioFormat == null) {
            throw new LineUnavailableException("Line is already open or AudioFormat is null");
        }

        isOpen = true;
        isActive = true;
    }

    @Override
    public int write(byte[] bytes, int i, int i1) {

        if (!isOpen || !isActive) {
            throw new IllegalStateException("Line is not open");
        }

        if (bytes == null || i < 0 || i1 <= 0 || i + i1 > bytes.length) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        if (dataBuffer == null) {
            dataBuffer = new byte[i1];
        } else if (dataBuffer.length < i1) {
            dataBuffer = new byte[i1];
        }
        else{
            System.arraycopy(bytes, i, dataBuffer, 0, i1);
        }

        return i1;
    }

    @Override
    public void drain() {
        if (!isOpen || !isActive) {
            throw new IllegalStateException("Line is not open");
        }

        if (!isRunning) {
            throw new IllegalStateException("Line is not running");
        }

        dataBuffer = null;
    }

    @Override
    public void flush() {

        if (!isOpen || !isActive) {
            throw new IllegalStateException("Line is not open");
        }

        if (!isRunning) {
            throw new IllegalStateException("Line is not running");
        }

        dataBuffer = null;
    }

    @Override
    public void start() {

        if (!isOpen || !isActive) {
            throw new IllegalStateException("Line is not open");
        }

        if (isRunning) {
            throw new IllegalStateException("Line is already running");
        }

        isRunning = true;
        dataBuffer = new byte[0];
        for (LineListener lineListener : lineListeners) {
            lineListener.update(new LineEvent(this, LineEvent.Type.START, 0));
        }
    }

    @Override
    public void stop() {

        if (!isOpen || !isActive) {
            throw new IllegalStateException("Line is not open");
        }

        if (!isRunning) {
            throw new IllegalStateException("Line is not running");
        }

        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public AudioFormat getFormat() {
        return new MockAudioDataFormat();
    }

    @Override
    public int getBufferSize() {
        return dataBuffer.length;
    }

    @Override
    public int available() {
        return 0;
    }

    @Override
    public int getFramePosition() {
        return 0;
    }

    @Override
    public long getLongFramePosition() {
        return 0;
    }

    @Override
    public long getMicrosecondPosition() {
        return 0;
    }

    @Override
    public float getLevel() {
        return 0;
    }

    @Override
    public Line.Info getLineInfo() {
        return new MockLineInfo(SourceDataLine.class);
    }

    @Override
    public void open() throws LineUnavailableException {

        if (isOpen || isActive) {
            throw new LineUnavailableException("Line is already open");
        }

        isOpen = true;
    }

    @Override
    public void close() {

        if (!isOpen || !isActive) {
            throw new IllegalStateException("Line is not open");
        }

        isOpen = false;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public Control[] getControls() {

        Faker faker = new Faker();
        int maxNumberOfDecimals = faker.number().numberBetween(1, 3);
        int min = faker.number().negative();
        int max = faker.number().positive();
        int initialValue = faker.number().numberBetween(min, max);
        Control[] controls = new Control[2];

        MockFloatControl mockFloatControl = new MockFloatControl(FloatControl.Type.MASTER_GAIN, min, max, maxNumberOfDecimals, max, initialValue, "dB");

        MockBooleanControl mockBooleanControl = new MockBooleanControl(BooleanControl.Type.MUTE);
        mockBooleanControl.setValue(faker.bool().bool());

        controls[0] = mockFloatControl;
        controls[1] = mockBooleanControl;
        return controls;
    }

    @Override
    public boolean isControlSupported(Control.Type type) {

        if (type == null) {
            throw new NullPointerException();
        }

        return type.equals(FloatControl.Type.MASTER_GAIN) || type.equals(BooleanControl.Type.MUTE);
    }

    @Override
    public Control getControl(Control.Type type) {

        if (type == null) {
            throw new NullPointerException();
        }

        if (isControlSupported(type)) {

            Control[] controls = this.getControls();

            for (Control control : controls){

                if (control.getType().equals(type)){
                    return control;
                }
            }
        }

        return null;
    }

    @Override
    public void addLineListener(LineListener lineListener) {

        if (lineListener == null) {
            throw new NullPointerException("LineListener is null");
        }

        lineListeners.add(lineListener);

    }

    @Override
    public void removeLineListener(LineListener lineListener) {

        if (lineListener == null) {
            throw new NullPointerException("LineListener is null");
        }

        lineListeners.remove(lineListener);
    }
}
