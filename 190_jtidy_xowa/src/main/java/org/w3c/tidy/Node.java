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
 * Used for elements and text nodes element name is null for text nodes start and end are offsets into lexbuf which
 * contains the textual content of all elements in the parse tree. Parent and content allow traversal of the parse tree
 * in any direction. attributes are represented as a linked list of AttVal nodes which hold the strings for
 * attribute/value pairs.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 930 $ ($Author: aditsu $)
 */
public class Node
{

    /**
     * node type: root.
     */
    public static final short ROOT_NODE = 0;

    /**
     * node type: doctype.
     */
    public static final short DOCTYPE_TAG = 1;

    /**
     * node type: comment.
     */
    public static final short COMMENT_TAG = 2;

    /**
     * node type: .
     */
    public static final short PROC_INS_TAG = 3;

    /**
     * node type: text.
     */
    public static final short TEXT_NODE = 4;

    /**
     * Start tag.
     */
    public static final short START_TAG = 5;

    /**
     * End tag.
     */
    public static final short END_TAG = 6;

    /**
     * Start of an end tag.
     */
    public static final short START_END_TAG = 7;

    /**
     * node type: CDATA.
     */
    public static final short CDATA_TAG = 8;

    /**
     * node type: section tag.
     */
    public static final short SECTION_TAG = 9;

    /**
     * node type: asp tag.
     */
    public static final short ASP_TAG = 10;

    /**
     * node type: jste tag.
     */
    public static final short JSTE_TAG = 11;

    /**
     * node type: php tag.
     */
    public static final short PHP_TAG = 12;

    /**
     * node type: doctype.
     */
    public static final short XML_DECL = 13;

    /**
     * Description for all the node types. Used in toString.
     */
    private static final String[] NODETYPE_STRING = {
        "RootNode",
        "DocTypeTag",
        "CommentTag",
        "ProcInsTag",
        "TextNode",
        "StartTag",
        "EndTag",
        "StartEndTag",
        "SectionTag",
        "AspTag",
        "PhpTag",
        "XmlDecl"};

    /**
     * parent node.
     */
    protected Node parent;

    /**
     * pevious node.
     */
    protected Node prev;

    /**
     * next node.
     */
    protected Node next;

    /**
     * last node.
     */
    protected Node last;

    /**
     * start of span onto text array.
     */
    protected int start;

    /**
     * end of span onto text array.
     */
    protected int end;

    /**
     * the text array.
     */
    protected byte[] textarray;

    /**
     * TextNode, StartTag, EndTag etc.
     */
    protected short type;

    /**
     * true if closed by explicit end tag.
     */
    protected boolean closed;

    /**
     * true if inferred.
     */
    protected boolean implicit;

    /**
     * true if followed by a line break.
     */
    protected boolean linebreak;

    /**
     * old tag when it was changed.
     */
    protected Dict was;

    /**
     * tag's dictionary definition.
     */
    protected Dict tag;

    /**
     * Tag name.
     */
    protected String element;

    /**
     * Attribute/Value linked list.
     */
    protected AttVal attributes;

    /**
     * Contained node.
     */
    protected Node content;

    /**
     * DOM adapter.
     */
    protected org.w3c.dom.Node adapter;

    /**
     * Instantiates a new text node.
     */
    public Node()
    {
        this(TEXT_NODE, null, 0, 0);
    }

    /**
     * Instantiates a new node.
     * @param type node type: Node.ROOT_NODE | Node.DOCTYPE_TAG | Node.COMMENT_TAG | Node.PROC_INS_TAG | Node.TEXT_NODE |
     * Node.START_TAG | Node.END_TAG | Node.START_END_TAG | Node.CDATA_TAG | Node.SECTION_TAG | Node. ASP_TAG |
     * Node.JSTE_TAG | Node.PHP_TAG | Node.XML_DECL
     * @param textarray array of bytes contained in the Node
     * @param start start position
     * @param end end position
     */
    public Node(short type, byte[] textarray, int start, int end)
    {
        this.parent = null;
        this.prev = null;
        this.next = null;
        this.last = null;
        this.start = start;
        this.end = end;
        this.textarray = textarray;
        this.type = type;
        this.closed = false;
        this.implicit = false;
        this.linebreak = false;
        this.was = null;
        this.tag = null;
        this.element = null;
        this.attributes = null;
        this.content = null;
    }

