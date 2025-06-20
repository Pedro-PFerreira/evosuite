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

import org.evosuite.runtime.mock.StaticReplacementMock;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of NodeList
 * Author: Pedro-PFerreira
 */
public class MockNodeList implements NodeList, StaticReplacementMock {

    private final List<MockNode> nodes = populateNodes();

    public MockNodeList() {
    }

    @Override
    public Node item(int i) {
        return nodes.get(i);
    }

    public MockNode itemMock(int i) {
        return nodes.get(i);
    }

    @Override
    public int getLength() {
        return nodes.size();
    }

    public int indexOf(MockNode node) {
        return nodes.indexOf(node);
    }

    public boolean add(MockNode node) {
        return nodes.add(node);
    }

    public void add(int index, MockNode node) {
        nodes.add(index, node);
    }

    public void replace(int index, MockNode node) {
        nodes.set(index, node);
    }

    public Node remove(int index) {
        return index >= 0 && index < nodes.size() ? nodes.remove(index) : null;
    }

    private List<MockNode> populateNodes(){

        List<MockNode> result = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            result.add(new MockNode());
        }

        return result;
    }

    @Override
    public String getMockedClassName() {
        return "org.w3c.dom.NodeList";
    }
}
