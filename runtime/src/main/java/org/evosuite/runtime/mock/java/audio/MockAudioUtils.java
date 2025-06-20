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

import org.evosuite.runtime.Randomness;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Java util class with methods to generate random values for audio-related classes.
 *
 * @author Pedro-PFerreira
 */
public class MockAudioUtils {

    private static final int SAMPLE_SIZE_IN_BITS = 16;

    private MockAudioUtils() {
    }

    /**
     * Generates a random sample rate between 8000 and 44000 Hz.
     */
    public static float generateSampleRate() {

        int[] sampleRates = {8000, 11025, 16000, 22050, 32000, 44100};
        int randomIndex = Randomness.nextInt(sampleRates.length);
        return sampleRates[randomIndex];
    }

    /**
     * Generates a random number of channels (1 or 2), for mono or stereo, respectively.
     */
    public static int generateChannels() {
        return Randomness.nextInt(1, 2);
    }

    /**
     * Generates random audio content based on given sample rate and channels.
     */
    public static byte[] generateRandomContent(float sampleRate, int channels) {
        double duration = Randomness.nextInt(1,5);
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

    /**
     * Generates random audio input stream, based on the random content generated.
     */
    public static InputStream generateInputStream(float sampleRate, int channels) {
        return new ByteArrayInputStream(generateRandomContent(sampleRate, channels));
    }

}
