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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Check attribute values implementations.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 762 $ ($Author: fgiust $)
 */
public final class AttrCheckImpl
{

    /**
     * checker for URLs.
     */
    public static final AttrCheck URL = new CheckUrl();

    /**
     * checker for scripts.
     */
    public static final AttrCheck SCRIPT = new CheckScript();

    /**
     * checker for "name" attribute.
     */
    public static final AttrCheck NAME = new CheckName();

    /**
     * checker for ids.
     */
    public static final AttrCheck ID = new CheckId();

    /**
     * checker for "align" attribute.
     */
    public static final AttrCheck ALIGN = new CheckAlign();

    /**
     * checker for "valign" attribute.
     */
    public static final AttrCheck VALIGN = new CheckValign();

    /**
     * checker for boolean attributes.
     */
    public static final AttrCheck BOOL = new CheckBool();

    /**
     * checker for "lenght" attribute.
     */
    public static final AttrCheck LENGTH = new CheckLength();

    /**
     * checker for "target" attribute.
     */
    public static final AttrCheck TARGET = new CheckTarget();

    /**
     * checker for "submit" attribute.
     */
    public static final AttrCheck FSUBMIT = new CheckFsubmit();

    /**
     * checker for "clear" attribute.
     */
    public static final AttrCheck CLEAR = new CheckClear();

    /**
     * checker for "shape" attribute.
     */
    public static final AttrCheck SHAPE = new CheckShape();

    /**
     * checker for "number" attribute.
     */
    public static final AttrCheck NUMBER = new CheckNumber();

    /**
     * checker for "scope" attribute.
     */
    public static final AttrCheck SCOPE = new CheckScope();

    /**
     * checker for "color" attribute.
     */
    public static final AttrCheck COLOR = new CheckColor();

    /**
     * checker for "vtype" attribute.
     */
    public static final AttrCheck VTYPE = new CheckVType();

    /**
     * checker for "scroll" attribute.
     */
    public static final AttrCheck SCROLL = new CheckScroll();

    /**
     * checker for "dir" attribute.
     */
    public static final AttrCheck TEXTDIR = new CheckTextDir();

    /**
     * checker for "lang" and "xml:lang" attributes.
     */
    public static final AttrCheck LANG = new CheckLang();

    /**
     * checker for text attributes. Actually null (no validation).
     */
    public static final AttrCheck TEXT = null;

    /**
     * checker for "charset" attribute. Actually null (no validation).
     */
    public static final AttrCheck CHARSET = null;

    /**
     * checker for "type" attribute. Actually null (no validation).
     */
    public static final AttrCheck TYPE = null;

    /**
     * checker for attributes that can contain a single character. Actually null (no validation).
     */
    public static final AttrCheck CHARACTER = null;

    /**
     * checker for attributes which contain a list of urls. Actually null (no validation).
     */
    public static final AttrCheck URLS = null;

    /**
     * checker for "cols" attribute. Actually null (no validation).
     */
    public static final AttrCheck COLS = null;

    /**
     * checker for "coords" attribute. Actually null (no validation).
     */
    public static final AttrCheck COORDS = null;

    /**
     * checker for attributes containing dates. Actually null (no validation).
     */
    public static final AttrCheck DATE = null;

    /**
     * checker for attributes referencng an id. Actually null (no validation).
     */
    public static final AttrCheck IDREF = null;

    /**
     * checker for table "frame" attribute. Actually null (no validation).
     */
    public static final AttrCheck TFRAME = null;

    /**
     * checker for "frameborder" attribute. Actually null (no validation).
     */
    public static final AttrCheck FBORDER = null;

    /**
     * checker for "media" attribute. Actually null (no validation).
     */
    public static final AttrCheck MEDIA = null;

    /**
     * checker for "rel" and "rev" attributes. Actually null (no validation).
     */
    public static final AttrCheck LINKTYPES = null;

    /**
     * checker for table "rules" attribute. Actually null (no validation).
     */
    public static final AttrCheck TRULES = null;