    /**
     * Instantiates a new node.
     * @param type node type: Node.ROOT_NODE | Node.DOCTYPE_TAG | Node.COMMENT_TAG | Node.PROC_INS_TAG | Node.TEXT_NODE |
     * Node.START_TAG | Node.END_TAG | Node.START_END_TAG | Node.CDATA_TAG | Node.SECTION_TAG | Node. ASP_TAG |
     * Node.JSTE_TAG | Node.PHP_TAG | Node.XML_DECL
     * @param textarray array of bytes contained in the Node
     * @param start start position
     * @param end end position
     * @param element tag name
     * @param tt tag table instance
     */
    public Node(short type, byte[] textarray, int start, int end, String element, TagTable tt)
    {
        this.parent = null;
        this.prev = null;
        this.next = null;
        this.last = null;
        this.start = start;
        this.end = end;
        this.textarray = textarray;
        this.type = type;
        this.closed = false;
        this.implicit = false;
        this.linebreak = false;
        this.was = null;
        this.tag = null;
        this.element = element;
        this.attributes = null;
        this.content = null;
        if (type == START_TAG || type == START_END_TAG || type == END_TAG)
        {
            tt.findTag(this);
        }
    }

    /**
     * Returns an attribute with the given name in the current node.
     * @param name attribute name.
     * @return AttVal instance or null if no attribute with the iven name is found
     */
    public AttVal getAttrByName(String name)
    {
        AttVal attr;

        for (attr = this.attributes; attr != null; attr = attr.next)
        {
            if (name != null && attr.attribute != null && attr.attribute.equals(name))
            {
                break;
            }
        }

        return attr;
    }

    /**
     * Default method for checking an element's attributes.
     * @param lexer Lexer
     */
    public void checkAttributes(Lexer lexer)
    {
        AttVal attval;

        for (attval = this.attributes; attval != null; attval = attval.next)
        {
            attval.checkAttribute(lexer, this);
        }
    }

    /**
     * The same attribute name can't be used more than once in each element. Discard or join attributes according to
     * configuration.
     * @param lexer Lexer
     */
    public void repairDuplicateAttributes(Lexer lexer)
    {
        AttVal attval;

        for (attval = this.attributes; attval != null;)
        {
            if (attval.asp == null && attval.php == null)
            {
                AttVal current;

                for (current = attval.next; current != null;)
                {
                    if (current.asp == null
                        && current.php == null
                        && attval.attribute != null
                        && attval.attribute.equalsIgnoreCase(current.attribute))
                    {
                        AttVal temp;

                        if ("class".equalsIgnoreCase(current.attribute) && lexer.configuration.joinClasses)
                        {
                            // concatenate classes
                            current.value = current.value + " " + attval.value;

                            temp = attval.next;

                            if (temp.next == null)
                            {
                                current = null;
                            }
                            else
                            {
                                current = current.next;
                            }

                            lexer.report.attrError(lexer, this, attval, Report.JOINING_ATTRIBUTE);

                            removeAttribute(attval);
                            attval = temp;
                        }
                        else if (	"style".equalsIgnoreCase(current.attribute) && lexer.configuration.joinStyles	// 				&& attrIsSTYLE(first) && cfgBool(doc, TidyJoinStyles)
                        		&&	current.value != null && attval.value!= null  									// XOWA:tidy; 	&& AttrHasValue(first) && AttrHasValue(second))
                        		)
                        {
                            // concatenate styles

                            // this doesn't handle CSS comments and leading/trailing white-space very well see
                            // http://www.w3.org/TR/css-style-attr
                            int end = current.value.length() - 1;
                            if (current.value.charAt(end) == ';')
                            {
                                // attribute ends with declaration seperator
                                current.value = current.value + " " + attval.value;
                            }
                            else if (current.value.charAt(end) == '}')
                            {
                                // attribute ends with rule set
                                current.value = current.value + " { " + attval.value + " }";
                            }
                            else
                            {
                                // attribute ends with property value
                                current.value = current.value + "; " + attval.value;
                            }

                            temp = attval.next;

                            if (temp.next == null)
                            {
                                current = null;
                            }
                            else
                            {
                                current = current.next;
                            }
                            lexer.report.attrError(lexer, this, attval, Report.JOINING_ATTRIBUTE);

                            removeAttribute(attval);
                            attval = temp;
                        }
                        else if (lexer.configuration.duplicateAttrs == Configuration.KEEP_LAST)
                        {
                            temp = current.next;

                            lexer.report.attrError(lexer, this, current, Report.REPEATED_ATTRIBUTE);

                            removeAttribute(current);
                            current = temp;
                        }
                        else
                        {
                            temp = attval.next;

                            if (attval.next == null)
                            {
                                current = null;
                            }
                            else
                            {
                                current = current.next;
                            }

                            lexer.report.attrError(lexer, this, attval, Report.REPEATED_ATTRIBUTE);

                            removeAttribute(attval);
                            attval = temp;
                        }
                    }
                    else
                    {
                        current = current.next;
                    }
                }
                attval = attval.next;
            }
            else
            {
                attval = attval.next;
            }
        }
    }

