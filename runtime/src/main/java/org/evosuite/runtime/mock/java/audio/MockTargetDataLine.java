package org.evosuite.runtime.mock.java.audio;

import net.datafaker.Faker;
import org.evosuite.runtime.mock.StaticReplacementMock;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;

import static org.evosuite.runtime.mock.java.audio.MockAudioUtils.generateRandomContent;

public class MockTargetDataLine implements TargetDataLine, StaticReplacementMock {
    private boolean isOpen = false;
    private boolean isRunning = false;
    private boolean isActive = false;
    private byte[] dataBuffer;
    private List<LineListener> lineListeners = new ArrayList();

    public MockTargetDataLine() {
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

    @Override
    public int read(byte[] bytes, int i, int i1) {

        Faker faker = new Faker();

        if (this.isOpen && this.isActive){
            if (bytes != null && i >= 0 && i1 > 0 && i + i1 <= bytes.length) {
                for (int j = 0; j < i1; j++) {
                    bytes[i + j] = (byte)(faker.number().positive() * 256 - 128);
                }
                return bytes.length;
            } else {
                throw new IllegalArgumentException("Invalid arguments");
            }
        } else {
            throw new IllegalStateException("Line is not open");
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
        Faker faker = new Faker();
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
    @Override
    public String getMockedClassName() {
        return "javax.sound.sampled.TargetDataLine";
    }
}
