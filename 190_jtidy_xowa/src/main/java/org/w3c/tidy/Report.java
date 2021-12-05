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

import java.io.InputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.w3c.tidy.TidyMessage.Level;


/**
 * Error/informational message reporter. You should only need to edit the file TidyMessages.properties to localize HTML
 * tidy.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 921 $ ($Author: aditsu $)
 */
public final class Report
{

    /**
     * used to point to Web Accessibility Guidelines.
     */
    public static final String ACCESS_URL = "http://www.w3.org/WAI/GL";

    /**
     * Release date String.
     */
    public static final String RELEASE_DATE_STRING = readReleaseDate();
    
    private static String readReleaseDate() {
    	final Properties p = new Properties();
    	try {
			final InputStream s = Report.class.getResourceAsStream("/jtidy.properties");
			p.load(s);
			s.close();
		} catch (Exception e) {
			throw new RuntimeException("Failed to load jtidy.properties", e);
		}
		return p.getProperty("date");
    }

    /**
     * invalid entity: missing semicolon.
     */
    public static final short MISSING_SEMICOLON = 1;

    /**
     * invalid entity: missing semicolon.
     */
    public static final short MISSING_SEMICOLON_NCR = 2;

    /**
     * invalid entity: unknown entity.
     */
    public static final short UNKNOWN_ENTITY = 3;

    /**
     * invalid entity: unescaped ampersand.
     */
    public static final short UNESCAPED_AMPERSAND = 4;

    /**
     * invalid entity: apos undefined in current definition.
     */
    public static final short APOS_UNDEFINED = 5;

    /**
     * missing an end tag.
     */
    public static final short MISSING_ENDTAG_FOR = 6;

    /**
     * missing end tag before.
     */
    public static final short MISSING_ENDTAG_BEFORE = 7;

    /**
     * discarding unexpected element.
     */
    public static final short DISCARDING_UNEXPECTED = 8;

    /**
     * nested emphasis.
     */
    public static final short NESTED_EMPHASIS = 9;

    /**
     * non matching end tag.
     */
    public static final short NON_MATCHING_ENDTAG = 10;

    /**
     * tag not allowed in.
     */
    public static final short TAG_NOT_ALLOWED_IN = 11;

    /**
     * missing start tag.
     */
    public static final short MISSING_STARTTAG = 12;

    /**
     * unexpected end tag.
     */
    public static final short UNEXPECTED_ENDTAG = 13;

    /**
     * unsing br in place of.
     */
    public static final short USING_BR_INPLACE_OF = 14;

    /**
     * inserting tag.
     */
    public static final short INSERTING_TAG = 15;

    /**
     * suspected missing quote.
     */
    public static final short SUSPECTED_MISSING_QUOTE = 16;

    /**
     * missing title element.
     */
    public static final short MISSING_TITLE_ELEMENT = 17;

    /**
     * duplicate frameset.
     */
    public static final short DUPLICATE_FRAMESET = 18;

    /**
     * elments can be nested.
     */
    public static final short CANT_BE_NESTED = 19;

    /**
     * obsolete element.
     */
    public static final short OBSOLETE_ELEMENT = 20;

    /**
     * proprietary element.
     */
    public static final short PROPRIETARY_ELEMENT = 21;

    /**
     * unknown element.
     */
    public static final short UNKNOWN_ELEMENT = 22;

    /**
     * trim empty element.
     */
    public static final short TRIM_EMPTY_ELEMENT = 23;

    /**
     * coerce to end tag.
     */
    public static final short COERCE_TO_ENDTAG = 24;

    /**
     * illegal nesting.
     */
    public static final short ILLEGAL_NESTING = 25;

    /**
     * noframes content.
     */
    public static final short NOFRAMES_CONTENT = 26;

    /**
     * content after body.
     */
    public static final short CONTENT_AFTER_BODY = 27;

    /**
     * inconsistent version.
     */
    public static final short INCONSISTENT_VERSION = 28;

    /**
     * malformed comment.
     */
    public static final short MALFORMED_COMMENT = 29;

    /**
     * bad coment chars.
     */
    public static final short BAD_COMMENT_CHARS = 30;

    /**
     * bad xml comment.
     */
    public static final short BAD_XML_COMMENT = 31;

    /**
     * bad cdata comment.
     */
    public static final short BAD_CDATA_CONTENT = 32;

    /**
     * inconsistent namespace.
     */
    public static final short INCONSISTENT_NAMESPACE = 33;

    /**
     * doctype after tags.
     */
    public static final short DOCTYPE_AFTER_TAGS = 34;

    /**
     * malformed doctype.
     */
    public static final short MALFORMED_DOCTYPE = 35;

    /**
     * unexpected end of file.
     */
    public static final short UNEXPECTED_END_OF_FILE = 36;

    /**
     * doctype not upper case.
     */
    public static final short DTYPE_NOT_UPPER_CASE = 37;

    /**
     * too many element.
     */
    public static final short TOO_MANY_ELEMENTS = 38;

    /**
     * unescaped element.
     */
    public static final short UNESCAPED_ELEMENT = 39;

    /**
     * nested quotation.
     */
    public static final short NESTED_QUOTATION = 40;

    /**
     * element not empty.
     */
    public static final short ELEMENT_NOT_EMPTY = 41;

    /**
     * encoding IO conflict.
     */
    public static final short ENCODING_IO_CONFLICT = 42;

    /**
     * mixed content in block.
     */
    public static final short MIXED_CONTENT_IN_BLOCK = 43;

    /**
     * missing doctype.
     */
    public static final short MISSING_DOCTYPE = 44;

    /**
     * space preceding xml declaration.
     */
    public static final short SPACE_PRECEDING_XMLDECL = 45;

    /**
     * too many elements in.
     */
    public static final short TOO_MANY_ELEMENTS_IN = 46;

    /**
     * unexpected endag in.
     */
    public static final short UNEXPECTED_ENDTAG_IN = 47;

