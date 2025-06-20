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
 * Mock implementation of the Document interface
 * Author: Pedro-PFerreira
 */
public class MockDocument implements Document, StaticReplacementMock {

    public MockDocument() {
    }

    public String getNodeName() {
        return new MockNode().getNodeName();
    }

    @Override
    public String getNodeValue() {
        return new MockNode().getNodeName();
    }

    @Override
    public void setNodeValue(String nodeValue) { new MockNode().setNodeValue(nodeValue);}

    @Override
    public short getNodeType() {
        return 0;
    }

    @Override
    public Node getParentNode() {
        return new MockNode();
    }

    @Override
    public NodeList getChildNodes() {
        return new MockNode().getChildNodes();
    }

    @Override
    public Node getFirstChild() { return new MockNode().getFirstChild();}

    @Override
    public Node getLastChild() { return new MockNode().getLastChild();}

    @Override
    public Node getPreviousSibling() {
        return new MockNode().getPreviousSibling();
    }

    @Override
    public Node getNextSibling() {
        return new MockNode().getNextSibling();
    }

    @Override
    public NamedNodeMap getAttributes() {
        return new MockNode().getAttributes();
    }

    @Override
    public Document getOwnerDocument() {
        return new MockNode().getOwnerDocument();
    }

    @Override
    public Node insertBefore(Node node, Node node1) throws DOMException {
        return new MockNode().insertBefore(node, node1);
    }

    @Override
    public Node replaceChild(Node node, Node node1) throws DOMException {
        return new MockNode().replaceChild(node, node1);
    }

    @Override
    public Node removeChild(Node node) throws DOMException {
        return new MockNode().removeChild(node);
    }

    @Override
    public Node appendChild(Node node) throws DOMException {
        return new MockNode().appendChild(node);
    }

    @Override
    public boolean hasChildNodes() {
        return new MockNode().hasChildNodes();
    }

    @Override
    public Node cloneNode(boolean b) {
        return new MockNode().cloneNode(b);
    }

    @Override
    public void normalize() {}

    @Override
    public boolean isSupported(String s, String s1) {
        return new MockNode().isSupported(s,s1);
    }

    @Override
    public String getNamespaceURI() {
        return new MockNode().getNamespaceURI();
    }

    @Override
    public String getPrefix() {
        return new MockNode().getPrefix();
    }

    @Override
    public void setPrefix(String s) throws DOMException {
        new MockNode().setPrefix(s);
    }

    @Override
    public String getLocalName() {
        return new MockNode().getLocalName();
    }

    @Override
    public boolean hasAttributes() {
        return new MockNode().hasAttributes();
    }

    @Override
    public String getBaseURI() {
        return new MockNode().getBaseURI();
    }

    @Override
    public short compareDocumentPosition(Node node) throws DOMException {
        return 0;
    }

    @Override
    public String getTextContent() throws DOMException {
        return "";
    }

    @Override
    public void setTextContent(String s) throws DOMException {

    }

    @Override
    public boolean isSameNode(Node node) {
        return new MockNode().isSameNode(node);
    }

    @Override
    public String lookupPrefix(String s) {
        return "";
    }

    @Override
    public boolean isDefaultNamespace(String s) {
        return false;
    }

    @Override
    public String lookupNamespaceURI(String s) {
        return "";
    }

    @Override
    public boolean isEqualNode(Node node) {
        return new MockNode().equals(node);
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
    public DocumentType getDoctype() {
        return null;
    }

    @Override
    public DOMImplementation getImplementation() {
        return null;
    }

    @Override
    public Element getDocumentElement() {
        return new MockElement();
    }

    @Override
    public Element createElement(String s) throws DOMException {
        return null;
    }

    @Override
    public DocumentFragment createDocumentFragment() {
        return null;
    }

    @Override
    public Text createTextNode(String s) {
        return null;
    }

    @Override
    public Comment createComment(String s) {
        return null;
    }

    @Override
    public CDATASection createCDATASection(String s) throws DOMException {
        return null;
    }

    @Override
    public ProcessingInstruction createProcessingInstruction(String s, String s1) throws DOMException {
        return null;
    }

    @Override
    public Attr createAttribute(String s) throws DOMException {
        return null;
    }

    @Override
    public EntityReference createEntityReference(String s) throws DOMException {
        return null;
    }

    @Override
    public NodeList getElementsByTagName(String s) {
        return null;
    }

    @Override
    public Node importNode(Node node, boolean b) throws DOMException {
        return null;
    }

    @Override
    public Element createElementNS(String s, String s1) throws DOMException {
        return null;
    }

    @Override
    public Attr createAttributeNS(String s, String s1) throws DOMException {
        return null;
    }

    @Override
    public NodeList getElementsByTagNameNS(String s, String s1) {
        return null;
    }

    @Override
    public Element getElementById(String s) {
        return null;
    }

    @Override
    public String getInputEncoding() {
        return "";
    }

    @Override
    public String getXmlEncoding() {
        return "";
    }

    @Override
    public boolean getXmlStandalone() {
        return false;
    }

    @Override
    public void setXmlStandalone(boolean b) throws DOMException {

    }

    @Override
    public String getXmlVersion() {
        return "1.0";
    }

    @Override
    public void setXmlVersion(String s) throws DOMException {
    }

    @Override
    public boolean getStrictErrorChecking() {
        return false;
    }

    @Override
    public void setStrictErrorChecking(boolean b) {

    }

    @Override
    public String getDocumentURI() {
        return "";
    }

    @Override
    public void setDocumentURI(String s) {

    }

    @Override
    public Node adoptNode(Node node) throws DOMException {
        return null;
    }

    @Override
    public DOMConfiguration getDomConfig() {
        return null;
    }

    @Override
    public void normalizeDocument() {

        NodeList nodeList = new MockNode().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            nodeList.item(i).normalize();
        }
    }

    @Override
    public Node renameNode(Node node, String s, String s1) throws DOMException {
        MockNodeList nodeList = (MockNodeList) new MockNode().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).equals(node)) {
                nodeList.itemMock(i).setNodeName(s);
                return nodeList.item(i);
            }
        }

        return null;
    }

    @Override
    public String getMockedClassName() {
        return "org.w3c.dom.Document";
    }
}
