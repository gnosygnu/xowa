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
/*
 * Copyright (c) 2007 Henri Sivonen
 * Copyright (c) 2008-2011 Mozilla Foundation
 * Copyright (c) 2016 Wikimedia Foundation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 */

/*
 * This file is mostly copied from validator.nu's HtmlSerializer. Changes:
 *
 *  - Add a slash to void elements. This is allowed by the HTML 5 spec, it is
 *    documented as having no effect. It allows the output to pass XHTML
 *    validation.
 *
 *  - &nbsp; is replaced with &#160;
 *
 *  - Added getOutputStream(), setOutputStream(), write() to support
 *    CompatibiltySerializer.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
public class DepurateSerializer implements ContentHandler, LexicalHandler {

	private static final String[] VOID_ELEMENTS = { "area", "base", "basefont",
			"bgsound", "br", "col", "command", "embed", "frame", "hr", "img",
			"input", "keygen", "link", "meta", "param", "source", "track",
			"wbr" };

	private static final String[] NON_ESCAPING = { "iframe", "noembed",
			"noframes", "noscript", "plaintext", "script", "style", "xmp" };

	private static Writer wrap(OutputStream out) {
		try {
			return new OutputStreamWriter(out, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private int ignoreLevel = 0;

	private int escapeLevel = 0;

	private OutputStream outputStream;
	private Writer writer;

	public DepurateSerializer(OutputStream out) {
		outputStream = out;
		this.writer = wrap(out);
	}

	public OutputStream getOutputStream() throws SAXException {
		try {
			writer.flush();
		} catch (IOException e) {
			throw new SAXException(e);
		}
		return outputStream;
	}

	public void setOutputStream(OutputStream out) throws RuntimeException {
		outputStream = out;
		writer = wrap(out);
	}

	public void write(String s) throws SAXException {
		try {
			writer.write(s);
			writer.flush();
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}

	public void writeStream(ByteArrayOutputStream s) throws SAXException {
		try {
			writer.flush();
			s.writeTo(outputStream);
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		try {
			if (escapeLevel > 0) {
				writer.write(ch, start, length);
			} else {
				for (int i = start; i < start + length; i++) {
					char c = ch[i];
					switch (c) {
						case '<':
							writer.write("&lt;");
							break;
						case '>':
							writer.write("&gt;");
							break;
						case '&':
							writer.write("&amp;");
							break;
						case '\u00A0':
							writer.write("&#160;");
							break;
						default:
							writer.write(c);
							break;
					}
				}
			}
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}

	public void endDocument() throws SAXException {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (escapeLevel > 0) {
			escapeLevel--;
		}
		if (ignoreLevel > 0) {
			ignoreLevel--;
		} else {
			try {
				writer.write('<');
				writer.write('/');
				writer.write(localName);
				writer.write('>');
			} catch (IOException e) {
				throw new SAXException(e);
			}
		}
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

	public void startDocument() throws SAXException {
		try {
			writer.write("<!DOCTYPE html>\n");
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (escapeLevel > 0) {
			escapeLevel++;
		}
		boolean xhtml = "http://www.w3.org/1999/xhtml".equals(uri);
		if (ignoreLevel > 0
				|| !(xhtml || "http://www.w3.org/2000/svg".equals(uri) || "http://www.w3.org/1998/Math/MathML".equals(uri))) {
			ignoreLevel++;
			return;
		}
		try {
			writer.write('<');
			writer.write(localName);
			for (int i = 0; i < atts.getLength(); i++) {
				String attUri = atts.getURI(i);
				String attLocal = atts.getLocalName(i);
				if (attUri.length() == 0) {
					writer.write(' ');
				} else if (!xhtml
						&& "http://www.w3.org/1999/xlink".equals(attUri)) {
					writer.write(" xlink:");
				} else if ("http://www.w3.org/XML/1998/namespace".equals(attUri)) {
					if (xhtml) {
						if ("lang".equals(attLocal)) {
							writer.write(' ');
						} else {
							continue;
						}
					} else {
						writer.write(" xml:");
					}
				} else {
					continue;
				}
				writer.write(atts.getLocalName(i));
				writer.write('=');
				writer.write('"');
				String val = atts.getValue(i);
				for (int j = 0; j < val.length(); j++) {
					char c = val.charAt(j);
					switch (c) {
						case '"':
							writer.write("&quot;");
							break;
						case '&':
							writer.write("&amp;");
							break;
						case '\u00A0':
							writer.write("&nbsp;");
							break;
						default:
							writer.write(c);
							break;
					}
				}
				writer.write('"');
			}
			if (Arrays.binarySearch(VOID_ELEMENTS, localName) > -1) {
				writer.write(" />");
				ignoreLevel++;
				return;
			} else {
				writer.write('>');
			}
			if ("pre".equals(localName) || "textarea".equals(localName)
					|| "listing".equals(localName)) {
				writer.write('\n');
			}
			if (escapeLevel == 0
					&& Arrays.binarySearch(NON_ESCAPING, localName) > -1) {
				escapeLevel = 1;
			}
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}

	public void comment(char[] ch, int start, int length) throws SAXException {
		if (ignoreLevel > 0 || escapeLevel > 0) {
			return;
		}
		try {
			writer.write("<!--");
			writer.write(ch, start, length);
			writer.write("-->");
		} catch (IOException e) {
			throw new SAXException(e);
		}
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
