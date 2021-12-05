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

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Property parser instances.
 * @author Fabrizio Giustina
 * @version $Revision $ ($Author $)
 */
public final class ParsePropertyImpl
{

    /**
     * configuration parser for int values.
     */
    static final ParseProperty INT = new ParseInt();

    /**
     * configuration parser for boolean values.
     */
    static final ParseProperty BOOL = new ParseBoolean();

    /**
     * configuration parser for inverted boolean values.
     */
    static final ParseProperty INVBOOL = new ParseInvBoolean();

    /**
     * configuration parser for char encoding values.
     */
    static final ParseProperty CHAR_ENCODING = new ParseCharEncoding();

    /**
     * configuration parser for name values.
     */
    static final ParseProperty NAME = new ParseName();

    /**
     * configuration parser for tag names.
     */
    static final ParseProperty TAGNAMES = new ParseTagNames();

    /**
     * configuration parser for doctype property.
     */
    static final ParseProperty DOCTYPE = new ParseDocType();

    /**
     * configuration parser for repetated attribute property.
     */
    static final ParseProperty REPEATED_ATTRIBUTES = new ParseRepeatedAttribute();

    /**
     * configuration parser for String values.
     */
    static final ParseProperty STRING = new ParseString();

    /**
     * configuration parser for indent property.
     */
    static final ParseProperty INDENT = new ParseIndent();

    /**
     * configuration parser for css selectors.
     */
    static final ParseProperty CSS1SELECTOR = new ParseCSS1Selector();

    /**
     * configuration parser for new line bytes.
     */
    static final ParseProperty NEWLINE = new ParseNewLine();

    /**
     * don't instantiate.
     */
    private ParsePropertyImpl()
    {
        // unused
    }

