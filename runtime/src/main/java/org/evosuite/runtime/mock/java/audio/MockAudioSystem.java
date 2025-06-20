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
import org.evosuite.runtime.mock.StaticReplacementMock;
import org.instancio.Instancio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Mock implementation of Java's AudioSystem for testing purposes.
 * This ensures audio System instance is generated, using the other mock instances.
 *
 * @author Pedro-PFerreira
 */
public class MockAudioSystem implements StaticReplacementMock {

    public static Mixer.Info[] getMixerInfo() {

        Faker faker = new Faker();

        int size = faker.number().positive();

        return Instancio.ofList(MockMixerInfo.class).size(size).create().toArray(new MockMixerInfo[size]);
    }

    public static Mixer getMixer(Mixer.Info info) {
        if (info == null) {
            throw new NullPointerException();
        }
        return Instancio.of(MockMixer.class).create();
    }

    public static Line.Info[] getSourceLineInfo(Line.Info info) {

        if (info == null) {
            throw new NullPointerException();
        }

        return Instancio.ofList(MockLineInfo.class).size(1).create().toArray(new MockLineInfo[1]);
    }

    public static Line.Info[] getTargetLineInfo(Line.Info info) {

        if (info == null) {
            throw new NullPointerException();
        }
        return Instancio.ofList(MockLineInfo.class).size(1).create().toArray(new MockLineInfo[1]);
    }

    public static boolean isLineSupported(Line.Info info) {

        if (info == null) {
            throw new NullPointerException();
        }

        Mixer mixer = Instancio.create(MockMixer.class);

        return mixer.isLineSupported(info);
    }

    public static Line getLine(Line.Info info) throws LineUnavailableException {

        if (info == null) {
            throw new NullPointerException();
        }

        return Instancio.create(MockLine.class);
    }


    public static Clip getClip() {
        return Instancio.create(MockClip.class);
    }

    public Clip getClip(Mixer.Info info){
        if (info == null) {
            throw new NullPointerException();
        }

        return Instancio.create(MockClip.class);
    }

    public static SourceDataLine getSourceDataLine(AudioFormat format) {
        if (format == null) {
            throw new NullPointerException();
        }

        return Instancio.create(MockSourceDataLine.class);
    }


    public static AudioInputStream getAudioInputStream(InputStream inputStream) {
        if (inputStream == null) {
            throw new NullPointerException();
        }

        return Instancio.create(MockAudioInputStream.class);
    }

    public static AudioInputStream getAudioInputStream(URL url) {
        if (url == null) {
            throw new NullPointerException();
        }

        return Instancio.create(MockAudioInputStream.class);
    }

    public static AudioInputStream getAudioInputStream(File file){
        if (file == null) {
            throw new NullPointerException();
        }

        return Instancio.create(MockAudioInputStream.class);
    }

    @Override
    public String getMockedClassName() {
        return "javax.sound.sampled.AudioSystem";
    }
}
