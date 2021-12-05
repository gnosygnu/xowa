/*
 *  Java HTML Tidy - JTidy
 *  HTML parser and pretty printer
 *
 *  Copyright (c) 1998-2000 World Wide Web Consortium (Massachusetts
 *  Institute of Technology, Institut National de Recherche en
 *  Informatique et en Automatique, Keio University). All Rights
 *  Reserved.
 *
 *  Contributing Author(s):
 *
 *     Dave Raggett <dsr@w3.org>
 *     Andy Quick <ac.quick@sympatico.ca> (translation to Java)
 *     Gary L Peskin <garyp@firstech.com> (Java development)
 *     Sami Lempinen <sami@lempinen.net> (release management)
 *     Fabrizio Giustina <fgiust at users.sourceforge.net>
 *
 *  The contributing author(s) would like to thank all those who
 *  helped with testing, bug fixes, and patience.  This wouldn't
 *  have been possible without all of you.
 *
 *  COPYRIGHT NOTICE:
 * 
 *  This software and documentation is provided "as is," and
 *  the copyright holders and contributing author(s) make no
 *  representations or warranties, express or implied, including
 *  but not limited to, warranties of merchantability or fitness
 *  for any particular purpose or that the use of the software or
 *  documentation will not infringe any third party patents,
 *  copyrights, trademarks or other rights. 
 *
 *  The copyright holders and contributing author(s) will not be
 *  liable for any direct, indirect, special or consequential damages
 *  arising out of any use of the software or documentation, even if
 *  advised of the possibility of such damage.
 *
 *  Permission is hereby granted to use, copy, modify, and distribute
 *  this source code, or portions hereof, documentation and executables,
 *  for any purpose, without fee, subject to the following restrictions:
 *
 *  1. The origin of this source code must not be misrepresented.
 *  2. Altered versions must be plainly marked as such and must
 *     not be misrepresented as being the original source.
 *  3. This Copyright notice may not be removed or altered from any
 *     source or altered source distribution.
 * 
 *  The copyright holders and contributing author(s) specifically
 *  permit, without fee, and encourage the use of this source code
 *  as a component for supporting the Hypertext Markup Language in
 *  commercial products. If you use this source code in a product,
 *  acknowledgment is not required but would be appreciated.
 *
 */
package org.w3c.tidy;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;


/**
 * DOMDocumentImpl.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 738 $ ($Author: fgiust $)
 */
public class DOMDocumentImpl extends DOMNodeImpl implements org.w3c.dom.Document
{

    /**
     * A DOM Document has its own TagTable.
     */
    private TagTable tt;

    /**
     * Instantiates a new Dom document with a default tag table.
     * @param adaptee tidy Node
     */
    protected DOMDocumentImpl(Node adaptee)
    {
        super(adaptee);
        this.tt = new TagTable();
    }

    /**
     * @see org.w3c.dom.Node#getNodeName
     */
    public String getNodeName()
    {
        return "#document";
    }

    /**
     * @see org.w3c.dom.Node#getNodeType
     */
    public short getNodeType()
    {
        return org.w3c.dom.Node.DOCUMENT_NODE;
    }

    /**
     * @see org.w3c.dom.Document#getDoctype
     */
    public org.w3c.dom.DocumentType getDoctype()
    {
        Node node = this.adaptee.content;
        while (node != null)
        {
            if (node.type == Node.DOCTYPE_TAG)
            {
                break;
            }
            node = node.next;
        }
        if (node != null)
        {
            return (org.w3c.dom.DocumentType) node.getAdapter();
        }

        return null;
    }

    /**
     * @todo DOM level 2 getImplementation() Not implemented. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Document#getImplementation
     */
    public org.w3c.dom.DOMImplementation getImplementation()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @see org.w3c.dom.Document#getDocumentElement
     */
    public org.w3c.dom.Element getDocumentElement()
    {
        Node node = this.adaptee.content;
        while (node != null)
        {
            if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
            {
                break;
            }
            node = node.next;
        }
        if (node != null)
        {
            return (org.w3c.dom.Element) node.getAdapter();
        }

        return null;
    }

