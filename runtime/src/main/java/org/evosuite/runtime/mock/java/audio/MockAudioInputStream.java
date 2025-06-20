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

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Mock implementation of Java's AudioInputStream for testing purposes.
 * This ensures audio data is generated in memory without disk access.
 *
 * <p>All objects are created in memory, and no access to disk is ever done.</p>
 *
 * @author Pedro-PFerreira
 */
public class MockAudioInputStream extends AudioInputStream implements OverrideMock {

    private final float sampleRate;
    private final int channels;
    private final byte[] audioData;
    private int position = 0;
    private int markPosition = 0;
    private static final int SAMPLE_SIZE_IN_BITS = 16;

    /**
     * Default constructor: Generates random audio properties and mock audio data.
     */
    public MockAudioInputStream() {
        this(generateRandomContent(generateSampleRate(), generateChannels()),
                new AudioFormat(generateSampleRate(), 16, generateChannels(), true, false),
                0);
    }

    /**
     * Constructor using provided audio data and format.
     */
    public MockAudioInputStream(byte[] audioData, AudioFormat format, long length) {

        super(new ByteArrayInputStream(audioData), format, length);
        this.sampleRate = format.getSampleRate();
        this.channels = format.getChannels();
        this.frameSize = format.getFrameSize();
        this.audioData = audioData;
    }

    /**
     * Constructor using an input stream.
     */
    public MockAudioInputStream(InputStream stream, AudioFormat format, long length) {
        super(stream, format, length);
        this.sampleRate = format.getSampleRate();
        this.channels = format.getChannels();
        this.frameSize = format.getFrameSize();
        this.audioData = generateRandomContent(this.sampleRate, this.channels);
    }

    /**
     * Constructor using a TargetDataLine.
     */
    public MockAudioInputStream(TargetDataLine line) {
        super(line);
        this.sampleRate = line.getFormat().getSampleRate();
        this.channels = line.getFormat().getChannels();
        this.frameSize = line.getFormat().getFrameSize();
        this.audioData = generateRandomContent(this.sampleRate, this.channels); // No internal buffer
    }

    public byte[] getAudioData() {
        return audioData;
    }

    @Override
    public long getFrameLength() {
        return audioData.length / frameSize;
    }

    @Override
    public int read() throws IOException {
        if (position >= audioData.length) return -1; // End of stream
        return audioData[position++] & 0xFF; // Ensure signed byte return
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (position >= audioData.length) return -1;

        int bytesRead = Math.min(len, audioData.length - position);
        System.arraycopy(audioData, position, b, off, bytesRead);
        position += bytesRead;

        return bytesRead;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public long skip(long n) throws IOException {
        if (position >= audioData.length) return 0;

        long bytesToSkip = Math.min(n, audioData.length - position);
        position += (int) bytesToSkip;

        return bytesToSkip;
    }

    @Override
    public int available() throws IOException {
        return audioData.length - position;
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    @Override
    public void mark(int readLimit) {
        markPosition = position;
    }

    @Override
    public void reset() throws IOException {
        position = markPosition;
    }

    @Override
    public boolean markSupported() {
        return super.markSupported();
    }

    /**
     * Generates a random sample rate between 8000 and 44000 Hz.
     */
    private static float generateSampleRate() {
        Faker faker = new Faker();
        return faker.number().numberBetween(8000, 44000);
    }

    /**
     * Generates a random number of channels (1 or 2), for mono or stereo, respectively.
     */
    private static int generateChannels() {
        Faker faker = new Faker();
        return faker.number().numberBetween(1, 2);
    }

    /**
     * Generates random audio content based on given sample rate and channels.
     */
    private static byte[] generateRandomContent(float sampleRate, int channels) {
        Faker faker = new Faker();
        double duration = faker.number().numberBetween(1, 5);
        int frameSize = (SAMPLE_SIZE_IN_BITS / 8) * channels;
        int totalSamples = (int) (sampleRate * duration);
        int bufferSize = totalSamples * frameSize;
        byte[] audioData = new byte[bufferSize];

        for (int i = 0; i < totalSamples; i++) {
            short sample = (short) (Math.sin(2.0 * Math.PI * 440.0 * i / sampleRate) * Short.MAX_VALUE);
            int index = i * frameSize;

            audioData[index] = (byte) (sample & 0xff);
            audioData[index + 1] = (byte) ((sample >> 8) & 0xff);
        }
        return audioData;
    }
}
