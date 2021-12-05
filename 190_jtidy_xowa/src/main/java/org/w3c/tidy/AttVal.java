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


/**
 * Attribute/Value linked list node.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 899 $ ($Author: aditsu $)
 */
public class AttVal extends Object implements Cloneable
{

    /**
     * next AttVal.
     */
    protected AttVal next;

    /**
     * Attribute definition.
     */
    protected Attribute dict;

    /**
     * Asp node.
     */
    protected Node asp;

    /**
     * Php node.
     */
    protected Node php;

    /**
     * Delimiter (" or ').
     */
    protected int delim;

    /**
     * Attribute name.
     */
    protected String attribute;

    /**
     * Attribute value.
     */
    protected String value;

    /**
     * DOM adapter.
     */
    protected Attr adapter;

    /**
     * Instantiates a new empty AttVal.
     */
    public AttVal()
    {
        super();
    }

    /**
     * Instantiates a new AttVal.
     * @param next next linked AttVal
     * @param dict Attribute from dictionary
     * @param delim delimitator for attribute value
     * @param attribute attribute name
     * @param value attribute value
     */
    public AttVal(AttVal next, Attribute dict, int delim, String attribute, String value)
    {
        this.next = next;
        this.dict = dict;
        this.delim = delim;
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Instantiates a new AttVal.
     * @param next next linked AttVal
     * @param dict Attribute from dictionary
     * @param asp contained asp node
     * @param php contained php node
     * @param delim delimitator for attribute value
     * @param attribute attribute name
     * @param value attribute value
     */
    public AttVal(AttVal next, Attribute dict, Node asp, Node php, int delim, String attribute, String value)
    {
        this.next = next;
        this.dict = dict;
        this.asp = asp;
        this.php = php;
        this.delim = delim;
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * @see java.lang.Object#clone()
     */
    protected Object clone()
    {
        AttVal av = null;
        try
        {
            av = (AttVal) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            // should never happen
        }

        if (this.next != null)
        {
            av.next = (AttVal) this.next.clone();
        }
        if (this.asp != null)
        {
            av.asp = this.asp.cloneNode(false);
        }
        if (this.php != null)
        {
            av.php = this.php.cloneNode(false);
        }

        return av;
    }

    /**
     * Is this a boolean attribute.
     * @return <code>true</code> if this is a boolean attribute
     */
    public boolean isBoolAttribute()
    {
        Attribute attr = this.dict;
        if (attr != null)
        {
            if (attr.getAttrchk() == AttrCheckImpl.BOOL)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Check the attribute value for uppercase letters (only if the value should be lowercase, required for literal
     * values in xhtml).
     * @param lexer Lexer
     * @param node Node which contains this attribute
     */
    void checkLowerCaseAttrValue(Lexer lexer, Node node)
    {
        if (this.value == null)
        {
            return;
        }

        String lowercase = this.value.toLowerCase();

        if (!this.value.equals(lowercase))
        {
            if (lexer.isvoyager)
            {
                lexer.report.attrError(lexer, node, this, Report.ATTR_VALUE_NOT_LCASE);
            }

            if (lexer.isvoyager || lexer.configuration.lowerLiterals)
            {
                this.value = lowercase;
            }
        }
    }

    /**
     * Check attribute name/value and report errors.
     * @param lexer Lexer
     * @param node node which contains this attribute
     * @return Attribute
     */
    public Attribute checkAttribute(Lexer lexer, Node node)
    {
        TagTable tt = lexer.configuration.tt;

        Attribute attr = this.dict;

        // ignore unknown attributes for proprietary elements
        if (attr != null)
        {

            // if attribute looks like <foo/> check XML is ok
            if (TidyUtils.toBoolean(attr.getVersions() & Dict.VERS_XML))
            {
                if (!(lexer.configuration.xmlTags || lexer.configuration.xmlOut))
                {
                    lexer.report.attrError(lexer, node, this, Report.XML_ATTRIBUTE_VALUE);
                }
            }
            // title first appeared in HTML 4.0 except for a/link
            else if (attr != AttributeTable.attrTitle || !(node.tag == tt.tagA || node.tag == tt.tagLink))
            {
                lexer.constrainVersion(attr.getVersions());
            }

            if (attr.getAttrchk() != null)
            {
                attr.getAttrchk().check(lexer, node, this);
            }
            else if (TidyUtils.toBoolean(this.dict.getVersions() & Dict.VERS_PROPRIETARY))
            {
                lexer.report.attrError(lexer, node, this, Report.PROPRIETARY_ATTRIBUTE);
            }

        }
        else if (!lexer.configuration.xmlTags
            && !(node.tag == null)
            && this.asp == null
            && !(node.tag != null && (TidyUtils.toBoolean(node.tag.versions & Dict.VERS_PROPRIETARY))))
        {
            lexer.report.attrError(lexer, node, this, Report.UNKNOWN_ATTRIBUTE);
        }

        return attr;
    }

    /**
     * Return the org.w3c.dom.Attr adapter.
     * @return org.w3c.dom.Attr adapter
     */
    protected org.w3c.dom.Attr getAdapter()
    {
        if (this.adapter == null)
        {
            this.adapter = new DOMAttrImpl(this);
        }
        return this.adapter;
    }

    /**
     * Getter for <code>asp</code>.
     * @return Returns the asp.
     */
    public Node getAsp()
    {
        return this.asp;
    }

    /**
     * Setter for <code>asp</code>.
     * @param asp The asp to set.
     */
    public void setAsp(Node asp)
    {
        this.asp = asp;
    }

    /**
     * Getter for <code>attribute</code>.
     * @return Returns the attribute.
     */
    public String getAttribute()
    {
        return this.attribute;
    }

    /**
     * Setter for <code>attribute</code>.
     * @param attribute The attribute to set.
     */
    public void setAttribute(String attribute)
    {
        this.attribute = attribute;
    }

    /**
     * Getter for <code>delim</code>.
     * @return Returns the delim.
     */
    public int getDelim()
    {
        return this.delim;
    }

    /**
     * Setter for <code>delim</code>.
     * @param delim The delim to set.
     */
    public void setDelim(int delim)
    {
        this.delim = delim;
    }

    /**
     * Getter for <code>dict</code>.
     * @return Returns the dict.
     */
    public Attribute getDict()
    {
        return this.dict;
    }

    /**
     * Setter for <code>dict</code>.
     * @param dict The dict to set.
     */
    public void setDict(Attribute dict)
    {
        this.dict = dict;
    }

    /**
     * Getter for <code>next</code>.
     * @return Returns the next.
     */
    public AttVal getNext()
    {
        return this.next;
    }

    /**
     * Setter for <code>next</code>.
     * @param next The next to set.
     */
    public void setNext(AttVal next)
    {
        this.next = next;
    }

    /**
     * Getter for <code>php</code>.
     * @return Returns the php.
     */
    public Node getPhp()
    {
        return this.php;
    }

    /**
     * Setter for <code>php</code>.
     * @param php The php to set.
     */
    public void setPhp(Node php)
    {
        this.php = php;
    }

    /**
     * Getter for <code>value</code>.
     * @return Returns the value.
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * Setter for <code>value</code>.
     * @param value The value to set.
     */
    public void setValue(String value)
    {
        this.value = value;
    }

}