    /**
     * replacing element.
     */
    public static final short REPLACING_ELEMENT = 83;

    /**
     * replacing unexcaped element.
     */
    public static final short REPLACING_UNEX_ELEMENT = 84;

    /**
     * coerce to endtag.
     */
    public static final short COERCE_TO_ENDTAG_WARN = 85;

    /**
     * attribute: unknown attribute.
     */
    public static final short UNKNOWN_ATTRIBUTE = 48;

    /**
     * attribute: missing attribute.
     */
    public static final short MISSING_ATTRIBUTE = 49;

    /**
     * attribute: missing attribute value.
     */
    public static final short MISSING_ATTR_VALUE = 50;

    /**
     * attribute: bad attribute value.
     */
    public static final short BAD_ATTRIBUTE_VALUE = 51;

    /**
     * attribute: unexpected gt.
     */
    public static final short UNEXPECTED_GT = 52;

    /**
     * attribute: proprietary attribute.
     */
    public static final short PROPRIETARY_ATTRIBUTE = 53;

    /**
     * attribute: proprietary attribute value.
     */
    public static final short PROPRIETARY_ATTR_VALUE = 54;

    /**
     * attribute: repeated attribute.
     */
    public static final short REPEATED_ATTRIBUTE = 55;

    /**
     * attribute: missing image map.
     */
    public static final short MISSING_IMAGEMAP = 56;

    /**
     * attribute: xml attribute value.
     */
    public static final short XML_ATTRIBUTE_VALUE = 57;

    /**
     * attribute: missing quotemark.
     */
    public static final short MISSING_QUOTEMARK = 58;

    /**
     * attribute: unexpected quotemark.
     */
    public static final short UNEXPECTED_QUOTEMARK = 59;

    /**
     * attribute: id and name mismatch.
     */
    public static final short ID_NAME_MISMATCH = 60;

    /**
     * attribute: backslash in URI.
     */
    public static final short BACKSLASH_IN_URI = 61;

    /**
     * attribute: fixed backslash.
     */
    public static final short FIXED_BACKSLASH = 62;

    /**
     * attribute: illegal URI reference.
     */
    public static final short ILLEGAL_URI_REFERENCE = 63;

    /**
     * attribute: escaped illegal URI.
     */
    public static final short ESCAPED_ILLEGAL_URI = 64;

    /**
     * attribute: newline in URI.
     */
    public static final short NEWLINE_IN_URI = 65;

    /**
     * attribute: anchor not unique.
     */
    public static final short ANCHOR_NOT_UNIQUE = 66;

    /**
     * attribute: entity in id.
     */
    public static final short ENTITY_IN_ID = 67;

    /**
     * attribute: joining attribute.
     */
    public static final short JOINING_ATTRIBUTE = 68;

    /**
     * attribute: expected equalsign.
     */
    public static final short UNEXPECTED_EQUALSIGN = 69;

    /**
     * attribute: attribute value not lower case.
     */
    public static final short ATTR_VALUE_NOT_LCASE = 70;

    /**
     * attribute: id sintax.
     */
    public static final short XML_ID_SYNTAX = 71;

    /**
     * attribute: invalid attribute.
     */
    public static final short INVALID_ATTRIBUTE = 72;

    /**
     * attribute: bad attribute value replaced.
     */
    public static final short BAD_ATTRIBUTE_VALUE_REPLACED = 73;

    /**
     * attribute: invalid xml id.
     */
    public static final short INVALID_XML_ID = 74;

    /**
     * attribute: unexpected end of file.
     */
    public static final short UNEXPECTED_END_OF_FILE_ATTR = 75;

    /**
     * character encoding: vendor specific chars.
     */
    public static final short VENDOR_SPECIFIC_CHARS = 76;

    /**
     * character encoding: invalid sgml chars.
     */
    public static final short INVALID_SGML_CHARS = 77;

    /**
     * character encoding: invalid utf8.
     */
    public static final short INVALID_UTF8 = 78;

    /**
     * character encoding: invalid utf16.
     */
    public static final short INVALID_UTF16 = 79;

    /**
     * character encoding: encoding mismatch.
     */
    public static final short ENCODING_MISMATCH = 80;

    /**
     * character encoding: nvalid URI.
     */
    public static final short INVALID_URI = 81;

    /**
     * character encoding: invalid NCR.
     */
    public static final short INVALID_NCR = 82;

    /**
     * Constant used for reporting of given doctype.
     */
    public static final short DOCTYPE_GIVEN_SUMMARY = 110;

    /**
     * Constant used for reporting of version summary.
     */
    public static final short REPORT_VERSION_SUMMARY = 111;

    /**
     * Constant used for reporting of bad access summary.
     */
    public static final short BADACCESS_SUMMARY = 112;

    /**
     * Constant used for reporting of bad form summary.
     */
    public static final short BADFORM_SUMMARY = 113;

    /**
     * accessibility flaw: missing image map.
     */
    public static final short MISSING_IMAGE_ALT = 1;

    /**
     * accessibility flaw: missing link alt.
     */
    public static final short MISSING_LINK_ALT = 2;

    /**
     * accessibility flaw: missing summary.
     */
    public static final short MISSING_SUMMARY = 4;

    /**
     * accessibility flaw: missing image map.
     */
    public static final short MISSING_IMAGE_MAP = 8;

    /**
     * accessibility flaw: using frames.
     */
    public static final short USING_FRAMES = 16;

    /**
     * accessibility flaw: using noframes.
     */
    public static final short USING_NOFRAMES = 32;

    /**
     * presentation flaw: using spacer.
     */
    public static final short USING_SPACER = 1;

    /**
     * presentation flaw: using layer.
     */
    public static final short USING_LAYER = 2;

    /**
     * presentation flaw: using nobr.
     */
    public static final short USING_NOBR = 4;