    /**
     * @see org.w3c.dom.Document#createElement
     */
    public org.w3c.dom.Element createElement(String tagName) throws DOMException
    {
        Node node = new Node(Node.START_END_TAG, null, 0, 0, tagName, this.tt);
        if (node.tag == null) // Fix Bug 121206
        {
            node.tag = TagTable.XML_TAGS;
        }
        return (org.w3c.dom.Element) node.getAdapter();
    }

    /**
     * @todo DOM level 2 createDocumentFragment() Not implemented. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Document#createDocumentFragment
     */
    public org.w3c.dom.DocumentFragment createDocumentFragment()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @see org.w3c.dom.Document#createTextNode
     */
    public org.w3c.dom.Text createTextNode(String data)
    {
        byte[] textarray = TidyUtils.getBytes(data);
        Node node = new Node(Node.TEXT_NODE, textarray, 0, textarray.length);
        return (org.w3c.dom.Text) node.getAdapter();
    }

    /**
     * @see org.w3c.dom.Document#createComment
     */
    public org.w3c.dom.Comment createComment(String data)
    {
        byte[] textarray = TidyUtils.getBytes(data);
        Node node = new Node(Node.COMMENT_TAG, textarray, 0, textarray.length);
        return (org.w3c.dom.Comment) node.getAdapter();
    }

    /**
     * @todo DOM level 2 createCDATASection() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Document#createCDATASection
     */
    public org.w3c.dom.CDATASection createCDATASection(String data) throws DOMException
    {
        // NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
    }

    /**
     * @todo DOM level 2 createProcessingInstruction() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Document#createProcessingInstruction
     */
    public org.w3c.dom.ProcessingInstruction createProcessingInstruction(String target, String data)
        throws DOMException
    {
        // NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
    }

    /**
     * @see org.w3c.dom.Document#createAttribute
     */
    public org.w3c.dom.Attr createAttribute(String name) throws DOMException
    {
        AttVal av = new AttVal(null, null, '"', name, null);
        av.dict = AttributeTable.getDefaultAttributeTable().findAttribute(av);
        return av.getAdapter();
    }

