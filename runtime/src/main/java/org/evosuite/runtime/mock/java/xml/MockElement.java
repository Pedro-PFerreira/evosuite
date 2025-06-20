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
import org.w3c.dom.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock implementation of Element
 * Author: Pedro-PFerreira
 */
public class MockElement extends MockNode implements Element, OverrideMock {

    private final Map<String, String> attributes = new HashMap<>();

    public MockElement() {
        super();
    }

    public MockElement(MockDocument ownerDocument){
        super(ownerDocument);
    }

    @Override
    public String getTagName() {
        return getNodeName();
    }

    @Override
    public String getAttribute(String s) {
        return attributes.getOrDefault(s, "");
    }

    @Override
    public void setAttribute(String s, String s1) throws DOMException {
        attributes.put(s, s1);
    }

    @Override
    public void removeAttribute(String s) throws DOMException {
        attributes.remove(s);
    }

    @Override
    public Attr getAttributeNode(String s) {
        return null;
    }

    @Override
    public void normalize(){}

    @Override
    public Attr setAttributeNode(Attr attr) throws DOMException {
        attributes.put(attr.getName(), attr.getValue());
        return attr;
    }

    @Override
    public Attr removeAttributeNode(Attr attr) throws DOMException {
        return attributes.remove(attr.getName()) != null ? attr : null;
    }

    @Override
    public NodeList getElementsByTagName(String s) {
        MockNodeList matchingNodes = new MockNodeList();
        for (int i = 0; i < getChildNodes().getLength(); i++) {
            Node node = getChildNodes().item(i);
            if (node instanceof Element && node.getNodeName().equals(s)) {
                matchingNodes.add((MockNode) node);
            }
        }
        return matchingNodes;
    }

    @Override
    public String getAttributeNS(String s, String s1) throws DOMException {
        return getAttribute(s1);
    }

    @Override
    public void setAttributeNS(String s, String s1, String s2) throws DOMException {
        setAttribute(s1,s2);
    }

    @Override
    public void removeAttributeNS(String s, String s1) throws DOMException {
        removeAttribute(s1);
    }

    @Override
    public Attr getAttributeNodeNS(String s, String s1) throws DOMException {
        return getAttributeNode(s1);
    }

    @Override
    public Attr setAttributeNodeNS(Attr attr) throws DOMException {
        return setAttributeNode(attr);
    }

    @Override
    public NodeList getElementsByTagNameNS(String s, String s1) throws DOMException {
        return getElementsByTagName(s1);
    }

    @Override
    public boolean hasAttribute(String s) {
        return attributes.containsKey(s);
    }

    @Override
    public boolean hasAttributeNS(String s, String s1) throws DOMException {
        return hasAttribute(s1);
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return null;
    }

    @Override
    public void setIdAttribute(String s, boolean b) throws DOMException {

    }

    @Override
    public void setIdAttributeNS(String s, String s1, boolean b) throws DOMException {

    }

    @Override
    public void setIdAttributeNode(Attr attr, boolean b) throws DOMException {

    }
}
