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

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.TypeInfo;


/**
 * DOMElementImpl.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 738 $ ($Author: fgiust $)
 */
public class DOMElementImpl extends DOMNodeImpl implements org.w3c.dom.Element
{

    /**
     * Instantiates a new DOM element.
     * @param adaptee Tidy Node.
     */
    protected DOMElementImpl(Node adaptee)
    {
        super(adaptee);
    }

    /**
     * @see org.w3c.dom.Node#getNodeType
     */
    public short getNodeType()
    {
        return org.w3c.dom.Node.ELEMENT_NODE;
    }

    /**
     * @see org.w3c.dom.Element#getTagName
     */
    public String getTagName()
    {
        return super.getNodeName();
    }

    /**
     * @see org.w3c.dom.Element#getAttribute(java.lang.String)
     */
    public String getAttribute(String name)
    {
        if (this.adaptee == null)
        {
            return null;
        }

        AttVal att = this.adaptee.attributes;
        while (att != null)
        {
            if (att.attribute.equals(name))
            {
                break;
            }
            att = att.next;
        }
        if (att != null)
        {
            return att.value;
        }

        return "";
    }

    /**
     * @see org.w3c.dom.Element#setAttribute(java.lang.String, java.lang.String)
     */
    public void setAttribute(String name, String value) throws DOMException
    {
        if (this.adaptee == null)
        {
            return;
        }

        AttVal att = this.adaptee.attributes;
        while (att != null)
        {
            if (att.attribute.equals(name))
            {
                break;
            }
            att = att.next;
        }
        if (att != null)
        {
            att.value = value;
        }
        else
        {
            att = new AttVal(null, null, '"', name, value);
            att.dict = AttributeTable.getDefaultAttributeTable().findAttribute(att);
            if (this.adaptee.attributes == null)
            {
                this.adaptee.attributes = att;
            }
            else
            {
                att.next = this.adaptee.attributes;
                this.adaptee.attributes = att;
            }
        }
    }

    /**
     * @see org.w3c.dom.Element#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String name) throws DOMException
    {
        if (this.adaptee == null)
        {
            return;
        }

        AttVal att = this.adaptee.attributes;
        AttVal pre = null;
        while (att != null)
        {
            if (att.attribute.equals(name))
            {
                break;
            }
            pre = att;
            att = att.next;
        }
        if (att != null)
        {
            if (pre == null)
            {
                this.adaptee.attributes = att.next;
            }
            else
            {
                pre.next = att.next;
            }
        }
    }

    /**
     * @see org.w3c.dom.Element#getAttributeNode(java.lang.String)
     */
    public org.w3c.dom.Attr getAttributeNode(String name)
    {
        if (this.adaptee == null)
        {
            return null;
        }

        AttVal att = this.adaptee.attributes;
        while (att != null)
        {
            if (att.attribute.equals(name))
            {
                break;
            }
            att = att.next;
        }
        if (att != null)
        {
            return att.getAdapter();
        }

        return null;
    }

    /**
     * @see org.w3c.dom.Element#setAttributeNode(org.w3c.dom.Attr)
     */
    public org.w3c.dom.Attr setAttributeNode(org.w3c.dom.Attr newAttr) throws DOMException
    {
        if (newAttr == null)
        {
            return null;
        }
        if (!(newAttr instanceof DOMAttrImpl))
        {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "newAttr not instanceof DOMAttrImpl");
        }

        DOMAttrImpl newatt = (DOMAttrImpl) newAttr;
        String name = newatt.avAdaptee.attribute;
        org.w3c.dom.Attr result = null;

