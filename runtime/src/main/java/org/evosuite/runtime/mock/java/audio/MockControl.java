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

import javax.sound.sampled.Control;

/**
 * Mock implementation of the Control class
 * <p>
 * This class is used to mock the Control class from the javax.sound.sampled package.
 *
 * @author Pedro-PFerreira
 */
public class MockControl extends Control implements OverrideMock {
    private float value;

    protected MockControl(Type type, float initialValue) {
        super(type);
        this.value = initialValue;
    }

    public void setValue(float newValue) {
        this.value = newValue;
    }

    public float getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return getType() + " value: " + value;
    }
}