    /**
     * presentation flaw: using font.
     */
    public static final short USING_FONT = 8;

    /**
     * presentation flaw: using body.
     */
    public static final short USING_BODY = 16;

    /**
     * character encoding error: windows chars.
     */
    public static final short WINDOWS_CHARS = 1;

    /**
     * character encoding error: non ascii.
     */
    public static final short NON_ASCII = 2;

    /**
     * character encoding error: found utf16.
     */
    public static final short FOUND_UTF16 = 4;

    /**
     * char has been replaced.
     */
    public static final short REPLACED_CHAR = 0;

    /**
     * char has been discarder.
     */
    public static final short DISCARDED_CHAR = 1;

    /**
     * Resource bundle with messages.
     */
    private static ResourceBundle res;

    /**
     * Printed in GNU Emacs messages.
     */
    private String currentFile;

    /**
     * message listener for error reporting.
     */
    private TidyMessageListener listener;

    static
    {
        try
        {
            res = ResourceBundle.getBundle("org/w3c/tidy/TidyMessages");
        }
        catch (MissingResourceException e)
        {
            throw new Error(e.toString());
        }
    }

    /**
     * Instantiated only in Tidy() constructor.
     */
    protected Report()
    {
        super();
    }

    /**
     * Generates a complete message for the warning/error. The message is composed by:
     * <ul>
     * <li>position in file</li>
     * <li>prefix for the error level (warning: | error:)</li>
     * <li>message read from ResourceBundle</li>
     * <li>optional parameters added to message using MessageFormat</li>
     * </ul>
     * @param errorCode tidy error code
     * @param lexer Lexer
     * @param message key for the ResourceBundle
     * @param params optional parameters added with MessageFormat
     * @param level message level. One of <code>TidyMessage.LEVEL_ERROR</code>,
     * <code>TidyMessage.LEVEL_WARNING</code>,<code>TidyMessage.LEVEL_INFO</code>
     * @return formatted message
     * @throws MissingResourceException if <code>message</code> key is not available in jtidy resource bundle.
     * @see TidyMessage
     */
    protected String getMessage(int errorCode, Lexer lexer, String message, Object[] params, Level level)
        throws MissingResourceException
    {
        String resource;
        resource = res.getString(message);

        String position;

        if (lexer != null && level != Level.SUMMARY)
        {
            position = getPosition(lexer);
        }
        else
        {
            position = "";
        }

        String prefix;

        if (level == Level.ERROR)
        {
            prefix = res.getString("error");
        }
        else if (level == Level.WARNING)
        {
            prefix = res.getString("warning");
        }
        else
        {
            prefix = "";
        }

        String messageString;

        if (params != null)
        {
            messageString = MessageFormat.format(resource, params);
        }
        else
        {
            messageString = resource;
        }

        if (listener != null)
        {
            TidyMessage msg = new TidyMessage(errorCode, (lexer != null) ? lexer.lines : 0, (lexer != null)
                ? lexer.columns
                : 0, level, messageString);
            listener.messageReceived(msg);
        }

        return position + prefix + messageString;
    }

    /**
     * Prints a message to lexer.errout after calling getMessage().
     * @param errorCode tidy error code
     * @param lexer Lexer
     * @param message key for the ResourceBundle
     * @param params optional parameters added with MessageFormat
     * @param level message level. One of <code>TidyMessage.LEVEL_ERROR</code>,
     * <code>TidyMessage.LEVEL_WARNING</code>,<code>TidyMessage.LEVEL_INFO</code>
     * @see TidyMessage
     */
    private void printMessage(int errorCode, Lexer lexer, String message, Object[] params, Level level)
    {
        String resource;
        try
        {
            resource = getMessage(errorCode, lexer, message, params, level);
        }
        catch (MissingResourceException e)
        {
            lexer.errout.println(e.toString());
            return;
        }

        lexer.errout.println(resource);
    }

    /**
     * Prints a message to errout after calling getMessage(). Used when lexer is not yet defined.
     * @param errout PrintWriter
     * @param message key for the ResourceBundle
     * @param params optional parameters added with MessageFormat
     * @param level message level. One of <code>TidyMessage.LEVEL_ERROR</code>,
     * <code>TidyMessage.LEVEL_WARNING</code>,<code>TidyMessage.LEVEL_INFO</code>
     * @see TidyMessage
     */
    private void printMessage(PrintWriter errout, String message, Object[] params, Level level)
    {
        String resource;
        try
        {
            resource = getMessage(-1, null, message, params, level);
        }
        catch (MissingResourceException e)
        {
            errout.println(e.toString());
            return;
        }
        errout.println(resource);
    }

    /**
     * print version information.
     * @param p printWriter
     */
    public void showVersion(PrintWriter p)
    {
        printMessage(p, "version_summary", new Object[]{RELEASE_DATE_STRING}, Level.SUMMARY);
    }

    /**
     * Returns a formatted tag name handling start and ent tags, nulls, doctypes, and text.
     * @param tag Node
     * @return formatted tag name
     */
    private String getTagName(Node tag)
    {
        if (tag != null)
        {
            if (tag.type == Node.START_TAG)
            {
                return "<" + tag.element + ">";
            }
            else if (tag.type == Node.END_TAG)
            {
                return "</" + tag.element + ">";
            }
            else if (tag.type == Node.DOCTYPE_TAG)
            {
                return "<!DOCTYPE>";
            }
            else if (tag.type == Node.TEXT_NODE)
            {
                return "plain text";
            }
            else
            {
                return tag.element;
            }
        }
        return "";
    }

    /**
     * Prints an "unknown option" error message. Lexer is not defined when this is called.
     * @param option unknown option name
     */
    public void unknownOption(String option)
    {
        try
        {
            System.err.println(MessageFormat.format(res.getString("unknown_option"), new Object[]{option}));
        }
        catch (MissingResourceException e)
        {
            System.err.println(e.toString());
        }
    }