        AttVal att = this.adaptee.attributes;
        while (att != null)
        {
            if (att.attribute.equals(name))
            {
                break;
            }
            att = att.next;
        }
        if (att != null)
        {
            result = att.getAdapter();
            att.adapter = newAttr;
        }
        else
        {
            if (this.adaptee.attributes == null)
            {
                this.adaptee.attributes = newatt.avAdaptee;
            }
            else
            {
                newatt.avAdaptee.next = this.adaptee.attributes;
                this.adaptee.attributes = newatt.avAdaptee;
            }
        }
        return result;
    }

    /**
     * @see org.w3c.dom.Element#removeAttributeNode(org.w3c.dom.Attr)
     */
    public org.w3c.dom.Attr removeAttributeNode(org.w3c.dom.Attr oldAttr) throws DOMException
    {
        if (oldAttr == null)
        {
            return null;
        }

        org.w3c.dom.Attr result = null;
        AttVal att = this.adaptee.attributes;
        AttVal pre = null;
        while (att != null)
        {
            if (att.getAdapter() == oldAttr)
            {
                break;
            }
            pre = att;
            att = att.next;
        }
        if (att != null)
        {
            if (pre == null)
            {
                this.adaptee.attributes = att.next;
            }
            else
            {
                pre.next = att.next;
            }
            result = oldAttr;
        }
        else
        {
            throw new DOMException(DOMException.NOT_FOUND_ERR, "oldAttr not found");
        }
        return result;
    }

    /**
     * @see org.w3c.dom.Element#getElementsByTagName(java.lang.String)
     */
    public org.w3c.dom.NodeList getElementsByTagName(String name)
    {
        return new DOMNodeListByTagNameImpl(this.adaptee, name);
    }

    /**
     * @todo DOM level 2 getOwnerDocument() Not supported. Do nothing.
     * @see org.w3c.dom.Element#normalize
     */
    public void normalize()
    {
        // do nothing
    }

    /**
     * @todo DOM level 2 getAttributeNS() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Element#getAttributeNS(java.lang.String, java.lang.String)
     */
    public String getAttributeNS(String namespaceURI, String localName)
    {
        // DOMException - NOT_SUPPORTED_ERR: May be raised if the implementation does not support the feature "XML" and
        // the language exposed through the Document does not support XML Namespaces (such as HTML 4.01).
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @todo DOM level 2 setAttributeNS() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Element#setAttributeNS(java.lang.String, java.lang.String, java.lang.String)
     */
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws org.w3c.dom.DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @todo DOM level 2 removeAttributeNS() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Element#removeAttributeNS(java.lang.String, java.lang.String)
     */
    public void removeAttributeNS(String namespaceURI, String localName) throws org.w3c.dom.DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @todo DOM level 2 getAttributeNodeNS() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Element#getAttributeNodeNS(java.lang.String, java.lang.String)
     */
    public org.w3c.dom.Attr getAttributeNodeNS(String namespaceURI, String localName)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @todo DOM level 2 setAttributeNodeNS() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Element#setAttributeNodeNS(org.w3c.dom.Attr)
     */
    public org.w3c.dom.Attr setAttributeNodeNS(org.w3c.dom.Attr newAttr) throws org.w3c.dom.DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @todo DOM level 2 getElementsByTagNameNS() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Element#getElementsByTagNameNS(java.lang.String, java.lang.String)
     */
    public org.w3c.dom.NodeList getElementsByTagNameNS(String namespaceURI, String localName)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @todo DOM level 2 hasAttribute() Not supported. Returns false.
     * @see org.w3c.dom.Element#hasAttribute(java.lang.String)
     */
    public boolean hasAttribute(String name)
    {
        return false;
    }

    /**
     * @todo DOM level 2 hasAttribute() Not supported. Returns false.
     * @see org.w3c.dom.Element#hasAttributeNS(java.lang.String, java.lang.String)
     */
    public boolean hasAttributeNS(String namespaceURI, String localName)
    {
        return false;
    }

    /**
     * @todo DOM level 3 getSchemaTypeInfo() Not supported. Returns null.
     * @see org.w3c.dom.Element#getSchemaTypeInfo()
     */
    public TypeInfo getSchemaTypeInfo()
    {
        return null;
    }

    /**
     * @todo DOM level 3 setIdAttribute() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Element#setIdAttribute(java.lang.String, boolean)
     */
    public void setIdAttribute(String name, boolean isId) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @todo DOM level 3 setIdAttributeNode() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Element#setIdAttributeNode(org.w3c.dom.Attr, boolean)
     */
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @todo DOM level 3 setIdAttributeNS() Not supported. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.Element#setIdAttributeNS(java.lang.String, java.lang.String, boolean)
     */
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }
}