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
package org.evosuite.runtime.mock.java.xml;

import org.evosuite.runtime.mock.OverrideMock;
import org.instancio.Instancio;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Map;

/**
 * Mock implementation of DocumentBuilderFactory
 * Author: Pedro-PFerreira
 */
public class MockDocumentBuilderFactory extends DocumentBuilderFactory implements OverrideMock {

    private final Map<String, Object> mockAttributes;
    private final Map<String, Boolean> mockFeatures;

    public MockDocumentBuilderFactory(){
        super();
        this.mockAttributes = populateMockAttributes();
        this.mockFeatures = populateMockFeatures();
    }

    @Override
    public DocumentBuilder newDocumentBuilder() {
        return new MockDocumentBuilder();
    }

    public void setAttribute(String s, Object o) {

        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Attribute cannot be null or empty");
        }
        this.mockAttributes.put(s,o);
    }

    public Object getAttribute(String s) {

        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Attribute cannot be null or empty");
        }
        return mockAttributes.get(s);
    }

    public void setFeature(String s, boolean b){

        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Feature cannot be null or empty");
        }
        this.mockFeatures.put(s, b);
    }

    public boolean getFeature(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Feature cannot be null or empty");
        }
        return this.mockFeatures.get(s);
    }

    public Map<String, Object> populateMockAttributes(){

        return Instancio.ofMap(String.class, Object.class)
                .size(5)
                .create();
    }

    public Map<String, Boolean> populateMockFeatures(){
        return Instancio.ofMap(String.class, Boolean.class)
                .size(5)
                .create();
    }

}
