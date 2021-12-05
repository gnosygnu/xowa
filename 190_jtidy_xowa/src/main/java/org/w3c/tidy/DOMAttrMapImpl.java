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

import org.w3c.dom.DOMException;


/**
 * Tidy implementation of org.w3c.dom.NamedNodeMap.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 738 $ ($Author: fgiust $)
 */
public class DOMAttrMapImpl implements org.w3c.dom.NamedNodeMap
{

    /**
     * wrapped org.w3c.tidy.AttVal.
     */
    private AttVal first;

    /**
     * instantiates a new DOMAttrMapImpl for the given AttVal.
     * @param firstAttVal wrapped AttVal
     */
    protected DOMAttrMapImpl(AttVal firstAttVal)
    {
        this.first = firstAttVal;
    }

    /**
     * @see org.w3c.dom.NamedNodeMap#getNamedItem(java.lang.String)
     */
    public org.w3c.dom.Node getNamedItem(String name)
    {
        AttVal att = this.first;
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
     * @see org.w3c.dom.NamedNodeMap#item
     */
    public org.w3c.dom.Node item(int index)
    {
        int i = 0;
        AttVal att = this.first;
        while (att != null)
        {
            if (i >= index)
            {
                break;
            }
            i++;
            att = att.next;
        }
        if (att != null)
        {
            return att.getAdapter();
        }

        return null;
    }

    /**
     * @see org.w3c.dom.NamedNodeMap#getLength
     */
    public int getLength()
    {
        int len = 0;
        AttVal att = this.first;
        while (att != null)
        {
            len++;
            att = att.next;
        }
        return len;
    }

    /**
     * @todo DOM level 2 setNamedItem() Not implemented. Throws NOT_SUPPORTED_ERR.
     * @see org.w3c.dom.NamedNodeMap#setNamedItem
     */
    public org.w3c.dom.Node setNamedItem(org.w3c.dom.Node arg) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * @see org.w3c.dom.NamedNodeMap#removeNamedItem
     */
    public org.w3c.dom.Node removeNamedItem(String name) throws DOMException
    {
        AttVal att = this.first;
        AttVal previous = null;

        while (att != null)
        {
            if (att.attribute.equals(name))
            {
                if (previous == null)
                {
                    this.first = att.getNext();
                }
                else
                {
                    previous.setNext(att.getNext());
                }

                break;
            }
            previous = att;
            att = att.next;
        }

        if (att != null)
        {
            return att.getAdapter();
        }

        throw new DOMException(DOMException.NOT_FOUND_ERR, "Named item " + name + "Not found");
    }

    /**
     * Not supported, returns <code>DOMException.NOT_SUPPORTED_ERR</code>.
     * @see org.w3c.dom.NamedNodeMap#getNamedItemNS(java.lang.String, java.lang.String)
     */
    public org.w3c.dom.Node getNamedItemNS(String namespaceURI, String localName)
    {
        // NOT_SUPPORTED_ERR: May be raised if the implementation does not support the feature "XML" and the language
        // exposed through the Document does not support XML Namespaces (such as HTML 4.01).
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * Not supported, returns <code>DOMException.NOT_SUPPORTED_ERR</code>.
     * @see org.w3c.dom.NamedNodeMap#setNamedItemNS(org.w3c.dom.Node)
     */
    public org.w3c.dom.Node setNamedItemNS(org.w3c.dom.Node arg) throws org.w3c.dom.DOMException
    {
        // NOT_SUPPORTED_ERR: May be raised if the implementation does not support the feature "XML" and the language
        // exposed through the Document does not support XML Namespaces (such as HTML 4.01).
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

    /**
     * Not supported, returns <code>DOMException.NOT_SUPPORTED_ERR</code>.
     * @see org.w3c.dom.NamedNodeMap#removeNamedItemNS(java.lang.String, java.lang.String)
     */
    public org.w3c.dom.Node removeNamedItemNS(String namespaceURI, String localName) throws org.w3c.dom.DOMException
    {
        // NOT_SUPPORTED_ERR: May be raised if the implementation does not support the feature "XML" and the language
        // exposed through the Document does not support XML Namespaces (such as HTML 4.01).
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "DOM method not supported");
    }

}