    /**
     * Prints a "bad argument" error message. Lexer is not defined when this is called.
     * @param key argument name
     * @param value bad argument value
     */
    public void badArgument(String key, String value)
    {
        try
        {
            System.err.println(MessageFormat.format(res.getString("bad_argument"), new Object[]{value, key}));
        }
        catch (MissingResourceException e)
        {
            System.err.println(e.toString());
        }
    }

    /**
     * Returns a formatted String describing the current position in file.
     * @param lexer Lexer
     * @return String position ("line:column")
     */
    private String getPosition(Lexer lexer)
    {
        try
        {
            // Change formatting to be parsable by GNU Emacs
            if (lexer.configuration.emacs)
            {
                return MessageFormat.format(res.getString("emacs_format"), new Object[]{
                    this.currentFile,
                    new Integer(lexer.lines),
                    new Integer(lexer.columns)})
                    + " ";
            }
            // traditional format
            return MessageFormat.format(res.getString("line_column"), new Object[]{
                new Integer(lexer.lines),
                new Integer(lexer.columns)});

        }
        catch (MissingResourceException e)
        {
            lexer.errout.println(e.toString());
        }
        return "";
    }

    /**
     * Prints encoding error messages.
     * @param lexer Lexer
     * @param code error code
     * @param c invalid char
     */
    public void encodingError(Lexer lexer, int code, int c)
    {
        lexer.warnings++;

        if (lexer.errors > lexer.configuration.showErrors) // keep quiet after <showErrors> errors
        {
            return;
        }

        if (lexer.configuration.showWarnings)
        {
            String buf = Integer.toHexString(c);

            // An encoding mismatch is currently treated as a non-fatal error
            if ((code & ~DISCARDED_CHAR) == ENCODING_MISMATCH)
            {
                // actual encoding passed in "c"
                lexer.badChars |= ENCODING_MISMATCH;
                printMessage(
                    code,
                    lexer,
                    "encoding_mismatch",
                    new Object[]{
                        lexer.configuration.getInCharEncodingName(),
                        ParsePropertyImpl.CHAR_ENCODING.getFriendlyName(null, new Integer(c), lexer.configuration)},
                    Level.WARNING);
            }
            else if ((code & ~DISCARDED_CHAR) == VENDOR_SPECIFIC_CHARS)
            {
                lexer.badChars |= VENDOR_SPECIFIC_CHARS;
                printMessage(
                    code,
                    lexer,
                    "invalid_char",
                    new Object[]{new Integer(code & DISCARDED_CHAR), buf},
                    Level.WARNING);
            }
            else if ((code & ~DISCARDED_CHAR) == INVALID_SGML_CHARS)
            {
                lexer.badChars |= INVALID_SGML_CHARS;
                printMessage(
                    code,
                    lexer,
                    "invalid_char",
                    new Object[]{new Integer(code & DISCARDED_CHAR), buf},
                    Level.WARNING);
            }
            else if ((code & ~DISCARDED_CHAR) == INVALID_UTF8)
            {
                lexer.badChars |= INVALID_UTF8;
                printMessage(
                    code,
                    lexer,
                    "invalid_utf8",
                    new Object[]{new Integer(code & DISCARDED_CHAR), buf},
                    Level.WARNING);
            }

            else if ((code & ~DISCARDED_CHAR) == INVALID_UTF16)
            {
                lexer.badChars |= INVALID_UTF16;
                printMessage(
                    code,
                    lexer,
                    "invalid_utf16",
                    new Object[]{new Integer(code & DISCARDED_CHAR), buf},
                    Level.WARNING);

            }

            else if ((code & ~DISCARDED_CHAR) == INVALID_NCR)
            {
                lexer.badChars |= INVALID_NCR;
                printMessage(
                    code,
                    lexer,
                    "invalid_ncr",
                    new Object[]{new Integer(code & DISCARDED_CHAR), buf},
                    Level.WARNING);
            }

        }
    }

    /**
     * Prints entity error messages.
     * @param lexer Lexer
     * @param code error code
     * @param entity invalid entity String
     * @param c invalid char
     */
    public void entityError(Lexer lexer, short code, String entity, int c)
    {
        lexer.warnings++;

        if (lexer.errors > lexer.configuration.showErrors) // keep quiet after <showErrors> errors
        {
            return;
        }

        if (lexer.configuration.showWarnings)
        {
            switch (code)
            {
                case MISSING_SEMICOLON :
                    printMessage(code, lexer, "missing_semicolon", new Object[]{entity}, Level.WARNING);
                    break;
                case MISSING_SEMICOLON_NCR :
                    printMessage(code, lexer, "missing_semicolon_ncr", new Object[]{entity}, Level.WARNING);
                    break;
                case UNKNOWN_ENTITY :
                    printMessage(code, lexer, "unknown_entity", new Object[]{entity}, Level.WARNING);
                    break;
                case UNESCAPED_AMPERSAND :
                    printMessage(code, lexer, "unescaped_ampersand", null, Level.WARNING);
                    break;
                case APOS_UNDEFINED :
                    printMessage(code, lexer, "apos_undefined", null, Level.WARNING);
                    break;
                default :
                    // should not reach here
                    break;
            }
        }
    }