    /**
     * @todo DOM level 2 createEntityReference() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Document#createEntityReference
     */
    public org.w3c.dom.EntityReference createEntityReference(String name) throws DOMException
    {
        // NOT_SUPPORTED_ERR: Raised if this document is an HTML document
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "createEntityReference not supported");
    }

    /**
     * @see org.w3c.dom.Document#getElementsByTagName
     */
    public org.w3c.dom.NodeList getElementsByTagName(String tagname)
    {
        return new DOMNodeListByTagNameImpl(this.adaptee, tagname);
    }

    /**
     * @todo DOM level 2 importNode() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Document#importNode(org.w3c.dom.Node, boolean)
     */
    public org.w3c.dom.Node importNode(org.w3c.dom.Node importedNode, boolean deep) throws org.w3c.dom.DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "importNode not supported");
    }

    /**
     * @todo DOM level 2 createAttributeNS() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Document#createAttributeNS(java.lang.String, java.lang.String)
     */
    public org.w3c.dom.Attr createAttributeNS(String namespaceURI, String qualifiedName)
        throws org.w3c.dom.DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "createAttributeNS not supported");
    }

    /**
     * @todo DOM level 2 createElementNS() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Document#createElementNS(java.lang.String, java.lang.String)
     */
    public org.w3c.dom.Element createElementNS(String namespaceURI, String qualifiedName)
        throws org.w3c.dom.DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "createElementNS not supported");
    }

    /**
     * @todo DOM level 2 getElementsByTagNameNS() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Document#getElementsByTagNameNS(java.lang.String, java.lang.String)
     */
    public org.w3c.dom.NodeList getElementsByTagNameNS(String namespaceURI, String localName)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "getElementsByTagNameNS not supported");
    }

    /**
     * @todo DOM level 2 getElementById() Not implemented. Returns null.
     * @see org.w3c.dom.Document#getElementById(java.lang.String)
     */
    public org.w3c.dom.Element getElementById(String elementId)
    {
        return null;
    }

    /**
     * @todo DOM level 3 adoptNode() Not implemented.
     * @see org.w3c.dom.Document#adoptNode(org.w3c.dom.Node)
     */
    public org.w3c.dom.Node adoptNode(org.w3c.dom.Node source) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @todo DOM level 3 getDocumentURI() Not implemented. Returns null.
     * @see org.w3c.dom.Document#getDocumentURI()
     */
    public String getDocumentURI()
    {
        return null;
    }

    /**
     * @todo DOM level 3 getDomConfig() Not implemented. Returns null.
     * @see org.w3c.dom.Document#getDomConfig()
     */
    public DOMConfiguration getDomConfig()
    {
        return null;
    }

    /**
     * @todo DOM level 3 getInputEncoding() Not implemented. Returns null.
     * @see org.w3c.dom.Document#getInputEncoding()
     */
    public String getInputEncoding()
    {
        return null;
    }

    /**
     * @todo DOM level 3 getStrictErrorChecking() Not implemented. Returns true.
     * @see org.w3c.dom.Document#getStrictErrorChecking()
     */
    public boolean getStrictErrorChecking()
    {
        return true;
    }

    /**
     * @todo DOM level 3 getXmlEncoding() Not implemented. Returns null.
     * @see org.w3c.dom.Document#getXmlEncoding()
     */
    public String getXmlEncoding()
    {
        return null;
    }

    /**
     * @todo DOM level 3 getXmlStandalone() Not implemented. Returns false.
     * @see org.w3c.dom.Document#getXmlStandalone()
     */
    public boolean getXmlStandalone()
    {
        return false;
    }

    /**
     * @todo DOM level 3 getXmlVersion() Not implemented. Always returns "1.0".
     * @see org.w3c.dom.Document#getXmlVersion()
     */
    public String getXmlVersion()
    {
        // An attribute specifying, as part of the XML declaration, the version number of this document. If there is no
        // declaration and if this document supports the "XML" feature, the value is "1.0"
        return "1.0";
    }

    /**
     * @todo DOM level 3 normalizeDocument() Not implemented. Do nothing.
     * @see org.w3c.dom.Document#normalizeDocument()
     */
    public void normalizeDocument()
    {
        // do nothing
    }

    /**
     * @todo DOM level 3 renameNode() Not implemented. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Document#renameNode(org.w3c.dom.Node, java.lang.String, java.lang.String)
     */
    public org.w3c.dom.Node renameNode(org.w3c.dom.Node n, String namespaceURI, String qualifiedName)
        throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @todo DOM level 3 setDocumentURI() Not implemented. Do nothing.
     * @see org.w3c.dom.Document#setDocumentURI(java.lang.String)
     */
    public void setDocumentURI(String documentURI)
    {
        // do nothing
    }

    /**
     * @todo DOM level 3 setStrictErrorChecking() Not implemented. Do nothing.
     * @see org.w3c.dom.Document#setStrictErrorChecking(boolean)
     */
    public void setStrictErrorChecking(boolean strictErrorChecking)
    {
        // do nothing
    }

    /**
     * @todo DOM level 3 setXmlStandalone() Not implemented. Do nothing.
     * @see org.w3c.dom.Document#setXmlStandalone(boolean)
     */
    public void setXmlStandalone(boolean xmlStandalone) throws DOMException
    {
        // do nothing
    }

    /**
     * @todo DOM level 3 setXmlVersion() Not implemented. Do nothing.
     * @see org.w3c.dom.Document#setXmlVersion(java.lang.String)
     */
    public void setXmlVersion(String xmlVersion) throws DOMException
    {
        // do nothing
    }
}