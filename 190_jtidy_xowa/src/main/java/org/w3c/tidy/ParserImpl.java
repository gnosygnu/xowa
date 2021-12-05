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
 * HTML Parser implementation.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 932 $ ($Author: aditsu $)
 */
public final class ParserImpl
{

    /**
     * parser for html.
     */
    public static final Parser HTML = new ParseHTML();

    /**
     * parser for head.
     */
    public static final Parser HEAD = new ParseHead();

    /**
     * parser for title.
     */
    public static final Parser TITLE = new ParseTitle();

    /**
     * parser for script.
     */
    public static final Parser SCRIPT = new ParseScript();

    /**
     * parser for body.
     */
    public static final Parser BODY = new ParseBody();

    /**
     * parser for frameset.
     */
    public static final Parser FRAMESET = new ParseFrameSet();

    /**
     * parser for inline.
     */
    public static final Parser INLINE = new ParseInline();

    /**
     * parser for list.
     */
    public static final Parser LIST = new ParseList();

    /**
     * parser for definition lists.
     */
    public static final Parser DEFLIST = new ParseDefList();

    /**
     * parser for pre.
     */
    public static final Parser PRE = new ParsePre();

    /**
     * parser for block elements.
     */
    public static final Parser BLOCK = new ParseBlock();

    /**
     * parser for table.
     */
    public static final Parser TABLETAG = new ParseTableTag();

    /**
     * parser for colgroup.
     */
    public static final Parser COLGROUP = new ParseColGroup();

    /**
     * parser for rowgroup.
     */
    public static final Parser ROWGROUP = new ParseRowGroup();

    /**
     * parser for row.
     */
    public static final Parser ROW = new ParseRow();

    /**
     * parser for noframes.
     */
    public static final Parser NOFRAMES = new ParseNoFrames();

    /**
     * parser for select.
     */
    public static final Parser SELECT = new ParseSelect();

    /**
     * parser for text.
     */
    public static final Parser TEXT = new ParseText();

    /**
     * parser for empty elements.
     */
    public static final Parser EMPTY = new ParseEmpty();

    /**
     * parser for optgroup.
     */
    public static final Parser OPTGROUP = new ParseOptGroup();

    /**
     * ParserImpl should not be instantiated.
     */
    private ParserImpl()
    {
        // unused
    }

    /**
     * @param lexer
     * @param node
     * @param mode
     */
    protected static void parseTag(Lexer lexer, Node node, short mode)
    {
        // Fix by GLP 2000-12-21. Need to reset insertspace if this
        // is both a non-inline and empty tag (base, link, meta, isindex, hr, area).
        if ((node.tag.model & Dict.CM_EMPTY) != 0)
        {
            lexer.waswhite = false;
            if (node.tag.getParser() == null)	// XOWA:tidy
                return;
        }
        else if (!((node.tag.model & Dict.CM_INLINE) != 0))
            lexer.insertspace = false;

        if (node.tag.getParser() == null)
            return;
        
        if (node.type == Node.START_END_TAG)
        {
            Node.trimEmptyElement(lexer, node);
            return;
        }

        node.tag.getParser().parse(lexer, node, mode);
    }

