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

/**
 * Java util class with methods to generate random values for audio-related classes.
 *
 * @author Pedro-PFerreira
 */
public class MockAudioUtils {

    private MockAudioUtils() {
    }

    /**
     * Generates a random sample rate between 8000 and 44000 Hz.
     */
    public static float generateSampleRate() {
        Faker faker = new Faker();

        // Choose one value from list containing 8000, 11025, 16000, 22050, 32000, 44100

        int[] sampleRates = {8000, 11025, 16000, 22050, 32000, 44100};
        int randomIndex = faker.number().numberBetween(0, sampleRates.length - 1);
        return sampleRates[randomIndex];
    }

    /**
     * Generates a random number of channels (1 or 2), for mono or stereo, respectively.
     */
    public static int generateChannels() {
        Faker faker = new Faker();
        return faker.number().numberBetween(1, 2);
    }
}
