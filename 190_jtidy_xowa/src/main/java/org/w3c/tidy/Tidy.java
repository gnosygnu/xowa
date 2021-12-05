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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * HTML parser and pretty printer.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 923 $ ($Author: aditsu $)
 */
public class Tidy implements Serializable
{

    /**
     * Serial Version UID to avoid problems during serialization.
     */
    static final long serialVersionUID = -2794371560623987718L;

    /**
     * Alias for configuration options accepted in command line.
     */
    private static final Map CMDLINE_ALIAS = new HashMap();

    static
    {
        CMDLINE_ALIAS.put("xml", "input-xml");
        CMDLINE_ALIAS.put("xml", "output-xhtml");
        CMDLINE_ALIAS.put("asxml", "output-xhtml");
        CMDLINE_ALIAS.put("ashtml", "output-html");
        CMDLINE_ALIAS.put("omit", "hide-endtags");
        CMDLINE_ALIAS.put("upper", "uppercase-tags");
        CMDLINE_ALIAS.put("raw", "output-raw");
        CMDLINE_ALIAS.put("numeric", "numeric-entities");
        CMDLINE_ALIAS.put("change", "write-back");
        CMDLINE_ALIAS.put("update", "write-back");
        CMDLINE_ALIAS.put("modify", "write-back");
        CMDLINE_ALIAS.put("errors", "only-errors");
        CMDLINE_ALIAS.put("slides", "split");
        CMDLINE_ALIAS.put("lang", "language");
        CMDLINE_ALIAS.put("w", "wrap");
        CMDLINE_ALIAS.put("file", "error-file");
        CMDLINE_ALIAS.put("f", "error-file");
    }

    /**
     * Error output stream.
     */
    private PrintWriter errout;

    private PrintWriter stderr;

    private Configuration configuration;

    private String inputStreamName = "InputStream";

    private int parseErrors;

    private int parseWarnings;

    private Report report;

    /**
     * Instantiates a new Tidy instance. It's reccomended that a new instance is used at each parsing.
     */
    public Tidy()
    {
        this.report = new Report();
        configuration = new Configuration(this.report);

        TagTable tt = new TagTable();
        tt.setConfiguration(configuration);
        configuration.tt = tt;

        configuration.errfile = null;
        stderr = new PrintWriter(System.err, true);
        errout = stderr;
    }

    /**
     * Returns the actual configuration
     * @return tidy configuration
     */
    public Configuration getConfiguration()
    {
        return configuration;
    }

    public PrintWriter getStderr()
    {
        return stderr;
    }

    /**
     * ParseErrors - the number of errors that occurred in the most recent parse operation.
     * @return number of errors that occurred in the most recent parse operation.
     */
    public int getParseErrors()
    {
        return parseErrors;
    }

    /**
     * ParseWarnings - the number of warnings that occurred in the most recent parse operation.
     * @return number of warnings that occurred in the most recent parse operation.
     */
    public int getParseWarnings()
    {
        return parseWarnings;
    }

    /**
     * InputStreamName - the name of the input stream (printed in the header information).
     * @param name input stream name
     */
    public void setInputStreamName(String name)
    {
        if (name != null)
        {
            inputStreamName = name;
        }
    }

    public String getInputStreamName()
    {
        return inputStreamName;
    }

    /**
     * Errout - the error output stream.
     * @return error output stream.
     */
    public PrintWriter getErrout()
    {
        return errout;
    }

    public void setErrout(PrintWriter out)
    {
        this.errout = out;
    }

    /**
     * Sets the configuration from a configuration file.
     * @param filename configuration file name/path.
     */
    public void setConfigurationFromFile(String filename)
    {
        configuration.parseFile(filename);
    }

    /**
     * Sets the configuration from a properties object.
     * @param props Properties object
     */
    public void setConfigurationFromProps(Properties props)
    {
        configuration.addProps(props);
    }

    /**
     * Creates an empty DOM Document.
     * @return a new org.w3c.dom.Document
     */
    public static org.w3c.dom.Document createEmptyDocument()
    {
        Node document = new Node(Node.ROOT_NODE, new byte[0], 0, 0);
        Node node = new Node(Node.START_TAG, new byte[0], 0, 0, "html", new TagTable());
        if (document != null && node != null)
        {
            document.insertNodeAtStart(node);
            return (org.w3c.dom.Document) document.getAdapter();
        }

        return null;
    }

    /**
     * Reads from the given input and returns the root Node. If out is non-null, pretty prints to out. Warning: caller
     * is responsible for calling close() on input and output after calling this method.
     * @param in input
     * @param out optional destination for pretty-printed document
     * @return parsed org.w3c.tidy.Node
     */
    public Node parse(InputStream in, OutputStream out)
    {

        StreamIn streamIn = StreamInFactory.getStreamIn(configuration, in);

        Out o = null;
        if (out != null)
        {
            o = OutFactory.getOut(this.configuration, out); // normal output stream
        }

        return parse(streamIn, o);
    }

    /**
     * Reads from the given input and returns the root Node. If out is non-null, pretty prints to out. Warning: caller
     * is responsible for calling close() on input and output after calling this method.
     * @param in input
     * @param out optional destination for pretty-printed document
     * @return parsed org.w3c.tidy.Node
     */
    public Node parse(Reader in, OutputStream out)
    {

        StreamIn streamIn = StreamInFactory.getStreamIn(configuration, in);

        Out o = null;
        if (out != null)
        {
            o = OutFactory.getOut(this.configuration, out); // normal output stream
        }

        return parse(streamIn, o);
    }

    /**
     * Reads from the given input and returns the root Node. If out is non-null, pretty prints to out. Warning: caller
     * is responsible for calling close() on input and output after calling this method.
     * @param in input
     * @param out optional destination for pretty-printed document
     * @return parsed org.w3c.tidy.Node
     */
    public Node parse(Reader in, Writer out)
    {
        StreamIn streamIn = StreamInFactory.getStreamIn(configuration, in);

        Out o = null;
        if (out != null)
        {
            o = OutFactory.getOut(this.configuration, out); // normal output stream
        }

        return parse(streamIn, o);
    }

    /**
     * Reads from the given input and returns the root Node. If out is non-null, pretty prints to out. Warning: caller
     * is responsible for calling close() on input and output after calling this method.
     * @param in input
     * @param out optional destination for pretty-printed document
     * @return parsed org.w3c.tidy.Node
     */
    public Node parse(InputStream in, Writer out)
    {
        StreamIn streamIn = StreamInFactory.getStreamIn(configuration, in);

        Out o = null;
        if (out != null)
        {
            o = OutFactory.getOut(this.configuration, out); // normal output stream
        }

        return parse(streamIn, o);
    }

    /**
     * Parses InputStream in and returns a DOM Document node. If out is non-null, pretty prints to OutputStream out.
     * @param in input stream
     * @param out optional output stream
     * @return parsed org.w3c.dom.Document
     */
    public org.w3c.dom.Document parseDOM(InputStream in, OutputStream out)
    {
        Node document = parse(in, out);
        if (document != null)
        {
            return (org.w3c.dom.Document) document.getAdapter();
        }
        return null;
    }

    public org.w3c.dom.Document parseDOM(Reader in, Writer out) {
        Node document = parse(in, out);
        if (document != null) {
            return (org.w3c.dom.Document) document.getAdapter();
        }
        return null;
    }