    /**
     * Adds an attribute to the node.
     * @param name attribute name
     * @param value attribute value
     */
    public void addAttribute(String name, String value)
    {
        AttVal av = new AttVal(null, null, null, null, '"', name, value);
        av.dict = AttributeTable.getDefaultAttributeTable().findAttribute(av);

        if (this.attributes == null)
        {
            this.attributes = av;
        }
        else
        {
            // append to end of attributes
            AttVal here = this.attributes;

            while (here.next != null)
            {
                here = here.next;
            }

            here.next = av;
        }
    }

    /**
     * Remove an attribute from node and then free it.
     * @param attr attribute to remove
     */
    public void removeAttribute(AttVal attr)
    {
        AttVal av;
        AttVal prev = null;
        AttVal next;

        for (av = this.attributes; av != null; av = next)
        {
            next = av.next;

            if (av == attr)
            {
                if (prev != null)
                {
                    prev.next = next;
                }
                else
                {
                    this.attributes = next;
                }
            }
            else
            {
                prev = av;
            }
        }
    }

    /**
     * Find the doctype element.
     * @return doctype node or null if not found
     */
    public Node findDocType()
    {
        Node node = this.content;

        while (node != null && node.type != DOCTYPE_TAG)
        {
            node = node.next;
        }

        return node;
    }

    /**
     * Discard the doctype node.
     */
    public void discardDocType()
    {
        Node node;

        node = findDocType();
        if (node != null)
        {
            if (node.prev != null)
            {
                node.prev.next = node.next;
            }
            else
            {
                node.parent.content = node.next;
            }

            if (node.next != null)
            {
                node.next.prev = node.prev;
            }

            node.next = null;
        }
    }

    /**
     * Remove node from markup tree and discard it.
     * @param element discarded node
     * @return next node
     */
    public static Node discardElement(Node element)
    {
        Node next = null;

        if (element != null)
        {
            next = element.next;
            element.removeNode();
        }

        return next;
    }

    /**
     * Insert a node into markup tree.
     * @param node to insert
     */
    public void insertNodeAtStart(Node node)
    {
        node.parent = this;

        if (this.content == null)
        {
            this.last = node;
        }
        else
        {
            this.content.prev = node; // AQ added 13 Apr 2000
        }

        node.next = this.content;
        node.prev = null;
        this.content = node;
    }

    /**
     * Insert node into markup tree.
     * @param node Node to insert
     */
    public void insertNodeAtEnd(Node node)
    {
        node.parent = this;
        node.prev = this.last;

        if (this.last != null)
        {
            this.last.next = node;
        }
        else
        {
            this.content = node;
        }

        this.last = node;
    }

