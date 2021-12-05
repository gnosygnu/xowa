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

/**
 * HTML attribute.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 400 $ ($Author: fgiust $)
 */
public class Attribute
{

    /**
     * attribute name.
     */
    private String name;

    /**
     * don't wrap attribute.
     */
    private boolean nowrap;

    /**
     * unmodifiable attribute?
     */
    private boolean literal;

    /**
     * html versions for this attribute.
     */
    private short versions;

    /**
     * checker for the attribute.
     */
    private AttrCheck attrchk;

    /**
     * Instantiates a new Attribute.
     * @param attributeName attribute name
     * @param htmlVersions versions in which this attribute is supported
     * @param check AttrCheck instance
     */
    public Attribute(String attributeName, short htmlVersions, AttrCheck check)
    {
        this.name = attributeName;
        this.versions = htmlVersions;
        this.attrchk = check;
    }

    /**
     * Is this a literal (unmodifiable) attribute?
     * @param isLiteral boolean <code>true</code> for a literal attribute
     */
    public void setLiteral(boolean isLiteral)
    {
        this.literal = isLiteral;
    }

    /**
     * Don't wrap this attribute?
     * @param isNowrap boolean <code>true</code>= don't wrap
     */
    public void setNowrap(boolean isNowrap)
    {
        this.nowrap = isNowrap;
    }

    /**
     * Returns the checker for this attribute.
     * @return instance of AttrCheck.
     */
    public AttrCheck getAttrchk()
    {
        return this.attrchk;
    }

    /**
     * Is this a literal (unmodifiable) attribute?
     * @return <code>true</code> for a literal attribute
     */
    public boolean isLiteral()
    {
        return this.literal;
    }

    /**
     * Returns the attribute name.
     * @return attribute name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Don't wrap this attribute?
     * @return <code>true</code>= don't wrap
     */
    public boolean isNowrap()
    {
        return this.nowrap;
    }

    /**
     * Returns the html versions in which this attribute is supported.
     * @return html versions for this attribute.
     * @see Dict
     */
    public short getVersions()
    {
        return this.versions;
    }

}