    /**
     * utility class, don't instantiate.
     */
    private AttrCheckImpl()
    {
        // empty private constructor
    }

    /**
     * AttrCheck implementation for checking URLs.
     */
    public static class CheckUrl implements AttrCheck
    {

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            char c;
            StringBuffer dest;
            boolean escapeFound = false;
            boolean backslashFound = false;
            int i = 0;

            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            String p = attval.value;

            for (i = 0; i < p.length(); ++i)
            {
                c = p.charAt(i);
                // find \
                if (c == '\\')
                {
                    backslashFound = true;
                }
                // find non-ascii chars
                else if ((c > 0x7e) || (c <= 0x20) || (c == '<') || (c == '>'))
                {
                    escapeFound = true;
                }
            }

            // backslashes found, fix them
            if (lexer.configuration.fixBackslash && backslashFound)
            {
                attval.value = attval.value.replace('\\', '/');
                p = attval.value;
            }

            // non-ascii chars found, fix them
            if (lexer.configuration.fixUri && escapeFound)
            {
                dest = new StringBuffer();

                for (i = 0; i < p.length(); ++i)
                {
                    c = p.charAt(i);
                    if ((c > 0x7e) || (c <= 0x20) || (c == '<') || (c == '>'))
                    {
                        dest.append('%');
                        dest.append(Integer.toHexString(c).toUpperCase());
                    }
                    else
                    {
                        dest.append(c);
                    }
                }

                attval.value = dest.toString();
            }
            if (backslashFound)
            {
                if (lexer.configuration.fixBackslash)
                {
                    lexer.report.attrError(lexer, node, attval, Report.FIXED_BACKSLASH);
                }
                else
                {
                    lexer.report.attrError(lexer, node, attval, Report.BACKSLASH_IN_URI);
                }
            }
            if (escapeFound)
            {
                if (lexer.configuration.fixUri)
                {
                    lexer.report.attrError(lexer, node, attval, Report.ESCAPED_ILLEGAL_URI);
                }
                else
                {
                    lexer.report.attrError(lexer, node, attval, Report.ILLEGAL_URI_REFERENCE);
                }

                lexer.badChars |= Report.INVALID_URI;
            }

        }
    }

    /**
     * AttrCheck implementation for checking scripts.
     */
    public static class CheckScript implements AttrCheck
    {

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            // not implemented
        }

    }

    /**
     * AttrCheck implementation for checking the "align" attribute.
     */
    public static class CheckAlign implements AttrCheck
    {

        /**
         * valid values for this attribute.
         */
        private static final String[] VALID_VALUES = new String[]{"left", "center", "right", "justify"};

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            // IMG, OBJECT, APPLET and EMBED use align for vertical position
            if (node.tag != null && ((node.tag.model & Dict.CM_IMG) != 0))
            {
                VALIGN.check(lexer, node, attval);
                return;
            }

            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            attval.checkLowerCaseAttrValue(lexer, node);

            if (!TidyUtils.isInValuesIgnoreCase(VALID_VALUES, attval.value))
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }
        }

    }

    /**
     * AttrCheck implementation for checking the "valign" attribute.
     */
    public static class CheckValign implements AttrCheck
    {

        /**
         * valid values for this attribute.
         */
        private static final String[] VALID_VALUES = new String[]{"top", "middle", "bottom", "baseline"};

        /**
         * valid values for this attribute (only for img tag).
         */
        private static final String[] VALID_VALUES_IMG = new String[]{"left", "right"};

        /**
         * proprietary values for this attribute.
         */
        private static final String[] VALID_VALUES_PROPRIETARY = new String[]{
            "texttop",
            "absmiddle",
            "absbottom",
            "textbottom"};

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            String value;

            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            attval.checkLowerCaseAttrValue(lexer, node);

            value = attval.value;

            if (TidyUtils.isInValuesIgnoreCase(VALID_VALUES, value))
            {
                // all is fine
                return;
            }

            if (TidyUtils.isInValuesIgnoreCase(VALID_VALUES_IMG, value))
            {
                if (!(node.tag != null && ((node.tag.model & Dict.CM_IMG) != 0)))
                {
                    lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
                }
            }
            else if (TidyUtils.isInValuesIgnoreCase(VALID_VALUES_PROPRIETARY, value))
            {
                lexer.constrainVersion(Dict.VERS_PROPRIETARY);
                lexer.report.attrError(lexer, node, attval, Report.PROPRIETARY_ATTR_VALUE);
            }
            else
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }
        }

    }

    /**
     * AttrCheck implementation for checking boolean attributes.
     */
    public static class CheckBool implements AttrCheck
    {

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            if (attval.value == null)
            {
                return;
            }

            attval.checkLowerCaseAttrValue(lexer, node);
        }

    }

    /**
     * AttrCheck implementation for checking the "length" attribute.
     */
    public static class CheckLength implements AttrCheck
    {

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {

            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            // don't check for <col width=...> and <colgroup width=...>
            if ("width".equalsIgnoreCase(attval.attribute)
                && (node.tag == lexer.configuration.tt.tagCol || node.tag == lexer.configuration.tt.tagColgroup))
            {
                return;
            }

            String p = attval.value;

            if (p.length() == 0 || (!Character.isDigit(p.charAt(0)) && !('%' == p.charAt(0))))
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }
            else
            {

                TagTable tt = lexer.configuration.tt;

                for (int j = 1; j < p.length(); j++)
                {
                    // elements th and td must not use percentages
                    if ((!Character.isDigit(p.charAt(j)) && (node.tag == tt.tagTd || node.tag == tt.tagTh))
                        || (!Character.isDigit(p.charAt(j)) && p.charAt(j) != '%'))
                    {
                        lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
                        break;
                    }
                }
            }
        }
    }

    /**
     * AttrCheck implementation for checking the "target" attribute.
     */
    public static class CheckTarget implements AttrCheck
    {

        /**
         * valid values for this attribute.
         */
        private static final String[] VALID_VALUES = new String[]{"_blank", "_self", "_parent", "_top"};

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {

            // No target attribute in strict HTML versions
            lexer.constrainVersion(~Dict.VERS_HTML40_STRICT);

            if (attval.value == null || attval.value.length() == 0)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            String value = attval.value;

            // target names must begin with A-Za-z ...
            if (Character.isLetter(value.charAt(0)))
            {
                return;
            }

            // or be one of _blank, _self, _parent and _top
            if (!TidyUtils.isInValuesIgnoreCase(VALID_VALUES, value))
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }

        }
    }

    /**
     * AttrCheck implementation for checking the "submit" attribute.
     */
    public static class CheckFsubmit implements AttrCheck
    {

        /**
         * valid values for this attribute.
         */
        private static final String[] VALID_VALUES = new String[]{"get", "post"};

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            attval.checkLowerCaseAttrValue(lexer, node);

            if (!TidyUtils.isInValuesIgnoreCase(VALID_VALUES, attval.value))
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }
        }
    }

    /**
     * AttrCheck implementation for checking the "clear" attribute.
     */
    public static class CheckClear implements AttrCheck
    {

        /**
         * valid values for this attribute.
         */
        private static final String[] VALID_VALUES = new String[]{"none", "left", "right", "all"};

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                attval.value = VALID_VALUES[0];
                return;
            }

            attval.checkLowerCaseAttrValue(lexer, node);

            if (!TidyUtils.isInValuesIgnoreCase(VALID_VALUES, attval.value))
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }

        }
    }

    /**
     * AttrCheck implementation for checking the "shape" attribute.
     */
    public static class CheckShape implements AttrCheck
    {

        /**
         * valid values for this attribute.
         */
        private static final String[] VALID_VALUES = new String[]{"rect", "default", "circle", "poly"};

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            attval.checkLowerCaseAttrValue(lexer, node);

            if (!TidyUtils.isInValuesIgnoreCase(VALID_VALUES, attval.value))
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }

        }
    }

    /**
     * AttrCheck implementation for checking Scope.
     */
    public static class CheckScope implements AttrCheck
    {

        /**
         * valid values for this attribute.
         */
        private static final String[] VALID_VALUES = new String[]{"row", "rowgroup", "col", "colgroup"};

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {

            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            attval.checkLowerCaseAttrValue(lexer, node);

            if (!TidyUtils.isInValuesIgnoreCase(VALID_VALUES, attval.value))
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }
        }
    }

    /**
     * AttrCheck implementation for checking numbers.
     */
    public static class CheckNumber implements AttrCheck
    {

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {

            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            // don't check <frameset cols=... rows=...>
            if (("cols".equalsIgnoreCase(attval.attribute) || "rows".equalsIgnoreCase(attval.attribute))
                && node.tag == lexer.configuration.tt.tagFrameset)
            {
                return;
            }

            String value = attval.value;

            int j = 0;

            // font size may be preceded by + or -
            if (node.tag == lexer.configuration.tt.tagFont && (value.startsWith("+") || value.startsWith("-")))
            {
                ++j;
            }

            for (; j < value.length(); j++)
            {
                char p = value.charAt(j);
                if (!Character.isDigit(p))
                {
                    lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
                    break;
                }
            }
        }
    }

    /**
     * AttrCheck implementation for checking ids.
     */
    public static class CheckId implements AttrCheck
    {

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            Node old;

            if (attval.value == null || attval.value.length() == 0)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            String p = attval.value;
            char s = p.charAt(0);

            if (p.length() == 0 || !Character.isLetter(p.charAt(0)))
            {
                if (lexer.isvoyager && (TidyUtils.isXMLLetter(s) || s == '_' || s == ':'))
                {
                    lexer.report.attrError(lexer, node, attval, Report.XML_ID_SYNTAX);
                }
                else
                {
                    lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
                }
            }
            else
            {

                for (int j = 1; j < p.length(); j++)
                {
                    s = p.charAt(j);

                    if (!TidyUtils.isNamechar(s))
                    {
                        if (lexer.isvoyager && TidyUtils.isXMLNamechar(s))
                        {
                            lexer.report.attrError(lexer, node, attval, Report.XML_ID_SYNTAX);
                        }
                        else
                        {
                            lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
                        }
                        break;
                    }
                }
            }

            if (((old = lexer.configuration.tt.getNodeByAnchor(attval.value)) != null) && old != node)
            {
                lexer.report.attrError(lexer, node, attval, Report.ANCHOR_NOT_UNIQUE);
            }
            else
            {
                lexer.configuration.tt.anchorList = lexer.configuration.tt.addAnchor(attval.value, node);
            }
        }

    }

    /**
     * AttrCheck implementation for checking the "name" attribute.
     */
    public static class CheckName implements AttrCheck
    {

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            Node old;

            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }
            else if (lexer.configuration.tt.isAnchorElement(node))
            {
                lexer.constrainVersion(~Dict.VERS_XHTML11);

                if (((old = lexer.configuration.tt.getNodeByAnchor(attval.value)) != null) && old != node)
                {
                    lexer.report.attrError(lexer, node, attval, Report.ANCHOR_NOT_UNIQUE);
                }
                else
                {
                    lexer.configuration.tt.anchorList = lexer.configuration.tt.addAnchor(attval.value, node);
                }
            }
        }

    }

    /**
     * AttrCheck implementation for checking colors.
     */
    public static class CheckColor implements AttrCheck
    {

        /**
         * valid html colors.
         */
        private static final Map COLORS = new HashMap();

        static
        {
            COLORS.put("black", "#000000");
            COLORS.put("green", "#008000");
            COLORS.put("silver", "#C0C0C0");
            COLORS.put("lime", "#00FF00");
            COLORS.put("gray", "#808080");
            COLORS.put("olive", "#808000");
            COLORS.put("white", "#FFFFFF");
            COLORS.put("yellow", "#FFFF00");
            COLORS.put("maroon", "#800000");
            COLORS.put("navy", "#000080");
            COLORS.put("red", "#FF0000");
            COLORS.put("blue", "#0000FF");
            COLORS.put("purple", "#800080");
            COLORS.put("teal", "#008080");
            COLORS.put("fuchsia", "#FF00FF");
            COLORS.put("aqua", "#00FFFF");
        }

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            boolean hexUppercase = true;
            boolean invalid = false;
            boolean found = false;

            if (attval.value == null || attval.value.length() == 0)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            String given = attval.value;

            Iterator colorIter = COLORS.entrySet().iterator();

            while (colorIter.hasNext())
            {
                Map.Entry color = (Map.Entry) colorIter.next();

                if (given.charAt(0) == '#')
                {
                    if (given.length() != 7)
                    {
                        lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
                        invalid = true;
                        break;
                    }
                    else if (given.equalsIgnoreCase((String) color.getValue()))
                    {
                        if (lexer.configuration.replaceColor)
                        {
                            attval.value = (String) color.getKey();
                        }
                        found = true;
                        break;
                    }
                }
                else if (TidyUtils.isLetter(given.charAt(0)))
                {
                    if (given.equalsIgnoreCase((String) color.getKey()))
                    {
                        if (lexer.configuration.replaceColor)
                        {
                            attval.value = (String) color.getKey();
                        }
                        found = true;
                        break;
                    }
                }
                else
                {

                    lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);

                    invalid = true;
                    break;
                }
            }
            if (!found && !invalid)
            {
                if (given.charAt(0) == '#')
                {
                    // check if valid hex digits and letters

                    for (int i = 1; i < 7; ++i)
                    {
                        if (!TidyUtils.isDigit(given.charAt(i))
                            && ("abcdef".indexOf(Character.toLowerCase(given.charAt(i))) == -1))
                        {
                            lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
                            invalid = true;
                            break;
                        }
                    }
                    // convert hex letters to uppercase
                    if (!invalid && hexUppercase)
                    {
                        for (int i = 1; i < 7; ++i)
                        {
                            attval.value = given.toUpperCase();
                        }
                    }
                }

                else
                {
                    // we could search for more colors and mark the file as HTML Proprietary, but I don't thinks
                    // it's worth the effort, so values not in HTML 4.01 are invalid
                    lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
                    invalid = true;
                }
            }
        }
    }

    /**
     * AttrCheck implementation for checking valuetype.
     */
    public static class CheckVType implements AttrCheck
    {

        /**
         * valid values for this attribute.
         */
        private static final String[] VALID_VALUES = new String[]{"data", "object", "ref"};

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {
            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            attval.checkLowerCaseAttrValue(lexer, node);

            if (!TidyUtils.isInValuesIgnoreCase(VALID_VALUES, attval.value))
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }
        }
    }

    /**
     * AttrCheck implementation for checking scroll.
     */
    public static class CheckScroll implements AttrCheck
    {

        /**
         * valid values for this attribute.
         */
        private static final String[] VALID_VALUES = new String[]{"no", "yes", "auto"};

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {

            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            attval.checkLowerCaseAttrValue(lexer, node);

            if (!TidyUtils.isInValuesIgnoreCase(VALID_VALUES, attval.value))
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }
        }
    }

    /**
     * AttrCheck implementation for checking dir.
     */
    public static class CheckTextDir implements AttrCheck
    {

        /**
         * valid values for this attribute.
         */
        private static final String[] VALID_VALUES = new String[]{"rtl", "ltr"};

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {

            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }

            attval.checkLowerCaseAttrValue(lexer, node);

            if (!TidyUtils.isInValuesIgnoreCase(VALID_VALUES, attval.value))
            {
                lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
            }
        }
    }

    /**
     * AttrCheck implementation for checking lang and xml:lang.
     */
    public static class CheckLang implements AttrCheck
    {

        /**
         * @see AttrCheck#check(Lexer, Node, AttVal)
         */
        public void check(Lexer lexer, Node node, AttVal attval)
        {

            if ("lang".equals(attval.attribute))
            {
                lexer.constrainVersion(~Dict.VERS_XHTML11);
            }

            if (attval.value == null)
            {
                lexer.report.attrError(lexer, node, attval, Report.MISSING_ATTR_VALUE);
                return;
            }
        }
    }

}