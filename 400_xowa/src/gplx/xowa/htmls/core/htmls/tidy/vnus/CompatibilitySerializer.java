/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.core.htmls.tidy.vnus; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.htmls.tidy.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Stack;
import java.util.EmptyStackException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;
public class CompatibilitySerializer implements ContentHandler, LexicalHandler {
	protected class StackEntry {
		public String uri;
		public String localName;
		public String qName;
		public Attributes attrs;
		OutputStream savedStream;
		public boolean needsPWrapping;
		public boolean isPWrapper;
		public boolean blank;
		public boolean hasText;
		public boolean split;
		public int blockNestingLevel;
		public boolean isDisabledPWrapper;

		public StackEntry(String uri_, String localName_, String qName_,
				Attributes attrs_, OutputStream savedStream_) {
			uri = uri_;
			localName = localName_;
			qName = qName_;
			attrs = attrs_;
			savedStream = savedStream_;
			needsPWrapping = "body".equals(localName_)
				|| "blockquote".equals(localName_);
			blank = true;
			hasText = false;
			isPWrapper = "mw:p-wrap".equals(localName_);
			blockNestingLevel = 0;
			isDisabledPWrapper = false;
			split = false;
		}
	}

	protected Stack<StackEntry> m_stack;
	protected DepurateSerializer m_serializer;
	protected Stack<StackEntry> m_pStack;

	// Warning: this list must be in alphabetical order
	protected static final String[] ONLY_INLINE_ELEMENTS = {"a", "abbr", "acronym",
		"applet", "b", "basefont", "bdo", "big", "br", "button", "cite",
		"code", "dfn", "em", "font", "i", "iframe", "img", "input", "kbd",
		"label", "legend", "map", "object", "param", "q", "rb", "rbc", "rp",
		"rt", "rtc", "ruby", "s", "samp", "select", "small", "span", "strike",
		"strong", "sub", "sup", "textarea", "tt", "u", "var"};

	// Warning: this list must be in alphabetical order
	protected static final String[] MARKED_EMPTY_ELEMENTS = {"li", "p", "tr"};

	public CompatibilitySerializer(OutputStream out) {
		m_stack = new Stack<StackEntry>();
		m_pStack = new Stack<StackEntry>();
		m_serializer = new DepurateSerializer(out);
	}

	private StackEntry peek(Stack<StackEntry> stack) throws SAXException {
		try {
			return stack.peek();
		} catch (EmptyStackException e) {
			return null;
		}
	}

	/**
	 * Pop the top of the stack, restore the parent stream in the serializer
	 * and return the previous stream
	 */
	private ByteArrayOutputStream popAndGetContents() throws SAXException {
		try {
			StackEntry entry = m_stack.pop();
			if (entry.isPWrapper) {
				m_pStack.pop();
			}
			ByteArrayOutputStream entryStream =
				(ByteArrayOutputStream)m_serializer.getOutputStream();
			m_serializer.setOutputStream(entry.savedStream);
			return entryStream;
		} catch (EmptyStackException e) {
			throw new SAXException(e);
		}
	}

	/**
	 * Push a new element to the top of the stack, and set up a new empty
	 * stream in the serializer. Returns the new element.
	 */
	private StackEntry push(String uri, String localName, String qName,
				Attributes attrs) throws SAXException {
		StackEntry entry = new StackEntry(uri, localName, qName, attrs,
				m_serializer.getOutputStream());
		m_stack.push(entry);
		m_serializer.setOutputStream(new ByteArrayOutputStream());
		return entry;
	}

	/**
	 * Equivalent to push() for a proposed p element. Will become a real
	 * p element if the contents is non-blank and contains no block elements.
	 */
	private StackEntry pushPWrapper() throws SAXException {
		StackEntry entry = push("", "mw:p-wrap", "mw:p-wrap", new AttributesImpl());
		m_pStack.push(entry);
		return entry;
	}

	private void writePWrapper(StackEntry entry, ByteArrayOutputStream contents)
			throws SAXException {
		if (!entry.isDisabledPWrapper && !entry.blank) {
			m_serializer.write("<p>");
			m_serializer.writeStream(contents);
			m_serializer.write("</p>");
		} else {
			m_serializer.writeStream(contents);
		}
	}

	public void characters(char[] chars, int start, int length)
			throws SAXException {
		StackEntry entry = peek(m_stack);
		if (entry != null) {
			if (entry.needsPWrapping) {
				entry = pushPWrapper();
			}
			if (entry.blank || !entry.hasText) {
				for (int i = start; i < start + length; i++) {
					char c = chars[i];
					if (!(c == 9 || c == 10 || c == 12 || c == 13 || c == 32)) {
						entry.blank = false;
						entry.hasText = true;
						if (peek(m_pStack) != null) {
							peek(m_pStack).blank = false;
						}
						break;
					}
				}
			}
		}
		m_serializer.characters(chars, start, length);
	}