    /**
     * Prints error messages for attributes.
     * @param lexer Lexer
     * @param node current tag
     * @param attribute attribute
     * @param code error code
     */
    public void attrError(Lexer lexer, Node node, AttVal attribute, short code)
    {
        if (code == UNEXPECTED_GT)
        {
            lexer.errors++;
        }
        else
        {
            lexer.warnings++;
        }

        if (lexer.errors > lexer.configuration.showErrors) // keep quiet after <showErrors> errors
        {
            return;
        }

        if (code == UNEXPECTED_GT) // error
        {
            printMessage(code, lexer, "unexpected_gt", new Object[]{getTagName(node)}, Level.ERROR);
        }

        if (!lexer.configuration.showWarnings) // warnings
        {
            return;
        }

        switch (code)
        {
            case UNKNOWN_ATTRIBUTE :
                printMessage(code, lexer, "unknown_attribute", new Object[]{attribute.attribute}, Level.WARNING);
                break;

            case MISSING_ATTRIBUTE :
                printMessage(
                    code,
                    lexer,
                    "missing_attribute",
                    new Object[]{getTagName(node), attribute.attribute},
                    Level.WARNING);
                break;

            case MISSING_ATTR_VALUE :
                printMessage(
                    code,
                    lexer,
                    "missing_attr_value",
                    new Object[]{getTagName(node), attribute.attribute},
                    Level.WARNING);
                break;

            case MISSING_IMAGEMAP :
                printMessage(code, lexer, "missing_imagemap", new Object[]{getTagName(node)}, Level.WARNING);
                lexer.badAccess |= MISSING_IMAGE_MAP;
                break;

            case BAD_ATTRIBUTE_VALUE :
                printMessage(code, lexer, "bad_attribute_value", new Object[]{
                    getTagName(node),
                    attribute.attribute,
                    attribute.value}, Level.WARNING);
                break;

            case XML_ID_SYNTAX :
                printMessage(
                    code,
                    lexer,
                    "xml_id_sintax",
                    new Object[]{getTagName(node), attribute.attribute},
                    Level.WARNING);
                break;

            case XML_ATTRIBUTE_VALUE :
                printMessage(
                    code,
                    lexer,
                    "xml_attribute_value",
                    new Object[]{getTagName(node), attribute.attribute},
                    Level.WARNING);
                break;

            case UNEXPECTED_QUOTEMARK :
                printMessage(code, lexer, "unexpected_quotemark", new Object[]{getTagName(node)}, Level.WARNING);
                break;

            case MISSING_QUOTEMARK :
                printMessage(code, lexer, "missing_quotemark", new Object[]{getTagName(node)}, Level.WARNING);
                break;

            case REPEATED_ATTRIBUTE :
                printMessage(code, lexer, "repeated_attribute", new Object[]{
                    getTagName(node),
                    attribute.value,
                    attribute.attribute}, Level.WARNING);
                break;

            case PROPRIETARY_ATTR_VALUE :
                printMessage(
                    code,
                    lexer,
                    "proprietary_attr_value",
                    new Object[]{getTagName(node), attribute.value},
                    Level.WARNING);
                break;

            case PROPRIETARY_ATTRIBUTE :
                printMessage(
                    code,
                    lexer,
                    "proprietary_attribute",
                    new Object[]{getTagName(node), attribute.attribute},
                    Level.WARNING);
                break;

            case UNEXPECTED_END_OF_FILE :
                // on end of file adjust reported position to end of input
                lexer.lines = lexer.in.getCurline();
                lexer.columns = lexer.in.getCurcol();
                printMessage(code, lexer, "unexpected_end_of_file", new Object[]{getTagName(node)}, Level.WARNING);
                break;

            case ID_NAME_MISMATCH :
                printMessage(code, lexer, "id_name_mismatch", new Object[]{getTagName(node)}, Level.WARNING);
                break;

            case BACKSLASH_IN_URI :
                printMessage(code, lexer, "backslash_in_uri", new Object[]{getTagName(node)}, Level.WARNING);
                break;

            case FIXED_BACKSLASH :
                printMessage(code, lexer, "fixed_backslash", new Object[]{getTagName(node)}, Level.WARNING);
                break;

            case ILLEGAL_URI_REFERENCE :
                printMessage(code, lexer, "illegal_uri_reference", new Object[]{getTagName(node)}, Level.WARNING);
                break;

            case ESCAPED_ILLEGAL_URI :
                printMessage(code, lexer, "escaped_illegal_uri", new Object[]{getTagName(node)}, Level.WARNING);
                break;

            case NEWLINE_IN_URI :
                printMessage(code, lexer, "newline_in_uri", new Object[]{getTagName(node)}, Level.WARNING);
                break;

            case ANCHOR_NOT_UNIQUE :
                printMessage(
                    code,
                    lexer,
                    "anchor_not_unique",
                    new Object[]{getTagName(node), attribute.value},
                    Level.WARNING);
                break;

            case ENTITY_IN_ID :
                printMessage(code, lexer, "entity_in_id", null, Level.WARNING);
                break;

            case JOINING_ATTRIBUTE :
                printMessage(
                    code,
                    lexer,
                    "joining_attribute",
                    new Object[]{getTagName(node), attribute.attribute},
                    Level.WARNING);
                break;

            case UNEXPECTED_EQUALSIGN :
                printMessage(code, lexer, "expected_equalsign", new Object[]{getTagName(node)}, Level.WARNING);
                break;

            case ATTR_VALUE_NOT_LCASE :
                printMessage(code, lexer, "attr_value_not_lcase", new Object[]{
                    getTagName(node),
                    attribute.value,
                    attribute.attribute}, Level.WARNING);
                break;

            default :
                break;
        }
    }