    /**
     * parser for integer values.
     */
    static class ParseInt implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            int i = 0;
            try
            {
                i = Integer.parseInt(value);
            }
            catch (NumberFormatException e)
            {
                configuration.report.badArgument(value, option);
                i = -1;
            }
            return new Integer(i);
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "Integer";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "0, 1, 2, ...";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            return value == null ? "" : value.toString();
        }
    }

    /**
     * parser for boolean values.
     */
    static class ParseBoolean implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            Boolean b = Boolean.TRUE;
            if (value != null && value.length() > 0)
            {
                char c = value.charAt(0);
                if ((c == 't') || (c == 'T') || (c == 'Y') || (c == 'y') || (c == '1'))
                {
                    b = Boolean.TRUE;
                }
                else if ((c == 'f') || (c == 'F') || (c == 'N') || (c == 'n') || (c == '0'))
                {
                    b = Boolean.FALSE;
                }
                else
                {
                    configuration.report.badArgument(value, option);
                }
            }
            return b;
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "Boolean";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "y/n, yes/no, t/f, true/false, 1/0";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            if (value == null)
            {
                return "";
            }

            return ((Boolean) value).booleanValue() ? "yes" : "no";
        }
    }

    /**
     * parser for boolean values.
     */
    static class ParseInvBoolean implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            return (((Boolean) BOOL.parse(value, option, configuration)).booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "Boolean";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "yes, no, true, false";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            if (value == null)
            {
                return "";
            }

            return ((Boolean) value).booleanValue() ? "no" : "yes";
        }
    }

    /**
     * parse character encoding option. Can be any java encoding name supported by the runtime platform.
     */
    static class ParseCharEncoding implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {

            if ("raw".equalsIgnoreCase(value))
            {
                // special value for compatibility with tidy c
                configuration.rawOut = true;
            }
            else if (!TidyUtils.isCharEncodingSupported(value))
            {
                configuration.report.badArgument(value, option);
            }
            else if ("input-encoding".equalsIgnoreCase(option))
            {
                configuration.setInCharEncodingName(value);
            }
            else if ("output-encoding".equalsIgnoreCase(option))
            {
                configuration.setOutCharEncodingName(value);
            }
            else if ("char-encoding".equalsIgnoreCase(option))
            {
                configuration.setInCharEncodingName(value);
                configuration.setOutCharEncodingName(value);
            }

            return null;
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "Encoding";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            // ascii, latin1, raw, utf-8, iso2022, mac, utf-16, utf-16be, utf-16le, big5, shiftjis
            return "Any valid java char encoding name";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            if ("output-encoding".equalsIgnoreCase(option))
            {
                return configuration.getOutCharEncodingName();
            }

            // for input-encoding or char-encoding
            return configuration.getInCharEncodingName();
        }
    }

    /**
     * parser for name values (a string excluding whitespace).
     */
    static class ParseName implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            StringTokenizer t = new StringTokenizer(value);
            String rs = null;
            if (t.countTokens() >= 1)
            {
                rs = t.nextToken();
            }
            else
            {
                configuration.report.badArgument(value, option);
            }
            return rs;
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "Name";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "-";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            return value == null ? "" : value.toString();
        }
    }

    /**
     * parser for name values.
     */
    static class ParseTagNames implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            short tagType = Dict.TAGTYPE_INLINE;

            if ("new-inline-tags".equals(option))
            {
                tagType = Dict.TAGTYPE_INLINE;
            }
            else if ("new-blocklevel-tags".equals(option))
            {
                tagType = Dict.TAGTYPE_BLOCK;
            }
            else if ("new-empty-tags".equals(option))
            {
                tagType = Dict.TAGTYPE_EMPTY;
            }
            else if ("new-pre-tags".equals(option))
            {
                tagType = Dict.TAGTYPE_PRE;
            }

            StringTokenizer t = new StringTokenizer(value, " \t\n\r,");
            while (t.hasMoreTokens())
            {
                configuration.definedTags |= tagType;
                configuration.tt.defineTag(tagType, t.nextToken());
            }
            return null;
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "Tag names";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "tagX, tagY, ...";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            short tagType;
            if ("new-inline-tags".equals(option))
            {
                tagType = Dict.TAGTYPE_INLINE;
            }
            else if ("new-blocklevel-tags".equals(option))
            {
                tagType = Dict.TAGTYPE_BLOCK;
            }
            else if ("new-empty-tags".equals(option))
            {
                tagType = Dict.TAGTYPE_EMPTY;
            }
            else if ("new-pre-tags".equals(option))
            {
                tagType = Dict.TAGTYPE_PRE;
            }
            else
            {
                return "";
            }

            List tagList = configuration.tt.findAllDefinedTag(tagType);
            if (tagList.isEmpty())
            {
                return "";
            }

            StringBuffer buffer = new StringBuffer();
            Iterator iterator = tagList.iterator();
            while (iterator.hasNext())
            {
                buffer.append(iterator.next());
                buffer.append(" ");
            }

            return buffer.toString();
        }
    }

    /**
     * Parse doctype preference. doctype: <code>omit | auto | strict | loose | [fpi]</code> where the fpi is a string
     * similar to <code>"-//ACME//DTD HTML 3.14159//EN"</code>.
     */
    static class ParseDocType implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            value = value.trim();

            /* "-//ACME//DTD HTML 3.14159//EN" or similar */

            if (value.startsWith("\""))
            {
                configuration.docTypeMode = Configuration.DOCTYPE_USER;
                return value;
            }

            /* read first word */
            String word = "";
            StringTokenizer t = new StringTokenizer(value, " \t\n\r,");
            if (t.hasMoreTokens())
            {
                word = t.nextToken();
            }
            // #443663 - fix by Terry Teague 23 Jul 01
            if ("auto".equalsIgnoreCase(word))
            {
                configuration.docTypeMode = Configuration.DOCTYPE_AUTO;
            }
            else if ("omit".equalsIgnoreCase(word))
            {
                configuration.docTypeMode = Configuration.DOCTYPE_OMIT;
            }
            else if ("strict".equalsIgnoreCase(word))
            {
                configuration.docTypeMode = Configuration.DOCTYPE_STRICT;
            }
            else if ("loose".equalsIgnoreCase(word) || "transitional".equalsIgnoreCase(word))
            {
                configuration.docTypeMode = Configuration.DOCTYPE_LOOSE;
            }
            else
            {
                configuration.report.badArgument(value, option);
            }
            return null;
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "DocType";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "omit | auto | strict | loose | [fpi]";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {

            String stringValue;

            switch (configuration.docTypeMode)
            {
                case Configuration.DOCTYPE_AUTO :
                    stringValue = "auto";
                    break;

                case Configuration.DOCTYPE_OMIT :
                    stringValue = "omit";
                    break;

                case Configuration.DOCTYPE_STRICT :
                    stringValue = "strict";
                    break;

                case Configuration.DOCTYPE_LOOSE :
                    stringValue = "transitional";
                    break;

                case Configuration.DOCTYPE_USER :
                    stringValue = configuration.docTypeStr;
                    break;

                default :
                    stringValue = "unknown";
                    break;
            }

            return stringValue;
        }
    }

    /**
     * keep-first or keep-last?
     */
    static class ParseRepeatedAttribute implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            int dupAttr;

            if ("keep-first".equalsIgnoreCase(value))
            {
                dupAttr = Configuration.KEEP_FIRST;
            }
            else if ("keep-last".equalsIgnoreCase(value))
            {
                dupAttr = Configuration.KEEP_LAST;
            }
            else
            {
                configuration.report.badArgument(value, option);
                dupAttr = -1;
            }
            return new Integer(dupAttr);
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "Enum";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "keep-first, keep-last";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            if (value == null)
            {
                return "";
            }

            int intValue = ((Integer) value).intValue();
            String stringValue;

            switch (intValue)
            {
                case Configuration.KEEP_FIRST :
                    stringValue = "keep-first";
                    break;

                case Configuration.KEEP_LAST :
                    stringValue = "keep-last";
                    break;

                default :
                    stringValue = "unknown";
                    break;
            }

            return stringValue;
        }
    }

    /**
     * Parser for String values.
     */
    static class ParseString implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            return value;
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "String";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "-";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            return value == null ? "" : (String) value;
        }
    }

    /**
     * Parser for indent values.
     */
    static class ParseIndent implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            boolean b = configuration.indentContent;

            if ("yes".equalsIgnoreCase(value))
            {
                b = true;
                configuration.smartIndent = false;
            }
            else if ("true".equalsIgnoreCase(value))
            {
                b = true;
                configuration.smartIndent = false;
            }
            else if ("no".equalsIgnoreCase(value))
            {
                b = false;
                configuration.smartIndent = false;
            }
            else if ("false".equalsIgnoreCase(value))
            {
                b = false;
                configuration.smartIndent = false;
            }
            else if ("auto".equalsIgnoreCase(value))
            {
                b = true;
                configuration.smartIndent = true;
            }
            else
            {
                configuration.report.badArgument(value, option);
            }
            return b ? Boolean.TRUE : Boolean.FALSE;
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "Indent";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "auto, y/n, yes/no, t/f, true/false, 1/0";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            return value == null ? "" : value.toString();
        }
    }

    /**
     * Parser for css selectors.
     */
    static class ParseCSS1Selector implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            StringTokenizer t = new StringTokenizer(value);
            String buf = null;
            if (t.countTokens() >= 1)
            {
                buf = t.nextToken() + "-"; // Make sure any escaped Unicode is terminated so valid class names are
                // generated after Tidy appends last digits.
            }
            else
            {
                configuration.report.badArgument(value, option);
            }

            if (!Lexer.isCSS1Selector(value))
            {
                configuration.report.badArgument(value, option);
            }

            return buf;
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "Name";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "CSS1 selector";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            return value == null ? "" : (String) value;
        }
    }

    /**
     * Parser for newline bytes. Allows lf|crlf|cr.
     */
    static class ParseNewLine implements ParseProperty
    {

        /**
         * @see org.w3c.tidy.ParseProperty#parse(java.lang.String, java.lang.String, org.w3c.tidy.Configuration)
         */
        public Object parse(String value, String option, Configuration configuration)
        {
            // lf|crlf|cr
            if ("lf".equalsIgnoreCase(value))
            {
                configuration.newline = new char[]{'\n'};
            }
            else if ("cr".equalsIgnoreCase(value))
            {
                configuration.newline = new char[]{'\r'};
            }
            else if ("crlf".equalsIgnoreCase(value))
            {
                configuration.newline = new char[]{'\r', '\n'};
            }
            else
            {
                configuration.report.badArgument(value, option);
            }
            return null;
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getType()
         */
        public String getType()
        {
            return "Enum";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getOptionValues()
         */
        public String getOptionValues()
        {
            return "lf, crlf, cr";
        }

        /**
         * @see org.w3c.tidy.ParseProperty#getFriendlyName(java.lang.String, java.lang.Object, Configuration)
         */
        public String getFriendlyName(String option, Object value, Configuration configuration)
        {
            if (configuration.newline.length == 1)
            {
                return (configuration.newline[0] == '\n') ? "lf" : "cr";
            }
            return "crlf";
        }
    }

}