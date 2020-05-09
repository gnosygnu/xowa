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
package gplx.xowa.mediawiki.includes.parsers.preprocessors_new; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
// MW.SRC:1.33
/**
* Stack cl+ass to help Preprocessor::preprocessToObj()
* @ingroup Parser
*/
public abstract class XomwPPDStackElement {
	/**
	* @var String Opening character (\n for heading)
	*/
	public String open;

	/**
	* @var String Matching closing character
	*/
	public String close;

	/**
	* @var String Saved prefix that may affect later processing,
	*  e.g. to differentiate `-{{{{` and `{{{{` after later seeing `}}}`.
	*/
	public String savedPrefix = "";

	/**
	* @var int Number of opening characters found (number of "=" for heading)
	*/
	public int count;

	/**
	* @var PPDPart[] Array of PPDPart objects describing pipe-separated parts.
	*/
	public XophpArray parts;

	/**
	* @var boolean True if the open char appeared at the start of the input line.
	*  Not set for headings.
	*/
	public boolean lineStart;

	public XomwPPDPart partClass;// = new XomwPPDPart("");

	public int startPos; // XO.NOTE: added from Preprocessor_Hash; assume this is magic property somewhere

	public XomwPPDStackElement(XomwPPDPart partClass, String open, String close, XophpArray parts, int count, boolean lineStart, int startPos) {
		this.partClass = partClass;
		this.parts = parts == null ? XophpArray.New(partClass.New()) : parts;

		// XO.NOTE: PHP does XophpArray.New().Add("open", open)... but PHP does reflection-like assignment of array members to class members;
		// EX: foreach (item in data) {item.key = item.val;}
		this.open = open;
		this.close = close;
		this.count = count;
		this.lineStart = lineStart;
		this.startPos = startPos;
	}

	public XophpArray getAccum() {
		return (XophpArray)((XomwPPDPart)(this.parts.Get_at(XophpArray.count(this.parts) - 1))).output;
	}

	public void addPart(String s) { // s = ""
		XomwPPDPart clz = this.partClass;
		this.parts.Add(clz.New(s));
	}

	/**
	* @return PPDPart
	*/
	public XomwPPDPart getCurrentPart() {
		return (XomwPPDPart)this.parts.Get_at(XophpArray.count(this.parts) - 1);
	}

	/**
	* @return array
	*/
	public XophpArray getFlags() {
		int partCount = XophpArray.count(this.parts);
		boolean findPipe = !String_.Eq(this.open, "\n") && !String_.Eq(this.open, "[");
		return XophpArray.New()
			.Add("findPipe", findPipe)
			.Add("findEquals", findPipe && partCount > 1 && !XophpObject_.isset_obj(((XomwPPDPart)this.parts.Get_at(partCount - 1)).eqpos))
			.Add("inHeading", String_.Eq(this.open, "\n"))
		;
	}

	/**
	* Get the output String that would result if the close is not found.
	*
	* @param boolean|int $openingCount
	* @return String
	*/
	// Used by XomwPPDStackElement_DOM
	//	public String breakSyntax(int openingCount) { // openingCount = false
	//		String s = null;
	//		if (String_.Eq(this.open, "\n")) {
	//			s = this.savedPrefix + ((XomwPPDPart)this.parts.Get_at(0)).output;
	//		} else {
	//			if (XophpInt_.is_false(openingCount)) {
	//				openingCount = this.count;
	//			}
	//			s = XophpString_.substr(this.open, 0, -1);
	//			s += XophpString_.str_repeat(
	//				XophpString_.substr(this.open, -1),
	//				openingCount - XophpString_.strlen(s)
	//			);
	//			s = this.savedPrefix + s;
	//			boolean first = true;
	//			int parts_len = this.parts.Len();
	//			for (int i = 0; i < parts_len; i++) {
	//				XomwPPDPart part = (XomwPPDPart)this.parts.Get_at(i);
	//				if (first) {
	//					first = false;
	//				} else {
	//					s += '|';
	//				}
	//				s += part.output;
	//			}
	//		}
	//		return s;
	//	}

	public abstract XomwPPDStackElement New(XomwPPDPart partClass, String open, String close, XophpArray parts, int count, boolean lineStart, int startPos);
}