    /**
     * Insert node into markup tree in pace of element which is moved to become the child of the node.
     * @param element child node. Will be inserted as a child of element
     * @param node parent node
     */
    public static void insertNodeAsParent(Node element, Node node)
    {
        node.content = element;
        node.last = element;
        node.parent = element.parent;
        element.parent = node;

        if (node.parent.content == element)
        {
            node.parent.content = node;
        }

        if (node.parent.last == element)
        {
            node.parent.last = node;
        }

        node.prev = element.prev;
        element.prev = null;

        if (node.prev != null)
        {
            node.prev.next = node;
        }

        node.next = element.next;
        element.next = null;

        if (node.next != null)
        {
            node.next.prev = node;
        }
    }

    /**
     * Insert node into markup tree before element.
     * @param element child node. Will be insertedbefore element
     * @param node following node
     */
    public static void insertNodeBeforeElement(Node element, Node node)
    {
        Node parent;

        parent = element.parent;
        node.parent = parent;
        node.next = element;
        node.prev = element.prev;
        element.prev = node;

        if (node.prev != null)
        {
            node.prev.next = node;
        }

        if (parent != null && parent.content == element)
        {
            parent.content = node;
        }
    }

    /**
     * Insert node into markup tree after element.
     * @param node new node to insert
     */
    public void insertNodeAfterElement(Node node)
    {
        Node parent;

        parent = this.parent;
        node.parent = parent;

        // AQ - 13Jan2000 fix for parent == null
        if (parent != null && parent.last == this)
        {
            parent.last = node;
        }
        else
        {
            node.next = this.next;
            // AQ - 13Jan2000 fix for node.next == null
            if (node.next != null)
            {
                node.next.prev = node;
            }
        }

        this.next = node;
        node.prev = this;
    }

    /**
     * Trim an empty element.
     * @param lexer Lexer
     * @param element empty node to be removed
     */
    public static Node trimEmptyElement(Lexer lexer, Node element)
    {
        // don't trim if user explicitely set trim-empty-elements to false
        // empty element can be needed in css sites
        if (lexer.configuration.trimEmpty)
        {
//            TagTable tt = lexer.configuration.tt;

            if (lexer.canPrune(element))
            {
                if (element.type != TEXT_NODE)
                    lexer.report.warning(lexer, element, null, Report.TRIM_EMPTY_ELEMENT);

                return discardElement(element);
            }
            // XOWA:jtidy;
//            else if (element.tag == tt.tagP && element.content == null)
//            {
//                // replace <p></p> by <br><br> to preserve formatting
//                Node node = lexer.inferredTag("br");
//                Node.coerceNode(lexer, element, tt.tagBr);
//                element.insertNodeAfterElement(node);
//            }
        }
        return element.next;
    }
    // XOWA:tidy
    public static Node dropEmptyElements(Lexer lexer, Node node) {
    	Node next = null;
    	while (node != null)
    	{
    		next = node.next;
    		if (node.content != null)
    			dropEmptyElements(lexer, node.content);
    		if (	!(node.type == Node.START_TAG || node.type == Node.START_END_TAG)
    			&&	!(node.type == Node.TEXT_NODE) && !(node.start < node.end)
    			) 
    		{
    			node = next;
    			continue;
    		}
    		next = Node.trimEmptyElement(lexer, node);
    		node = next;
    	}
    	return node;
    }

    /**
     * This maps <em> hello </em> <strong>world </strong> to <em> hello </em> <strong>world </strong>. If last child of
     * element is a text node then trim trailing white space character moving it to after element's end tag.
     * @param lexer Lexer
     * @param element node
     * @param last last child of element
     */
    public static void trimTrailingSpace(Lexer lexer, Node element, Node last)
    {
        byte c;
        TagTable tt = lexer.configuration.tt;

        if (last != null && last.type == Node.TEXT_NODE)
        {
            if (last.end > last.start)

            {
                c = lexer.lexbuf[last.end - 1];

                if (c == 160 || c == (byte) ' ')
                {
                    // take care with <td> &nbsp; </td>
                    // fix for [435920]
                    if (c == 160 && (element.tag == tt.tagTd || element.tag == tt.tagTh))
                    {
                        if (last.end > last.start + 1)
                        {
                            last.end -= 1;
                        }
                    }
                    else
                    {
                        last.end -= 1;

                        if (TidyUtils.toBoolean(element.tag.model & Dict.CM_INLINE)
                            && !TidyUtils.toBoolean(element.tag.model & Dict.CM_FIELD))
                        {
                            lexer.insertspace = true;
                        }
                    }
                }
            }
            // if empty string then delete from parse tree
            if (last.start == last.end) // COMMENT_NBSP_FIX: && tag != tag_td && tag != tag_th
            {
                trimEmptyElement(lexer, last);
            }
        }
    }