    /**
     * Prints warnings.
     * @param lexer Lexer
     * @param element parent/missing tag
     * @param node current tag
     * @param code error code
     */
    public void warning(Lexer lexer, Node element, Node node, short code)
    {

        TagTable tt = lexer.configuration.tt;
        if (!((code == DISCARDING_UNEXPECTED) && lexer.badForm != 0)) // lexer->errors++; already done in BadForm()
        {
            lexer.warnings++;
        }

        // keep quiet after <showErrors> errors
        if (lexer.errors > lexer.configuration.showErrors)
        {
            return;
        }

        if (lexer.configuration.showWarnings)
        {
            switch (code)
            {
                case MISSING_ENDTAG_FOR :
                    printMessage(code, lexer, "missing_endtag_for", new Object[]{element.element}, Level.WARNING);
                    break;

                case MISSING_ENDTAG_BEFORE :
                    printMessage(
                        code,
                        lexer,
                        "missing_endtag_before",
                        new Object[]{element.element, getTagName(node)},
                        Level.WARNING);
                    break;

                case DISCARDING_UNEXPECTED :
                    if (lexer.badForm == 0)
                    {
                        // the case for when this is an error not a warning, is handled later
                        printMessage(
                            code,
                            lexer,
                            "discarding_unexpected",
                            new Object[]{getTagName(node)},
                            Level.WARNING);
                    }
                    break;

                case NESTED_EMPHASIS :
                    printMessage(code, lexer, "nested_emphasis", new Object[]{getTagName(node)}, Level.INFO);
                    break;

                case COERCE_TO_ENDTAG :
                    printMessage(code, lexer, "coerce_to_endtag", new Object[]{element.element}, Level.INFO);
                    break;

                case NON_MATCHING_ENDTAG :
                    printMessage(
                        code,
                        lexer,
                        "non_matching_endtag",
                        new Object[]{getTagName(node), element.element},
                        Level.WARNING);
                    break;

                case TAG_NOT_ALLOWED_IN :
                    printMessage(
                        code,
                        lexer,
                        "tag_not_allowed_in",
                        new Object[]{getTagName(node), element.element},
                        Level.WARNING);
                    break;

                case DOCTYPE_AFTER_TAGS :
                    printMessage(code, lexer, "doctype_after_tags", null, Level.WARNING);
                    break;

                case MISSING_STARTTAG :
                    printMessage(code, lexer, "missing_starttag", new Object[]{node.element}, Level.WARNING);
                    break;

                case UNEXPECTED_ENDTAG :
                    if (element != null)
                    {
                        printMessage(
                            code,
                            lexer,
                            "unexpected_endtag_in",
                            new Object[]{node.element, element.element},
                            Level.WARNING);
                    }
                    else
                    {
                        printMessage(code, lexer, "unexpected_endtag", new Object[]{node.element}, Level.WARNING);
                    }
                    break;

                case TOO_MANY_ELEMENTS :
                    if (element != null)
                    {
                        printMessage(
                            code,
                            lexer,
                            "too_many_elements_in",
                            new Object[]{node.element, element.element},
                            Level.WARNING);
                    }
                    else
                    {
                        printMessage(code, lexer, "too_many_elements", new Object[]{node.element}, Level.WARNING);
                    }
                    break;

                case USING_BR_INPLACE_OF :
                    printMessage(code, lexer, "using_br_inplace_of", new Object[]{getTagName(node)}, Level.WARNING);
                    break;

                case INSERTING_TAG :
                    printMessage(code, lexer, "inserting_tag", new Object[]{node.element}, Level.WARNING);
                    break;

                case CANT_BE_NESTED :
                    printMessage(code, lexer, "cant_be_nested", new Object[]{getTagName(node)}, Level.WARNING);
                    break;

                case PROPRIETARY_ELEMENT :
                    printMessage(code, lexer, "proprietary_element", new Object[]{getTagName(node)}, Level.WARNING);

                    if (node.tag == tt.tagLayer)
                    {
                        lexer.badLayout |= USING_LAYER;
                    }
                    else if (node.tag == tt.tagSpacer)
                    {
                        lexer.badLayout |= USING_SPACER;
                    }
                    else if (node.tag == tt.tagNobr)
                    {
                        lexer.badLayout |= USING_NOBR;
                    }
                    break;

                case OBSOLETE_ELEMENT :
                    if (element.tag != null && (element.tag.model & Dict.CM_OBSOLETE) != 0)
                    {
                        printMessage(code, lexer, "obsolete_element", new Object[]{
                            getTagName(element),
                            getTagName(node)}, Level.WARNING);
                    }
                    else
                    {
                        printMessage(code, lexer, "replacing_element", new Object[]{
                            getTagName(element),
                            getTagName(node)}, Level.WARNING);
                    }
                    break;

                case UNESCAPED_ELEMENT :
                    printMessage(code, lexer, "unescaped_element", new Object[]{getTagName(element)}, Level.WARNING);
                    break;

                case TRIM_EMPTY_ELEMENT :
                    printMessage(code, lexer, "trim_empty_element", new Object[]{getTagName(element)}, Level.WARNING);
                    break;

                case MISSING_TITLE_ELEMENT :
                    printMessage(code, lexer, "missing_title_element", null, Level.WARNING);
                    break;

                case ILLEGAL_NESTING :
                    printMessage(code, lexer, "illegal_nesting", new Object[]{getTagName(element)}, Level.WARNING);
                    break;

                case NOFRAMES_CONTENT :
                    printMessage(code, lexer, "noframes_content", new Object[]{getTagName(node)}, Level.WARNING);
                    break;

                case INCONSISTENT_VERSION :
                    printMessage(code, lexer, "inconsistent_version", null, Level.WARNING);
                    break;

                case MALFORMED_DOCTYPE :
                    printMessage(code, lexer, "malformed_doctype", null, Level.WARNING);
                    break;

                case CONTENT_AFTER_BODY :
                    printMessage(code, lexer, "content_after_body", null, Level.WARNING);
                    break;

                case MALFORMED_COMMENT :
                    printMessage(code, lexer, "malformed_comment", null, Level.WARNING);
                    break;

                case BAD_COMMENT_CHARS :
                    printMessage(code, lexer, "bad_comment_chars", null, Level.WARNING);
                    break;

                case BAD_XML_COMMENT :
                    printMessage(code, lexer, "bad_xml_comment", null, Level.WARNING);
                    break;

                case BAD_CDATA_CONTENT :
                    printMessage(code, lexer, "bad_cdata_content", null, Level.WARNING);
                    break;

                case INCONSISTENT_NAMESPACE :
                    printMessage(code, lexer, "inconsistent_namespace", null, Level.WARNING);
                    break;

                case DTYPE_NOT_UPPER_CASE :
                    printMessage(code, lexer, "dtype_not_upper_case", null, Level.WARNING);
                    break;

                case UNEXPECTED_END_OF_FILE :
                    // on end of file adjust reported position to end of input
                    lexer.lines = lexer.in.getCurline();
                    lexer.columns = lexer.in.getCurcol();
                    printMessage(
                        code,
                        lexer,
                        "unexpected_end_of_file",
                        new Object[]{getTagName(element)},
                        Level.WARNING);
                    break;

                case NESTED_QUOTATION :
                    printMessage(code, lexer, "nested_quotation", null, Level.WARNING);
                    break;

                case ELEMENT_NOT_EMPTY :
                    printMessage(code, lexer, "element_not_empty", new Object[]{getTagName(element)}, Level.WARNING);
                    break;

                case MISSING_DOCTYPE :
                    printMessage(code, lexer, "missing_doctype", null, Level.WARNING);
                    break;

                default :
                    break;
            }
        }

        if ((code == DISCARDING_UNEXPECTED) && lexer.badForm != 0)
        {
            // the case for when this is a warning not an error, is handled earlier
            printMessage(code, lexer, "discarding_unexpected", new Object[]{getTagName(node)}, Level.ERROR);
        }

    }

