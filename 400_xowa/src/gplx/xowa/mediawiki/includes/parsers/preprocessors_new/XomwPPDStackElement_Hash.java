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
class XomwPPDStackElement_Hash extends XomwPPDStackElement {
	public XomwPPDStackElement_Hash(XomwPPDPart partClass, String open, String close, XophpArray parts, int count, boolean lineStart, int startPos) {super(partClass, open, close, parts, count, lineStart, startPos);
	}

	/**
	* Get the accumulator that would result if the close is not found.
	*
	* @param int|boolean $openingCount
	* @return array
	*/
	public XophpArray breakSyntax() {return breakSyntax(0);}
	public XophpArray breakSyntax(int openingCount) {
		XophpArray accum;
		if (String_.Eq(this.open, "\n")) {
			accum = XophpArray.array_merge(XophpArray.New(this.savedPrefix), XophpArray.New(((XomwPPDPart)this.parts.Get_at(0)).output));
		} else {
			if (XophpInt_.is_false(openingCount)) {
				openingCount = this.count;
			}
			String s = XophpString_.substr(this.open, 0, -1);
			s += XophpString_.str_repeat(
				XophpString_.substr(this.open, -1),
				openingCount - XophpString_.strlen(s)
			);
			accum = XophpArray.New(this.savedPrefix + s);
			int lastIndex = 0;
			boolean first = true;
			int parts_len = this.parts.Len();
			for (int i = 0; i < parts_len; i++) {
				XomwPPDPart part = (XomwPPDPart)this.parts.Get_at(i);
				if (first) {
					first = false;
				} else if (XophpType_.is_string(accum.Get_at(lastIndex))) {
					accum.Concat_str(lastIndex, "|");
				} else {
					accum.Set(++lastIndex, "|");
				}

				XophpArray partOutput = part.output;
				int partOutputLen = partOutput.Len();
				for (int j = 0; j < partOutputLen; j++) {
					Object node = partOutput.Get_at(j);
					if (XophpType_.is_string(node) && XophpType_.is_string(accum.Get_at(lastIndex))) {
						accum.Concat_str(lastIndex, (String)node);
					} else {
						accum.Set(++lastIndex, node);
					}
				}
			}
		}
		return accum;
	}

	@Override public XomwPPDStackElement New(XomwPPDPart partClass, String open, String close, XophpArray parts, int count, boolean lineStart, int startPos) {return new XomwPPDStackElement_Hash(partClass, open, close, parts, count, lineStart, startPos);}

	public static final XomwPPDStackElement_Hash Prototype = new XomwPPDStackElement_Hash(new XomwPPDPart_Hash(""), null, null, null, 0, false, 0);
}