    /**
     * Escapes the given tag.
     * @param lexer Lexer
     * @param element node to be escaped
     * @return escaped node
     */
    protected static Node escapeTag(Lexer lexer, Node element)
    {
        Node node = lexer.newNode();
        node.start = lexer.lexsize;
        node.textarray = element.textarray; // @todo check it
        lexer.addByte('<');

        if (element.type == END_TAG)
        {
            lexer.addByte('/');
        }

        if (element.element != null)
        {
            lexer.addStringLiteral(element.element);
        }
        else if (element.type == DOCTYPE_TAG)
        {
            int i;

            lexer.addByte('!');
            lexer.addByte('D');
            lexer.addByte('O');
            lexer.addByte('C');
            lexer.addByte('T');
            lexer.addByte('Y');
            lexer.addByte('P');
            lexer.addByte('E');
            lexer.addByte(' ');

            for (i = element.start; i < element.end; ++i)
            {
                lexer.addByte(lexer.lexbuf[i]);
            }
        }

        if (element.type == START_END_TAG)
        {
            lexer.addByte('/');
        }

        lexer.addByte('>');
        node.end = lexer.lexsize;

        return node;
    }

    /**
     * Is the node content empty or blank? Assumes node is a text node.
     * @param lexer Lexer
     * @return <code>true</code> if the node content empty or blank
     */
    public boolean isBlank(Lexer lexer)
    {
        if (this.type == TEXT_NODE)
        {
            if (this.end == this.start)
            {
                return true;
            }
            if (this.end == this.start + 1 && lexer.lexbuf[this.end - 1] == ' ')
            {
                return true;
            }
        }
        return false;
    }

    /**
     * This maps <code>&lt;p> hello &lt;em> world &lt;/em></code> to <code>&lt;p> hello &lt;em> world &lt;/em></code>.
     * Trims initial space, by moving it before the start tag, or if this element is the first in parent's content, then
     * by discarding the space.
     * @param lexer Lexer
     * @param element parent node
     * @param text text node
     */
    public static void trimInitialSpace(Lexer lexer, Node element, Node text)
    {
        Node prev, node;

        // #427677 - fix by Gary Peskin 31 Oct 00
        if (text.type == TEXT_NODE && text.textarray[text.start] == (byte) ' ' && (text.start < text.end))
        {
            if (TidyUtils.toBoolean(element.tag.model & Dict.CM_INLINE)
                && !TidyUtils.toBoolean(element.tag.model & Dict.CM_FIELD)
                && element.parent.content != element)
            {
                prev = element.prev;

                if (prev != null && prev.type == TEXT_NODE)
                {
                    if (prev.textarray[prev.end - 1] != (byte) ' ')
                    {
                        prev.textarray[prev.end++] = (byte) ' ';
                    }

                    ++element.start;
                }
                else
                {
                    // create new node
                    node = lexer.newNode();
                    // Local fix for bug 228486 (GLP). This handles the case
                    // where we need to create a preceeding text node but there are
                    // no "slots" in textarray that we can steal from the current
                    // element. Therefore, we create a new textarray containing
                    // just the blank. When Tidy is fixed, this should be removed.
                    if (element.start >= element.end)
                    {
                        node.start = 0;
                        node.end = 1;
                        node.textarray = new byte[1];
                    }
                    else
                    {
                        node.start = element.start++;
                        node.end = element.start;
                        node.textarray = element.textarray;
                    }
                    node.textarray[node.start] = (byte) ' ';
                    node.prev = prev;
                    if (prev != null)
                    {
                        prev.next = node;
                    }
                    node.next = element;
                    element.prev = node;
                    node.parent = element.parent;
                }
            }

            // discard the space in current node
            ++text.start;
        }
    }