	private void splitTagStack(boolean haveContent) throws SAXException {
		StackEntry currentPWrapper = peek(m_pStack);
		ByteArrayOutputStream seContent;
		int n = m_stack.size();
		int i = n - 1;
		StackEntry se = m_stack.get(i);
		while (se != currentPWrapper) {
			seContent = (ByteArrayOutputStream)m_serializer.getOutputStream();
			m_serializer.setOutputStream(se.savedStream);

			if (se.hasText) {
				haveContent = true;
			}

			// Emit content accumulated so far
			if (haveContent) {
				m_serializer.startElement(se.uri, se.localName, se.qName, se.attrs);
				m_serializer.writeStream(seContent);
				m_serializer.endElement(se.uri, se.localName, se.qName);

				// All text has been output at this point
				// Record that it has been split and reset it.
				se.split = true;
				se.blank = true;
				se.hasText = false;
			}

			// Reset parent's saved stream always.
			// As we unwind the stack, its saved content
			// could get output.
			se.savedStream = new ByteArrayOutputStream();

			i--;
			se = m_stack.get(i);
		}

		// Dump <p>.. contents ..</p>
		// Note se == currentPWrapper
		if (haveContent || se.hasText) {
			seContent = (ByteArrayOutputStream)m_serializer.getOutputStream();
			m_serializer.setOutputStream(se.savedStream);

			// Emit content accumulated so far
			writePWrapper(se, seContent);

			// All text has been output at this point
			se.blank = true;
		}

		// New stream going forward
		m_serializer.setOutputStream(new ByteArrayOutputStream());
	}

	private boolean isOnlyInline(String localName) {
		return Arrays.binarySearch(ONLY_INLINE_ELEMENTS, localName) > -1;
	}

	private void enterBlock(String tagName) throws SAXException {
		// Whenever we enter a new block wrapper that is
		// embedded within a p-wrapper,
		//
		// 1. Disable p-wrapping.
		// 2. Split the tag stack and emit accumulated output
		//    with a p-wrapper.

		StackEntry currentPWrapper = peek(m_pStack);

		if (currentPWrapper.blockNestingLevel == 0) {
			splitTagStack(false);
		}

		currentPWrapper.blockNestingLevel++;
		currentPWrapper.isDisabledPWrapper = true;
	}

	private void leaveBlock(String tagName) throws SAXException {
		// Whenever we leave the outermost block wrapper that is
		// embedded within a p-wrapper,
		//
		// 1. Re-enable p-wrapping.
		// 2. Split the tag stack and emit accumulated output
		//    without a p-wrapper.

		StackEntry currentPWrapper = peek(m_pStack);
		currentPWrapper.blockNestingLevel--;

		if (currentPWrapper.blockNestingLevel == 0) {
			splitTagStack(true);
		}

		currentPWrapper.isDisabledPWrapper = false;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {

		StackEntry oldEntry = peek(m_stack);
		if (oldEntry != null) {
			if (oldEntry.isPWrapper) {
				if (!isOnlyInline(localName)) {
					// This is non-inline so close the p-wrapper
					ByteArrayOutputStream contents = popAndGetContents();
					writePWrapper(oldEntry, contents);
					oldEntry = peek(m_stack);
				} else {
					// We're putting an element inside the p-wrapper, so it is non-blank now
					oldEntry.blank = false;
				}
			} else {
				oldEntry.blank = false;
			}
		}

		// Track block nesting level
		boolean onlyInline = isOnlyInline(localName);
		StackEntry currentPWrapper = peek(m_pStack);
		if (currentPWrapper != null && !onlyInline) {
			enterBlock(localName);
		}

		if (oldEntry != null && oldEntry.needsPWrapping && onlyInline) {
			StackEntry entry = pushPWrapper();
			// We're putting an element inside the p-wrapper, so it is non-blank
			entry.blank = false;
		}
		push(uri, localName, qName, atts);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		StackEntry entry = peek(m_stack);
		ByteArrayOutputStream contents = popAndGetContents();

		if (entry.isPWrapper) {
			// Since we made this p-wrapper, the caller really wants to end the parent element.
			// So first we need to close the p-wrapper
			writePWrapper(entry, contents);
			entry = peek(m_stack);
			contents = popAndGetContents();
		}

		// Annotate empty tr and li elements so that they can be hidden in CSS,
		// for compatibility with tidy and existing wikitext
		if (Arrays.binarySearch(MARKED_EMPTY_ELEMENTS, localName) > -1) {
			if (entry.attrs.getLength() == 0 && entry.blank) {
				AttributesImpl newAttrs = new AttributesImpl();
				newAttrs.addAttribute("", "class", "class", "", "mw-empty-elt");
				entry.attrs = newAttrs;
			}
		}

		if (!entry.split || !entry.blank) {
			m_serializer.startElement(entry.uri, entry.localName, entry.qName, entry.attrs);
			m_serializer.writeStream(contents);
			m_serializer.endElement(uri, localName, qName);
		}

		// Track block nesting level
		boolean onlyInline = isOnlyInline(localName);
		StackEntry currentPWrapper = peek(m_pStack);
		if (currentPWrapper != null && !onlyInline) {
			leaveBlock(localName);
		}
	}

	public void startDocument() throws SAXException {
	}

	public void endDocument() throws SAXException {
		m_serializer.endDocument();
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		characters(ch, start, length);
	}
	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void comment(char[] ch, int start, int length) throws SAXException {
		m_serializer.comment(ch, start, length);
	}

	public void endCDATA() throws SAXException {
	}
	public void endDTD() throws SAXException {
	}

	public void endEntity(String name) throws SAXException {
	}

	public void startCDATA() throws SAXException {
	}

	public void startDTD(String name, String publicId, String systemId)
			throws SAXException {
	}

	public void startEntity(String name) throws SAXException {
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}

	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public void skippedEntity(String name) throws SAXException {
	}
}
