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
import org.instancio.Instancio;

import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of Java's Line for testing purposes.
 * This ensures audio line is generated with random data.
 *
 * @author Pedro-PFerreira
 */
public class MockLine implements Line, OverrideMock {

    private boolean isOpen = false;

    private List<LineListener> lineListeners = new ArrayList<>();

    @Override
    public Info getLineInfo() {
        return Instancio.create(Info.class);
    }

    @Override
    public void open() throws LineUnavailableException {

        if (isOpen) {
            throw new LineUnavailableException("Line is already open");
        }

        isOpen = true;
    }

    @Override
    public void close() {

        isOpen = false;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public Control[] getControls() {

        Faker faker = new Faker();
        int size = faker.number().positive();
        Control[] controls = new Control[size];

        Object[] controlsList = Instancio.ofList(MockControl.class).size(size).create().toArray();

        for (int i = 0; i < size; i++) {
            controls[i] = (Control) controlsList[i];
        }

        return controls;
    }

    @Override
    public boolean isControlSupported(Control.Type type) {

        if (type == null) {
            throw new NullPointerException();
        }

        return true;
    }

    @Override
    public Control getControl(Control.Type type) {

        if (type == null) {
            throw new NullPointerException();
        }

        if (isControlSupported(type)) {
            return Instancio.create(MockControl.class);
        }

        return null;
    }

    @Override
    public void addLineListener(LineListener lineListener) {

        if (lineListener == null) {
            throw new NullPointerException();
        }

        lineListeners.add(lineListener);
    }

    @Override
    public void removeLineListener(LineListener lineListener) {

        if (lineListener == null) {
            throw new NullPointerException();
        }

        lineListeners.remove(lineListener);
    }
}
