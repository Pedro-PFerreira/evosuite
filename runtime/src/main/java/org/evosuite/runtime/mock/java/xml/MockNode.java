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
import org.w3c.dom.*;

/**
 * Mock implementation of Node
 * Author: Pedro-PFerreira
 */
public class MockNode implements Node, StaticReplacementMock {

    private String nodeName = "document";
    private String nodeValue = "value";
    private MockNode parentNode;
    private final MockDocument ownerDocument;

    public MockNode() {
        this.parentNode = null;
        this.ownerDocument = null;
    }

    public MockNode(MockDocument ownerDocument) {
        this.ownerDocument = ownerDocument;
    }

    public void setNodeName(String nodeName) {this.nodeName = nodeName;}
    @Override
    public String getNodeName() {
        return this.nodeName;
    }

    @Override
    public String getNodeValue() { return this.nodeValue;}

    @Override
    public void setNodeValue(String s) throws DOMException {
        this.nodeValue = s;
    }

    @Override
    public short getNodeType() {
        return 0;
    }

    @Override
    public Node getParentNode() {
        return this.parentNode;
    }

    @Override
    public NodeList getChildNodes() {
        return new MockNodeList();
    }

    @Override
    public Node getFirstChild() {
        return new MockNodeList().item(0);
    }

    @Override
    public Node getLastChild() {

        MockNodeList mockNodeList = new MockNodeList();

        return mockNodeList.item(mockNodeList.getLength() - 1);
    }

    @Override
    public Node getPreviousSibling() {
        return new MockNode();
    }

    @Override
    public Node getNextSibling() {
        return new MockNode();
    }

    @Override
    public NamedNodeMap getAttributes() {
        return null;
    }

    @Override
    public Document getOwnerDocument() {
        return this.ownerDocument;
    }

    @Override
    public Node insertBefore(Node node, Node node1) throws DOMException {

        MockNodeList mockNodeList = new MockNodeList();

        int position = mockNodeList.indexOf((MockNode) node);

        if (position > 0){
            mockNodeList.add(position, (MockNode) node1);
            return mockNodeList.itemMock(position);
        }

        return null;
    }

    @Override
    public Node replaceChild(Node node, Node node1) throws DOMException {

        MockNodeList mockNodeList = new MockNodeList();

        for (int i = 0; i < mockNodeList.getLength(); i++){
            if (mockNodeList.itemMock(i).equals((MockNode) node)){
                this.insertBefore(node, node1);
                this.removeChild(node);
                return node1;
            }
        }

        return null;
    }

    @Override
    public Node removeChild(Node node) throws DOMException {

        MockNodeList mockNodeList = new MockNodeList();

        int childPosition = mockNodeList.indexOf((MockNode) node);

        if (childPosition >= 0) {
            return mockNodeList.remove(childPosition);
        }

        return null;
    }

    @Override
    public Node appendChild(Node node) throws DOMException {

        MockNodeList mockNodeList = new MockNodeList();
        MockNode addedNode = (MockNode) node;

        return mockNodeList.add(addedNode)? addedNode : null;
    }

    @Override
    public boolean hasChildNodes() {
        return new MockNodeList().getLength() > 0;
    }

    @Override
    public Node cloneNode(boolean deep) {
        return deep ? new MockNode(ownerDocument) : new MockNode();
    }

    @Override
    public void normalize() {
    }

    @Override
    public boolean isSupported(String s, String s1) {
        return false;
    }

    @Override
    public String getNamespaceURI() {
        return "";
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public void setPrefix(String s) throws DOMException {

    }

    @Override
    public String getLocalName() {
        return "";
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    @Override
    public String getBaseURI() {
        return "";
    }

    @Override
    public short compareDocumentPosition(Node node) throws DOMException {
        return 0;
    }

    @Override
    public String getTextContent() {
        return nodeValue;
    }

    @Override
    public void setTextContent(String s) throws DOMException {
        if (this.parentNode != null){
            this.parentNode.nodeName = s;
        }
    }

    @Override
    public boolean isSameNode(Node node) {
        return false;
    }

    @Override
    public String lookupPrefix(String s) {

        return "";
    }

    @Override
    public boolean isDefaultNamespace(String s) {
        return this.nodeName != null && this.nodeName.equals("/" + s);
    }

    @Override
    public String lookupNamespaceURI(String s) {
        return "";
    }

    @Override
    public boolean isEqualNode(Node node) {
        return this.equals(node);
    }

    @Override
    public Object getFeature(String s, String s1) {
        return null;
    }

    @Override
    public Object setUserData(String s, Object o, UserDataHandler userDataHandler) {
        return null;
    }

    @Override
    public Object getUserData(String s) {
        return null;
    }

    @Override
    public String getMockedClassName() {
        return "org.w3c.dom.Node";
    }
}