    /**
     * Pretty-prints a DOM Document. Must be an instance of org.w3c.tidy.DOMDocumentImpl. Caller is responsible for
     * closing the outputStream after calling this method.
     * @param doc org.w3c.dom.Document
     * @param out output stream
     */
    public void pprint(org.w3c.dom.Document doc, OutputStream out)
    {
        if (!(doc instanceof DOMDocumentImpl))
        {
            // @todo should we inform users that tidy can't print a generic Document or change the method signature?
            return;
        }

        pprint(((DOMDocumentImpl) doc).adaptee, out);
    }

    /**
     * Pretty-prints a DOM Node. Caller is responsible for closing the outputStream after calling this method.
     * @param node org.w3c.dom.Node. Must be an instance of org.w3c.tidy.DOMNodeImpl.
     * @param out output stream
     */
    public void pprint(org.w3c.dom.Node node, OutputStream out)
    {
        if (!(node instanceof DOMNodeImpl))
        {
            // @todo should we inform users than tidy can't print a generic Node or change the method signature?
            return;
        }

        pprint(((DOMNodeImpl) node).adaptee, out);
    }

    /**
     * Internal routine that actually does the parsing.
     * @param streamIn tidy StreamIn
     * @param o tidy Out
     * @return parsed org.w3c.tidy.Node
     */
    private Node parse(StreamIn streamIn, Out o)	// from tidylib.c|DocParseStream
    {
        Lexer lexer;
        Node document = null;
        Node doctype;
        PPrint pprint;

        if (errout == null)
        {
            return null;
        }

        // ensure config is self-consistent
        configuration.adjust();
        configuration.tt.freeAnchors();	// XOWA:xowa; free anchors, else will leak memory

        parseErrors = 0;
        parseWarnings = 0;

        lexer = new Lexer(streamIn, configuration, this.report);
        lexer.errout = errout;

        // store pointer to lexer in input stream to allow character encoding errors to be reported
        streamIn.setLexer(lexer);

        this.report.setFilename(inputStreamName); // #431895 - fix by Dave Bryan 04 Jan 01

        // Tidy doesn't alter the doctype for generic XML docs
        if (configuration.xmlTags)
        {
            document = ParserImpl.parseXMLDocument(lexer);
            if (!document.checkNodeIntegrity())
            {
                if (!configuration.quiet)
                {
                    report.badTree(errout);
                }
                return null;
            }
        }
        else
        {
            lexer.warnings = 0;

            document = ParserImpl.parseDocument(lexer);

            if (!document.checkNodeIntegrity())
            {
                if (!configuration.quiet)
                {
                    this.report.badTree(errout);
                }
                return null;
            }

            Clean cleaner = new Clean(configuration.tt);	// XOWA: rest of proc comes from tidyDocCleanAndRepair

            // simplifies <b><b> ... </b> ... </b> etc.
            cleaner.nestedEmphasis(document);

            // cleans up <dir> indented text </dir> etc.
            cleaner.list2BQ(document);
            cleaner.bQ2Div(document);

            // replaces i by em and b by strong
            if (configuration.logicalEmphasis)
                cleaner.emFromI(document);

            if (configuration.word2000 && cleaner.isWord2000(document))
            {
                // prune Word2000's <![if ...]> ... <![endif]>
                cleaner.dropSections(lexer, document);

                // drop style & class attributes and empty p, span elements
                cleaner.cleanWord2000(lexer, document);
                Node.dropEmptyElements(lexer, document);	// XOWA:tidy
            }

            // replaces presentational markup by style rules
            if (configuration.makeClean || configuration.dropFontTags)
                cleaner.cleanTree(lexer, document);

            // XOWA:tidy.todo; Reconcile http-equiv meta element with output encoding
            
            if (!document.checkNodeIntegrity())
            {
                this.report.badTree(errout);
                return null;
            }

            // remember given doctype for reporting
            doctype = document.findDocType();

            if (doctype != null)
            {
            	// XOWA:jtidy; differs from tidy which seems to create a new doctype node
                doctype = doctype.cloneNode(false);
            }

            if (document.content != null)
            {
            	// XOWA:tidy.todo; "If we had XHTML input but want HTML output"
            	
            	// XOWA:tidy.todo; significant differences; skipping as XHTML not supported by XOWA
                if (configuration.xHTML)
                {
                    lexer.setXHTMLDocType(document);
                }
                else
                {
                    lexer.fixDocType(document);
                }

                if (configuration.tidyMark)
                    lexer.addGenerator(document);
            }

            // ensure presence of initial <?XML version="1.0"?>
            if (configuration.xmlOut && configuration.xmlPi)
                lexer.fixXmlDecl(document);

            if (!configuration.quiet && document.content != null)
            {
                this.report.reportVersion(errout, lexer, inputStreamName, doctype);
            }
        }

        // XOWA:jtidy?; can't find analogue
        if (!configuration.quiet)
        {
            parseWarnings = lexer.warnings;
            parseErrors = lexer.errors;
            this.report.reportNumWarnings(errout, lexer);
        }

        if (!configuration.quiet && lexer.errors > 0 && !configuration.forceOutput)
        {
            this.report.needsAuthorIntervention(errout);
        }

        if (!configuration.onlyErrors && (lexer.errors == 0 || configuration.forceOutput))
        {
            if (configuration.burstSlides)
            {
                Node body;

                body = null;
                // remove doctype to avoid potential clash with markup introduced when bursting into slides

                // discard the document type
                doctype = document.findDocType();

                if (doctype != null)
                {
                    Node.discardElement(doctype);
                }

                /* slides use transitional features */
                lexer.versions |= Dict.VERS_HTML40_LOOSE;

                // and patch up doctype to match
                if (configuration.xHTML)
                {
                    lexer.setXHTMLDocType(document);
                }
                else
                {
                    lexer.fixDocType(document);
                }

                // find the body element which may be implicit
                body = document.findBody(configuration.tt);

                if (body != null)
                {
                    pprint = new PPrint(configuration);
                    if (!configuration.quiet)
                    {
                        this.report.reportNumberOfSlides(errout, pprint.countSlides(body));
                    }
                    pprint.createSlides(lexer, document);
                }
                else if (!configuration.quiet)
                {
                    this.report.missingBody(errout);
                }
            }
            else if (o != null)
            {
                pprint = new PPrint(configuration);

                if (document.findDocType() == null)
                {
                    // only use numeric character references if no doctype could be determined (e.g., because
                    // the document contains proprietary features) to ensure well-formedness.
                    configuration.numEntities = true;
                }
                if (configuration.bodyOnly)
                {
                    // Feature request #434940 - fix by Dave Raggett/Ignacio Vazquez-Abrams 21 Jun 01
                    pprint.printBody(o, lexer, document, configuration.xmlOut);
                }
                else if (configuration.xmlOut && !configuration.xHTML)
                {
                    pprint.printXMLTree(o, (short) 0, 0, lexer, document);
                }
                else
                {
                    pprint.printTree(o, (short) 0, 0, lexer, document);
                }

                pprint.flushLine(o, 0);
                o.flush();
            }

        }

        if (!configuration.quiet)
        {
            this.report.errorSummary(lexer);
        }
        configuration.tt.freeAnchors();	// XOWA:xowa; free anchors, else will leak memory 
        return document;
    }

