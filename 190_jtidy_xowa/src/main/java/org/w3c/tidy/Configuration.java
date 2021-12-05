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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Read configuration file and manage configuration properties. Configuration files associate a property name with a
 * value. The format is that of a Java .properties file.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 817 $ ($Author: steffenyount $)
 */
public class Configuration implements Serializable
{

    /**
     * character encoding = RAW.
     * @deprecated use <code>Tidy.setRawOut(true)</code> for raw output
     */
    public static final int RAW = 0;

    /**
     * character encoding = ASCII.
     * @deprecated
     */
    public static final int ASCII = 1;

    /**
     * character encoding = LATIN1.
     * @deprecated
     */
    public static final int LATIN1 = 2;

    /**
     * character encoding = UTF8.
     * @deprecated
     */
    public static final int UTF8 = 3;

    /**
     * character encoding = ISO2022.
     * @deprecated
     */
    public static final int ISO2022 = 4;

    /**
     * character encoding = MACROMAN.
     * @deprecated
     */
    public static final int MACROMAN = 5;

    /**
     * character encoding = UTF16LE.
     * @deprecated
     */
    public static final int UTF16LE = 6;

    /**
     * character encoding = UTF16BE.
     * @deprecated
     */
    public static final int UTF16BE = 7;

    /**
     * character encoding = UTF16.
     * @deprecated
     */
    public static final int UTF16 = 8;

    /**
     * character encoding = WIN1252.
     * @deprecated
     */
    public static final int WIN1252 = 9;

    /**
     * character encoding = BIG5.
     * @deprecated
     */
    public static final int BIG5 = 10;

    /**
     * character encoding = SHIFTJIS.
     * @deprecated
     */
    public static final int SHIFTJIS = 11;

    /**
     * Convert from deprecated tidy encoding constant to standard java encoding name.
     */
    private final String[] ENCODING_NAMES = new String[]{"raw", // rawOut, it will not be mapped to a java encoding
        "ASCII",
        "ISO8859_1",
        "UTF8",
        "JIS",
        "MacRoman",
        "UnicodeLittle",
        "UnicodeBig",
        "Unicode",
        "Cp1252",
        "Big5",
        "SJIS"};

    /**
     * treatment of doctype: omit.
     * @todo should be an enumeration DocTypeMode
     */
    public static final int DOCTYPE_OMIT = 0;

    /**
     * treatment of doctype: auto.
     */
    public static final int DOCTYPE_AUTO = 1;

    /**
     * treatment of doctype: strict.
     */
    public static final int DOCTYPE_STRICT = 2;

    /**
     * treatment of doctype: loose.
     */
    public static final int DOCTYPE_LOOSE = 3;

    /**
     * treatment of doctype: user.
     */
    public static final int DOCTYPE_USER = 4;

    /**
     * Keep last duplicate attribute.
     * @todo should be an enumeration DupAttrMode
     */
    public static final int KEEP_LAST = 0;

    /**
     * Keep first duplicate attribute.
     */
    public static final int KEEP_FIRST = 1;

    /**
     * Map containg all the valid configuration options and the related parser. Tag entry contains String(option
     * name)-Flag instance.
     */
    private static final Map OPTIONS = new HashMap();

    /**
     * serial version UID for this class.
     */
    private static final long serialVersionUID = -4955155037138560842L;

