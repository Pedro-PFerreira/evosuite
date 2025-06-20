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
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.io.IOException;

/**
 * Mock implementation of DocumentBuilder
 * Author: Pedro-PFerreira
 */
public class MockDocumentBuilder extends DocumentBuilder implements OverrideMock {

    public MockDocumentBuilder() {
        super();
    }

    public void reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Document parse(String uri) {
        if (uri == null || uri.isEmpty()) {
            throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        return new MockDocument();
    }

    @Override
    public Document parse(File file) throws IOException, SAXException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        return new MockDocument();
    }

    @Override
    public Document parse(InputSource inputSource) {
        if (inputSource == null) {
            throw new IllegalArgumentException("InputSource cannot be null");
        }
        return new MockDocument();
    }

    public MockDocument parse(MockDocument mockDocument){
        if (mockDocument == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        return mockDocument;
    }

    @Override
    public boolean isNamespaceAware() {
        return false;
    }

    @Override
    public boolean isValidating() {
        return false;
    }

    @Override
    public void setEntityResolver(EntityResolver entityResolver) {
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
    }

    public MockDocument newDocument() {
        return new MockDocument();
    }

    public DOMImplementation getDOMImplementation() {
        return null;
    }
}