    /**
     * Move node to the head, where element is used as starting point in hunt for head. Normally called during parsing.
     * @param lexer
     * @param element
     * @param node
     */
    protected static void moveToHead(Lexer lexer, Node element, Node node)
    {
        Node head;
        node.removeNode(); // make sure that node is isolated

        TagTable tt = lexer.configuration.tt;

        if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
        {
            lexer.report.warning(lexer, element, node, Report.TAG_NOT_ALLOWED_IN);

            while (element.tag != tt.tagHtml)
            {
                element = element.parent;
            }

            for (head = element.content; head != null; head = head.next)
            {
                if (head.tag == tt.tagHead)
                {
                    head.insertNodeAtEnd(node);
                    break;
                }
            }

            if (node.tag.getParser() != null)
            {
                parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);
            }
        }
        else
        {
            lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
        }
    }

    /**
     * moves given node to end of body element.
     * @param lexer Lexer
     * @param node Node to insert
     */
    static void moveNodeToBody(Lexer lexer, Node node)
    {
        node.removeNode();
        Node body = lexer.root.findBody(lexer.configuration.tt);
        body.insertNodeAtEnd(node);
    }

    /**
     * Parser for HTML.
     */
    public static class ParseHTML implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node html, short mode)
        {
            Node node, head;
            Node frameset = null;
            Node noframes = null;

            lexer.configuration.xmlTags = false;
            lexer.seenEndBody = false;
            TagTable tt = lexer.configuration.tt;

            while (true)
            {
                node = lexer.getToken(Lexer.IGNORE_WHITESPACE);

                if (node == null)
                {
                    node = lexer.inferredTag("head");
                    break;
                }

                if (node.tag == tt.tagHead)
                    break;

                if (node.tag == html.tag && node.type == Node.END_TAG)
                {
                    lexer.report.warning(lexer, html, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // deal with comments etc.
                if (Node.insertMisc(html, node))
                    continue;

                lexer.ungetToken();
                node = lexer.inferredTag("head");
                break;
            }

            head = node;
            html.insertNodeAtEnd(head);
            HEAD.parse(lexer, head, mode);

            while (true)
            {
                node = lexer.getToken(Lexer.IGNORE_WHITESPACE);

                if (node == null)
                {
                    if (frameset == null) // implied body
                    {                        
                        node = lexer.inferredTag("body");
                        html.insertNodeAtEnd(node);
                        BODY.parse(lexer, node, mode);
                    }

                    return;
                }

                // robustly handle html tags
                if (node.tag == html.tag)
                {
                    if (node.type != Node.START_TAG && frameset == null)
                        lexer.report.warning(lexer, html, node, Report.DISCARDING_UNEXPECTED);
                    // XOWA:jtidy
                    // else if (node.type == Node.END_TAG)
                    //    lexer.seenEndHtml = true;
                    continue;
                }

                // deal with comments etc.
                if (Node.insertMisc(html, node))
                    continue;

                // if frameset document coerce <body> to <noframes>
                if (node.tag == tt.tagBody)
                {
                    if (node.type != Node.START_TAG)
                    {
                        lexer.report.warning(lexer, html, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    // if ( cfg(doc, TidyAccessibilityCheckLevel) == 0 )
                    if (frameset != null)
                    {
                        lexer.ungetToken();

                        if (noframes == null)
                        {
                            noframes = lexer.inferredTag("noframes");
                            frameset.insertNodeAtEnd(noframes);
                            lexer.report.warning(lexer, html, noframes, Report.INSERTING_TAG);
                        }
                        else// XOWA:tidy
                        {
                            if (noframes.type == Node.START_END_TAG)
                                noframes.type = Node.START_TAG;
                        }

                        parseTag(lexer, noframes, mode);
                        continue;
                    }

                    lexer.constrainVersion(~Dict.VERS_FRAMESET);
                    break; // to parse body
                }

                // flag an error if we see more than one frameset
                if (node.tag == tt.tagFrameset)
                {
                    if (node.type != Node.START_TAG)
                    {
                        lexer.report.warning(lexer, html, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    if (frameset != null)
                        lexer.report.error(lexer, html, node, Report.DUPLICATE_FRAMESET);
                    else
                        frameset = node;

                    html.insertNodeAtEnd(node);
                    parseTag(lexer, node, mode);

                    // see if it includes a noframes element so that we can merge subsequent noframes elements
                    for (node = frameset.content; node != null; node = node.next)
                    {
                        if (node.tag == tt.tagNoframes)
                            noframes = node;
                    }
                    continue;
                }

                // if not a frameset document coerce <noframes> to <body>
                if (node.tag == tt.tagNoframes)
                {
                    if (node.type != Node.START_TAG)
                    {
                        lexer.report.warning(lexer, html, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    if (frameset == null)
                    {
                        lexer.report.warning(lexer, html, node, Report.DISCARDING_UNEXPECTED);
                        node = lexer.inferredTag("body");
                        break;
                    }

                    if (noframes == null)
                    {
                        noframes = node;
                        frameset.insertNodeAtEnd(noframes);
                    }

                    parseTag(lexer, noframes, mode);
                    continue;
                }

                if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                {
                    if (node.tag != null && (node.tag.model & Dict.CM_HEAD) != 0)
                    {
                        moveToHead(lexer, html, node);
                        continue;
                    }

                    // #427675 - discard illegal frame element following a frameset - fix by Randy Waki 11 Oct 00
                    if (frameset != null && node.tag == tt.tagFrame)
                    {
                        lexer.report.warning(lexer, html, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }
                }

                lexer.ungetToken();

                // insert other content into noframes element
                if (frameset != null)
                {
                    if (noframes == null)
                    {
                        noframes = lexer.inferredTag("noframes");
                        frameset.insertNodeAtEnd(noframes);
                    }
                    else
                    {
                        lexer.report.warning(lexer, html, node, Report.NOFRAMES_CONTENT);
                        // XOWA:tidy
                        if (noframes.type == Node.START_END_TAG)
                            noframes.type = Node.START_TAG;
                    }

                    lexer.constrainVersion(Dict.VERS_FRAMESET);
                    parseTag(lexer, noframes, mode);
                    continue;
                }

                node = lexer.inferredTag("body");
                lexer.report.warning(lexer, html, node, Report.INSERTING_TAG);	// XOWA:tidy
                lexer.constrainVersion(~Dict.VERS_FRAMESET);
                break;
            }

            // node must be body
            html.insertNodeAtEnd(node);
            parseTag(lexer, node, mode);
            lexer.seenEndHtml = true;
        }
    }

    /**
     * Parser for HEAD.
     */
    public static class ParseHead implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node head, short mode)
        {
            Node node;
            int hasTitle = 0;
            int hasBase = 0;
            TagTable tt = lexer.configuration.tt;

            while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
            {
                if (node.tag == head.tag && node.type == Node.END_TAG)
                {
                    head.closed = true;
                    break;
                }

                // find and discard multiple <head> elements
                // find and discard <html> in <head> elements
                if ((node.tag == head.tag || node.tag == tt.tagHtml && node.type == Node.START_TAG))
                {
                    lexer.report.warning(lexer, head, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }
                
                if (node.type == Node.TEXT_NODE)
                {
                    lexer.report.warning(lexer, head, node, Report.TAG_NOT_ALLOWED_IN);
                    lexer.ungetToken();
                    break;
                }

                if (	node.type == Node.PROC_INS_TAG && node.element != null
                    	&&	"xml-stylesheet".equals(node.element)
                	)	
                {
                	lexer.report.warning(lexer, head, node, Report.TAG_NOT_ALLOWED_IN);
                	Node.insertNodeBeforeElement(lexer.root.findHTML(lexer.configuration.tt), node); // TY_(InsertNodeBeforeElement)(TY_(FindHTML)(doc), node);
                    continue;
                }

                // deal with comments etc.
                if (Node.insertMisc(head, node))
                    continue;

                if (node.type == Node.DOCTYPE_TAG)
                {
                    Node.insertDocType(lexer, head, node);
                    continue;
                }

                // discard unknown tags
                if (node.tag == null)
                {
                    lexer.report.warning(lexer, head, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // if it doesn't belong in the head then
                // treat as implicit end of head and deal
                // with as part of the body
                if (!TidyUtils.toBoolean(node.tag.model & Dict.CM_HEAD))
                {
                    // #545067 Implicit closing of head broken - warn only for XHTML input
                    if (lexer.isvoyager)
                        lexer.report.warning(lexer, head, node, Report.TAG_NOT_ALLOWED_IN);
                    lexer.ungetToken();
                    break;
                }

                if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                {
                    if (node.tag == tt.tagTitle)
                    {
                        ++hasTitle;

                        if (hasTitle > 1)
                            lexer.report.warning(lexer, head, node, head != null ? Report.TOO_MANY_ELEMENTS_IN : Report.TOO_MANY_ELEMENTS);
                    }
                    else if (node.tag == tt.tagBase)
                    {
                        ++hasBase;

                        if (hasBase > 1)
                            lexer.report.warning(lexer, head, node, head != null ? Report.TOO_MANY_ELEMENTS_IN : Report.TOO_MANY_ELEMENTS);
                    }
                    else if (node.tag == tt.tagNoscript)
                    {
                        lexer.report.warning(lexer, head, node, Report.TAG_NOT_ALLOWED_IN);
                    }
                    // XOWA: skip AUTO_INPUT_ENCODING

                    head.insertNodeAtEnd(node);
                    parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);
                    continue;
                }

                // discard unexpected text nodes and end tags
                lexer.report.warning(lexer, head, node, Report.DISCARDING_UNEXPECTED);
            }
        }
    }

    /**
     * Parser for TITLE.
     */
    public static class ParseTitle implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node title, short mode)
        {
            Node node;

            while ((node = lexer.getToken(Lexer.MIXED_CONTENT)) != null)
            {                
                if (node.tag == title.tag && node.type == Node.START_TAG) // [438658] : Missing / in title endtag makes 2 titles
                {
                    lexer.report.warning(lexer, title, node, Report.COERCE_TO_ENDTAG);
                    node.type = Node.END_TAG;
                    lexer.ungetToken();	// XOWA:tidy
                    continue;
                }
                else if (node.tag == title.tag && node.type == Node.END_TAG)
                {
                    title.closed = true;
                    Node.trimSpaces(lexer, title);
                    return;
                }

                if (node.type == Node.TEXT_NODE)
                {
                    // only called for 1st child
                    if (title.content == null)
                        Node.trimInitialSpace(lexer, title, node);

                    if (node.start >= node.end)
                    {
                        continue;
                    }

                    title.insertNodeAtEnd(node);
                    continue;
                }

                // deal with comments etc.
                if (Node.insertMisc(title, node))
                    continue;

                // discard unknown tags
                if (node.tag == null)
                {
                    lexer.report.warning(lexer, title, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // pushback unexpected tokens
                lexer.report.warning(lexer, title, node, Report.MISSING_ENDTAG_BEFORE);
                lexer.ungetToken();
                Node.trimSpaces(lexer, title);
                return;
            }

            lexer.report.warning(lexer, title, node, Report.MISSING_ENDTAG_FOR);
        }
    }

    /**
     * Parser for SCRIPT.
     */
    public static class ParseScript implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node script, short mode) {
            Node node = lexer.getCDATA(script);
            if (node != null) {
                script.insertNodeAtEnd(node);
            } else {
                // handle e.g. a document like "<script>"
                lexer.report.error(lexer, script, null, Report.MISSING_ENDTAG_FOR);
                return;
            }
            node = lexer.getToken(Lexer.IGNORE_WHITESPACE);
            if (	!(node != null && node.type == Node.END_TAG && node.tag != null
            	&&	node.tag.name.equalsIgnoreCase(script.tag.name))) {
                lexer.report.error(lexer, script, node, Report.MISSING_ENDTAG_FOR);
                if (node != null)
                	lexer.ungetToken();
            }
        }
    }

    /**
     * Parser for BODY.
     */
    public static class ParseBody implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node body, short mode)
        {
            Node node;
            boolean checkstack, iswhitenode;

            mode = Lexer.IGNORE_WHITESPACE;
            checkstack = true;
            TagTable tt = lexer.configuration.tt;

            Clean.bumpObject(lexer, body.parent);

            while ((node = lexer.getToken(mode)) != null)
            {
            	// XOWA:tidy
				// find and discard multiple <body> elements
				if (node.tag == body.tag && node.type == Node.START_TAG)
				{
					lexer.report.warning(lexer, body, node, Report.DISCARDING_UNEXPECTED);
				    continue;
				}
				
                // #538536 Extra endtags not detected
                if (node.tag == tt.tagHtml)
                {
                    if (node.type == Node.START_TAG || node.type == Node.START_END_TAG || lexer.seenEndHtml)
                        lexer.report.warning(lexer, body, node, Report.DISCARDING_UNEXPECTED);
                    else
                        lexer.seenEndHtml = true;

                    continue;
                }

                if (	lexer.seenEndBody
                    && (	node.type == Node.START_TAG
                    	||	node.type == Node.END_TAG
                    	||	node.type == Node.START_END_TAG
                    	)
                    )
                {
                    lexer.report.warning(lexer, body, node, Report.CONTENT_AFTER_BODY);
                }

                if (node.tag == body.tag && node.type == Node.END_TAG)
                {
                    body.closed = true;
                    Node.trimSpaces(lexer, body);
                    lexer.seenEndBody = true;
                    mode = Lexer.IGNORE_WHITESPACE;

                    if (body.parent.tag == tt.tagNoframes)
                        break;

                    continue;
                }

                if (node.tag == tt.tagNoframes)
                {
                    if (node.type == Node.START_TAG)
                    {
                        body.insertNodeAtEnd(node);
                        BLOCK.parse(lexer, node, mode);
                        continue;
                    }

                    if (node.type == Node.END_TAG && body.parent.tag == tt.tagNoframes)
                    {
                        Node.trimSpaces(lexer, body);
                        lexer.ungetToken();
                        break;
                    }
                }

                if (	(node.tag == tt.tagFrame || node.tag == tt.tagFrameset)
                	&& 	body.parent.tag == tt.tagNoframes
                	)
                {
                    Node.trimSpaces(lexer, body);
                    lexer.ungetToken();
                    break;
                }

                iswhitenode = false;

                if (node.type == Node.TEXT_NODE
                    && node.end <= node.start + 1
                    && node.textarray[node.start] == (byte) ' ')
                {
                    iswhitenode = true;
                }

                // deal with comments etc.
                if (Node.insertMisc(body, node))
                    continue;

                // #538536 Extra endtags not detected
                // if (lexer.seenEndBody && !iswhitenode)
                // {
                // 		lexer.seenEndBody = true;
                // 		lexer.report.warning(lexer, body, node, Report.CONTENT_AFTER_BODY);
                // }

                // mixed content model permits text
                if (node.type == Node.TEXT_NODE)
                {
                    if (iswhitenode && mode == Lexer.IGNORE_WHITESPACE)
                    {
                        continue;
                    }

                    // XOWA:jtidy; re-enabled; DATE:2015-11-08
                    if (lexer.configuration.encloseBodyText && !iswhitenode)
                    {
                        Node para;

                        lexer.ungetToken();
                        para = lexer.inferredTag("p");
                        body.insertNodeAtEnd(para);
                        parseTag(lexer, para, mode);
                        mode = Lexer.MIXED_CONTENT;
                        continue;
                    }

                    // HTML2 and HTML4 strict doesn't allow text here
                    lexer.constrainVersion(~(Dict.VERS_HTML40_STRICT | Dict.VERS_HTML20));

                    if (checkstack)
                    {
                        checkstack = false;

                        if (lexer.inlineDup(node) > 0)
                            continue;
                    }

                    body.insertNodeAtEnd(node);
                    mode = Lexer.MIXED_CONTENT;
                    continue;
                }

                if (node.type == Node.DOCTYPE_TAG)
                {
                    Node.insertDocType(lexer, body, node);
                    continue;
                }
                // discard unknown and PARAM tags
                if (node.tag == null || node.tag == tt.tagParam)
                {
                    lexer.report.warning(lexer, body, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // Netscape allows LI and DD directly in BODY We infer UL or DL respectively and use this boolean to
                // exclude block-level elements so as to match Netscape's observed behaviour.
                lexer.excludeBlocks = false;

                if (	node.tag == tt.tagInput
                	||	(!((node.tag.model & Dict.CM_BLOCK) != 0) && !((node.tag.model & Dict.CM_INLINE) != 0))
                    )
                {
                    // avoid this error message being issued twice
                    if (!((node.tag.model & Dict.CM_HEAD) != 0))
                        lexer.report.warning(lexer, body, node, Report.TAG_NOT_ALLOWED_IN);

                    if ((node.tag.model & Dict.CM_HTML) != 0)
                    {
                        // copy body attributes if current body was inferred
                        if (	node.tag == tt.tagBody && body.implicit
                        	&&	body.attributes == null)
                        {
                            body.attributes = node.attributes;
                            node.attributes = null;
                        }

                        continue;
                    }

                    if ((node.tag.model & Dict.CM_HEAD) != 0)
                    {
                        moveToHead(lexer, body, node);
                        continue;
                    }

                    if ((node.tag.model & Dict.CM_LIST) != 0)
                    {
                        lexer.ungetToken();
                        node = lexer.inferredTag("ul");
                        node.addClass("noindent");
                        lexer.excludeBlocks = true;
                    }
                    else if ((node.tag.model & Dict.CM_DEFLIST) != 0)
                    {
                        lexer.ungetToken();
                        node = lexer.inferredTag("dl");
                        lexer.excludeBlocks = true;
                    }
                    else if ((node.tag.model & (Dict.CM_TABLE | Dict.CM_ROWGRP | Dict.CM_ROW)) != 0)
                    {
                        // http://tidy.sf.net/issue/2855621
                        if (node.type != Node.END_TAG) {
                        	lexer.ungetToken();
                        	node = lexer.inferredTag("table");
                        }
                        lexer.excludeBlocks = true;
                    }
                    else if (node.tag == tt.tagInput)
                    {
                        lexer.ungetToken();
                        node = lexer.inferredTag("form");
                        lexer.excludeBlocks = true;
                    }
                    else
                    {
                        if (!((node.tag.model & (Dict.CM_ROW | Dict.CM_FIELD)) != 0))
                        {
                            lexer.ungetToken();
                            return;
                        }

                        // ignore </td></th> <option> etc.
                        continue;
                    }
                }

                if (node.type == Node.END_TAG)
                {
                    if (node.tag == tt.tagBr)
                        node.type = Node.START_TAG;
                    else if (node.tag == tt.tagP)
                    {
                        node.type = Node.START_END_TAG;	// XOWA:tidy
                        node.implicit = true;			// XOWA:tidy
                        // XOWA: tidy; obsolete
//                        Node.coerceNode(lexer, node, tt.tagBr);
//                        tt.freeAttrs(node);				// XOWA:tidy
//                        body.insertNodeAtEnd(node);
//                        node = lexer.inferredTag("br");
                    }
                    else if ((node.tag.model & Dict.CM_INLINE) != 0)
                        lexer.popInline(node);
                }

                if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                {
                    if (((node.tag.model & Dict.CM_INLINE) != 0) && !((node.tag.model & Dict.CM_MIXED) != 0))
                    {
                        // HTML4 strict doesn't allow inline content here
                        // but HTML2 does allow img elements as children of body
                        if (node.tag == tt.tagImg)
                            lexer.constrainVersion(~Dict.VERS_HTML40_STRICT);
                        else
                            lexer.constrainVersion(~(Dict.VERS_HTML40_STRICT | Dict.VERS_HTML20));

                        if (checkstack && !node.implicit)
                        {
                            checkstack = false;

                            if (lexer.inlineDup(node) > 0)
                                continue;
                        }

                        mode = Lexer.MIXED_CONTENT;
                    }
                    else
                    {
                        checkstack = true;
                        mode = Lexer.IGNORE_WHITESPACE;
                    }

                    if (node.implicit)
                        lexer.report.warning(lexer, body, node, Report.INSERTING_TAG);

                    body.insertNodeAtEnd(node);
                    parseTag(lexer, node, mode);
                    continue;
                }

                // discard unexpected tags
                lexer.report.warning(lexer, body, node, Report.DISCARDING_UNEXPECTED);
            }
        }
    }

    /**
     * Parser for FRAMESET.
     */
    public static class ParseFrameSet implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node frameset, short mode)
        {
            Node node;
            TagTable tt = lexer.configuration.tt;

            lexer.badAccess |= Report.USING_FRAMES;

            while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
            {
                if (node.tag == frameset.tag && node.type == Node.END_TAG)
                {
                    frameset.closed = true;
                    Node.trimSpaces(lexer, frameset);
                    return;
                }

                // deal with comments etc.
                if (Node.insertMisc(frameset, node))
                    continue;

                if (node.tag == null)
                {
                    lexer.report.warning(lexer, frameset, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                {
                    if (node.tag != null && (node.tag.model & Dict.CM_HEAD) != 0)
                    {
                        moveToHead(lexer, frameset, node);
                        continue;
                    }
                }

                if (node.tag == tt.tagBody)
                {
                    lexer.ungetToken();
                    node = lexer.inferredTag("noframes");
                    lexer.report.warning(lexer, frameset, node, Report.INSERTING_TAG);
                }

                if (node.type == Node.START_TAG && (node.tag.model & Dict.CM_FRAMES) != 0)
                {
                    frameset.insertNodeAtEnd(node);
                    lexer.excludeBlocks = false;
                    parseTag(lexer, node, Lexer.MIXED_CONTENT);
                    continue;
                }
                else if (node.type == Node.START_END_TAG && (node.tag.model & Dict.CM_FRAMES) != 0)
                {
                    frameset.insertNodeAtEnd(node);
                    continue;
                }

                // discard unexpected tags
                lexer.report.warning(lexer, frameset, node, Report.DISCARDING_UNEXPECTED);
            }

            lexer.report.warning(lexer, frameset, node, Report.MISSING_ENDTAG_FOR);
        }
    }

    /**
     * Parser for INLINE.
     */
    public static class ParseInline implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node element, short mode)
        {
            Node node, parent;
            TagTable tt = lexer.configuration.tt;

            if (TidyUtils.toBoolean(element.tag.model & Dict.CM_EMPTY))
                return;

            // ParseInline is used for some block level elements like H1 to H6 For such elements we need to insert
            // inline emphasis tags currently on the inline stack. For Inline elements, we normally push them onto the
            // inline stack provided they aren't implicit or OBJECT/APPLET. This test is carried out in PushInline and
            // PopInline, see istack.c We don't push SPAN to replicate current browser behavior
            if (	TidyUtils.toBoolean(element.tag.model & Dict.CM_BLOCK) || (element.tag == tt.tagDt)
            	&& !TidyUtils.toBoolean(element.tag.model & Dict.CM_MIXED)		// XOWA:tidy; DATE:2014-06-02
            	)
                lexer.inlineDup(null);
            else if (TidyUtils.toBoolean(element.tag.model & Dict.CM_INLINE)
                    // EUNYEE: Add back this condition 
                    // because this causes the infinite loop problem when the span does not have the ending tag.
            		// && element.tag != tt.tagA && element.tag != tt.tagSpan	// XOWA:jtidy; DATE:2014-06-02
                    )
            {
                // && element.tag != tt.tagSpan #540571 Inconsistent behaviour with span inline element
                lexer.pushInline(element);
            }

            if (element.tag == tt.tagNobr)
                lexer.badLayout |= Report.USING_NOBR;
            else if (element.tag == tt.tagFont)
                lexer.badLayout |= Report.USING_FONT;

            // Inline elements may or may not be within a preformatted element
            if (mode != Lexer.PREFORMATTED)
                mode = Lexer.MIXED_CONTENT;

            while ((node = lexer.getToken(mode)) != null)
            {
                // end tag for current element
                if (node.tag == element.tag && node.type == Node.END_TAG)
                {
                    if (TidyUtils.toBoolean(element.tag.model & Dict.CM_INLINE))
                        lexer.popInline(node);

                    if (!TidyUtils.toBoolean(mode & Lexer.PREFORMATTED))
                        Node.trimSpaces(lexer, element);

                    // if a font element wraps an anchor and nothing else then move the font element inside the anchor
                    // since otherwise it won't alter the anchor text color
                    if (	element.tag == tt.tagFont
                    	&& 	element.content != null && element.content == element.last)
                    {
                        Node child = element.content;

                        if (child.tag == tt.tagA)
                        {
                            child.parent = element.parent;
                            child.next = element.next;
                            child.prev = element.prev;

                            // XOWA:jtidy.bgn
							// if (child.prev != null)
							// {
							//     child.prev.next = child;
							// }
							// else
							// {
							//     child.parent.content = child;
							// }
							//
							// if (child.next != null)
							// {
							//     child.next.prev = child;
							// }
							// else
							// {
							//     child.parent.last = child;
							// }
                            // XOWA:jtidy.end

                            element.next = null;
                            element.prev = null;
                            element.parent = child;
                            
                            element.content = child.content;
                            element.last = child.last;
                            child.content = element;
                            
                            Clean.fixNodeLinks(child);	// XOWA:tidy
                            Clean.fixNodeLinks(element);// XOWA:tidy
                            // XOWA:jtidy
                            // child.last = element;
                            // for (child = element.content; child != null; child = child.next)
                            // {
                            //     child.parent = element;
                            // }
                        }
                    }
                    element.closed = true;
                    Node.trimSpaces(lexer, element);
                    Node.trimEmptyElement(lexer, element); // XOWA:jtidy
                    return;
                }

                // <u> ... <u> map 2nd <u> to </u> if 1st is explicit
                // otherwise emphasis nesting is probably unintentional
                // big and small have cumulative effect to leave them alone
                if (	node.type == Node.START_TAG
                    &&	node.tag == element.tag
                    &&	lexer.isPushed(node)
                    &&	!node.implicit
                    &&	!element.implicit
                    &&	node.tag != null && ((node.tag.model & Dict.CM_INLINE) != 0)
                    &&	node.tag != tt.tagA
                    &&	node.tag != tt.tagFont
                    &&	node.tag != tt.tagBig
                    &&	node.tag != tt.tagSmall
                    &&	node.tag != tt.tagSub	// XOWA:tidy; DATE:2014-06-02
                    &&	node.tag != tt.tagSup	// XOWA:tidy; DATE:2014-06-02
                    &&	node.tag != tt.tagQ
                    &&	node.tag != tt.tagSpan	// XOWA:tidy; DATE:2014-06-02
                    )
                {
                    if 	(	element.content != null && node.attributes == null
                    	&& 	element.last.type == Node.TEXT_NODE	// XOWA:tidy; DATE:2014-06-02
                    	&& 	PPrint.TextNodeEndWithSpace(lexer, element.last) 
                       )
                    {
                        lexer.report.warning(lexer, element, node, Report.COERCE_TO_ENDTAG);
                        node.type = Node.END_TAG;
                        lexer.ungetToken();
                        continue;
                    }

                    if (node.attributes == null || element.attributes == null)	// XOWA:tidy
                    	lexer.report.warning(lexer, element, node, Report.NESTED_EMPHASIS);
                }
                else if (	lexer.isPushed(node) && node.type == Node.START_TAG
                		&&	node.tag == tt.tagQ)
                {
                    lexer.report.warning(lexer, element, node, Report.NESTED_QUOTATION);
                }

                if (node.type == Node.TEXT_NODE)
                {
                    // only called for 1st child
                    if (element.content == null && !TidyUtils.toBoolean(mode & Lexer.PREFORMATTED))
                        Node.trimSpaces(lexer, element);

                    if (node.start >= node.end)
                    {
                        continue;
                    }

                    element.insertNodeAtEnd(node);
                    continue;
                }

                // mixed content model so allow text
                if (Node.insertMisc(element, node))
                    continue;

                // deal with HTML tags
                if (node.tag == tt.tagHtml)
                {
                    if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                    {
                        lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    // otherwise infer end of inline element
                    lexer.ungetToken();
                    
                    if (!((mode & Lexer.PREFORMATTED) != 0))
                        Node.trimSpaces(lexer, element);
                    Node.trimEmptyElement(lexer, element);	// XOWA:jtidy; DATE:2014-06-02
                    return;
                }

                // within <dt> or <pre> map <p> to <br>
                if	(	node.tag == tt.tagP
                    &&	node.type == Node.START_TAG
                    &&	(	(mode & Lexer.PREFORMATTED) != 0
                    	||	element.tag == tt.tagDt
                    	||	element.isDescendantOf(tt.tagDt)
                    	)
                    )
                {
                    node.tag = tt.tagBr;
                    node.element = "br";
                    Node.trimSpaces(lexer, element);
                    element.insertNodeAtEnd(node);
                    continue;
                }

                // XOWA:tidy
                // <p> allowed within <address> in HTML 4.01 Transitional 
                if	(	node.tag == tt.tagP
                	&&	node.type == Node.START_TAG
                	&&	element.tag == tt.tagAddress
                	)
                {
                	lexer.constrainVersion(~Dict.VERS_HTML40_STRICT);
                	element.insertNodeAtEnd(node);
                	parseTag(lexer, node, mode);
                    continue;
                }

                // ignore unknown and PARAM tags
                if (node.tag == null || node.tag == tt.tagParam)
                {
                    lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                if (node.tag == tt.tagBr && node.type == Node.END_TAG)
                    node.type = Node.START_TAG;

                if (node.type == Node.END_TAG)
                {
                    // coerce </br> to <br>
                    if (node.tag == tt.tagBr)
                        node.type = Node.START_TAG;
                    else if (node.tag == tt.tagP)
                    {
                        // coerce unmatched </p> to <br><br>
                        if (!element.isDescendantOf(tt.tagP))
                        {
                            Node.coerceNode(lexer, node, tt.tagBr);
                            Node.trimSpaces(lexer, element);
                            element.insertNodeAtEnd(node);
                            node = lexer.inferredTag("br");
                            element.insertNodeAtEnd(node); // todo: check this
                            continue;
                        }
                    }
                    else if (	((node.tag.model & Dict.CM_INLINE) != 0)
                    		&&	node.tag != tt.tagA
                    		&& 	!((node.tag.model & Dict.CM_OBJECT) != 0)
                    		&& ((element.tag.model & Dict.CM_INLINE) != 0)
                    		)
                    {
                    	// XOWA:tidy
                        // http://tidy.sf.net/issue/1426419
                        // but, like the browser, retain an earlier inline element.
                        // This is implemented by setting the lexer into a mode
                        // where it gets tokens from the inline stack rather than
                        // from the input stream. Check if the scenerio fits. */
                    	if 	(	element.tag != tt.tagA
                    		&&	node.tag != element.tag
                    		&&  lexer.isPushed(node)	
                    		&&  lexer.isPushed(element)	
                    		) {
                            // we have something like
                            // <b>bold <i>bold and italic</b> italics</i>                    	
                    		if (lexer.switchInline(element, node))
                    		{
                    			lexer.report.warning(lexer, element, node, Report.NON_MATCHING_ENDTAG);
                    			lexer.ungetToken();	// put this back
                    			lexer.inlineDup1(node, element); // dupe the <i>, after </b>
                                if (!TidyUtils.toBoolean(mode & Lexer.PREFORMATTED))
                                    Node.trimSpaces(lexer, element);
                                return; // close <i>, but will re-open it, after </b>
                    		}
                    	}

                        lexer.popInline(element);	// allow any inline end tag to end current element

                        if (element.tag != tt.tagA)
                        {
                            if (node.tag == tt.tagA && node.tag != element.tag)
                            {
                                lexer.report.warning(lexer, element, node, Report.MISSING_ENDTAG_BEFORE);
                                lexer.ungetToken();
                            }
                            else
                            {
                                lexer.report.warning(lexer, element, node, Report.NON_MATCHING_ENDTAG);
                            }

                            if (!TidyUtils.toBoolean(mode & Lexer.PREFORMATTED))
                                Node.trimSpaces(lexer, element);
                            Node.trimEmptyElement(lexer, element);	// XOWA:jtidy;
                            return;
                        }

                        // if parent is <a> then discard unexpected inline end tag
                        lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    } // special case </tr> etc. for stuff moved in front of table
                    else if (	lexer.exiled
                    		&&	node.tag.model != 0
                    		&&	(	(node.tag.model & Dict.CM_TABLE) != 0
                    			||	node.tag == tt.tagTable		// XOWA:tidy
                    			)
                    		)
                    {
                        lexer.ungetToken();
                        Node.trimSpaces(lexer, element);
                        Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                        return;
                    }
                }

                // allow any header tag to end current header
                if ((node.tag.model & Dict.CM_HEADING) != 0 && (element.tag.model & Dict.CM_HEADING) != 0)
                {
                    if (node.tag == element.tag)
                    {
                        lexer.report.warning(lexer, element, node, Report.NON_MATCHING_ENDTAG);
                    }
                    else
                    {
                        lexer.report.warning(lexer, element, node, Report.MISSING_ENDTAG_BEFORE);
                        lexer.ungetToken();
                    }
                    if (!TidyUtils.toBoolean(mode & Lexer.PREFORMATTED))
                        Node.trimSpaces(lexer, element);
                    Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                    return;
                }

                // an <A> tag to ends any open <A> element but <A href=...> is mapped to </A><A href=...>
                // #427827 - fix by Randy Waki and Bjoern Hoehrmann 23 Aug 00
                // if (node.tag == tt.tagA && !node.implicit && lexer.isPushed(node))
                if	(	node.tag == tt.tagA
                    &&	!node.implicit
                    && 	(element.tag == tt.tagA || element.isDescendantOf(tt.tagA))
                    )
                {
                    // coerce <a> to </a> unless it has some attributes
                    // #427827 - fix by Randy Waki and Bjoern Hoehrmann 23 Aug 00
                    // other fixes by Dave Raggett
                    // if (node.attributes == null)
                    if (node.type != Node.END_TAG && node.attributes == null)
                    {
                        node.type = Node.END_TAG;
                        lexer.report.warning(lexer, element, node, Report.COERCE_TO_ENDTAG);
                        // lexer.popInline(node);
                        lexer.ungetToken();
                        continue;
                    }

                    lexer.ungetToken();
                    lexer.report.warning(lexer, element, node, Report.MISSING_ENDTAG_BEFORE);
                    // lexer.popInline(element);
                    if (!TidyUtils.toBoolean(mode & Lexer.PREFORMATTED))
                        Node.trimSpaces(lexer, element);
                    Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                    return;
                }

                if ((element.tag.model & Dict.CM_HEADING) != 0)
                {
                    if (node.tag == tt.tagCenter || node.tag == tt.tagDiv)
                    {
                        if (node.type != Node.START_TAG && node.type != Node.START_END_TAG)
                        {
                            lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                            continue;
                        }

                        lexer.report.warning(lexer, element, node, Report.TAG_NOT_ALLOWED_IN);

                        // insert center as parent if heading is empty
                        if (element.content == null)
                        {
                            Node.insertNodeAsParent(element, node);
                            continue;
                        }

                        // split heading and make center parent of 2nd part
                        element.insertNodeAfterElement(node);

                        if (!((mode & Lexer.PREFORMATTED) != 0))
                            Node.trimSpaces(lexer, element);

                        element = lexer.cloneNode(element);
                        element.start = lexer.lexsize; element.end = lexer.lexsize;	// XOWA:jtidy; Clone implemented differently
                        node.insertNodeAtEnd(element);
                        continue;
                    }

                    if (node.tag == tt.tagHr)
                    {
                        if (node.type != Node.START_TAG && node.type != Node.START_END_TAG)
                        {
                            lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                            continue;
                        }

                        lexer.report.warning(lexer, element, node, Report.TAG_NOT_ALLOWED_IN);

                        // insert hr before heading if heading is empty
                        if (element.content == null)
                        {
                            Node.insertNodeBeforeElement(element, node);
                            continue;
                        }

                        // split heading and insert hr before 2nd part
                        element.insertNodeAfterElement(node);

                        if (!((mode & Lexer.PREFORMATTED) != 0))
                            Node.trimSpaces(lexer, element);

                        element = lexer.cloneNode(element);
                        element.start = lexer.lexsize; element.end = lexer.lexsize;	// XOWA:jtidy; Clone implemented differently
                        node.insertNodeAfterElement(element);
                        continue;
                    }
                }

                if (element.tag == tt.tagDt)
                {
                    if (node.tag == tt.tagHr)
                    {
                        Node dd;

                        if (node.type != Node.START_TAG && node.type != Node.START_END_TAG)
                        {
                            lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                            continue;
                        }

                        lexer.report.warning(lexer, element, node, Report.TAG_NOT_ALLOWED_IN);
                        dd = lexer.inferredTag("dd");

                        // insert hr within dd before dt if dt is empty
                        if (element.content == null)
                        {
                            Node.insertNodeBeforeElement(element, dd);
                            dd.insertNodeAtEnd(node);
                            continue;
                        }

                        // split dt and insert hr within dd before 2nd part
                        element.insertNodeAfterElement(dd);
                        dd.insertNodeAtEnd(node);

                        if (!((mode & Lexer.PREFORMATTED) != 0))
                            Node.trimSpaces(lexer, element);

                        element = lexer.cloneNode(element);
                        element.start = lexer.lexsize; element.end = lexer.lexsize;
                        dd.insertNodeAfterElement(element);
                        continue;
                    }
                }

                // if this is the end tag for an ancestor element then infer end tag for this element
                if (node.type == Node.END_TAG)
                {
                    for (parent = element.parent; parent != null; parent = parent.parent)
                    {
                        if (node.tag == parent.tag)
                        {
                            if (!((element.tag.model & Dict.CM_OPT) != 0) && !element.implicit)
                                lexer.report.warning(lexer, element, node, Report.MISSING_ENDTAG_BEFORE);

                            // if (element.tag == tt.tagA) // XOWA:jtidy
                            if( lexer.isPushedLast(element, node) ) 
                                lexer.popInline(element);
                            lexer.ungetToken();

                            if (!((mode & Lexer.PREFORMATTED) != 0))
                                Node.trimSpaces(lexer, element);

                            Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                            return;
                        }
                    }
                }

                // block level tags end this element
                if (	!((node.tag.model & Dict.CM_INLINE) != 0)
                	&&	!((element.tag.model & Dict.CM_MIXED) != 0)  // XOWA:tidy;
                	)
                {
                    if (node.type != Node.START_TAG && node.type != Node.START_END_TAG)	// XOWA:tidy "&& node.type != Node.START_END_TAG" was missing
                    {
                        lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    if (!((element.tag.model & Dict.CM_OPT) != 0))
                        lexer.report.warning(lexer, element, node, Report.MISSING_ENDTAG_BEFORE);

                    if ((node.tag.model & Dict.CM_HEAD) != 0 && !((node.tag.model & Dict.CM_BLOCK) != 0))
                    {
                        moveToHead(lexer, element, node);
                        continue;
                    }

                    // prevent anchors from propagating into block tags except for headings h1 to h6
                    if (element.tag == tt.tagA)
                    {
                        if (node.tag != null && !((node.tag.model & Dict.CM_HEADING) != 0))
                            lexer.popInline(element);
                        else if (!(element.content != null))
                        {
                            Node.discardElement(element);
                            lexer.ungetToken();
                            return;
                        }
                    }

                    lexer.ungetToken();

                    if (!((mode & Lexer.PREFORMATTED) != 0))
                        Node.trimSpaces(lexer, element);

                    Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                    return;
                }

                // parse inline element
                if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                {
                    if (node.implicit)
                        lexer.report.warning(lexer, element, node, Report.INSERTING_TAG);

                    // trim white space before <br>
                    if (node.tag == tt.tagBr)
                        Node.trimSpaces(lexer, element);

                    element.insertNodeAtEnd(node);
                    parseTag(lexer, node, mode);
                    continue;
                }

                // discard unexpected tags
                lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                continue;
            }

            if (!((element.tag.model & Dict.CM_OPT) != 0))
                lexer.report.warning(lexer, element, node, Report.MISSING_ENDTAG_FOR);

            Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
        }
    }

    /**
     * Parser for LIST.
     */
    public static class ParseList implements Parser
    {
        public void parse(Lexer lexer, Node list, short mode)
        {
            Node node;
            Node parent;
            TagTable tt = lexer.configuration.tt;

            if ((list.tag.model & Dict.CM_EMPTY) != 0)
                return;

            lexer.insert = -1; // defer implicit inline start tags

            while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
            {
                if (node.tag == list.tag && node.type == Node.END_TAG)
                {
                	// XOWA:jtidy
                    // if ((list.tag.model & Dict.CM_OBSOLETE) != 0)
                    // {
                    //    Node.coerceNode(lexer, list, tt.tagUl);
                    // }
                    list.closed = true;
                    Node.trimEmptyElement(lexer, list);	// XOWA:jtidy
                    return;
                }

                // deal with comments etc.
                if (Node.insertMisc(list, node))
                    continue;

                if (node.type != Node.TEXT_NODE && node.tag == null)
                {
                    lexer.report.warning(lexer, list, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // if this is the end tag for an ancestor element then infer end tag for this element
                if (node.type == Node.END_TAG)
                {
                    if (node.tag == tt.tagForm)
                    {
                        badForm(lexer);
                        lexer.report.warning(lexer, list, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    if (node.tag != null && (node.tag.model & Dict.CM_INLINE) != 0)
                    {
                        lexer.report.warning(lexer, list, node, Report.DISCARDING_UNEXPECTED);
                        lexer.popInline(node);
                        continue;
                    }

                    for (parent = list.parent; parent != null; parent = parent.parent)
                    {
                    	// XOWA:tidy
                        // Do not match across BODY to avoid infinite loop
                        // between ParseBody and this parser,
                        // See http://tidy.sf.net/bug/1053626.
                    	if (parent.tag == tt.tagBody)
                    		break;
                        if (node.tag == parent.tag)
                        {
                            lexer.report.warning(lexer, list, node, Report.MISSING_ENDTAG_BEFORE);
                            lexer.ungetToken();

                            // XOWA:jtidy
                            // if ((list.tag.model & Dict.CM_OBSOLETE) != 0)
                            // {
                            //    Node.coerceNode(lexer, list, tt.tagUl);
                            // }
                            Node.trimEmptyElement(lexer, list); // XOWA:jtidy
                            return;
                        }
                    }

                    lexer.report.warning(lexer, list, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                if (node.tag != tt.tagLi)
                {
                    lexer.ungetToken();

                    if (node.tag != null && (node.tag.model & Dict.CM_BLOCK) != 0 && lexer.excludeBlocks)
                    {
                        lexer.report.warning(lexer, list, node, Report.MISSING_ENDTAG_BEFORE);
                        Node.trimEmptyElement(lexer, list);	// XOWA:jtidy
                        return;
                    }
                    // XOWA:tidy
                    // http://tidy.sf.net/issue/1316307
                    // In exiled mode, return so table processing can continue.
                    else if (	lexer.exiled
                    		&& 	(	((node.tag.model & (Dict.CM_TABLE | Dict.CM_ROWGRP | Dict.CM_ROW)) != 0)// TY_(nodeHasCM)(node, CM_TABLE|CM_ROWGRP|CM_ROW)
                                 ||	node.tag == tt.tagTable)
                    		)
                        return;
                    
                    // XOWA:tidy;
                    // http://tidy.sf.net/issue/836462
                    // If "list" is an unordered list, insert the next tag within
                    // the last <li> to preserve the numbering to match the visual
                    // rendering of most browsers.                    
                    Node lastli = lexer.findLastLI(list); 
                    if (	list.tag == tt.tagOl 
                    	&& 	lastli != null
                    	)
                    {
                    	// Create a node for error reporting
                    	node = lexer.inferredTag("li");
                    	lexer.report.warning(lexer, list, node, Report.MISSING_STARTTAG);
                    	node = lastli;
                    }
					else
					{
						 // Add an inferred <li>
						boolean wasblock = node.tag != null && ((node.tag.model & Dict.CM_BLOCK) != 0);		// XOWA:xowa;null check; DATE:2014-06-06
						node = lexer.inferredTag("li");
					 	// Add "display: inline" to avoid a blank line after <li> with 
					 	// Internet Explorer. See http://tidy.sf.net/issue/836462
	                    node.addAttribute("style"
	                    	, wasblock
	                    	? "list-style: none; display: inline"
	                    	: "list-style: none"
	                    	);
	                    lexer.report.warning(lexer, list, node, Report.MISSING_STARTTAG);
	                    list.insertNodeAtEnd(node);
					}
					
                    // XOWA:jtidy
                    // node = lexer.inferredTag("li");
                    // node.addAttribute("style", "list-style: none");
                    // lexer.report.warning(lexer, list, node, Report.MISSING_STARTTAG);
                }
                else
                	// node should be <LI>
                	list.insertNodeAtEnd(node);
                parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);
            }

            // XOWA:jtidy
            // if ((list.tag.model & Dict.CM_OBSOLETE) != 0)
            // {
            //    Node.coerceNode(lexer, list, tt.tagUl);
            // }
            lexer.report.warning(lexer, list, node, Report.MISSING_ENDTAG_FOR);
            Node.trimEmptyElement(lexer, list);	// XOWA:jtidy
        }

    }

    /**
     * Parser for empty elements.
     */
    public static class ParseEmpty implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node element, short mode)
        {
            if (lexer.isvoyager)
            {
                Node node = lexer.getToken(mode);
                if (node != null) 
                {
                	if  (!(node.type == Node.END_TAG && node.tag == element.tag))
                	{
                        lexer.report.warning(lexer, element, node, Report.ELEMENT_NOT_EMPTY);
                        lexer.ungetToken();                		
                	}
                }
            }
        }
    }

    /**
     * Parser for DEFLIST.
     */
    public static class ParseDefList implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node list, short mode)
        {
            Node node, parent;
            TagTable tt = lexer.configuration.tt;

            if ((list.tag.model & Dict.CM_EMPTY) != 0)
                return;

            lexer.insert = -1; // defer implicit inline start tags

            while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
            {
                if (node.tag == list.tag && node.type == Node.END_TAG)
                {
                    list.closed = true;
                    Node.trimEmptyElement(lexer, list);	// XOWA:jtidy
                    return;
                }

                // deal with comments etc.
                if (Node.insertMisc(list, node))
                    continue;

                if (node.type == Node.TEXT_NODE)
                {
                    lexer.ungetToken();
                    node = lexer.inferredTag("dt");
                    lexer.report.warning(lexer, list, node, Report.MISSING_STARTTAG);
                }

                if (node.tag == null)
                {
                    lexer.report.warning(lexer, list, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // if this is the end tag for an ancestor element then infer end tag for this element
                if (node.type == Node.END_TAG)
                {
                	boolean discardIt = false;
                    if (node.tag == tt.tagForm)
                    {
                        badForm(lexer);
                        lexer.report.warning(lexer, list, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    for (parent = list.parent; parent != null; parent = parent.parent)
                    {
						// Do not match across BODY to avoid infinite loop
						// between ParseBody and this parser,
						// See http://tidy.sf.net/bug/1098012.
						if (parent.tag == tt.tagBody)
						{
							discardIt = true;
						    break;
						}
                        if (node.tag == parent.tag)
                        {
                            lexer.report.warning(lexer, list, node, Report.MISSING_ENDTAG_BEFORE);

                            lexer.ungetToken();
                            Node.trimEmptyElement(lexer, list);	// XOWA:jtidy
                            return;
                        }
                    }
                    if (discardIt)
                    {
                    	lexer.report.warning(lexer, list, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }
                }

                // center in a dt or a dl breaks the dl list in two
                if (node.tag == tt.tagCenter)
                {
                    if (list.content != null)
                        list.insertNodeAfterElement(node);
                    else
                    {
                        // trim empty dl list
                        Node.insertNodeBeforeElement(list, node);

                        // #540296 tidy dumps with empty definition list
                        // Node.discardElement(list);	// XOWA:tidy; disabled inside conditional compilation
                    }

					// #426885 - fix by Glenn Carroll 19 Apr 00, and
					// Gary Dechaines 11 Aug 00
					
					// ParseTag can destroy node, if it finds that
					// this <center> is followed immediately by </center>.
					// It's awkward but necessary to determine if this
					// has happened.
					parent = node.parent;
					
					// and parse contents of center
					lexer.excludeBlocks = false;
					parseTag(lexer, node, mode);
					lexer.excludeBlocks = true;
					
					/* now create a new dl element,
					* unless node has been blown away because the
					* center was empty, as above.
					*/
					if (parent.last == node)
					{
						list = lexer.inferredTag("dl");
						node.insertNodeAfterElement(list);
					}

					// XOWA:jtidy
//       				// and parse contents of center
//                    parseTag(lexer, node, mode);
//
//                    // now create a new dl element
//                    list = lexer.inferredTag("dl");
//                    node.insertNodeAfterElement(list);
                    continue;
                }

                if (!(node.tag == tt.tagDt || node.tag == tt.tagDd))
                {
                    lexer.ungetToken();

                    if (!((node.tag.model & (Dict.CM_BLOCK | Dict.CM_INLINE)) != 0))
                    {
                        lexer.report.warning(lexer, list, node, Report.TAG_NOT_ALLOWED_IN);
                        Node.trimEmptyElement(lexer, list);	// XOWA:jtidy
                        return;
                    }

                    // if DD appeared directly in BODY then exclude blocks
                    if (!((node.tag.model & Dict.CM_INLINE) != 0) && lexer.excludeBlocks)
                    {
                        Node.trimEmptyElement(lexer, list);	// XOWA:jtidy
                        return;
                    }

                    node = lexer.inferredTag("dd");
                    lexer.report.warning(lexer, list, node, Report.MISSING_STARTTAG);
                }

                if (node.type == Node.END_TAG)
                {
                    lexer.report.warning(lexer, list, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // node should be <DT> or <DD>
                list.insertNodeAtEnd(node);
                parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);
            }

            lexer.report.warning(lexer, list, node, Report.MISSING_ENDTAG_FOR);
            Node.trimEmptyElement(lexer, list);	// XOWA:jtidy
        }
    }

    /**
     * Parser for PRE.
     */
    public static class ParsePre implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node pre, short mode)
        {
            Node node;
            TagTable tt = lexer.configuration.tt;

            if ((pre.tag.model & Dict.CM_EMPTY) != 0)
                return;

            // if ((pre.tag.model & Dict.CM_OBSOLETE) != 0)	// XOWA:jtidy
            //    Node.coerceNode(lexer, pre, tt.tagPre);		// XOWA:jtidy

            lexer.inlineDup(null); // tell lexer to insert inlines if needed

            while ((node = lexer.getToken(Lexer.PREFORMATTED)) != null)
            {
                if (	node.type == Node.END_TAG
                	&& 	(	node.tag == pre.tag 
                		||	node.isDescendantOf(tt.tagPre) // XOWA:tidy
                		)
                	)
                {
                    if (node.tag == tt.tagBody || node.tag == tt.tagHtml)
                    {
                    	lexer.report.warning(lexer, pre, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }
                    if (node.tag == pre.tag) 
                    {
                    	// FreeNode
                    }
                    else
                    {
                    	lexer.report.warning(lexer, pre, node, Report.MISSING_ENDTAG_BEFORE);
                    	lexer.ungetToken();                    	
                    }
                    pre.closed = true;
                    Node.trimSpaces(lexer, pre);
                    return;
                }

                // XOWA:jtidy
//                if (node.tag == tt.tagHtml)
//                {
//                    if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
//                    {
//                        lexer.report.warning(lexer, pre, node, Report.DISCARDING_UNEXPECTED);
//                    }
//
//                    continue;
//                }

                if (node.type == Node.TEXT_NODE)
                {
                	// XOWA: block jtidy
                    // if first check for inital newline
//                    if (pre.content == null)
//                    {
//                        if (node.textarray[node.start] == (byte) '\n')
//                        {
//                            ++node.start;
//                        }
//
//                        if (node.start >= node.end)
//                        {
//                            continue;
//                        }
//                    }
                    pre.insertNodeAtEnd(node);
                    continue;
                }

                // deal with comments etc.
                if (Node.insertMisc(pre, node))
                    continue;

                // XOWA:tidy
                if (node.tag == null)
                {
                	lexer.report.warning(lexer, pre, node, Report.DISCARDING_UNEXPECTED);
                	continue;
                }

                // strip unexpected tags
                if (!lexer.preContent(node))
                {
                    Node newnode;

                    // lexer.report.warning(lexer, pre, node, Report.UNESCAPED_ELEMENT);	// XOWA:jtidy
                    // newnode = Node.escapeTag(lexer, node); // XOWA:jtidy
                    // pre.insertNodeAtEnd(newnode);	// XOWA:jtidy
                    
                    // XOWA:tidy
                    // fix for http://tidy.sf.net/bug/772205
                    if (node.type == Node.END_TAG)
                    {
                    	// http://tidy.sf.net/issue/1590220
                    	if (	lexer.exiled
                    		&& ((node.tag.model & Dict.CM_TABLE) != 0 || node.tag == tt.tagTable)
                    		) 
                    	{
                    		lexer.ungetToken();
                    		Node.trimSpaces(lexer, pre);
                    		return;
                    	}
                    	
                    	lexer.report.warning(lexer, pre, node, Report.DISCARDING_UNEXPECTED);
                    	continue;
                    }
                    // node.tag.model
                    // http://tidy.sf.net/issue/1590220
                    else if (	(node.tag.model & (Dict.CM_TABLE|Dict.CM_ROW)) != 0
                    		|| 	node.tag == tt.tagTable)
                    {
                    	if (lexer.exiled)	// No missing close warning if exiled.
                    		lexer.report.warning(lexer, pre, node, Report.MISSING_ENDTAG_BEFORE);
                    	lexer.ungetToken();
                    	return;
                    }
                    
					//  This is basically what Tidy 04 August 2000 did and far more accurate
					//  with respect to browser behaivour than the code commented out above.
					//  Tidy could try to propagate the <pre> into each disallowed child where
					//  <pre> is allowed in order to replicate some browsers behaivour, but
					//  there are a lot of exceptions, e.g. Internet Explorer does not propagate
					//  <pre> into table cells while Mozilla does. Opera 6 never propagates
					//  <pre> into blocklevel elements while Opera 7 behaves much like Mozilla.
					//
					//  Tidy behaves thus mostly like Opera 6 except for nested <pre> elements
					//  which are handled like Mozilla takes them (Opera6 closes all <pre> after
					//  the first </pre>).
					//
					//  There are similar issues like replacing <p> in <pre> with <br>, for
					//  example
					//
					//    <pre>...<p>...</pre>                 (Input)
					//    <pre>...<br>...</pre>                (Tidy)
					//    <pre>...<br>...</pre>                (Opera 7 and Internet Explorer)
					//    <pre>...<br><br>...</pre>            (Opera 6 and Mozilla)
					//
					//    <pre>...<p>...</p>...</pre>          (Input)
					//    <pre>...<br>......</pre>             (Tidy, BUG!)
					//    <pre>...<br>...<br>...</pre>         (Internet Explorer)
					//    <pre>...<br><br>...<br><br>...</pre> (Mozilla, Opera 6)
					//    <pre>...<br>...<br><br>...</pre>     (Opera 7)
					//    
					//  or something similar, they could also be closing the <pre> and propagate
					//  the <pre> into the newly opened <p>.
					//
					//  Todo: IMG, OBJECT, APPLET, BIG, SMALL, SUB, SUP, FONT, and BASEFONT are
					//  dissallowed in <pre>, Tidy neither detects this nor does it perform any
					//  cleanup operation. Tidy should at least issue a warning if it encounters
					//  such constructs.
					//
					//  Todo: discarding </p> is abviously a bug, it should be replaced by <br>.
                    pre.insertNodeAfterElement(node);
                    lexer.report.warning(lexer, pre, node, Report.MISSING_ENDTAG_BEFORE);
                    parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);

                    newnode = lexer.inferredTag("pre");
                    lexer.report.warning(lexer, pre, newnode, Report.INSERTING_TAG);
                    pre = newnode;
                    node.insertNodeAfterElement(pre);
                    continue;
                }

                if (node.tag == tt.tagP)
                {
                    if (node.type == Node.START_TAG)
                    {
                        lexer.report.warning(lexer, pre, node, Report.USING_BR_INPLACE_OF);

                        // trim white space before <p> in <pre>
                        Node.trimSpaces(lexer, pre);

                        // coerce both <p> and </p> to <br>
                        Node.coerceNode(lexer, node, tt.tagBr);
                        tt.freeAttrs(node);	// discard align attribute etc.
                        pre.insertNodeAtEnd(node);
                    }
                    else
                    {
                        lexer.report.warning(lexer, pre, node, Report.DISCARDING_UNEXPECTED);
                    }
                    continue;
                }

                if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                {
                    // trim white space before <br>
                    if (node.tag == tt.tagBr)
                        Node.trimSpaces(lexer, pre);

                    pre.insertNodeAtEnd(node);
                    parseTag(lexer, node, Lexer.PREFORMATTED);
                    continue;
                }

                // discard unexpected tags
                lexer.report.warning(lexer, pre, node, Report.DISCARDING_UNEXPECTED);
            }

            lexer.report.warning(lexer, pre, node, Report.MISSING_ENDTAG_FOR);
            Node.trimEmptyElement(lexer, pre);	// XOWA:jtidy
        }
    }

    /**
     * Parser for block elements.
     */
    public static class ParseBlock implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node element, short mode)
        {
            // element is node created by the lexer upon seeing the start tag, or by the parser when the start tag is inferred.
            TagTable tt = lexer.configuration.tt;
            Node node;
            boolean checkstack = true;
            int istackbase = 0;

            if ((element.tag.model & Dict.CM_EMPTY) != 0)
                return;

            if (	element.tag == tt.tagForm
            	&& 	element.isDescendantOf(tt.tagForm))
                lexer.report.warning(lexer, element, null, Report.ILLEGAL_NESTING);

            // InlineDup() asks the lexer to insert inline emphasis tags currently pushed on the istack, but take care
            // to avoid propagating inline emphasis inside OBJECT or APPLET. For these elements a fresh inline stack
            // context is created and disposed of upon reaching the end of the element. They thus behave like table
            // cells in this respect.
            if ((element.tag.model & Dict.CM_OBJECT) != 0)
            {
                istackbase = lexer.istackbase;
                lexer.istackbase = lexer.istack.size();
            }

            if (!((element.tag.model & Dict.CM_MIXED) != 0))
                lexer.inlineDup(null);

            mode = Lexer.IGNORE_WHITESPACE;

            while ((node = lexer.getToken(mode)) != null)		// MixedContent
            {
                // end tag for this element
                if (node.type == Node.END_TAG && node.tag != null
                    && (node.tag == element.tag || element.was == node.tag))
                {
                    if ((element.tag.model & Dict.CM_OBJECT) != 0)
                    {
                        // pop inline stack
                        while (lexer.istack.size() > lexer.istackbase)
                            lexer.popInline(null);
                        lexer.istackbase = istackbase;
                    }

                    element.closed = true;
                    Node.trimSpaces(lexer, element);
                    Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                    return;
                }

                // XOWA:tidy
                if (node.tag == tt.tagBody && element.isDescendantOf(tt.tagHead))
                {
                    // If we're in the HEAD, close it before proceeding. This is an extremely rare occurance, but has been observed.
                	lexer.ungetToken();
                    break;
                }
                
                if (node.tag == tt.tagHtml || node.tag == tt.tagHead || node.tag == tt.tagBody)
                {
                    if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                        lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                if (node.type == Node.END_TAG)
                {
                    if (node.tag == null)
                    {
                        lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }
                    else if (node.tag == tt.tagBr)
                        node.type = Node.START_TAG;
                    else if (node.tag == tt.tagP)
                    {
						// Cannot have a block inside a paragraph, so no checking
						// for an ancestor is necessary -- but we _can_ have
						// paragraphs inside a block, so change it to an implicit
						// empty paragraph, to be dealt with according to the user's options
                        node.type = Node.START_TAG;		// XOWA:tidy
                        node.implicit = true;			// XOWA:tidy
                        // XOWA:tidy;OBSOLETE
//                        Node.coerceNode(lexer, node, tt.tagBr);
//                        tt.freeAttrs(node);				// XOWA:tidy
//                        element.insertNodeAtEnd(node);
//                        node = lexer.inferredTag("br");
                   	}
                    // XOWA:jtidy; significant differences for the next "else if" and "else" branches
                    else if (element.isDescendantOf(node.tag))
                    {
                    	lexer.ungetToken();
                    	break;
                    	// XOWA: following section marked by conditional compilation constant OBSOLETE
//                        // if this is the end tag for an ancestor element then infer end tag for this element
//                        for (	parent = element.parent;
//                        		parent != null;
//                        		parent = parent.parent)
//                        {
//                            if (node.tag == parent.tag)
//                            {
//                                if (!((element.tag.model & Dict.CM_OPT) != 0))
//                                    lexer.report.warning(lexer, element, node, Report.MISSING_ENDTAG_BEFORE);
//
//                                lexer.ungetToken();
//
//                                if ((element.tag.model & Dict.CM_OBJECT) != 0)
//                                {
//                                    // pop inline stack
//                                    while (lexer.istack.size() > lexer.istackbase)
//                                        lexer.popInline(null);
//                                    lexer.istackbase = istackbase;
//                                }
//
//                                Node.trimSpaces(lexer, element);
//                                Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
//                                return;
//                            }
//                        }
                    }
                }
                else
                {                
                  // special case </tr> etc. for stuff moved in front of table
                	if 	(	lexer.exiled
                		&&	node.tag != null && node.tag.model != 0 && (node.tag.model & Dict.CM_TABLE) != 0)
					{
						lexer.ungetToken();
						Node.trimSpaces(lexer, element);
						Node.trimEmptyElement(lexer, element); // XOWA:jtidy
						return;
					}
                }

                // mixed content model permits text
                if (node.type == Node.TEXT_NODE)
                {
                	// XOWA:jtidy
//                    boolean iswhitenode = false;
//                    if (node.type == Node.TEXT_NODE
//                        && node.end <= node.start + 1
//                        && lexer.lexbuf[node.start] == (byte) ' ')
//                    {
//                        iswhitenode = true;
//                    }
//
//                    if (lexer.configuration.encloseBlockText && !iswhitenode)
//                    {
//                        lexer.ungetToken();
//                        node = lexer.inferredTag("p");
//                        element.insertNodeAtEnd(node);
//                        parseTag(lexer, node, Lexer.MIXED_CONTENT);
//                        continue;
//                    }

                    if (checkstack)
                    {
                        checkstack = false;
                        if (!((element.tag.model & Dict.CM_MIXED) != 0))
                        {
                            if (lexer.inlineDup(node) > 0)
                                continue;
                        }
                    }

                    element.insertNodeAtEnd(node);
                    mode = Lexer.MIXED_CONTENT;

                    // HTML4 strict doesn't allow mixed content for elements with %block; as their content model
                    // But only body, map, blockquote, form and noscript have content model %block;
                    if (	element.tag == tt.tagBody
                        ||	element.tag == tt.tagMap
                        ||	element.tag == tt.tagBlockquote
                        ||	element.tag == tt.tagForm
                        ||	element.tag == tt.tagNoscript)
                    {
                        lexer.constrainVersion(~Dict.VERS_HTML40_STRICT);
                    }
                    continue;
                }

                if (Node.insertMisc(element, node))
                    continue;

                // allow PARAM elements?
                if (node.tag == tt.tagParam)
                {
                    if (	((element.tag.model & Dict.CM_PARAM) != 0)
                        && 	(node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                        )
                    {
                        element.insertNodeAtEnd(node);
                        continue;
                    }

                    // otherwise discard it
                    lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // allow AREA elements?
                if (node.tag == tt.tagArea)
                {
                    if ((element.tag == tt.tagMap) && (node.type == Node.START_TAG || node.type == Node.START_END_TAG))
                    {
                        element.insertNodeAtEnd(node);
                        continue;
                    }

                    // otherwise discard it
                    lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // ignore unknown start/end tags
                if (node.tag == null)
                {
                    lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // Allow Dict.CM_INLINE elements here. Allow Dict.CM_BLOCK elements here unless lexer.excludeBlocks is yes.
                // LI and DD are special cased. Otherwise infer end tag for this element.
                if (!((node.tag.model & Dict.CM_INLINE) != 0))
                {
                    if (node.type != Node.START_TAG && node.type != Node.START_END_TAG)
                    {
                        if (node.tag == tt.tagForm)
                            badForm(lexer);
                        lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    // #427671 - Fix by Randy Waki - 10 Aug 00
                    // If an LI contains an illegal FRAME, FRAMESET, OPTGROUP, or OPTION start tag, discard the start
                    // tag and let the subsequent content get parsed as content of the enclosing LI. This seems to
                    // mimic IE and Netscape, and avoids an infinite loop: without this check, ParseBlock (which is
                    // parsing the LI's content) and ParseList (which is parsing the LI's parent's content) repeatedly
                    // defer to each other to parse the illegal start tag, each time inferring a missing </li> or <li>
                    // respectively. NOTE: This check is a bit fragile. It specifically checks for the four tags that
                    // happen to weave their way through the current series of tests performed by ParseBlock and
                    // ParseList to trigger the infinite loop.
                    if (element.tag == tt.tagLi)
                    {
                        if (	node.tag == tt.tagFrame
                            ||	node.tag == tt.tagFrameset
                            ||	node.tag == tt.tagOptgroup
                            ||	node.tag == tt.tagOption)
                        {
                            lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                            continue;
                        }
                    }

                    if (element.tag == tt.tagTd || element.tag == tt.tagTh)
                    {
                        // if parent is a table cell, avoid inferring the end of the cell
                        if ((node.tag.model & Dict.CM_HEAD) != 0)
                        {
                            moveToHead(lexer, element, node);
                            continue;
                        }

                        if ((node.tag.model & Dict.CM_LIST) != 0)
                        {
                            lexer.ungetToken();
                            node = lexer.inferredTag("ul");
                            node.addClass("noindent");
                            lexer.excludeBlocks = true;
                        }
                        else if ((node.tag.model & Dict.CM_DEFLIST) != 0)
                        {
                            lexer.ungetToken();
                            node = lexer.inferredTag("dl");
                            lexer.excludeBlocks = true;
                        }

                        // infer end of current table cell
                        if (!((node.tag.model & Dict.CM_BLOCK) != 0))
                        {
                            lexer.ungetToken();
                            Node.trimSpaces(lexer, element);
                            Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                            return;
                        }
                    }
                    else if ((node.tag.model & Dict.CM_BLOCK) != 0)
                    {
                        if (lexer.excludeBlocks)
                        {
                            if (!((element.tag.model & Dict.CM_OPT) != 0))
                                lexer.report.warning(lexer, element, node, Report.MISSING_ENDTAG_BEFORE);

                            lexer.ungetToken();

                            if ((element.tag.model & Dict.CM_OBJECT) != 0)
                                lexer.istackbase = istackbase;

                            Node.trimSpaces(lexer, element);
                            Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                            return;
                        }
                    }
                    else	// things like list items
                    {
                        if ((node.tag.model & Dict.CM_HEAD) != 0)
                        {
                            moveToHead(lexer, element, node);
                            continue;
                        }

                        // special case where a form start tag occurs in a tr and is followed by td or th
                        if (	element.tag == tt.tagForm
                        	&& 	element.parent.tag == tt.tagTd
                        	&&	element.parent.implicit)
                        {
                            if (node.tag == tt.tagTd)
                            {
                                lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                                continue;
                            }

                            if (node.tag == tt.tagTh)
                            {
                                lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                                node = element.parent;
                                node.element = "th";
                                node.tag = tt.tagTh;
                                continue;
                            }
                        }

                        if (!((element.tag.model & Dict.CM_OPT) != 0) && !element.implicit)
                            lexer.report.warning(lexer, element, node, Report.MISSING_ENDTAG_BEFORE);

                        lexer.ungetToken();

                        if ((node.tag.model & Dict.CM_LIST) != 0)
                        {
                            if (element.parent != null
                                && element.parent.tag != null
                                && element.parent.tag.getParser() == LIST)
                            {
                                Node.trimSpaces(lexer, element);
                                Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                                return;
                            }

                            node = lexer.inferredTag("ul");
                            node.addClass("noindent");
                        }
                        else if ((node.tag.model & Dict.CM_DEFLIST) != 0)
                        {
                            if (element.parent.tag == tt.tagDl)
                            {
                                Node.trimSpaces(lexer, element);
                                Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                                return;
                            }

                            node = lexer.inferredTag("dl");
                        }
                        else if ((node.tag.model & Dict.CM_TABLE) != 0 || (node.tag.model & Dict.CM_ROW) != 0)
                        {
                            // XOWA: DATE:2014-05-31
                            /* http://tidy.sf.net/issue/1316307 */
                            /* In exiled mode, return so table processing can 
                               continue. */
                            if (lexer.exiled)
                                return;
                            node = lexer.inferredTag("table");
                        }
                        else if ((element.tag.model & Dict.CM_OBJECT) != 0)
                        {
                            // pop inline stack
                            while (lexer.istack.size() > lexer.istackbase)
                                lexer.popInline(null);
                            lexer.istackbase = istackbase;
                            Node.trimSpaces(lexer, element);
                            Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                            return;
                        }
                        else
                        {
                            Node.trimSpaces(lexer, element);
                            Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
                            return;
                        }
                    }
                }

                // parse known element
                if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                {
                    if (TidyUtils.toBoolean(node.tag.model & Dict.CM_INLINE))
                    {
                        // DSR - 27Apr02 ensure we wrap anchors and other inline content
                        // fgiust: commented out due to [1403105]: java.lang.StackOverflowError in Tidy.parseDOM()
                        // if (lexer.configuration.encloseBlockText)
                        // {
                        // lexer.ungetToken();
                        // node = lexer.inferredTag("p");
                        // element.insertNodeAtEnd(node);
                        // parseTag(lexer, node, Lexer.MIXED_CONTENT);
                        // continue;
                        // }
                    	
                        if (checkstack && !node.implicit)
                        {
                            checkstack = false;
                            
                            if (!TidyUtils.toBoolean(element.tag.model & Dict.CM_MIXED)) // #431731 - fix by Randy Waki 25 Dec 00
                            {
                                if (lexer.inlineDup(node) > 0)
                                    continue;
                            }
                        }

                        mode = Lexer.MIXED_CONTENT;
                    }
                    else
                    {
                        checkstack = true;
                        mode = Lexer.IGNORE_WHITESPACE;
                    }

                    // trim white space before <br>
                    if (node.tag == tt.tagBr)
                        Node.trimSpaces(lexer, element);

                    element.insertNodeAtEnd(node);

                    if (node.implicit)
                        lexer.report.warning(lexer, element, node, Report.INSERTING_TAG);

                    parseTag(lexer, node, Lexer.IGNORE_WHITESPACE); // Lexer.MixedContent                    
                    continue;
                }

                // discard unexpected tags
                if (node.type == Node.END_TAG)
                    lexer.popInline(node); // if inline end tag

                lexer.report.warning(lexer, element, node, Report.DISCARDING_UNEXPECTED);
                continue;
            }

            if (!((element.tag.model & Dict.CM_OPT) != 0))
                lexer.report.warning(lexer, element, node, Report.MISSING_ENDTAG_FOR);

            if ((element.tag.model & Dict.CM_OBJECT) != 0)
            {
                // pop inline stack
                while (lexer.istack.size() > lexer.istackbase)
                    lexer.popInline(null);
                lexer.istackbase = istackbase;
            }

            Node.trimSpaces(lexer, element);
            Node.trimEmptyElement(lexer, element);	// XOWA:jtidy
        }
    }

    /**
     * Parser for TABLE.
     */
    public static class ParseTableTag implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node table, short mode)
        {
            Node node, parent;
            int istackbase;
            TagTable tt = lexer.configuration.tt;

            lexer.deferDup();
            istackbase = lexer.istackbase;
            lexer.istackbase = lexer.istack.size();

            while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
            {
                if (node.tag == table.tag && node.type == Node.END_TAG)
                {
                    lexer.istackbase = istackbase;
                    table.closed = true;
                    Node.trimEmptyElement(lexer, table);	// XOWA:jtidy
                    return;
                }

                // deal with comments etc.
                if (Node.insertMisc(table, node))
                    continue;

                // discard unknown tags
                if (node.tag == null && node.type != Node.TEXT_NODE)
                {
                    lexer.report.warning(lexer, table, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // if TD or TH or text or inline or block then infer <TR>
                if (node.type != Node.END_TAG)
                {
                    if (node.tag == tt.tagTd || node.tag == tt.tagTh || node.tag == tt.tagTable)
                    {
                        lexer.ungetToken();
                        node = lexer.inferredTag("tr");
                        lexer.report.warning(lexer, table, node, Report.MISSING_STARTTAG);
                    }
                    else if (node.type == Node.TEXT_NODE || (node.tag.model & (Dict.CM_BLOCK | Dict.CM_INLINE)) != 0)
                    {
                        Node.insertNodeBeforeElement(table, node);
                        lexer.report.warning(lexer, table, node, Report.TAG_NOT_ALLOWED_IN);
                        lexer.exiled = true;

                        if (!(node.type == Node.TEXT_NODE)) // #427662 - was (!node.type == TextNode) - fix by Young
                        	parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);

                        lexer.exiled = false;
                        continue;
                    }
                    else if ((node.tag.model & Dict.CM_HEAD) != 0)
                    {
                        moveToHead(lexer, table, node);
                        continue;
                    }
                }

                // if this is the end tag for an ancestor element then infer end tag for this element
                if (node.type == Node.END_TAG)
                {
                    if (node.tag == tt.tagForm
                        // || (node.tag != null && ((node.tag.model & (Dict.CM_BLOCK | Dict.CM_INLINE)) != 0))	// XOWA:jtidy
                        )
                    {
                        badForm(lexer);
                        lexer.report.warning(lexer, table, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    // best to discard unexpected block/inline end tags
                    if (	(node.tag != null && (node.tag.model & (Dict.CM_TABLE | Dict.CM_ROW)) != 0)
                        || 	(node.tag != null && (node.tag.model & (Dict.CM_BLOCK | Dict.CM_INLINE)) != 0))
                    {
                        lexer.report.warning(lexer, table, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    for (	parent = table.parent;
                    		parent != null;
                    		parent = parent.parent)
                    {
                        if (node.tag == parent.tag)
                        {
                            lexer.report.warning(lexer, table, node, Report.MISSING_ENDTAG_BEFORE);
                            lexer.ungetToken();
                            lexer.istackbase = istackbase;
                            Node.trimEmptyElement(lexer, table);	// XOWA:jtidy
                            return;
                        }
                    }
                }

                if (!((node.tag.model & Dict.CM_TABLE) != 0))
                {
                    lexer.ungetToken();
                    lexer.report.warning(lexer, table, node, Report.TAG_NOT_ALLOWED_IN);
                    lexer.istackbase = istackbase;
                    Node.trimEmptyElement(lexer, table);	// XOWA:jtidy
                    return;
                }

                if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                {
                    table.insertNodeAtEnd(node);
                    parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);
                    continue;
                }

                // discard unexpected text nodes and end tags
                lexer.report.warning(lexer, table, node, Report.DISCARDING_UNEXPECTED);
            }

            lexer.report.warning(lexer, table, node, Report.MISSING_ENDTAG_FOR);
            Node.trimEmptyElement(lexer, table);	// XOWA:jtidy
            lexer.istackbase = istackbase;
        }
    }

    /**
     * Parser for COLGROUP.
     */
    public static class ParseColGroup implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node colgroup, short mode)
        {
            Node node, parent;
            TagTable tt = lexer.configuration.tt;

            if ((colgroup.tag.model & Dict.CM_EMPTY) != 0)
                return;

            while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
            {
                if (node.tag == colgroup.tag && node.type == Node.END_TAG)
                {
                    colgroup.closed = true;
                    return;
                }

                // if this is the end tag for an ancestor element then infer end tag for this element
                if (node.type == Node.END_TAG)
                {
                    if (node.tag == tt.tagForm)
                    {
                        badForm(lexer);
                        lexer.report.warning(lexer, colgroup, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    for (	parent = colgroup.parent;
                    		parent != null;
                    		parent = parent.parent)
                    {

                        if (node.tag == parent.tag)
                        {
                            lexer.ungetToken();
                            return;
                        }
                    }
                }

                if (node.type == Node.TEXT_NODE)
                {
                    lexer.ungetToken();
                    return;
                }

                // deal with comments etc.
                if (Node.insertMisc(colgroup, node))
                    continue;

                // discard unknown tags
                if (node.tag == null)
                {
                    lexer.report.warning(lexer, colgroup, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                if (node.tag != tt.tagCol)
                {
                    lexer.ungetToken();
                    return;
                }

                if (node.type == Node.END_TAG)
                {
                    lexer.report.warning(lexer, colgroup, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // node should be <COL>
                colgroup.insertNodeAtEnd(node);
                parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);
            }
        }

    }

    /**
     * Parser for ROWGROUP.
     */
    public static class ParseRowGroup implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node rowgroup, short mode)
        {
            Node node, parent;
            TagTable tt = lexer.configuration.tt;

            if ((rowgroup.tag.model & Dict.CM_EMPTY) != 0)
                return;

            while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
            {
                if (node.tag == rowgroup.tag)
                {
                    if (node.type == Node.END_TAG)
                    {
                        rowgroup.closed = true;
                        Node.trimEmptyElement(lexer, rowgroup);	// XOWA:jtidy
                        return;
                    }

                    lexer.ungetToken();
                    return;
                }

                // if </table> infer end tag
                if (node.tag == tt.tagTable && node.type == Node.END_TAG)
                {
                    lexer.ungetToken();
                    Node.trimEmptyElement(lexer, rowgroup);	// XOWA:jtidy
                    return;
                }

                // deal with comments etc.
                if (Node.insertMisc(rowgroup, node))
                    continue;

                // discard unknown tags
                if (node.tag == null && node.type != Node.TEXT_NODE)
                {
                    lexer.report.warning(lexer, rowgroup, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // if TD or TH then infer <TR> if text or inline or block move before table if head content move to head
                if (node.type != Node.END_TAG)
                {
                    if (node.tag == tt.tagTd || node.tag == tt.tagTh)
                    {
                        lexer.ungetToken();
                        node = lexer.inferredTag("tr");
                        lexer.report.warning(lexer, rowgroup, node, Report.MISSING_STARTTAG);
                    }
                    else if (	node.type == Node.TEXT_NODE
                    		|| (node.tag.model & (Dict.CM_BLOCK | Dict.CM_INLINE)) != 0)
                    {
                        Node.moveBeforeTable(rowgroup, node, tt);
                        lexer.report.warning(lexer, rowgroup, node, Report.TAG_NOT_ALLOWED_IN);
                        lexer.exiled = true;

                        // #427662 was (!node.type == TextNode) fix by Young 04 Aug 00
                        if (node.type != Node.TEXT_NODE)
                            parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);

                        lexer.exiled = false;
                        continue;
                    }
                    else if ((node.tag.model & Dict.CM_HEAD) != 0)
                    {
                        lexer.report.warning(lexer, rowgroup, node, Report.TAG_NOT_ALLOWED_IN);
                        moveToHead(lexer, rowgroup, node);
                        continue;
                    }
                }

                // if this is the end tag for ancestor element then infer end tag for this element
                if (node.type == Node.END_TAG)
                {
                    if (	node.tag == tt.tagForm
                        || (node.tag != null && (node.tag.model & (Dict.CM_BLOCK | Dict.CM_INLINE)) != 0))
                    {
                        if (node.tag == tt.tagForm)
                            badForm(lexer);
                        
                        lexer.report.warning(lexer, rowgroup, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    if (node.tag == tt.tagTr || node.tag == tt.tagTd || node.tag == tt.tagTh)
                    {
                        lexer.report.warning(lexer, rowgroup, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    for (parent = rowgroup.parent; parent != null; parent = parent.parent)
                    {
                        if (node.tag == parent.tag)
                        {
                            lexer.ungetToken();
                            Node.trimEmptyElement(lexer, rowgroup);	// XOWA:jtidy
                            return;
                        }
                    }
                }

                // if THEAD, TFOOT or TBODY then implied end tag
                if ((node.tag.model & Dict.CM_ROWGRP) != 0)
                {
                    if (node.type != Node.END_TAG)
                    {
                        lexer.ungetToken();
                    }

                    Node.trimEmptyElement(lexer, rowgroup);	// XOWA:jtidy
                    return;
                }

                if (node.type == Node.END_TAG)
                {
                    lexer.report.warning(lexer, rowgroup, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                if (!(node.tag == tt.tagTr))
                {
                    node = lexer.inferredTag("tr");
                    lexer.report.warning(lexer, rowgroup, node, Report.MISSING_STARTTAG);
                    lexer.ungetToken();
                }

                // node should be <TR>
                rowgroup.insertNodeAtEnd(node);
                parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);
            }
            Node.trimEmptyElement(lexer, rowgroup);	// XOWA:jtidy
        }
    }

    /**
     * Parser for ROW.
     */
    public static class ParseRow implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node row, short mode)
        {
            Node node;
            boolean excludeState;
            TagTable tt = lexer.configuration.tt;

            if ((row.tag.model & Dict.CM_EMPTY) != 0)
                return;

            while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
            {
                if (node.tag == row.tag)
                {
                    if (node.type == Node.END_TAG)
                    {
                        row.closed = true;
                        Node.fixEmptyRow(lexer, row);
                        return;
                    }

                    // New row start implies end of current row
                    lexer.ungetToken();
                    Node.fixEmptyRow(lexer, row);
                    return;
                }

                // if this is the end tag for an ancestor element then infer end tag for this element
                if (node.type == Node.END_TAG)
                {
//                    if ( (TY_(nodeHasCM)(node, CM_HTML|CM_TABLE) || nodeIsTABLE(node))
//                            && DescendantOf(row, TagId(node)) )
                	if	(	((node.tag != null && (node.tag.model & (Dict.CM_HTML | Dict.CM_TABLE)) != 0) || node.tag == tt.tagTable)
                		&&	row.isDescendantOf(node.tag)
                		)
                	{
                		lexer.ungetToken();
                		return;
                	}
                	
                    if (	node.tag == tt.tagForm
                        || (node.tag != null && (node.tag.model & (Dict.CM_BLOCK | Dict.CM_INLINE)) != 0))
                    {
                        if (node.tag == tt.tagForm)
                            badForm(lexer);

                        lexer.report.warning(lexer, row, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    if (node.tag == tt.tagTd || node.tag == tt.tagTh)
                    {
                        lexer.report.warning(lexer, row, node, Report.DISCARDING_UNEXPECTED);
                        continue;
                    }

                    // XOWA:jtidy
//                    for (parent = row.parent; parent != null; parent = parent.parent)
//                    {
//                        if (node.tag == parent.tag)
//                        {
//                            lexer.ungetToken();
//                            Node.trimEmptyElement(lexer, row);
//                            return;
//                        }
//                    }
                }

                // deal with comments etc.
                if (Node.insertMisc(row, node))
                    continue;

                // discard unknown tags
                if (node.tag == null && node.type != Node.TEXT_NODE)
                {
                    lexer.report.warning(lexer, row, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // discard unexpected <table> element
                if (node.tag == tt.tagTable)
                {
                    lexer.report.warning(lexer, row, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // THEAD, TFOOT or TBODY
                if (node.tag != null && (node.tag.model & Dict.CM_ROWGRP) != 0)
                {
                    lexer.ungetToken();
                    Node.trimEmptyElement(lexer, row); // XOWA:jtidy
                    return;
                }

                if (node.type == Node.END_TAG)
                {
                    lexer.report.warning(lexer, row, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // if text or inline or block move before table if head content move to head
                if (node.type != Node.END_TAG)
                {
                    if (node.tag == tt.tagForm)
                    {
                        lexer.ungetToken();
                        node = lexer.inferredTag("td");
                        lexer.report.warning(lexer, row, node, Report.MISSING_STARTTAG);
                    }
                    else if (	node.type == Node.TEXT_NODE
                    		|| (node.tag.model & (Dict.CM_BLOCK | Dict.CM_INLINE)) != 0)
                    {
                        Node.moveBeforeTable(row, node, tt);
                        lexer.report.warning(lexer, row, node, Report.TAG_NOT_ALLOWED_IN);
                        
                        lexer.exiled = true;
                        excludeState = lexer.excludeBlocks;		// XOWA:tidy
                        lexer.excludeBlocks = false;			// XOWA:tidy

                        if (node.type != Node.TEXT_NODE)
                            parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);
                        
                        lexer.exiled = false;
                        lexer.excludeBlocks = excludeState;		// XOWA:tidy
                        continue;
                    }
                    else if ((node.tag.model & Dict.CM_HEAD) != 0)
                    {
                        lexer.report.warning(lexer, row, node, Report.TAG_NOT_ALLOWED_IN);
                        moveToHead(lexer, row, node);
                        continue;
                    }
                }

                if (!(node.tag == tt.tagTd || node.tag == tt.tagTh))
                {
                    lexer.report.warning(lexer, row, node, Report.TAG_NOT_ALLOWED_IN);
                    continue;
                }

                // node should be <TD> or <TH>
                row.insertNodeAtEnd(node);
                excludeState = lexer.excludeBlocks;
                lexer.excludeBlocks = false;
                parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);
                lexer.excludeBlocks = excludeState;

                // pop inline stack
                while (lexer.istack.size() > lexer.istackbase)
                    lexer.popInline(null);
            }
            Node.trimEmptyElement(lexer, row);	// XOWA:jtidy
        }
    }

    /**
     * Parser for NOFRAMES.
     */
    public static class ParseNoFrames implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node noframes, short mode)
        {
            Node node;
            TagTable tt = lexer.configuration.tt;

            lexer.badAccess |= Report.USING_NOFRAMES;
            mode = Lexer.IGNORE_WHITESPACE;

            while ((node = lexer.getToken(mode)) != null)
            {
                if (node.tag == noframes.tag && node.type == Node.END_TAG)
                {
                    noframes.closed = true;
                    Node.trimSpaces(lexer, noframes);
                    return;
                }

                if ((node.tag == tt.tagFrame || node.tag == tt.tagFrameset))
                {
                    Node.trimSpaces(lexer, noframes);                    
                    if (node.type == Node.END_TAG)	// fix for [539369]
                    {
                        lexer.report.warning(lexer, noframes, node, Report.DISCARDING_UNEXPECTED);
                        // Throw it away
                    }
                    else
                    {
                        lexer.report.warning(lexer, noframes, node, Report.MISSING_ENDTAG_BEFORE);
                        lexer.ungetToken();
                    }
                    return;
                }

                if (node.tag == tt.tagHtml)
                {
                    if (node.type == Node.START_TAG || node.type == Node.START_END_TAG)
                        lexer.report.warning(lexer, noframes, node, Report.DISCARDING_UNEXPECTED);

                    continue;
                }

                // deal with comments etc.
                if (Node.insertMisc(noframes, node))
                    continue;

                if (	node.tag == tt.tagBody
                	&&	node.type == Node.START_TAG)
                {
                    boolean seenbody = lexer.seenEndBody;
                    noframes.insertNodeAtEnd(node);
                    parseTag(lexer, node, Lexer.IGNORE_WHITESPACE); // MixedContent

                    // fix for bug http://tidy.sf.net/bug/887259
                    if (seenbody && lexer.root.findBody(tt) != node) 
                    {
                        Node.coerceNode(lexer, node, tt.tagDiv);
                        moveNodeToBody(lexer, node);
                    }
                    continue;
                }

                // implicit body element inferred
                if (node.type == Node.TEXT_NODE || (node.tag != null && node.type != Node.END_TAG))
                {
                    Node body = lexer.root.findBody(tt);
                    if (body != null || lexer.seenEndBody)
                    {
                    	if (body == null)
                    	{
                            lexer.report.warning(lexer, noframes, node, Report.DISCARDING_UNEXPECTED);                    		
                    		continue;
                    	}
                        if (node.type == Node.TEXT_NODE)
                        {
                            lexer.ungetToken();
                            node = lexer.inferredTag("p");
                            lexer.report.warning(lexer, noframes, node, Report.CONTENT_AFTER_BODY);
                        }
                        body.insertNodeAtEnd(node);
                    }
                    else
                    {
                        lexer.ungetToken();
                        node = lexer.inferredTag("body");
                        if (lexer.configuration.xmlOut)
                            lexer.report.warning(lexer, noframes, node, Report.INSERTING_TAG);
                        noframes.insertNodeAtEnd(node);
                    }
                    parseTag(lexer, node, Lexer.IGNORE_WHITESPACE); // MixedContent
                    continue;
                }
                // discard unexpected end tags
                lexer.report.warning(lexer, noframes, node, Report.DISCARDING_UNEXPECTED);
            }
            lexer.report.warning(lexer, noframes, node, Report.MISSING_ENDTAG_FOR);
        }
    }

    /**
     * Parser for SELECT.
     */
    public static class ParseSelect implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node field, short mode)
        {
            Node node;
            TagTable tt = lexer.configuration.tt;

            lexer.insert = -1; // defer implicit inline start tags

            while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
            {
                if (node.tag == field.tag && node.type == Node.END_TAG)
                {
                    field.closed = true;
                    Node.trimSpaces(lexer, field);
                    return;
                }

                // deal with comments etc.
                if (Node.insertMisc(field, node))
                    continue;

                if (node.type == Node.START_TAG
                    &&	(	node.tag == tt.tagOption
                    	||	node.tag == tt.tagOptgroup
                    	||	node.tag == tt.tagScript
                    	)
                    )
                {
                    field.insertNodeAtEnd(node);
                    parseTag(lexer, node, Lexer.IGNORE_WHITESPACE);
                    continue;
                }

                // discard unexpected tags
                lexer.report.warning(lexer, field, node, Report.DISCARDING_UNEXPECTED);
            }

            lexer.report.warning(lexer, field, node, Report.MISSING_ENDTAG_FOR);
        }

    }

    /**
     * Parser for text nodes.
     */
    public static class ParseText implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node field, short mode)
        {
            Node node;
            TagTable tt = lexer.configuration.tt;

            lexer.insert = -1; // defer implicit inline start tags

            if (field.tag == tt.tagTextarea)
                mode = Lexer.PREFORMATTED;
            else
                mode = Lexer.MIXED_CONTENT; // kludge for font tags

            while ((node = lexer.getToken(mode)) != null)
            {
                if (node.tag == field.tag && node.type == Node.END_TAG)
                {
                    field.closed = true;
                    Node.trimSpaces(lexer, field);
                    return;
                }

                // deal with comments etc.
                if (Node.insertMisc(field, node))
                    continue;

                if (node.type == Node.TEXT_NODE)
                {
                    // only called for 1st child
                    if (field.content == null && !((mode & Lexer.PREFORMATTED) != 0))
                        Node.trimSpaces(lexer, field);

                    if (node.start >= node.end)
                    {
                        continue;
                    }

                    field.insertNodeAtEnd(node);
                    continue;
                }

                // for textarea should all cases of < and & be escaped?
                // discard inline tags e.g. font
                if (	node.tag != null
                    && (node.tag.model & Dict.CM_INLINE) != 0
                    && (node.tag.model & Dict.CM_FIELD) == 0	// #487283 - fix by Lee Passey 25 Jan 02
                    ) 
                {
                    lexer.report.warning(lexer, field, node, Report.DISCARDING_UNEXPECTED);
                    continue;
                }

                // terminate element on other tags
                if (!((field.tag.model & Dict.CM_OPT) != 0))
                    lexer.report.warning(lexer, field, node, Report.MISSING_ENDTAG_BEFORE);

                lexer.ungetToken();
                Node.trimSpaces(lexer, field);
                return;
            }

            if (!((field.tag.model & Dict.CM_OPT) != 0))
                lexer.report.warning(lexer, field, node, Report.MISSING_ENDTAG_FOR);
        }
    }

    /**
     * Parser for OPTGROUP.
     */
    public static class ParseOptGroup implements Parser
    {
        /**
         * @see org.w3c.tidy.Parser#parse(org.w3c.tidy.Lexer, org.w3c.tidy.Node, short)
         */
        public void parse(Lexer lexer, Node field, short mode)
        {
            Node node;
            TagTable tt = lexer.configuration.tt;

            lexer.insert = -1; // defer implicit inline start tags

            while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
            {
                if (node.tag == field.tag && node.type == Node.END_TAG)
                {
                    field.closed = true;
                    Node.trimSpaces(lexer, field);
                    return;
                }

                // deal with comments etc.
                if (Node.insertMisc(field, node))
                    continue;

                if (	node.type == Node.START_TAG
                	&& (node.tag == tt.tagOption || node.tag == tt.tagOptgroup))
                {
                    if (node.tag == tt.tagOptgroup)
                        lexer.report.warning(lexer, field, node, Report.CANT_BE_NESTED);

                    field.insertNodeAtEnd(node);
                    parseTag(lexer, node, Lexer.MIXED_CONTENT);
                    continue;
                }

                // discard unexpected tags
                lexer.report.warning(lexer, field, node, Report.DISCARDING_UNEXPECTED);
            }
        }
    }

    /**
     * HTML is the top level element.
     */
    public static Node parseDocument(Lexer lexer)
    {
        Node node, document, html;
        Node doctype = null;
        TagTable tt = lexer.configuration.tt;

        document = lexer.newNode();
        document.type = Node.ROOT_NODE;

        lexer.root = document;

        while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
        {
            // deal with comments etc.
            if (Node.insertMisc(document, node))
            {
                continue;
            }

            if (node.type == Node.DOCTYPE_TAG)
            {
                if (doctype == null)
                {
                    document.insertNodeAtEnd(node);
                    doctype = node;
                }
                else
                {
                    lexer.report.warning(lexer, document, node, Report.DISCARDING_UNEXPECTED);
                }
                continue;
            }

            if (node.type == Node.END_TAG)
            {
                lexer.report.warning(lexer, document, node, Report.DISCARDING_UNEXPECTED); // TODO?
                continue;
            }

            if (node.type != Node.START_TAG || node.tag != tt.tagHtml)
            {
                lexer.ungetToken();
                html = lexer.inferredTag("html");
            }
            else
            {
                html = node;
            }

            if (document.findDocType() == null && !lexer.configuration.bodyOnly)
            {
                lexer.report.warning(lexer, null, null, Report.MISSING_DOCTYPE);
            }

            document.insertNodeAtEnd(html);
            HTML.parse(lexer, html, (short) 0); // TODO?
            break;
        }
        
        if (lexer.root.findHTML(lexer.configuration.tt) == null) {
            /* a later check should complain if <body> is empty */
            html = lexer.inferredTag("html");
            lexer.root.insertNodeAtEnd(html);
            HTML.parse(lexer, html, Lexer.IGNORE_WHITESPACE);
        }
        
        if (lexer.root.findTITLE(lexer.configuration.tt) == null) {
            Node head = lexer.root.findHEAD(lexer.configuration.tt);
            lexer.report.warning(lexer, head, null, Report.MISSING_TITLE_ELEMENT);
            head.insertNodeAtEnd(lexer.inferredTag("title"));
        }
//        if (lexer.configuration.encloseBlockText) // XOWA: tidy; DATE:2015-11-08
//        	encloseBlockText(lexer, document);

        return document;
    }

    /**
     * Indicates whether or not whitespace should be preserved for this element. If an <code>xml:space</code>
     * attribute is found, then if the attribute value is <code>preserve</code>, returns <code>true</code>. For
     * any other value, returns <code>false</code>. If an <code>xml:space</code> attribute was <em>not</em>
     * found, then the following element names result in a return value of <code>true:
     *  pre, script, style,</code> and
     * <code>xsl:text</code>. Finally, if a <code>TagTable</code> was passed in and the element appears as the
     * "pre" element in the <code>TagTable</code>, then <code>true</code> will be returned. Otherwise,
     * <code>false</code> is returned.
     * @param element The <code>Node</code> to test to see if whitespace should be preserved.
     * @param tt The <code>TagTable</code> to test for the <code>getNodePre()</code> function. This may be
     * <code>null</code>, in which case this test is bypassed.
     * @return <code>true</code> or <code>false</code>, as explained above.
     */
    public static boolean XMLPreserveWhiteSpace(Node element, TagTable tt)
    {
        AttVal attribute;

        // search attributes for xml:space
        for (attribute = element.attributes; attribute != null; attribute = attribute.next)
        {
            if (attribute.attribute.equals("xml:space"))
            {
                if (attribute.value.equals("preserve"))
                {
                    return true;
                }

                return false;
            }
        }

        if (element.element == null) // Debian Bug #137124. Fix based on suggestion by Cesar Eduardo Barros 06 Mar 02
        {
            return false;
        }

        // kludge for html docs without explicit xml:space attribute
        if ("pre".equalsIgnoreCase(element.element)
            || "script".equalsIgnoreCase(element.element)
            || "style".equalsIgnoreCase(element.element))
        {
            return true;
        }

        if ((tt != null) && (tt.findParser(element) == PRE))
        {
            return true;
        }

        // kludge for XSL docs
        if ("xsl:text".equalsIgnoreCase(element.element))
        {
            return true;
        }

        return false;
    }

    /**
     * XML documents.
     */
    public static void parseXMLElement(Lexer lexer, Node element, short mode)
    {
        Node node;

        // if node is pre or has xml:space="preserve" then do so

        if (XMLPreserveWhiteSpace(element, lexer.configuration.tt))
        {
            mode = Lexer.PREFORMATTED;
        }

        while ((node = lexer.getToken(mode)) != null)
        {
            if (node.type == Node.END_TAG && node.element.equals(element.element))
            {
                element.closed = true;
                break;
            }

            // discard unexpected end tags
            if (node.type == Node.END_TAG)
            {
                lexer.report.error(lexer, element, node, Report.UNEXPECTED_ENDTAG);
                continue;
            }

            // parse content on seeing start tag
            if (node.type == Node.START_TAG)
            {
                parseXMLElement(lexer, node, mode);
            }

            element.insertNodeAtEnd(node);
        }

        // if first child is text then trim initial space and delete text node if it is empty.

        node = element.content;

        if (node != null && node.type == Node.TEXT_NODE && mode != Lexer.PREFORMATTED)
        {
            if (node.textarray[node.start] == (byte) ' ')
            {
                node.start++;

                if (node.start >= node.end)
                {
                    Node.discardElement(node);
                }
            }
        }

        // if last child is text then trim final space and delete the text node if it is empty

        node = element.last;

        if (node != null && node.type == Node.TEXT_NODE && mode != Lexer.PREFORMATTED)
        {
            if (node.textarray[node.end - 1] == (byte) ' ')
            {
                node.end--;

                if (node.start >= node.end)
                {
                    Node.discardElement(node);
                }
            }
        }
    }

    public static Node parseXMLDocument(Lexer lexer)
    {
        Node node, document, doctype;

        document = lexer.newNode();
        document.type = Node.ROOT_NODE;
        doctype = null;
        lexer.configuration.xmlTags = true;

        while ((node = lexer.getToken(Lexer.IGNORE_WHITESPACE)) != null)
        {
            // discard unexpected end tags
            if (node.type == Node.END_TAG)
            {
                lexer.report.warning(lexer, null, node, Report.UNEXPECTED_ENDTAG);
                continue;
            }

            // deal with comments etc.
            if (Node.insertMisc(document, node))
            {
                continue;
            }

            if (node.type == Node.DOCTYPE_TAG)
            {
                if (doctype == null)
                {
                    document.insertNodeAtEnd(node);
                    doctype = node;
                }
                else
                {
                    lexer.report.warning(lexer, document, node, Report.DISCARDING_UNEXPECTED); // TODO
                }
                continue;
            }

            if (node.type == Node.START_END_TAG)
            {
                document.insertNodeAtEnd(node);
                continue;
            }

            // if start tag then parse element's content
            if (node.type == Node.START_TAG)
            {
                document.insertNodeAtEnd(node);
                parseXMLElement(lexer, node, Lexer.IGNORE_WHITESPACE);
            }

        }

        if (doctype != null && !lexer.checkDocTypeKeyWords(doctype))
        {
            lexer.report.warning(lexer, doctype, null, Report.DTYPE_NOT_UPPER_CASE);
        }

        // ensure presence of initial <?XML version="1.0"?>
        if (lexer.configuration.xmlPi)
        {
            lexer.fixXmlDecl(document);
        }

        return document;
    }
    static void encloseBlockText(Lexer lexer, Node node) {// XOWA: tidy; DATE:2015-11-08
        TagTable tt = lexer.configuration.tt;
    	while (node != null) {
    		Node next = node.next;
    		if (node.content != null)
    			encloseBlockText(lexer, node.content);
    		if (	!(node.tag == tt.tagForm || node.tag == tt.tagNoscript || node.tag == tt.tagBlockquote)
    			|| 	node.content == null) {
    			node = next;
    			continue;
    		}
    		Node block = node.content;
    		if (	(block.type == Node.TEXT_NODE && !isBlank(lexer, block))
    			||  (block.isElement() && nodeCmIsOnlyInline(block))
    			) {
    			Node para = lexer.inferredTag("p");
    			Node.insertNodeBeforeElement(block, para);
    			while 	(	block != null
    					&&	(!block.isElement() || nodeCmIsOnlyInline(block))
    					) {
    				Node tempNext = block.next;
    				block.removeNode();
    				para.insertNodeAtEnd(block);
    				block = tempNext;
    			}
    			Node.trimSpaces(lexer, para);
        		continue;
    		}
    		node = next;
    	}
    }
    static boolean isBlank(Lexer lexer, Node node) {// XOWA: tidy; DATE:2015-11-08
    	boolean isBlank = node.type == Node.TEXT_NODE;
    	if (isBlank)
    		isBlank = 		node.end == node.start				// Zero length
    				|| 	(	node.end == node.start + 1			// or one blank
    					&& 	lexer.lexbuf[node.start] == ' ')
    				;
    	return isBlank;
    }
    static boolean nodeCmIsOnlyInline(Node node) {// XOWA: tidy; DATE:2015-11-08
    	return node.hasCm(Dict.CM_INLINE) && !node.hasCm(Dict.CM_BLOCK);
    }
    /**
     * errors in positioning of form start or end tags generally require human intervention to fix.
     */
    static void badForm(Lexer lexer)
    {
        lexer.badForm = 1;
        lexer.errors++;
    }
}