    static
    {
        addConfigOption(new Flag("indent-spaces", "spaces", ParsePropertyImpl.INT));
        addConfigOption(new Flag("wrap", "wraplen", ParsePropertyImpl.INT));
        addConfigOption(new Flag("show-errors", "showErrors", ParsePropertyImpl.INT));
        addConfigOption(new Flag("tab-size", "tabsize", ParsePropertyImpl.INT));

        addConfigOption(new Flag("wrap-attributes", "wrapAttVals", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("wrap-script-literals", "wrapScriptlets", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("wrap-sections", "wrapSection", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("wrap-asp", "wrapAsp", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("wrap-jste", "wrapJste", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("wrap-php", "wrapPhp", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("literal-attributes", "literalAttribs", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("show-body-only", "bodyOnly", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("fix-uri", "fixUri", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("lower-literals", "lowerLiterals", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("hide-comments", "hideComments", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("indent-cdata", "indentCdata", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("force-output", "forceOutput", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("ascii-chars", "asciiChars", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("join-classes", "joinClasses", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("join-styles", "joinStyles", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("escape-cdata", "escapeCdata", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("replace-color", "replaceColor", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("quiet", "quiet", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("tidy-mark", "tidyMark", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("indent-attributes", "indentAttributes", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("hide-endtags", "hideEndTags", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("input-xml", "xmlTags", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("output-xml", "xmlOut", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("output-html", "htmlOut", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("output-xhtml", "xHTML", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("add-xml-pi", "xmlPi", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("add-xml-decl", "xmlPi", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("assume-xml-procins", "xmlPIs", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("uppercase-tags", "upperCaseTags", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("uppercase-attributes", "upperCaseAttrs", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("bare", "makeBare", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("clean", "makeClean", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("logical-emphasis", "logicalEmphasis", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("word-2000", "word2000", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("drop-empty-paras", "dropEmptyParas", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("drop-font-tags", "dropFontTags", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("drop-proprietary-attributes", "dropProprietaryAttributes", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("enclose-text", "encloseBodyText", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("enclose-block-text", "encloseBlockText", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("add-xml-space", "xmlSpace", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("fix-bad-comments", "fixComments", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("split", "burstSlides", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("break-before-br", "breakBeforeBR", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("numeric-entities", "numEntities", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("quote-marks", "quoteMarks", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("quote-nbsp", "quoteNbsp", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("quote-ampersand", "quoteAmpersand", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("write-back", "writeback", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("keep-time", "keepFileTimes", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("show-warnings", "showWarnings", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("ncr", "ncr", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("fix-backslash", "fixBackslash", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("gnu-emacs", "emacs", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("only-errors", "onlyErrors", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("output-raw", "rawOut", ParsePropertyImpl.BOOL));
        addConfigOption(new Flag("trim-empty-elements", "trimEmpty", ParsePropertyImpl.BOOL));

        addConfigOption(new Flag("markup", "onlyErrors", ParsePropertyImpl.INVBOOL));

        addConfigOption(new Flag("char-encoding", null, ParsePropertyImpl.CHAR_ENCODING));
        addConfigOption(new Flag("input-encoding", null, ParsePropertyImpl.CHAR_ENCODING));
        addConfigOption(new Flag("output-encoding", null, ParsePropertyImpl.CHAR_ENCODING));

        addConfigOption(new Flag("error-file", "errfile", ParsePropertyImpl.NAME));
        addConfigOption(new Flag("slide-style", "slidestyle", ParsePropertyImpl.NAME));
        addConfigOption(new Flag("language", "language", ParsePropertyImpl.NAME));

        addConfigOption(new Flag("new-inline-tags", null, ParsePropertyImpl.TAGNAMES));
        addConfigOption(new Flag("new-blocklevel-tags", null, ParsePropertyImpl.TAGNAMES));
        addConfigOption(new Flag("new-empty-tags", null, ParsePropertyImpl.TAGNAMES));
        addConfigOption(new Flag("new-pre-tags", null, ParsePropertyImpl.TAGNAMES));

        addConfigOption(new Flag("doctype", "docTypeStr", ParsePropertyImpl.DOCTYPE));

        addConfigOption(new Flag("repeated-attributes", "duplicateAttrs", ParsePropertyImpl.REPEATED_ATTRIBUTES));

        addConfigOption(new Flag("alt-text", "altText", ParsePropertyImpl.STRING));

        addConfigOption(new Flag("indent", "indentContent", ParsePropertyImpl.INDENT));

        addConfigOption(new Flag("css-prefix", "cssPrefix", ParsePropertyImpl.CSS1SELECTOR));

        addConfigOption(new Flag("newline", null, ParsePropertyImpl.NEWLINE));
    }

    /**
     * default indentation.
     */
    protected int spaces = 2;

    /**
     * default wrap margin (68).
     */
    protected int wraplen = 68;

    /**
     * default tab size (8).
     */
    protected int tabsize = 8;

    /**
     * see doctype property.
     */
    protected int docTypeMode = DOCTYPE_AUTO;

    /**
     * Keep first or last duplicate attribute.
     */
    protected int duplicateAttrs = KEEP_LAST;

    /**
     * default text for alt attribute.
     */
    protected String altText;

    /**
     * style sheet for slides.
     * @deprecated does nothing
     */
    protected String slidestyle;

    /**
     * RJ language property.
     */
    protected String language; // #431953

    /**
     * user specified doctype.
     */
    protected String docTypeStr;

    /**
     * file name to write errors to.
     */
    protected String errfile;

    /**
     * if true then output tidied markup.
     */
    protected boolean writeback;

    /**
     * if true normal output is suppressed.
     */
    protected boolean onlyErrors;

    /**
     * however errors are always shown.
     */
    protected boolean showWarnings = true;

    /**
     * no 'Parsing X', guessed DTD or summary.
     */
    protected boolean quiet;

    /**
     * indent content of appropriate tags.
     */
    protected boolean indentContent;

    /**
     * does text/block level content effect indentation.
     */
    protected boolean smartIndent;

    /**
     * suppress optional end tags.
     */
    protected boolean hideEndTags;

    /**
     * treat input as XML.
     */
    protected boolean xmlTags;

    /**
     * create output as XML.
     */
    protected boolean xmlOut;

    /**
     * output extensible HTML.
     */
    protected boolean xHTML;

    /**
     * output plain-old HTML, even for XHTML input. Yes means set explicitly.
     */
    protected boolean htmlOut;

    /**
     * add <code>&lt;?xml?&gt;</code> for XML docs.
     */
    protected boolean xmlPi;

    /**
     * output tags in upper not lower case.
     */
    protected boolean upperCaseTags;

    /**
     * output attributes in upper not lower case.
     */
    protected boolean upperCaseAttrs;

    /**
     * remove presentational clutter.
     */
    protected boolean makeClean;

    /**
     * Make bare HTML: remove Microsoft cruft.
     */
    protected boolean makeBare;

    /**
     * replace i by em and b by strong.
     */
    protected boolean logicalEmphasis;

    /**
     * discard presentation tags.
     */
    protected boolean dropFontTags;

    /**
     * discard proprietary attributes.
     */
    protected boolean dropProprietaryAttributes;

    /**
     * discard empty p elements.
     */
    protected boolean dropEmptyParas = true;

    /**
     * fix comments with adjacent hyphens.
     */
    protected boolean fixComments = true;

    /**
     * trim empty elements.
     */
    protected boolean trimEmpty = true;

    /**
     * o/p newline before br or not?
     */
    protected boolean breakBeforeBR;

    /**
     * create slides on each h2 element.
     */
    protected boolean burstSlides;

    /**
     * use numeric entities.
     */
    protected boolean numEntities;

    /**
     * output " marks as &quot;.
     */
    protected boolean quoteMarks;

    /**
     * output non-breaking space as entity.
     */
    protected boolean quoteNbsp = true;

    /**
     * output naked ampersand as &amp;.
     */
    protected boolean quoteAmpersand = true;

    /**
     * wrap within attribute values.
     */
    protected boolean wrapAttVals;

    /**
     * wrap within JavaScript string literals.
     */
    protected boolean wrapScriptlets;

    /**
     * wrap within CDATA section tags.
     */
    protected boolean wrapSection = true;

    /**
     * wrap within ASP pseudo elements.
     */
    protected boolean wrapAsp = true;

    /**
     * wrap within JSTE pseudo elements.
     */
    protected boolean wrapJste = true;

    /**
     * wrap within PHP pseudo elements.
     */
    protected boolean wrapPhp = true;

    /**
     * fix URLs by replacing \ with /.
     */
    protected boolean fixBackslash = true;

    /**
     * newline+indent before each attribute.
     */
    protected boolean indentAttributes;

    /**
     * If set to yes PIs must end with <code>?&gt;</code>.
     */
    protected boolean xmlPIs;

    /**
     * if set to yes adds xml:space attr as needed.
     */
    protected boolean xmlSpace;

    /**
     * if yes text at body is wrapped in p's.
     */
    protected boolean encloseBodyText;

    /**
     * if yes text in blocks is wrapped in p's.
     */
    protected boolean encloseBlockText;

    /**
     * if yes last modied time is preserved.
     */
    protected boolean keepFileTimes = true;

    /**
     * draconian cleaning for Word2000.
     */
    protected boolean word2000;

    /**
     * add meta element indicating tidied doc.
     */
    protected boolean tidyMark = true;

    /**
     * if true format error output for GNU Emacs.
     */
    protected boolean emacs;

    /**
     * if true attributes may use newlines.
     */
    protected boolean literalAttribs;

    /**
     * output BODY content only.
     */
    protected boolean bodyOnly;

    /**
     * properly escape URLs.
     */
    protected boolean fixUri = true;

    /**
     * folds known attribute values to lower case.
     */
    protected boolean lowerLiterals = true;

    /**
     * replace hex color attribute values with names.
     */
    protected boolean replaceColor;

    /**
     * hides all (real) comments in output.
     */
    protected boolean hideComments;

    /**
     * indent CDATA sections.
     */
    protected boolean indentCdata;

    /**
     * output document even if errors were found.
     */
    protected boolean forceOutput;

    /**
     * number of errors to put out.
     */
    protected int showErrors = 6;

    /**
     * convert quotes and dashes to nearest ASCII char.
     */
    protected boolean asciiChars = true;

    /**
     * join multiple class attributes.
     */
    protected boolean joinClasses;

    /**
     * join multiple style attributes.
     */
    protected boolean joinStyles = true;

    /**
     * replace CDATA sections with escaped text.
     */
    protected boolean escapeCdata = true;

    /**
     * allow numeric character references.
     */
    protected boolean ncr = true; // #431953

    /**
     * CSS class naming for -clean option.
     */
    protected String cssPrefix;

    /**
     * char encoding used when replacing illegal SGML chars, regardless of specified encoding.
     */
    protected String replacementCharEncoding = "WIN1252"; // by default

    /**
     * TagTable associated with this Configuration.
     */
    protected TagTable tt;

    /**
     * Report instance. Used for messages.
     */
    protected Report report;

    /**
     * track what types of tags user has defined to eliminate unnecessary searches.
     */
    protected int definedTags;

    /**
     * bytes for the newline marker.
     */
    protected char[] newline = (System.getProperty("line.separator")).toCharArray();

    /**
     * Input character encoding (defaults to "ISO8859_1").
     */
    private String inCharEncoding = "ISO8859_1";

    /**
     * Output character encoding (defaults to "ASCII").
     */
    private String outCharEncoding = "ASCII";

    /**
     * Avoid mapping values > 127 to entities.
     */
    protected boolean rawOut;

    /**
     * configuration properties.
     */
    private transient Properties properties = new Properties();

    /**
     * Instantiates a new Configuration. This method should be called by Tidy only.
     * @param report Report instance
     */
    protected Configuration(Report report)
    {
        this.report = report;
    }

    /**
     * adds a config option to the map.
     * @param flag configuration options added
     */
    private static void addConfigOption(Flag flag)
    {
        OPTIONS.put(flag.getName(), flag);
    }

    /**
     * adds configuration Properties.
     * @param p Properties
     */
    public void addProps(Properties p)
    {
        Enumeration propEnum = p.propertyNames();
        while (propEnum.hasMoreElements())
        {
            String key = (String) propEnum.nextElement();
            String value = p.getProperty(key);
            properties.put(key, value);
        }
        parseProps();
    }

    /**
     * Parses a property file.
     * @param filename file name
     */
    public void parseFile(String filename)
    {
        try
        {
            properties.load(new FileInputStream(filename));
        }
        catch (IOException e)
        {
            System.err.println(filename + " " + e.toString());
            return;
        }
        parseProps();
    }

    /**
     * Is the given String a valid configuration flag?
     * @param name configuration parameter name
     * @return <code>true</code> if the given String is a valid config option
     */
    public static boolean isKnownOption(String name)
    {
        return name != null && OPTIONS.containsKey(name);
    }

    /**
     * Parses the configuration properties file.
     */
    private void parseProps()
    {
        Iterator iterator = properties.keySet().iterator();

        while (iterator.hasNext())
        {
            String key = (String) iterator.next();
            Flag flag = (Flag) OPTIONS.get(key);
            if (flag == null)
            {
                report.unknownOption(key);
                continue;
            }

            String stringValue = properties.getProperty(key);
            Object value = flag.getParser().parse(stringValue, key, this);
            if (flag.getLocation() != null)
            {
                try
                {
                    flag.getLocation().set(this, value);
                }
                catch (IllegalArgumentException e)
                {
                    throw new RuntimeException("IllegalArgumentException during config initialization for field "
                        + key
                        + "with value ["
                        + value
                        + "]: "
                        + e.getMessage());
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException("IllegalArgumentException during config initialization for field "
                        + key
                        + "with value ["
                        + value
                        + "]: "
                        + e.getMessage());
                }
            }
        }
    }

    /**
     * Ensure that config is self consistent.
     */
    public void adjust()
    {
        if (encloseBlockText)
        {
            encloseBodyText = true;
        }

        // avoid the need to set IndentContent when SmartIndent is set
        if (smartIndent)
        {
            indentContent = true;
        }

        // disable wrapping
        if (wraplen == 0)
        {
            wraplen = 0x7FFFFFFF;
        }

        // Word 2000 needs o:p to be declared as inline
        if (word2000)
        {
            definedTags |= Dict.TAGTYPE_INLINE;
            tt.defineTag(Dict.TAGTYPE_INLINE, "o:p");
        }

        // #480701 disable XHTML output flag if both output-xhtml and xml are set
        if (xmlTags)
        {
            xHTML = false;
        }

        // XHTML is written in lower case
        if (xHTML)
        {
            xmlOut = true;
            upperCaseTags = false;
            upperCaseAttrs = false;
        }

        // if XML in, then XML out
        if (xmlTags)
        {
            xmlOut = true;
            xmlPIs = true;
        }

        // #427837 - fix by Dave Raggett 02 Jun 01
        // generate <?xml version="1.0" encoding="iso-8859-1"?> if the output character encoding is Latin-1 etc.
        if (!"UTF8".equals(getOutCharEncodingName()) && !"ASCII".equals(getOutCharEncodingName()) && xmlOut)
        {
            xmlPi = true;
        }

        // XML requires end tags
        if (xmlOut)
        {
            quoteAmpersand = true;
            hideEndTags = false;
        }
    }

    /**
     * prints available configuration options.
     * @param errout where to write
     * @param showActualConfiguration print actual configuration values
     */
    public void printConfigOptions(Writer errout, boolean showActualConfiguration)
    {
        String pad = "                                                                               ";
        try
        {
            errout.write("\nConfiguration File Settings:\n\n");

            if (showActualConfiguration)
            {
                errout.write("Name                        Type       Current Value\n");
            }
            else
            {
                errout.write("Name                        Type       Allowable values\n");
            }

            errout.write("=========================== =========  ========================================\n");

            Flag configItem;

            // sort configuration options
            List values = new ArrayList(OPTIONS.values());
            Collections.sort(values);

            Iterator iterator = values.iterator();

            while (iterator.hasNext())
            {
                configItem = (Flag) iterator.next();

                errout.write(configItem.getName());
                errout.write(pad, 0, 28 - configItem.getName().length());

                errout.write(configItem.getParser().getType());
                errout.write(pad, 0, 11 - configItem.getParser().getType().length());

                if (showActualConfiguration)
                {
                    Field field = configItem.getLocation();
                    Object actualValue = null;

                    if (field != null)
                    {
                        try
                        {
                            actualValue = field.get(this);
                        }
                        catch (IllegalArgumentException e1)
                        {
                            // should never happen
                            throw new RuntimeException("IllegalArgument when reading field " + field.getName());
                        }
                        catch (IllegalAccessException e1)
                        {
                            // should never happen
                            throw new RuntimeException("IllegalAccess when reading field " + field.getName());
                        }
                    }

                    errout.write(configItem.getParser().getFriendlyName(configItem.getName(), actualValue, this));
                }
                else
                {
                    errout.write(configItem.getParser().getOptionValues());
                }

                errout.write("\n");

            }
            errout.flush();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * A configuration option.
     */
    static class Flag implements Comparable
    {

        /**
         * option name.
         */
        private String name;

        /**
         * field name.
         */
        private String fieldName;

        /**
         * Field where the evaluated value is saved.
         */
        private Field location;

        /**
         * Parser for the configuration property.
         */
        private ParseProperty parser;

        /**
         * Instantiates a new Flag.
         * @param name option name
         * @param fieldName field name (can be null)
         * @param parser parser for property
         */
        Flag(String name, String fieldName, ParseProperty parser)
        {

            this.fieldName = fieldName;
            this.name = name;
            this.parser = parser;
        }

        /**
         * Getter for <code>location</code>.
         * @return Returns the location.
         */
        public Field getLocation()
        {
            // lazy initialization to speed up loading
            if (fieldName != null && this.location == null)
            {
                try
                {
                    this.location = Configuration.class.getDeclaredField(fieldName);
                }
                catch (NoSuchFieldException e)
                {
                    throw new RuntimeException("NoSuchField exception during config initialization for field "
                        + fieldName);
                }
                catch (SecurityException e)
                {
                    throw new RuntimeException("Security exception during config initialization for field "
                        + fieldName
                        + ": "
                        + e.getMessage());
                }
            }

            return this.location;
        }

        /**
         * Getter for <code>name</code>.
         * @return Returns the name.
         */
        public String getName()
        {
            return this.name;
        }

        /**
         * Getter for <code>parser</code>.
         * @return Returns the parser.
         */
        public ParseProperty getParser()
        {
            return this.parser;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object obj)
        {
            return this.name.equals(((Flag) obj).name);
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        public int hashCode()
        {
            // returning the hashCode of String, to be consistent with equals and compareTo
            return this.name.hashCode();
        }

        /**
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(Object o)
        {
            return this.name.compareTo(((Flag) o).name);
        }

    }

    /**
     * Getter for <code>inCharEncodingName</code>.
     * @return Returns the inCharEncodingName.
     */
    protected String getInCharEncodingName()
    {
        return this.inCharEncoding;
    }

    /**
     * Setter for <code>inCharEncodingName</code>.
     * @param encoding The inCharEncodingName to set.
     */
    protected void setInCharEncodingName(String encoding)
    {
        String javaEncoding = EncodingNameMapper.toJava(encoding);
        if (javaEncoding != null)
        {
            this.inCharEncoding = javaEncoding;
        }
    }

    /**
     * Getter for <code>outCharEncodingName</code>.
     * @return Returns the outCharEncodingName.
     */
    protected String getOutCharEncodingName()
    {
        return this.outCharEncoding;
    }

    /**
     * Setter for <code>outCharEncodingName</code>.
     * @param encoding The outCharEncodingName to set.
     */
    protected void setOutCharEncodingName(String encoding)
    {
        String javaEncoding = EncodingNameMapper.toJava(encoding);
        if (javaEncoding != null)
        {
            this.outCharEncoding = javaEncoding;
        }
    }

    /**
     * Setter for <code>inOutCharEncodingName</code>.
     * @param encoding The CharEncodingName to set.
     */
    protected void setInOutEncodingName(String encoding)
    {
        setInCharEncodingName(encoding);
        setOutCharEncodingName(encoding);
    }

    /**
     * Setter for <code>outCharEncoding</code>.
     * @param encoding The outCharEncoding to set.
     * @deprecated use setOutCharEncodingName(String)
     */
    protected void setOutCharEncoding(int encoding)
    {
        setOutCharEncodingName(convertCharEncoding(encoding));
    }

    /**
     * Setter for <code>inCharEncoding</code>.
     * @param encoding The inCharEncoding to set.
     * @deprecated use setInCharEncodingName(String)
     */
    protected void setInCharEncoding(int encoding)
    {
        setInCharEncodingName(convertCharEncoding(encoding));
    }

    /**
     * Convert a char encoding from the deprecated tidy constant to a standard java encoding name.
     * @param code encoding code
     * @return encoding name
     */
    protected String convertCharEncoding(int code)
    {
        if (code != 0 && code < ENCODING_NAMES.length)
        {
            return ENCODING_NAMES[code];
        }
        return null;
    }

}