    /**
     * Prints errors.
     * @param lexer Lexer
     * @param element parent/missing tag
     * @param node current tag
     * @param code error code
     */
    public void error(Lexer lexer, Node element, Node node, short code)
    {
        lexer.errors++;

        // keep quiet after <showErrors> errors
        if (lexer.errors > lexer.configuration.showErrors)
        {
            return;
        }

        if (code == SUSPECTED_MISSING_QUOTE)
        {
            printMessage(code, lexer, "suspected_missing_quote", null, Level.ERROR);
        }
        else if (code == DUPLICATE_FRAMESET)
        {
            printMessage(code, lexer, "duplicate_frameset", null, Level.ERROR);
        }
        else if (code == UNKNOWN_ELEMENT)
        {
            printMessage(code, lexer, "unknown_element", new Object[]{getTagName(node)}, Level.ERROR);
        }
        else if (code == UNEXPECTED_ENDTAG)
        {
            if (element != null)
            {
                printMessage(
                    code,
                    lexer,
                    "unexpected_endtag_in",
                    new Object[]{node.element, element.element},
                    Level.ERROR);
            }
            else
            {
                printMessage(code, lexer, "unexpected_endtag", new Object[]{node.element}, Level.ERROR);
            }
        }
    }

    /**
     * Prints error summary.
     * @param lexer Lexer
     */
    public void errorSummary(Lexer lexer)
    {
        // adjust badAccess to that its null if frames are ok
        if ((lexer.badAccess & (USING_FRAMES | USING_NOFRAMES)) != 0)
        {
            if (!(((lexer.badAccess & USING_FRAMES) != 0) && ((lexer.badAccess & USING_NOFRAMES) == 0)))
            {
                lexer.badAccess &= ~(USING_FRAMES | USING_NOFRAMES);
            }
        }
        if (lexer.badChars != 0)
        {
            if ((lexer.badChars & VENDOR_SPECIFIC_CHARS) != 0)
            {
                int encodingChoiche = 0;

                if ("Cp1252".equals(lexer.configuration.getInCharEncodingName()))
                {
                    encodingChoiche = 1;
                }
                else if ("MacRoman".equals(lexer.configuration.getInCharEncodingName()))
                {
                    encodingChoiche = 2;
                }

                printMessage(VENDOR_SPECIFIC_CHARS, lexer, "vendor_specific_chars_summary", new Object[]{new Integer(
                    encodingChoiche)}, Level.SUMMARY);
            }

            if ((lexer.badChars & INVALID_SGML_CHARS) != 0 || (lexer.badChars & INVALID_NCR) != 0)
            {
                int encodingChoiche = 0;

                if ("Cp1252".equals(lexer.configuration.getInCharEncodingName()))
                {
                    encodingChoiche = 1;
                }
                else if ("MacRoman".equals(lexer.configuration.getInCharEncodingName()))
                {
                    encodingChoiche = 2;
                }

                printMessage(INVALID_SGML_CHARS, lexer, "invalid_sgml_chars_summary", new Object[]{new Integer(
                    encodingChoiche)}, Level.SUMMARY);
            }

            if ((lexer.badChars & INVALID_UTF8) != 0)
            {
                printMessage(INVALID_UTF8, lexer, "invalid_utf8_summary", null, Level.SUMMARY);
            }

            if ((lexer.badChars & INVALID_UTF16) != 0)
            {
                printMessage(INVALID_UTF16, lexer, "invalid_utf16_summary", null, Level.SUMMARY);
            }

            if ((lexer.badChars & INVALID_URI) != 0)
            {
                printMessage(INVALID_URI, lexer, "invaliduri_summary", null, Level.SUMMARY);
            }
        }

        if (lexer.badForm != 0)
        {
            printMessage(BADFORM_SUMMARY, lexer, "badform_summary", null, Level.SUMMARY);
        }

        if (lexer.badAccess != 0)
        {
            if ((lexer.badAccess & MISSING_SUMMARY) != 0)
            {
                printMessage(MISSING_SUMMARY, lexer, "badaccess_missing_summary", null, Level.SUMMARY);
            }

            if ((lexer.badAccess & MISSING_IMAGE_ALT) != 0)
            {
                printMessage(MISSING_IMAGE_ALT, lexer, "badaccess_missing_image_alt", null, Level.SUMMARY);
            }

            if ((lexer.badAccess & MISSING_IMAGE_MAP) != 0)
            {
                printMessage(MISSING_IMAGE_MAP, lexer, "badaccess_missing_image_map", null, Level.SUMMARY);
            }

            if ((lexer.badAccess & MISSING_LINK_ALT) != 0)
            {
                printMessage(MISSING_LINK_ALT, lexer, "badaccess_missing_link_alt", null, Level.SUMMARY);
            }

            if (((lexer.badAccess & USING_FRAMES) != 0) && ((lexer.badAccess & USING_NOFRAMES) == 0))
            {
                printMessage(USING_FRAMES, lexer, "badaccess_frames", null, Level.SUMMARY);
            }

            printMessage(BADACCESS_SUMMARY, lexer, "badaccess_summary", new Object[]{ACCESS_URL}, Level.SUMMARY);
        }

        if (lexer.badLayout != 0)
        {
            if ((lexer.badLayout & USING_LAYER) != 0)
            {
                printMessage(USING_LAYER, lexer, "badlayout_using_layer", null, Level.SUMMARY);
            }

            if ((lexer.badLayout & USING_SPACER) != 0)
            {
                printMessage(USING_SPACER, lexer, "badlayout_using_spacer", null, Level.SUMMARY);
            }

            if ((lexer.badLayout & USING_FONT) != 0)
            {
                printMessage(USING_FONT, lexer, "badlayout_using_font", null, Level.SUMMARY);
            }

            if ((lexer.badLayout & USING_NOBR) != 0)
            {
                printMessage(USING_NOBR, lexer, "badlayout_using_nobr", null, Level.SUMMARY);
            }

            if ((lexer.badLayout & USING_BODY) != 0)
            {
                printMessage(USING_BODY, lexer, "badlayout_using_body", null, Level.SUMMARY);
            }
        }
    }

