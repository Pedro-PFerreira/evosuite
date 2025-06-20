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
import org.evosuite.runtime.Randomness;
import org.evosuite.runtime.mock.StaticReplacementMock;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.evosuite.runtime.mock.java.audio.MockAudioUtils.generateRandomContent;

/**
 * Mock implementation of Java's SourceDataLine for testing purposes.
 * This ensures audio data is generated in memory without disk access.
 *
 * <p>All objects are created in memory, and no access to disk is ever done.</p>
 *
 * @author Pedro-PFerreira
 */
public class MockSourceDataLine implements SourceDataLine, StaticReplacementMock {
    private boolean isOpen;
    private boolean isRunning;
    private boolean isActive;
    private byte[] dataBuffer;
    private List<LineListener> lineListeners;

    static{
        String seed = System.getenv("SEED_FOR_MOCKS");
        if (seed != null) {
            Randomness.setSeed(Long.parseLong(seed));
        }
    }

    public MockSourceDataLine() {

        this.isOpen = false;
        this.isRunning = false;
        this.isActive = true;
        this.dataBuffer = MockAudioUtils.generateRandomContent(MockAudioUtils.generateSampleRate(), Randomness.nextInt(1, 2));
        this.lineListeners = new ArrayList<>();
    }

    public void open(AudioFormat audioFormat, int i) throws LineUnavailableException {

        if (audioFormat == null) {
            throw new NullPointerException("AudioFormat is null");
        }

        if (i < 0) {
            throw new IllegalArgumentException("Buffer size is negative");
        }

        if (!this.isOpen) {
            this.isOpen = true;
            this.isActive = true;
            this.dataBuffer = new byte[i];
        }
    }

    public void open(AudioFormat audioFormat) throws LineUnavailableException {
        if (audioFormat == null) {
            throw new NullPointerException("AudioFormat is null");
        }

        if (!this.isOpen) {
            this.isOpen = true;
            this.isActive = true;
            this.dataBuffer = generateRandomContent(audioFormat.getSampleRate(), audioFormat.getChannels());
        }
    }

    public int write(byte[] bytes, int i, int i1) {
        if (this.isOpen() && this.isActive) {
            if (bytes != null && i >= 0 && i1 > 0 && i + i1 <= bytes.length) {
                if (this.dataBuffer == null) {
                    this.dataBuffer = new byte[i1];
                } else if (this.dataBuffer.length < i1) {
                    this.dataBuffer = new byte[i1];
                } else {
                    System.arraycopy(bytes, i, this.dataBuffer, 0, i1);
                }

                return i1;
            } else {
                throw new IllegalArgumentException("Invalid arguments");
            }
        } else {
            throw new IllegalStateException("Line is not open");
        }
    }

    public void drain() {
        if (this.isOpen() && this.isActive) {
            if (!this.isRunning) {
                throw new IllegalStateException("Line is not running");
            } else {
                this.dataBuffer = null;
            }
        }
    }

    public void flush() {
        if (this.isOpen() && this.isActive) {
            if (!this.isRunning) {
                throw new IllegalStateException("Line is not running");
            } else {
                this.dataBuffer = null;
            }
        }
    }

    public void start() {
        if (this.isOpen() && this.isActive) {
            this.isRunning = true;
            this.dataBuffer = new byte[0];

            for(LineListener lineListener : this.lineListeners) {
                lineListener.update(new LineEvent(this, LineEvent.Type.START, 0L));
            }
        }
    }

    public void stop() {
        if (this.isOpen() && this.isActive) {
            if (!this.isRunning) {
                throw new IllegalStateException("Line is not running");
            } else {
                this.isRunning = false;
            }
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public AudioFormat getFormat() {
        return new MockAudioDataFormat();
    }

    public int getBufferSize() {
        return this.dataBuffer.length;
    }

    public int available() {
        return 0;
    }

    public int getFramePosition() {
        return 0;
    }

    public long getLongFramePosition() {
        return 0L;
    }

    public long getMicrosecondPosition() {
        return 0L;
    }

    public float getLevel() {
        return 0.0F;
    }

    public Line.Info getLineInfo() {
        return new MockLineInfo(SourceDataLine.class);
    }

    public void open() throws LineUnavailableException {
        if (!this.isOpen() && !this.isActive) {
            this.isOpen = true;
        } else {
            throw new LineUnavailableException("Line is already open");
        }
    }

    public void close() {

        if (!this.isOpen() && !this.isActive) {
            throw new IllegalStateException("Line is already closed");
        }

        this.isOpen = false;
        this.isActive = false;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public Control[] getControls() {
        Faker faker = new Faker(new Random(Randomness.getSeed()));
        int maxNumberOfDecimals = faker.number().numberBetween(1, 3);
        int min = faker.number().negative();
        int max = faker.number().positive();
        int initialValue = faker.number().numberBetween(min, max);
        Control[] controls = new Control[2];
        MockFloatControl mockFloatControl = new MockFloatControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN, (float)min, (float)max, (float)maxNumberOfDecimals, max, (float)initialValue, "dB");
        MockBooleanControl mockBooleanControl = new MockBooleanControl(javax.sound.sampled.BooleanControl.Type.MUTE);
        mockBooleanControl.setValue(faker.bool().bool());
        controls[0] = mockFloatControl;
        controls[1] = mockBooleanControl;
        return controls;
    }

    public boolean isControlSupported(Control.Type type) {
        if (type == null) {
            throw new NullPointerException();
        } else {
            return true;
        }
    }

    public Control getControl(Control.Type type) {
        if (type == null) {
            throw new NullPointerException();
        } else {
            if (this.isControlSupported(type)) {
                Control[] controls = this.getControls();

                for(Control control : controls) {
                    if (control.getType().equals(type)) {
                        return control;
                    }
                }
            }

            return null;
        }
    }

    public void addLineListener(LineListener lineListener) {
        if (lineListener == null) {
            throw new NullPointerException("LineListener is null");
        } else {
            this.lineListeners.add(lineListener);
        }
    }

    public void removeLineListener(LineListener lineListener) {
        if (lineListener == null) {
            throw new NullPointerException("LineListener is null");
        } else {
            this.lineListeners.remove(lineListener);
        }
    }

    public String getMockedClassName() {
        return "javax.sound.sampled.SourceDataLine";
    }
}