    /**
     * Move initial and trailing space out. This routine maps: hello <em> world </em> to hello <em> world </em> and
     * <em> hello </em> <strong>world </strong> to <em> hello </em> <strong>world </strong>.
     * @param lexer Lexer
     * @param element Node
     */
    public static void trimSpaces(Lexer lexer, Node element)
    {
        Node text = element.content;
        TagTable tt = lexer.configuration.tt;
// XOWA        
//        if (element.tag == tt.tagPre || element.isDescendantOf(tt.tagPre))
//            return;

        if (text != null && text.type == Node.TEXT_NODE && element.tag != tt.tagPre)
        {
            trimInitialSpace(lexer, element, text);
        }

        text = element.last;

        if (text != null && text.type == Node.TEXT_NODE)
        {
            trimTrailingSpace(lexer, element, text);
        }
    }

    /**
     * Is this node contained in a given tag?
     * @param tag descendant tag
     * @return <code>true</code> if node is contained in tag
     */
    public boolean isDescendantOf(Dict tag)
    {
        Node parent;

        for (parent = this.parent; parent != null; parent = parent.parent)
        {
            if (parent.tag == tag)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * The doctype has been found after other tags, and needs moving to before the html element.
     * @param lexer Lexer
     * @param element document
     * @param doctype doctype node to insert at the beginning of element
     */
    public static void insertDocType(Lexer lexer, Node element, Node doctype)
    {
        TagTable tt = lexer.configuration.tt;

        lexer.report.warning(lexer, element, doctype, Report.DOCTYPE_AFTER_TAGS);

        while (element.tag != tt.tagHtml)
        {
            element = element.parent;
        }

        insertNodeBeforeElement(element, doctype);
    }

    /**
     * Find the body node.
     * @param tt tag table
     * @return body node
     */
    public Node findBody(TagTable tt)
    {
        Node node;

        node = this.content;

        while (node != null && node.tag != tt.tagHtml)
        {
            node = node.next;
        }

        if (node == null)
        {
            return null;
        }

        node = node.content;

        while (node != null && node.tag != tt.tagBody && node.tag != tt.tagFrameset)
        {
            node = node.next;
        }

        if (node.tag == tt.tagFrameset)
        {
            node = node.content;

            while (node != null && node.tag != tt.tagNoframes)
            {
                node = node.next;
            }

            if (node != null)
            {
                node = node.content;
                while (node != null && node.tag != tt.tagBody)
                {
                    node = node.next;
                }
            }
        }

        return node;
    }

    /**
     * Is the node an element?
     * @return <code>true</code> if type is START_TAG | START_END_TAG
     */
    public boolean isElement()
    {
        return (this.type == START_TAG || this.type == START_END_TAG ? true : false);
    }

    /**
     * Unexpected content in table row is moved to just before the table in accordance with Netscape and IE. This code
     * assumes that node hasn't been inserted into the row.
     * @param row Row node
     * @param node Node which should be moved before the table
     * @param tt tag table
     */
    public static void moveBeforeTable(Node row, Node node, TagTable tt)
    {
        Node table;

        /* first find the table element */
        for (table = row.parent; table != null; table = table.parent)
        {
            if (table.tag == tt.tagTable)
            {
                if (table.parent.content == table)
                {
                    table.parent.content = node;
                }

                node.prev = table.prev;
                node.next = table;
                table.prev = node;
                node.parent = table.parent;

                if (node.prev != null)
                {
                    node.prev.next = node;
                }

                break;
            }
        }
    }

    /**
     * If a table row is empty then insert an empty cell.This practice is consistent with browser behavior and avoids
     * potential problems with row spanning cells.
     * @param lexer Lexer
     * @param row row node
     */
    public static void fixEmptyRow(Lexer lexer, Node row)
    {
        Node cell;

        if (row.content == null)
        {
            cell = lexer.inferredTag("td");
            row.insertNodeAtEnd(cell);
            lexer.report.warning(lexer, row, cell, Report.MISSING_STARTTAG);
        }
    }

    /**
     * Coerce a node.
     * @param lexer Lexer
     * @param node Node
     * @param tag tag dictionary reference
     */
    public static void coerceNode(Lexer lexer, Node node, Dict tag)
    {
        Node tmp = lexer.inferredTag(tag.name);
        lexer.report.warning(lexer, node, tmp, Report.OBSOLETE_ELEMENT);
        node.was = node.tag;
        node.tag = tag;
        node.type = START_TAG;
        node.implicit = true;
        node.element = tag.name;
    }

    /**
     * Extract this node and its children from a markup tree.
     */
    public void removeNode()
    {
        if (this.prev != null)
        {
            this.prev.next = this.next;
        }

        if (this.next != null)
        {
            this.next.prev = this.prev;
        }

        if (this.parent != null)
        {
            if (this.parent.content == this)
            {
                this.parent.content = this.next;
            }

            if (this.parent.last == this)
            {
                this.parent.last = this.prev;
            }
        }

        this.parent = null;
        this.prev = null;
        this.next = null;
    }

    /**
     * Insert a node at the end.
     * @param element parent node
     * @param node will be inserted at the end of element
     * @return <code>true</code> if the node has been inserted
     */
    public static boolean insertMisc(Node element, Node node)
    {
        if (node.type == COMMENT_TAG
            || node.type == PROC_INS_TAG
            || node.type == CDATA_TAG
            || node.type == SECTION_TAG
            || node.type == ASP_TAG
            || node.type == JSTE_TAG
            || node.type == PHP_TAG
            || node.type == XML_DECL)
        {
            element.insertNodeAtEnd(node);
            return true;
        }

        return false;
    }

    /**
     * Is this a new (user defined) node? Used to determine how attributes without values should be printed. This was
     * introduced to deal with user defined tags e.g. Cold Fusion.
     * @return <code>true</code> if this node represents a user-defined tag.
     */
    public boolean isNewNode()
    {
        if (this.tag != null)
        {
            return TidyUtils.toBoolean(this.tag.model & Dict.CM_NEW);
        }

        return true;
    }

    /**
     * Does the node have one (and only one) child?
     * @return <code>true</code> if the node has one child
     */
    public boolean hasOneChild()
    {
        return (this.content != null && this.content.next == null);
    }

    /**
     * Find the "html" element.
     * @param tt tag table
     * @return html node
     */
    public Node findHTML(TagTable tt)
    {
        Node node;

        for (node = this.content; node != null && node.tag != tt.tagHtml; node = node.next)
        {
            //
        }

        return node;
    }

    /**
     * Find the head tag.
     * @param tt tag table
     * @return head node
     */
    public Node findHEAD(TagTable tt)
    {
        Node node;

        node = this.findHTML(tt);

        if (node != null)
        {
            for (node = node.content; node != null && node.tag != tt.tagHead; node = node.next)
            {
                //
            }
        }

        return node;
    }
    
    public Node findTITLE(TagTable tt) {
        Node node = findHEAD(tt);
        if (node != null) {
            for (node = node.content; node != null && node.tag != tt.tagTitle; node = node.next) {
            	// do nothing
            }
        }
        return node;
    }

    /**
     * Checks for node integrity.
     * @return false if node is not consistent
     */
    public boolean checkNodeIntegrity()
    {
        Node child;

        if (this.prev != null)
        {
            if (this.prev.next != this)
            {
                return false;
            }
        }

        if (this.next != null)
        {
            if (next == this || this.next.prev != this)
            {
                return false;
            }
        }

        if (this.parent != null)
        {
            if (this.prev == null && this.parent.content != this)
            {
                return false;
            }

            if (this.next == null && this.parent.last != this)
            {
                return false;
            }
        }

        for (child = this.content; child != null; child = child.next)
        {
            if (child.parent != this || !child.checkNodeIntegrity())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Add a css class to the node. If a class attribute already exists adds the value to the existing attribute.
     * @param classname css class name
     */
    public void addClass(String classname)
    {
        AttVal classattr = this.getAttrByName("class");

        // if there already is a class attribute then append class name after a space
        if (classattr != null)
        {
            classattr.value = classattr.value + " " + classname;
        }
        else
        {
            // create new class attribute
            this.addAttribute("class", classname);
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        String s = "";
        Node n = this;

        while (n != null)
        {
            s += "[Node type=";
            s += NODETYPE_STRING[n.type];
            s += ",element=";
            if (n.element != null)
            {
                s += n.element;
            }
            else
            {
                s += "null";
            }
            if (n.type == TEXT_NODE || n.type == COMMENT_TAG || n.type == PROC_INS_TAG)
            {
                s += ",text=";
                if (n.textarray != null && n.start <= n.end)
                {
                    s += "\"";
                    s += TidyUtils.getString(n.textarray, n.start, n.end - n.start);
                    s += "\"";
                }
                else
                {
                    s += "null";
                }
            }
            s += ",content=";
            if (n.content != null)
            {
                s += n.content.toString();
            }
            else
            {
                s += "null";
            }
            s += "]";
            if (n.next != null)
            {
                s += ",";
            }
            n = n.next;
        }
        return s;
    }

    /**
     * Returns a DOM Node which wrap the current tidy Node.
     * @return org.w3c.dom.Node instance
     */
    protected org.w3c.dom.Node getAdapter()
    {
        if (adapter == null)
        {
            switch (this.type)
            {
                case ROOT_NODE :
                    adapter = new DOMDocumentImpl(this);
                    break;
                case START_TAG :
                case START_END_TAG :
                    adapter = new DOMElementImpl(this);
                    break;
                case DOCTYPE_TAG :
                    adapter = new DOMDocumentTypeImpl(this);
                    break;
                case COMMENT_TAG :
                    adapter = new DOMCommentImpl(this);
                    break;
                case TEXT_NODE :
                    adapter = new DOMTextImpl(this);
                    break;
                case CDATA_TAG :
                    adapter = new DOMCDATASectionImpl(this);
                    break;
                case PROC_INS_TAG :
                    adapter = new DOMProcessingInstructionImpl(this);
                    break;
                default :
                    adapter = new DOMNodeImpl(this);
            }
        }
        return adapter;
    }

    /**
     * Clone this node.
     * @param deep if true deep clone the node (also clones all the contained nodes)
     * @return cloned node
     */
    protected Node cloneNode(boolean deep)
    {
    	Node node = new Node(type, textarray, start, end);
        node.parent = parent;
        node.closed = closed;
        node.implicit = implicit;
        node.tag = tag;
        node.element = element;
        if (attributes != null) {
        	node.attributes = (AttVal) attributes.clone();
        }
        if (deep)
        {
            Node child;
            Node newChild;
            for (child = this.content; child != null; child = child.next)
            {
                newChild = child.cloneNode(deep);
                node.insertNodeAtEnd(newChild);
            }
        }
        return node;
    }

    /**
     * Setter for node type.
     * @param newType a valid node type constant
     */
    protected void setType(short newType)
    {
        this.type = newType;
    }

    /**
     * Used to check script node for script language.
     * @return <code>true</code> if the script node contains javascript
     */
    public boolean isJavaScript()
    {
        boolean result = false;
        AttVal attr;

        if (this.attributes == null)
        {
            return true;
        }

        for (attr = this.attributes; attr != null; attr = attr.next)
        {
            if (("language".equalsIgnoreCase(attr.attribute) || "type".equalsIgnoreCase(attr.attribute))
                && "javascript".equalsIgnoreCase(attr.value))
            {
                result = true;
            }
        }

        return result;
    }

    /**
     * Does the node expect contents?
     * @return <code>false</code> if this node should be empty
     */
    public boolean expectsContent()
    {
        if (this.type != Node.START_TAG)
        {
            return false;
        }

        // unknown element?
        if (this.tag == null)
        {
            return true;
        }

        if (TidyUtils.toBoolean(this.tag.model & Dict.CM_EMPTY))
        {
            return false;
        }

        return true;
    }
    
    public boolean hasCm(int comp) {
    	return (tag.model & comp) != 0;
    }
}