    /**
     * Internal routine that actually does the parsing. The caller can pass either an InputStream or file name. If both
     * are passed, the file name is preferred.
     * @param in input stream (used only if <code>file</code> is null)
     * @param file file name
     * @param out output stream
     * @return parsed org.w3c.tidy.Node
     * @throws FileNotFoundException if <code>file</code> is not null but it can't be found
     * @throws IOException for errors in reading input stream or file
     */
    private Node parse(InputStream in, String file, OutputStream out) throws FileNotFoundException, IOException
    {

        StreamIn streamIn;
        Out o = null;
        boolean inputStreamOpen = false;
        boolean outputStreamOpen = false;

        if (file != null)
        {
            in = new FileInputStream(file);
            inputStreamOpen = true;
            inputStreamName = file;
        }
        else if (in == null)
        {
            in = System.in;
            inputStreamName = "stdin";
        }

        streamIn = StreamInFactory.getStreamIn(configuration, in);

        if (configuration.writeback && (file != null))
        {
            out = new FileOutputStream(file);
            outputStreamOpen = true;
        }

        if (out != null)
        {
            o = OutFactory.getOut(this.configuration, out); // normal output stream
        }

        Node node = parse(streamIn, o);

        // Try to close the InputStream but only if if we created it.
        if (inputStreamOpen)
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                // ignore
            }
        }

        // Try to close the OutputStream but only if if we created it.
        if (outputStreamOpen)
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                // ignore
            }
        }

        return node;

    }

    /**
     * Pretty-prints a tidy Node.
     * @param node org.w3c.tidy.Node
     * @param out output stream
     */
    private void pprint(Node node, OutputStream out)
    {
        PPrint pprint;

        if (out != null)
        {

            Out o = OutFactory.getOut(this.configuration, out);

            Lexer lexer = new Lexer(null, this.configuration, this.report);

            pprint = new PPrint(configuration);

            if (configuration.xmlTags)
            {
                pprint.printXMLTree(o, (short) 0, 0, lexer, node);
            }
            else
            {
                pprint.printTree(o, (short) 0, 0, lexer, node);
            }

            pprint.flushLine(o, 0);

            o.flush();
        }
    }

    /**
     * Command line interface to parser and pretty printer.
     * @param argv command line parameters
     */
    public static void main(String[] argv)
    {
        Tidy tidy = new Tidy();
        int returnCode = tidy.mainExec(argv);
        System.exit(returnCode);
    }

    /**
     * Main method, but returns the return code as an int instead of calling System.exit(code). Needed for testing main
     * method without shutting down tests.
     * @param argv command line parameters
     * @return return code
     */
    protected int mainExec(String[] argv)
    {
        String file;
        int argCount = argv.length;
        int argIndex = 0;

        // read command line
        Properties properties = new Properties();

        while (argCount > 0)
        {
            if (argv[argIndex].startsWith("-"))
            {
                // support -foo and --foo
                String argName = argv[argIndex].toLowerCase();
                while (argName.length() > 0 && argName.charAt(0) == '-')
                {
                    argName = argName.substring(1);
                }

                // "exclusive" options
                if (argName.equals("help") || argName.equals("h") || argName.equals("?"))
                {
                    this.report.helpText(new PrintWriter(System.out, true));
                    return 0;
                }
                else if (argName.equals("help-config"))
                {
                    configuration.printConfigOptions(new PrintWriter(System.out, true), false);
                    return 0;
                }
                else if (argName.equals("show-config"))
                {
                    configuration.adjust(); // ensure config is self-consistent
                    configuration.printConfigOptions(errout, true);
                    return 0;
                }
                else if (argName.equals("version") || argName.equals("v"))
                {
                    this.report.showVersion(errout);
                    return 0;
                }

                // optional value for non boolean options
                String argValue = null;
                if (argCount > 2 && !argv[argIndex + 1].startsWith("-"))
                {
                    argValue = argv[argIndex + 1];
                    --argCount;
                    ++argIndex;
                }

                // handle "special" aliases
                String alias = (String) CMDLINE_ALIAS.get(argName);
                if (alias != null)
                {
                    argName = alias;
                }

                if (Configuration.isKnownOption(argName)) // handle any standard config option
                {
                    properties.setProperty(argName, (argValue == null ? "" : argValue));
                }
                else if (argName.equals("config")) // parse a property file
                {
                    if (argValue != null)
                    {
                        configuration.parseFile(argValue);
                    }
                }
                else if (TidyUtils.isCharEncodingSupported(argName)) // handle any encoding name
                {
                    properties.setProperty("char-encoding", argName);
                }
                else
                {

                    for (int i = 0; i < argName.length(); i++)
                    {
                        switch (argName.charAt(i))
                        {
                            case 'i' :
                                configuration.indentContent = true;
                                configuration.smartIndent = true;
                                break;

                            case 'o' :
                                configuration.hideEndTags = true;
                                break;

                            case 'u' :
                                configuration.upperCaseTags = true;
                                break;

                            case 'c' :
                                configuration.makeClean = true;
                                break;

                            case 'b' :
                                configuration.makeBare = true;
                                break;

                            case 'n' :
                                configuration.numEntities = true;
                                break;

                            case 'm' :
                                configuration.writeback = true;
                                break;

                            case 'e' :
                                configuration.onlyErrors = true;
                                break;

                            case 'q' :
                                configuration.quiet = true;
                                break;

                            default :
                                this.report.unknownOption(this.errout, argName.charAt(i));
                                break;
                        }
                    }
                }

                --argCount;
                ++argIndex;
                continue;
            }

            configuration.addProps(properties);

            // ensure config is self-consistent
            configuration.adjust();

            // user specified error file
            if (configuration.errfile != null)
            {

                String errorfile = "stderr";

                // is it same as the currently opened file?
                if (!configuration.errfile.equals(errorfile))
                {
                    // no so close previous error file

                    if (this.errout != this.stderr)
                    {
                        this.errout.close();
                    }

                    // and try to open the new error file
                    try
                    {
                        this.setErrout(new PrintWriter(new FileWriter(configuration.errfile), true));
                        errorfile = configuration.errfile;
                    }
                    catch (IOException e)
                    {
                        // can't be opened so fall back to stderr
                        errorfile = "stderr";
                        this.setErrout(stderr);
                    }
                }
            }

            if (argCount > 0)
            {
                file = argv[argIndex];
            }
            else
            {
                file = "stdin";
            }

            try
            {
                parse(null, file, System.out);
            }
            catch (FileNotFoundException fnfe)
            {
                this.report.unknownFile(this.errout, file);
            }
            catch (IOException ioe)
            {
                this.report.unknownFile(this.errout, file);
            }

            --argCount;
            ++argIndex;

            if (argCount <= 0)
            {
                break;
            }
        }

        if (this.parseErrors + this.parseWarnings > 0 && !configuration.quiet)
        {
            this.report.generalInfo(this.errout);
        }

        if (this.errout != this.stderr)
        {
            this.errout.close();
        }

        // return status can be used by scripts
        if (this.parseErrors > 0)
        {
            return 2;
        }

        if (this.parseWarnings > 0)
        {
            return 1;
        }

        // 0 means all is ok
        return 0;
    }

    /**
     * Attach a TidyMessageListener which will be notified for messages and errors.
     * @param listener TidyMessageListener implementation
     */
    public void setMessageListener(TidyMessageListener listener)
    {
        this.report.addMessageListener(listener);
    }

    /**
     * <code>indent-spaces</code>- default indentation.
     * @param spaces number of spaces used for indentation
     * @see Configuration#spaces
     */
    public void setSpaces(int spaces)
    {
        configuration.spaces = spaces;
    }

    /**
     * <code>indent-spaces</code>- default indentation.
     * @return number of spaces used for indentation
     * @see Configuration#spaces
     */
    public int getSpaces()
    {
        return configuration.spaces;
    }

    /**
     * <code>wrap</code>- default wrap margin.
     * @param wraplen default wrap margin
     * @see Configuration#wraplen
     */
    public void setWraplen(int wraplen)
    {
        configuration.wraplen = wraplen;
    }

    /**
     * <code>wrap</code>- default wrap margin.
     * @return default wrap margin
     * @see Configuration#wraplen
     */
    public int getWraplen()
    {
        return configuration.wraplen;
    }

    /**
     * <code>tab-size</code>- tab size in chars.
     * @param tabsize tab size in chars
     * @see Configuration#tabsize
     */
    public void setTabsize(int tabsize)
    {
        configuration.tabsize = tabsize;
    }

    /**
     * <code>tab-size</code>- tab size in chars.
     * @return tab size in chars
     * @see Configuration#tabsize
     */
    public int getTabsize()
    {
        return configuration.tabsize;
    }

    /**
     * Errfile - file name to write errors to.
     * @param errfile file name to write errors to
     * @see Configuration#errfile
     */
    public void setErrfile(String errfile)
    {
        configuration.errfile = errfile;
    }

    /**
     * Errfile - file name to write errors to.
     * @return error file name
     * @see Configuration#errfile
     */
    public String getErrfile()
    {
        return configuration.errfile;
    }

    /**
     * writeback - if true then output tidied markup. NOTE: this property is ignored when parsing from an InputStream.
     * @param writeback <code>true</code>= output tidied markup
     * @see Configuration#writeback
     */
    public void setWriteback(boolean writeback)
    {
        configuration.writeback = writeback;
    }

    /**
     * writeback - if true then output tidied markup. NOTE: this property is ignored when parsing from an InputStream.
     * @return <code>true</code> if tidy will output tidied markup in input file
     * @see Configuration#writeback
     */
    public boolean getWriteback()
    {
        return configuration.writeback;
    }

    /**
     * only-errors - if true normal output is suppressed.
     * @param onlyErrors if <code>true</code> normal output is suppressed.
     * @see Configuration#onlyErrors
     */
    public void setOnlyErrors(boolean onlyErrors)
    {
        configuration.onlyErrors = onlyErrors;
    }

    /**
     * only-errors - if true normal output is suppressed.
     * @return <code>true</code> if normal output is suppressed.
     * @see Configuration#onlyErrors
     */
    public boolean getOnlyErrors()
    {
        return configuration.onlyErrors;
    }

    /**
     * show-warnings - show warnings? (errors are always shown).
     * @param showWarnings if <code>false</code> warnings are not shown
     * @see Configuration#showWarnings
     */
    public void setShowWarnings(boolean showWarnings)
    {
        configuration.showWarnings = showWarnings;
    }

    /**
     * show-warnings - show warnings? (errors are always shown).
     * @return <code>false</code> if warnings are not shown
     * @see Configuration#showWarnings
     */
    public boolean getShowWarnings()
    {
        return configuration.showWarnings;
    }

    /**
     * quiet - no 'Parsing X', guessed DTD or summary.
     * @param quiet <code>true</code>= don't output summary, warnings or errors
     * @see Configuration#quiet
     */
    public void setQuiet(boolean quiet)
    {
        configuration.quiet = quiet;
    }

    /**
     * quiet - no 'Parsing X', guessed DTD or summary.
     * @return <code>true</code> if tidy will not output summary, warnings or errors
     * @see Configuration#quiet
     */
    public boolean getQuiet()
    {
        return configuration.quiet;
    }

    /**
     * indent - indent content of appropriate tags.
     * @param indentContent indent content of appropriate tags
     * @see Configuration#indentContent
     */
    public void setIndentContent(boolean indentContent)
    {
        configuration.indentContent = indentContent;
    }

    /**
     * indent - indent content of appropriate tags.
     * @return <code>true</code> if tidy will indent content of appropriate tags
     * @see Configuration#indentContent
     */
    public boolean getIndentContent()
    {
        return configuration.indentContent;
    }

    /**
     * SmartIndent - does text/block level content effect indentation.
     * @param smartIndent <code>true</code> if text/block level content should effect indentation
     * @see Configuration#smartIndent
     */
    public void setSmartIndent(boolean smartIndent)
    {
        configuration.smartIndent = smartIndent;
    }

    /**
     * SmartIndent - does text/block level content effect indentation.
     * @return <code>true</code> if text/block level content should effect indentation
     * @see Configuration#smartIndent
     */
    public boolean getSmartIndent()
    {
        return configuration.smartIndent;
    }

    /**
     * hide-endtags - suppress optional end tags.
     * @param hideEndTags <code>true</code>= suppress optional end tags
     * @see Configuration#hideEndTags
     */
    public void setHideEndTags(boolean hideEndTags)
    {
        configuration.hideEndTags = hideEndTags;
    }

    /**
     * hide-endtags - suppress optional end tags.
     * @return <code>true</code> if tidy will suppress optional end tags
     * @see Configuration#hideEndTags
     */
    public boolean getHideEndTags()
    {
        return configuration.hideEndTags;
    }

    /**
     * input-xml - treat input as XML.
     * @param xmlTags <code>true</code> if tidy should treat input as XML
     * @see Configuration#xmlTags
     */
    public void setXmlTags(boolean xmlTags)
    {
        configuration.xmlTags = xmlTags;
    }

    /**
     * input-xml - treat input as XML.
     * @return <code>true</code> if tidy will treat input as XML
     * @see Configuration#xmlTags
     */
    public boolean getXmlTags()
    {
        return configuration.xmlTags;
    }

    /**
     * output-xml - create output as XML.
     * @param xmlOut <code>true</code> if tidy should create output as xml
     * @see Configuration#xmlOut
     */
    public void setXmlOut(boolean xmlOut)
    {
        configuration.xmlOut = xmlOut;
    }

    /**
     * output-xml - create output as XML.
     * @return <code>true</code> if tidy will create output as xml
     * @see Configuration#xmlOut
     */
    public boolean getXmlOut()
    {
        return configuration.xmlOut;
    }

    /**
     * output-xhtml - output extensible HTML.
     * @param xhtml <code>true</code> if tidy should output XHTML
     * @see Configuration#xHTML
     */
    public void setXHTML(boolean xhtml)
    {
        configuration.xHTML = xhtml;
    }

    /**
     * output-xhtml - output extensible HTML.
     * @return <code>true</code> if tidy will output XHTML
     * @see Configuration#xHTML
     */
    public boolean getXHTML()
    {
        return configuration.xHTML;
    }

    /**
     * uppercase-tags - output tags in upper case.
     * @param upperCaseTags <code>true</code> if tidy should output tags in upper case (default is lowercase)
     * @see Configuration#upperCaseTags
     */
    public void setUpperCaseTags(boolean upperCaseTags)
    {
        configuration.upperCaseTags = upperCaseTags;
    }

    /**
     * uppercase-tags - output tags in upper case.
     * @return <code>true</code> if tidy should will tags in upper case
     * @see Configuration#upperCaseTags
     */
    public boolean getUpperCaseTags()
    {
        return configuration.upperCaseTags;
    }

    /**
     * uppercase-attributes - output attributes in upper case.
     * @param upperCaseAttrs <code>true</code> if tidy should output attributes in upper case (default is lowercase)
     * @see Configuration#upperCaseAttrs
     */
    public void setUpperCaseAttrs(boolean upperCaseAttrs)
    {
        configuration.upperCaseAttrs = upperCaseAttrs;
    }

    /**
     * uppercase-attributes - output attributes in upper case.
     * @return <code>true</code> if tidy should will attributes in upper case
     * @see Configuration#upperCaseAttrs
     */
    public boolean getUpperCaseAttrs()
    {
        return configuration.upperCaseAttrs;
    }

    /**
     * make-clean - remove presentational clutter.
     * @param makeClean true to remove presentational clutter
     * @see Configuration#makeClean
     */
    public void setMakeClean(boolean makeClean)
    {
        configuration.makeClean = makeClean;
    }

    /**
     * make-clean - remove presentational clutter.
     * @return true if tidy will remove presentational clutter
     * @see Configuration#makeClean
     */
    public boolean getMakeClean()
    {
        return configuration.makeClean;
    }

    /**
     * make-bare - remove Microsoft cruft.
     * @param makeBare true to remove Microsoft cruft
     * @see Configuration#makeBare
     */
    public void setMakeBare(boolean makeBare)
    {
        configuration.makeBare = makeBare;
    }

    /**
     * make-clean - remove Microsoft cruft.
     * @return true if tidy will remove Microsoft cruft
     * @see Configuration#makeBare
     */
    public boolean getMakeBare()
    {
        return configuration.makeBare;
    }

    /**
     * break-before-br - output newline before &lt;br&gt;.
     * @param breakBeforeBR <code>true</code> if tidy should output a newline before &lt;br&gt;
     * @see Configuration#breakBeforeBR
     */
    public void setBreakBeforeBR(boolean breakBeforeBR)
    {
        configuration.breakBeforeBR = breakBeforeBR;
    }

    /**
     * break-before-br - output newline before &lt;br&gt;.
     * @return <code>true</code> if tidy will output a newline before &lt;br&gt;
     * @see Configuration#breakBeforeBR
     */
    public boolean getBreakBeforeBR()
    {
        return configuration.breakBeforeBR;
    }

    /**
     * <code>split</code>- create slides on each h2 element.
     * @param burstSlides <code>true</code> if tidy should create slides on each h2 element
     * @see Configuration#burstSlides
     */
    public void setBurstSlides(boolean burstSlides)
    {
        configuration.burstSlides = burstSlides;
    }

    /**
     * <code>split</code>- create slides on each h2 element.
     * @return <code>true</code> if tidy will create slides on each h2 element
     * @see Configuration#burstSlides
     */
    public boolean getBurstSlides()
    {
        return configuration.burstSlides;
    }

    /**
     * <code>numeric-entities</code>- output entities other than the built-in HTML entities in the numeric rather
     * than the named entity form.
     * @param numEntities <code>true</code> if tidy should output entities in the numeric form.
     * @see Configuration#numEntities
     */
    public void setNumEntities(boolean numEntities)
    {
        configuration.numEntities = numEntities;
    }

    /**
     * <code>numeric-entities</code>- output entities other than the built-in HTML entities in the numeric rather
     * than the named entity form.
     * @return <code>true</code> if tidy will output entities in the numeric form.
     * @see Configuration#numEntities
     */
    public boolean getNumEntities()
    {
        return configuration.numEntities;
    }

    /**
     * <code>quote-marks</code>- output " marks as &amp;quot;.
     * @param quoteMarks <code>true</code> if tidy should output " marks as &amp;quot;
     * @see Configuration#quoteMarks
     */
    public void setQuoteMarks(boolean quoteMarks)
    {
        configuration.quoteMarks = quoteMarks;
    }

    /**
     * <code>quote-marks</code>- output " marks as &amp;quot;.
     * @return <code>true</code> if tidy will output " marks as &amp;quot;
     * @see Configuration#quoteMarks
     */
    public boolean getQuoteMarks()
    {
        return configuration.quoteMarks;
    }

    /**
     * <code>quote-nbsp</code>- output non-breaking space as entity.
     * @param quoteNbsp <code>true</code> if tidy should output non-breaking space as entity
     * @see Configuration#quoteNbsp
     */
    public void setQuoteNbsp(boolean quoteNbsp)
    {
        configuration.quoteNbsp = quoteNbsp;
    }

    /**
     * <code>quote-nbsp</code>- output non-breaking space as entity.
     * @return <code>true</code> if tidy will output non-breaking space as entity
     * @see Configuration#quoteNbsp
     */
    public boolean getQuoteNbsp()
    {
        return configuration.quoteNbsp;
    }

    /**
     * <code>quote-ampersand</code>- output naked ampersand as &amp;.
     * @param quoteAmpersand <code>true</code> if tidy should output naked ampersand as &amp;
     * @see Configuration#quoteAmpersand
     */
    public void setQuoteAmpersand(boolean quoteAmpersand)
    {
        configuration.quoteAmpersand = quoteAmpersand;
    }

    /**
     * <code>quote-ampersand</code>- output naked ampersand as &amp;.
     * @return <code>true</code> if tidy will output naked ampersand as &amp;
     * @see Configuration#quoteAmpersand
     */
    public boolean getQuoteAmpersand()
    {
        return configuration.quoteAmpersand;
    }

    /**
     * <code>wrap-attributes</code>- wrap within attribute values.
     * @param wrapAttVals <code>true</code> if tidy should wrap within attribute values
     * @see Configuration#wrapAttVals
     */
    public void setWrapAttVals(boolean wrapAttVals)
    {
        configuration.wrapAttVals = wrapAttVals;
    }

    /**
     * <code>wrap-attributes</code>- wrap within attribute values.
     * @return <code>true</code> if tidy will wrap within attribute values
     * @see Configuration#wrapAttVals
     */
    public boolean getWrapAttVals()
    {
        return configuration.wrapAttVals;
    }

    /**
     * <code>wrap-script-literals</code>- wrap within JavaScript string literals.
     * @param wrapScriptlets <code>true</code> if tidy should wrap within JavaScript string literals
     * @see Configuration#wrapScriptlets
     */
    public void setWrapScriptlets(boolean wrapScriptlets)
    {
        configuration.wrapScriptlets = wrapScriptlets;
    }

    /**
     * <code>wrap-script-literals</code>- wrap within JavaScript string literals.
     * @return <code>true</code> if tidy will wrap within JavaScript string literals
     * @see Configuration#wrapScriptlets
     */
    public boolean getWrapScriptlets()
    {
        return configuration.wrapScriptlets;
    }

    /**
     * <code>wrap-sections</code>- wrap within &lt;![ ... ]&gt; section tags
     * @param wrapSection <code>true</code> if tidy should wrap within &lt;![ ... ]&gt; section tags
     * @see Configuration#wrapSection
     */
    public void setWrapSection(boolean wrapSection)
    {
        configuration.wrapSection = wrapSection;
    }

    /**
     * <code>wrap-sections</code>- wrap within &lt;![ ... ]&gt; section tags
     * @return <code>true</code> if tidy will wrap within &lt;![ ... ]&gt; section tags
     * @see Configuration#wrapSection
     */
    public boolean getWrapSection()
    {
        return configuration.wrapSection;
    }

    /**
     * <code>alt-text</code>- default text for alt attribute.
     * @param altText default text for alt attribute
     * @see Configuration#altText
     */
    public void setAltText(String altText)
    {
        configuration.altText = altText;
    }

    /**
     * <code>alt-text</code>- default text for alt attribute.
     * @return default text for alt attribute
     * @see Configuration#altText
     */
    public String getAltText()
    {
        return configuration.altText;
    }

    /**
     * <code>add-xml-pi</code>- add &lt;?xml?&gt; for XML docs.
     * @param xmlPi <code>true</code> if tidy should add &lt;?xml?&gt; for XML docs
     * @see Configuration#xmlPi
     */
    public void setXmlPi(boolean xmlPi)
    {
        configuration.xmlPi = xmlPi;
    }

    /**
     * <code>add-xml-pi</code>- add &lt;?xml?&gt; for XML docs.
     * @return <code>true</code> if tidy will add &lt;?xml?&gt; for XML docs
     * @see Configuration#xmlPi
     */
    public boolean getXmlPi()
    {
        return configuration.xmlPi;
    }

    /**
     * <code>drop-font-tags</code>- discard presentation tags.
     * @param dropFontTags <code>true</code> if tidy should discard presentation tags
     * @see Configuration#dropFontTags
     */
    public void setDropFontTags(boolean dropFontTags)
    {
        configuration.dropFontTags = dropFontTags;
    }

    /**
     * <code>drop-font-tags</code>- discard presentation tags.
     * @return <code>true</code> if tidy will discard presentation tags
     * @see Configuration#dropFontTags
     */
    public boolean getDropFontTags()
    {
        return configuration.dropFontTags;
    }

    /**
     * <code>drop-proprietary-attributes</code>- discard proprietary attributes.
     * @param dropProprietaryAttributes <code>true</code> if tidy should discard proprietary attributes
     * @see Configuration#dropProprietaryAttributes
     */
    public void setDropProprietaryAttributes(boolean dropProprietaryAttributes)
    {
        configuration.dropProprietaryAttributes = dropProprietaryAttributes;
    }

    /**
     * <code>drop-proprietary-attributes</code>- discard proprietary attributes.
     * @return <code>true</code> if tidy will discard proprietary attributes
     * @see Configuration#dropProprietaryAttributes
     */
    public boolean getDropProprietaryAttributes()
    {
        return configuration.dropProprietaryAttributes;
    }

    /**
     * <code>drop-empty-paras</code>- discard empty p elements.
     * @param dropEmptyParas <code>true</code> if tidy should discard empty p elements
     * @see Configuration#dropEmptyParas
     */
    public void setDropEmptyParas(boolean dropEmptyParas)
    {
        configuration.dropEmptyParas = dropEmptyParas;
    }

    /**
     * <code>drop-empty-paras</code>- discard empty p elements.
     * @return <code>true</code> if tidy will discard empty p elements
     * @see Configuration#dropEmptyParas
     */
    public boolean getDropEmptyParas()
    {
        return configuration.dropEmptyParas;
    }

    /**
     * <code>fix-bad-comments</code>- fix comments with adjacent hyphens.
     * @param fixComments <code>true</code> if tidy should fix comments with adjacent hyphens
     * @see Configuration#fixComments
     */
    public void setFixComments(boolean fixComments)
    {
        configuration.fixComments = fixComments;
    }

    /**
     * <code>fix-bad-comments</code>- fix comments with adjacent hyphens.
     * @return <code>true</code> if tidy will fix comments with adjacent hyphens
     * @see Configuration#fixComments
     */
    public boolean getFixComments()
    {
        return configuration.fixComments;
    }

    /**
     * <code>wrap-asp</code>- wrap within ASP pseudo elements.
     * @param wrapAsp <code>true</code> if tidy should wrap within ASP pseudo elements
     * @see Configuration#wrapAsp
     */
    public void setWrapAsp(boolean wrapAsp)
    {
        configuration.wrapAsp = wrapAsp;
    }

    /**
     * <code>wrap-asp</code>- wrap within ASP pseudo elements.
     * @return <code>true</code> if tidy will wrap within ASP pseudo elements
     * @see Configuration#wrapAsp
     */
    public boolean getWrapAsp()
    {
        return configuration.wrapAsp;
    }

    /**
     * <code>wrap-jste</code>- wrap within JSTE pseudo elements.
     * @param wrapJste <code>true</code> if tidy should wrap within JSTE pseudo elements
     * @see Configuration#wrapJste
     */
    public void setWrapJste(boolean wrapJste)
    {
        configuration.wrapJste = wrapJste;
    }

    /**
     * <code>wrap-jste</code>- wrap within JSTE pseudo elements.
     * @return <code>true</code> if tidy will wrap within JSTE pseudo elements
     * @see Configuration#wrapJste
     */
    public boolean getWrapJste()
    {
        return configuration.wrapJste;
    }

    /**
     * <code>wrap-php</code>- wrap within PHP pseudo elements.
     * @param wrapPhp <code>true</code> if tidy should wrap within PHP pseudo elements
     * @see Configuration#wrapPhp
     */
    public void setWrapPhp(boolean wrapPhp)
    {
        configuration.wrapPhp = wrapPhp;
    }

    /**
     * <code>wrap-php</code>- wrap within PHP pseudo elements.
     * @return <code>true</code> if tidy will wrap within PHP pseudo elements
     * @see Configuration#wrapPhp
     */
    public boolean getWrapPhp()
    {
        return configuration.wrapPhp;
    }

    /**
     * <code>fix-backslash</code>- fix URLs by replacing \ with /.
     * @param fixBackslash <code>true</code> if tidy should fix URLs by replacing \ with /
     * @see Configuration#fixBackslash
     */
    public void setFixBackslash(boolean fixBackslash)
    {
        configuration.fixBackslash = fixBackslash;
    }

    /**
     * <code>fix-backslash</code>- fix URLs by replacing \ with /.
     * @return <code>true</code> if tidy will fix URLs by replacing \ with /
     * @see Configuration#fixBackslash
     */
    public boolean getFixBackslash()
    {
        return configuration.fixBackslash;
    }

    /**
     * <code>indent-attributes</code>- newline+indent before each attribute.
     * @param indentAttributes <code>true</code> if tidy should output a newline+indent before each attribute
     * @see Configuration#indentAttributes
     */
    public void setIndentAttributes(boolean indentAttributes)
    {
        configuration.indentAttributes = indentAttributes;
    }

    /**
     * <code>indent-attributes</code>- newline+indent before each attribute.
     * @return <code>true</code> if tidy will output a newline+indent before each attribute
     * @see Configuration#indentAttributes
     */
    public boolean getIndentAttributes()
    {
        return configuration.indentAttributes;
    }

    /**
     * <code>doctype</code>- user specified doctype.
     * @param doctype <code>omit | auto | strict | loose | <em>fpi</em></code> where the <em>fpi </em> is a string
     * similar to &quot;-//ACME//DTD HTML 3.14159//EN&quot; Note: for <em>fpi </em> include the double-quotes in the
     * string.
     * @see Configuration#docTypeStr
     * @see Configuration#docTypeMode
     */
    public void setDocType(String doctype)
    {
        if (doctype != null)
        {
            configuration.docTypeStr = (String) ParsePropertyImpl.DOCTYPE.parse(doctype, "doctype", configuration);
        }
    }

    /**
     * <code>doctype</code>- user specified doctype.
     * @return <code>omit | auto | strict | loose | <em>fpi</em></code> where the <em>fpi </em> is a string similar
     * to &quot;-//ACME//DTD HTML 3.14159//EN&quot; Note: for <em>fpi </em> include the double-quotes in the string.
     * @see Configuration#docTypeStr
     * @see Configuration#docTypeMode
     */
    public String getDocType()
    {
        String result = null;
        switch (configuration.docTypeMode)
        {
            case Configuration.DOCTYPE_OMIT :
                result = "omit";
                break;
            case Configuration.DOCTYPE_AUTO :
                result = "auto";
                break;
            case Configuration.DOCTYPE_STRICT :
                result = "strict";
                break;
            case Configuration.DOCTYPE_LOOSE :
                result = "loose";
                break;
            case Configuration.DOCTYPE_USER :
                result = configuration.docTypeStr;
                break;
        }
        return result;
    }

    /**
     * <code>logical-emphasis</code>- replace i by em and b by strong.
     * @param logicalEmphasis <code>true</code> if tidy should replace i by em and b by strong
     * @see Configuration#logicalEmphasis
     */
    public void setLogicalEmphasis(boolean logicalEmphasis)
    {
        configuration.logicalEmphasis = logicalEmphasis;
    }

    /**
     * <code>logical-emphasis</code>- replace i by em and b by strong.
     * @return <code>true</code> if tidy will replace i by em and b by strong
     * @see Configuration#logicalEmphasis
     */
    public boolean getLogicalEmphasis()
    {
        return configuration.logicalEmphasis;
    }

    /**
     * <code>assume-xml-procins</code> This option specifies if Tidy should change the parsing of processing
     * instructions to require ?> as the terminator rather than >. This option is automatically set if the input is in
     * XML.
     * @param xmlPIs <code>true</code> if tidy should expect a ?> at the end of processing instructions
     * @see Configuration#xmlPIs
     */
    public void setXmlPIs(boolean xmlPIs)
    {
        configuration.xmlPIs = xmlPIs;
    }

    /**
     * <code>assume-xml-procins</code> This option specifies if Tidy should change the parsing of processing
     * instructions to require ?> as the terminator rather than >. This option is automatically set if the input is in
     * XML.
     * @return <code>true</code> if tidy will expect a ?> at the end of processing instructions
     * @see Configuration#xmlPIs
     */
    public boolean getXmlPIs()
    {
        return configuration.xmlPIs;
    }

    /**
     * <code>enclose-text</code>- if true text at body is wrapped in &lt;p&gt;'s.
     * @param encloseText <code>true</code> if tidy should wrap text at body in &lt;p&gt;'s.
     * @see Configuration#encloseBodyText
     */
    public void setEncloseText(boolean encloseText)
    {
        configuration.encloseBodyText = encloseText;
    }

    /**
     * <code>enclose-text</code>- if true text at body is wrapped in &lt;p&gt;'s.
     * @return <code>true</code> if tidy will wrap text at body in &lt;p&gt;'s.
     * @see Configuration#encloseBodyText
     */
    public boolean getEncloseText()
    {
        return configuration.encloseBodyText;
    }

    /**
     * <code>enclose-block-text</code>- if true text in blocks is wrapped in &lt;p&gt;'s.
     * @param encloseBlockText <code>true</code> if tidy should wrap text text in blocks in &lt;p&gt;'s.
     * @see Configuration#encloseBlockText
     */
    public void setEncloseBlockText(boolean encloseBlockText)
    {
        configuration.encloseBlockText = encloseBlockText;
    }

    /**
     * <code>enclose-block-text</code>- if true text in blocks is wrapped in &lt;p&gt;'s. return <code>true</code>
     * if tidy should will text text in blocks in &lt;p&gt;'s.
     * @see Configuration#encloseBlockText
     */
    public boolean getEncloseBlockText()
    {
        return configuration.encloseBlockText;
    }

    /**
     * <code>word-2000</code>- draconian cleaning for Word2000.
     * @param word2000 <code>true</code> if tidy should clean word2000 documents
     * @see Configuration#word2000
     */
    public void setWord2000(boolean word2000)
    {
        configuration.word2000 = word2000;
    }

    /**
     * <code>word-2000</code>- draconian cleaning for Word2000.
     * @return <code>true</code> if tidy will clean word2000 documents
     * @see Configuration#word2000
     */
    public boolean getWord2000()
    {
        return configuration.word2000;
    }

    /**
     * <code>tidy-mark</code>- add meta element indicating tidied doc.
     * @param tidyMark <code>true</code> if tidy should add meta element indicating tidied doc
     * @see Configuration#tidyMark
     */
    public void setTidyMark(boolean tidyMark)
    {
        configuration.tidyMark = tidyMark;
    }

    /**
     * <code>tidy-mark</code>- add meta element indicating tidied doc.
     * @return <code>true</code> if tidy will add meta element indicating tidied doc
     * @see Configuration#tidyMark
     */
    public boolean getTidyMark()
    {
        return configuration.tidyMark;
    }

    /**
     * <code>add-xml-space</code>- if set to yes adds xml:space attr as needed.
     * @param xmlSpace <code>true</code> if tidy should add xml:space attr as needed
     * @see Configuration#xmlSpace
     */
    public void setXmlSpace(boolean xmlSpace)
    {
        configuration.xmlSpace = xmlSpace;
    }

    /**
     * <code>add-xml-space</code>- if set to yes adds xml:space attr as needed.
     * @return <code>true</code> if tidy will add xml:space attr as needed
     * @see Configuration#xmlSpace
     */
    public boolean getXmlSpace()
    {
        return configuration.xmlSpace;
    }

    /**
     * <code>gnu-emacs</code>- if true format error output for GNU Emacs.
     * @param emacs <code>true</code> if tidy should format error output for GNU Emacs
     * @see Configuration#emacs
     */
    public void setEmacs(boolean emacs)
    {
        configuration.emacs = emacs;
    }

    /**
     * <code>gnu-emacs</code>- if true format error output for GNU Emacs.
     * @return <code>true</code> if tidy will format error output for GNU Emacs
     * @see Configuration#emacs
     */
    public boolean getEmacs()
    {
        return configuration.emacs;
    }

    /**
     * <code>literal-attributes</code>- if true attributes may use newlines.
     * @param literalAttribs <code>true</code> if attributes may use newlines
     * @see Configuration#literalAttribs
     */
    public void setLiteralAttribs(boolean literalAttribs)
    {
        configuration.literalAttribs = literalAttribs;
    }

    /**
     * <code>literal-attributes</code>- if true attributes may use newlines.
     * @return <code>true</code> if attributes may use newlines
     * @see Configuration#literalAttribs
     */
    public boolean getLiteralAttribs()
    {
        return configuration.literalAttribs;
    }

    /**
     * <code>print-body-only</code>- output BODY content only.
     * @param bodyOnly true = print only the document body
     * @see Configuration#bodyOnly
     */
    public void setPrintBodyOnly(boolean bodyOnly)
    {
        configuration.bodyOnly = bodyOnly;
    }

    /**
     * <code>print-body-only</code>- output BODY content only.
     * @return true if tidy will print only the document body
     */
    public boolean getPrintBodyOnly()
    {
        return configuration.bodyOnly;
    }

    /**
     * <code>fix-uri</code>- fix uri references applying URI encoding if necessary.
     * @param fixUri true = fix uri references
     * @see Configuration#fixUri
     */
    public void setFixUri(boolean fixUri)
    {
        configuration.fixUri = fixUri;
    }

    /**
     * <code>fix-uri</code>- output BODY content only.
     * @return true if tidy will fix uri references
     */
    public boolean getFixUri()
    {
        return configuration.fixUri;
    }

    /**
     * <code>lower-literals</code>- folds known attribute values to lower case.
     * @param lowerLiterals true = folds known attribute values to lower case
     * @see Configuration#lowerLiterals
     */
    public void setLowerLiterals(boolean lowerLiterals)
    {
        configuration.lowerLiterals = lowerLiterals;
    }

    /**
     * <code>lower-literals</code>- folds known attribute values to lower case.
     * @return true if tidy will folds known attribute values to lower case
     */
    public boolean getLowerLiterals()
    {
        return configuration.lowerLiterals;
    }

    /**
     * <code>hide-comments</code>- hides all (real) comments in output.
     * @param hideComments true = hides all comments in output
     * @see Configuration#hideComments
     */
    public void setHideComments(boolean hideComments)
    {
        configuration.hideComments = hideComments;
    }

    /**
     * <code>hide-comments</code>- hides all (real) comments in output.
     * @return true if tidy will hide all comments in output
     */
    public boolean getHideComments()
    {
        return configuration.hideComments;
    }

    /**
     * <code>indent-cdata</code>- indent CDATA sections.
     * @param indentCdata true = indent CDATA sections
     * @see Configuration#indentCdata
     */
    public void setIndentCdata(boolean indentCdata)
    {
        configuration.indentCdata = indentCdata;
    }

    /**
     * <code>indent-cdata</code>- indent CDATA sections.
     * @return true if tidy will indent CDATA sections
     */
    public boolean getIndentCdata()
    {
        return configuration.indentCdata;
    }

    /**
     * <code>force-output</code>- output document even if errors were found.
     * @param forceOutput true = output document even if errors were found
     * @see Configuration#forceOutput
     */
    public void setForceOutput(boolean forceOutput)
    {
        configuration.forceOutput = forceOutput;
    }

    /**
     * <code>force-output</code>- output document even if errors were found.
     * @return true if tidy will output document even if errors were found
     */
    public boolean getForceOutput()
    {
        return configuration.forceOutput;
    }

    /**
     * <code>show-errors</code>- set the number of errors to put out.
     * @param showErrors number of errors to put out
     * @see Configuration#showErrors
     */
    public void setShowErrors(int showErrors)
    {
        configuration.showErrors = showErrors;
    }

    /**
     * <code>show-errors</code>- number of errors to put out.
     * @return the number of errors tidy will put out
     */
    public int getShowErrors()
    {
        return configuration.showErrors;
    }

    /**
     * <code>ascii-chars</code>- convert quotes and dashes to nearest ASCII char.
     * @param asciiChars true = convert quotes and dashes to nearest ASCII char
     * @see Configuration#asciiChars
     */
    public void setAsciiChars(boolean asciiChars)
    {
        configuration.asciiChars = asciiChars;
    }

    /**
     * <code>ascii-chars</code>- convert quotes and dashes to nearest ASCII char.
     * @return true if tidy will convert quotes and dashes to nearest ASCII char
     */
    public boolean getAsciiChars()
    {
        return configuration.asciiChars;
    }

    /**
     * <code>join-classes</code>- join multiple class attributes.
     * @param joinClasses true = join multiple class attributes
     * @see Configuration#joinClasses
     */
    public void setJoinClasses(boolean joinClasses)
    {
        configuration.joinClasses = joinClasses;
    }

    /**
     * <code>join-classes</code>- join multiple class attributes.
     * @return true if tidy will join multiple class attributes
     */
    public boolean getJoinClasses()
    {
        return configuration.joinClasses;
    }

    /**
     * <code>join-styles</code>- join multiple style attributes.
     * @param joinStyles true = join multiple style attributes
     * @see Configuration#joinStyles
     */
    public void setJoinStyles(boolean joinStyles)
    {
        configuration.joinStyles = joinStyles;
    }

    /**
     * <code>join-styles</code>- join multiple style attributes.
     * @return true if tidy will join multiple style attributes
     */
    public boolean getJoinStyles()
    {
        return configuration.joinStyles;
    }

    /**
     * <code>trim-empty-elements</code>- trim empty elements.
     * @param trim-empty-elements true = trim empty elements
     * @see Configuration#trimEmpty
     */
    public void setTrimEmptyElements(boolean trimEmpty)
    {
        configuration.trimEmpty = trimEmpty;
    }

    /**
     * <code>trim-empty-elements</code>- trim empty elements.
     * @return true if tidy will trim empty elements
     */
    public boolean getTrimEmptyElements()
    {
        return configuration.trimEmpty;
    }

    /**
     * <code>replace-color</code>- replace hex color attribute values with names.
     * @param replaceColor true = replace hex color attribute values with names
     * @see Configuration#replaceColor
     */
    public void setReplaceColor(boolean replaceColor)
    {
        configuration.replaceColor = replaceColor;
    }

    /**
     * <code>replace-color</code>- replace hex color attribute values with names.
     * @return true if tidy will replace hex color attribute values with names
     */
    public boolean getReplaceColor()
    {
        return configuration.replaceColor;
    }

    /**
     * <code>escape-cdata</code>- replace CDATA sections with escaped text.
     * @param escapeCdata true = replace CDATA sections with escaped text
     * @see Configuration#escapeCdata
     */
    public void setEscapeCdata(boolean escapeCdata)
    {
        configuration.escapeCdata = escapeCdata;
    }

    /**
     * <code>escape-cdata</code> -replace CDATA sections with escaped text.
     * @return true if tidy will replace CDATA sections with escaped text
     */
    public boolean getEscapeCdata()
    {
        return configuration.escapeCdata;
    }

    /**
     * <code>repeated-attributes</code>- keep first or last duplicate attribute.
     * @param repeatedAttributes <code>Configuration.KEEP_FIRST | Configuration.KEEP_LAST</code>
     * @see Configuration#duplicateAttrs
     */
    public void setRepeatedAttributes(int repeatedAttributes)
    {
        configuration.duplicateAttrs = repeatedAttributes;
    }

    /**
     * <code>repeated-attributes</code>- keep first or last duplicate attribute.
     * @return <code>Configuration.KEEP_FIRST | Configuration.KEEP_LAST</code>
     */
    public int getRepeatedAttributes()
    {
        return configuration.duplicateAttrs;
    }

    /**
     * <code>keep-time</code>- if true last modified time is preserved.
     * @param keepFileTimes <code>true</code> if tidy should preserved last modified time in input file.
     * @todo <strong>this is NOT supported at this time. </strong>
     * @see Configuration#keepFileTimes
     */
    public void setKeepFileTimes(boolean keepFileTimes)
    {
        configuration.keepFileTimes = keepFileTimes;
    }

    /**
     * <code>keep-time</code>- if true last modified time is preserved.
     * @return <code>true</code> if tidy will preserved last modified time in input file.
     * @todo <strong>this is NOT supported at this time. </strong>
     * @see Configuration#keepFileTimes
     */
    public boolean getKeepFileTimes()
    {
        return configuration.keepFileTimes;
    }

    /**
     * <code>output-raw</code>- avoid mapping values > 127 to entities. This has the same effect of specifying a
     * "raw" encoding in the original version of tidy.
     * @param rawOut avoid mapping values > 127 to entities
     * @see Configuration#rawOut
     */
    public void setRawOut(boolean rawOut)
    {
        configuration.rawOut = rawOut;
    }

    /**
     * <code>output-raw</code>- avoid mapping values > 127 to entities.
     * @return <code>true</code> if tidy will not map values > 127 to entities
     * @see Configuration#rawOut
     */
    public boolean getRawOut()
    {
        return configuration.rawOut;
    }

    /**
     * <code>input-encoding</code> the character encoding used for input.
     * @param encoding a valid java encoding name
     */
    public void setInputEncoding(String encoding)
    {
        configuration.setInCharEncodingName(encoding);
    }

    /**
     * <code>input-encoding</code> the character encoding used for input.
     * @return the java name of the encoding currently used for input
     */
    public String getInputEncoding()
    {
        return configuration.getInCharEncodingName();
    }

    /**
     * <code>output-encoding</code> the character encoding used for output.
     * @param encoding a valid java encoding name
     */
    public void setOutputEncoding(String encoding)
    {
        configuration.setOutCharEncodingName(encoding);
    }

    /**
     * <code>output-encoding</code> the character encoding used for output.
     * @return the java name of the encoding currently used for output
     */
    public String getOutputEncoding()
    {
        return configuration.getOutCharEncodingName();
    }

}