    /**
     * Prints the "unknown option" message.
     * @param errout PrintWriter
     * @param c invalid option char
     */
    public void unknownOption(PrintWriter errout, char c)
    {
        printMessage(errout, "unrecognized_option", new Object[]{new String(new char[]{c})}, Level.ERROR);
    }

    /**
     * Prints the "unknown file" message.
     * @param errout PrintWriter
     * @param file invalid file name
     */
    public void unknownFile(PrintWriter errout, String file)
    {
        printMessage(errout, "unknown_file", new Object[]{"Tidy", file}, Level.ERROR);
    }

    /**
     * Prints the "needs author intervention" message.
     * @param errout PrintWriter
     */
    public void needsAuthorIntervention(PrintWriter errout)
    {
        printMessage(errout, "needs_author_intervention", null, Level.SUMMARY);
    }

    /**
     * Prints the "missing body" message.
     * @param errout PrintWriter
     */
    public void missingBody(PrintWriter errout)
    {
        printMessage(errout, "missing_body", null, Level.ERROR);
    }

    /**
     * Prints the number of generated slides.
     * @param errout PrintWriter
     * @param count slides count
     */
    public void reportNumberOfSlides(PrintWriter errout, int count)
    {
        printMessage(errout, "slides_found", new Object[]{new Integer(count)}, Level.SUMMARY);
    }

    /**
     * Prints tidy general info.
     * @param errout PrintWriter
     */
    public void generalInfo(PrintWriter errout)
    {
        printMessage(errout, "general_info", null, Level.SUMMARY);
    }

    /**
     * Sets the current file name.
     * @param filename current file.
     */
    public void setFilename(String filename)
    {
        this.currentFile = filename; // for use with Gnu Emacs
    }

    /**
     * Prints information for html version in input file.
     * @param errout PrintWriter
     * @param lexer Lexer
     * @param filename file name
     * @param doctype doctype Node
     */
    public void reportVersion(PrintWriter errout, Lexer lexer, String filename, Node doctype)
    {
        int i, c;
        int state = 0;
        String vers = lexer.htmlVersionName();
        int[] cc = new int[1];

        // adjust reported position to first line
        lexer.lines = 1;
        lexer.columns = 1;

        if (doctype != null)
        {

            StringBuffer doctypeBuffer = new StringBuffer();
            for (i = doctype.start; i < doctype.end; ++i)
            {
                c = doctype.textarray[i];

                // look for UTF-8 multibyte character
                if (c < 0)
                {
                    i += PPrint.get_utf8_jtidy(doctype.textarray, i, cc);
                    c = cc[0];
                }

                if (c == '"')
                {
                    ++state;
                }
                else if (state == 1)
                {
                    doctypeBuffer.append((char) c);
                }
            }

            printMessage(
                DOCTYPE_GIVEN_SUMMARY,
                lexer,
                "doctype_given",
                new Object[]{filename, doctypeBuffer},
                Level.SUMMARY);
        }

        printMessage(REPORT_VERSION_SUMMARY, lexer, "report_version", new Object[]{
            filename,
            (vers != null ? vers : "HTML proprietary")}, Level.SUMMARY);
    }

    /**
     * Prints the number of error/warnings found.
     * @param errout PrintWriter
     * @param lexer Lexer
     */
    public void reportNumWarnings(PrintWriter errout, Lexer lexer)
    {
        if (lexer.warnings > 0 || lexer.errors > 0)
        {
            printMessage(
                errout,
                "num_warnings",
                new Object[]{new Integer(lexer.warnings), new Integer(lexer.errors)},
                Level.SUMMARY);
        }
        else
        {
            printMessage(errout, "no_warnings", null, Level.SUMMARY);
        }
    }

    /**
     * Prints tidy help.
     * @param out PrintWriter
     */
    public void helpText(PrintWriter out)
    {
        printMessage(out, "help_text", new Object[]{"Tidy", RELEASE_DATE_STRING}, Level.SUMMARY);
    }

    /**
     * Prints the "bad tree" message.
     * @param errout PrintWriter
     */
    public void badTree(PrintWriter errout)
    {
        printMessage(errout, "bad_tree", null, Level.ERROR);
    }

    /**
     * Adds a message listener.
     * @param listener TidyMessageListener
     */
    public void addMessageListener(TidyMessageListener listener)
    {
        this.listener = listener;
    }
}