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
 * Tidy implementation of org.w3c.dom.CharacterData.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 588 $ ($Author: fgiust $)
 */
public class DOMCharacterDataImpl extends DOMNodeImpl implements org.w3c.dom.CharacterData
{

    /**
     * Instantiates a new DOMCharacterDataImpl which wraps the given Node.
     * @param adaptee wrapped node.
     */
    protected DOMCharacterDataImpl(Node adaptee)
    {
        super(adaptee);
    }

    /**
     * @see org.w3c.dom.CharacterData#getData
     */
    public String getData() throws DOMException
    {
        return getNodeValue();
    }

    /**
     * @see org.w3c.dom.CharacterData#getLength
     */
    public int getLength()
    {
        int len = 0;
        if (adaptee.textarray != null && adaptee.start < adaptee.end)
        {
            len = adaptee.end - adaptee.start;
        }
        return len;
    }

    /**
     * @see org.w3c.dom.CharacterData#substringData
     */
    public String substringData(int offset, int count) throws DOMException
    {
        int len;
        String value = null;
        if (count < 0)
        {
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid length");
        }
        if (adaptee.textarray != null && adaptee.start < adaptee.end)
        {
            if (adaptee.start + offset >= adaptee.end)
            {
                throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid offset");
            }
            len = count;
            if (adaptee.start + offset + len - 1 >= adaptee.end)
            {
                len = adaptee.end - adaptee.start - offset;
            }

            value = TidyUtils.getString(adaptee.textarray, adaptee.start + offset, len);
        }
        return value;
    }

    /**
     * Not supported.
     * @see org.w3c.dom.CharacterData#setData
     */
    public void setData(String data) throws DOMException
    {
        // NOT SUPPORTED
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

    /**
     * Not supported.
     * @see org.w3c.dom.CharacterData#appendData
     */
    public void appendData(String arg) throws DOMException
    {
        // NOT SUPPORTED
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

    /**
     * Not supported.
     * @see org.w3c.dom.CharacterData#insertData
     */
    public void insertData(int offset, String arg) throws DOMException
    {
        // NOT SUPPORTED
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

    /**
     * Not supported.
     * @see org.w3c.dom.CharacterData#deleteData
     */
    public void deleteData(int offset, int count) throws DOMException
    {
        // NOT SUPPORTED
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

    /**
     * Not supported.
     * @see org.w3c.dom.CharacterData#replaceData
     */
    public void replaceData(int offset, int count, String arg) throws DOMException
    {
        // NOT SUPPORTED
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

}