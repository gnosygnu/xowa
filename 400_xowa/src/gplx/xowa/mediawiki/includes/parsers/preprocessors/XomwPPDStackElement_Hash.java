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
package gplx.xowa.mediawiki.includes.parsers.preprocessors; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
// MW.FILE:Preprocessor_Hash
/**
* @ingroup Parser
*/
public class XomwPPDStackElement_Hash extends XomwPPDStackElement { 	public XomwPPDStackElement_Hash(XomwPPDPart part_factory, String open, String close, int count, int start_pos, boolean lineStart) {super(part_factory, open, close, count, start_pos, lineStart);
	}

	private XomwPPDPart_Hash Get_at_hash(int i) {
		return (XomwPPDPart_Hash)this.parts.Get_at(i);
	}
	/**
	* Get the accumulator that would result if the close is not found.
	*
	* @param int|boolean $openingCount
	* @return array
	*/
	@Override public Object breakSyntax(int openingCount) {
		XophpArray accum = null;
		if (String_.Eq(this.open, "\n")) {
			accum = (XophpArray)Get_at_hash(0).Accum();
		}
		else {
			if (openingCount == -1) {
				openingCount = this.count;
			}
			accum = XophpArray.New(XophpString_.str_repeat(this.open, openingCount));
			int lastIndex = 0;
			boolean first = true;
			int parts_len = parts.Len();
			for (int i = 0; i < parts_len; i++) {
				XomwPPDPart_Hash part = Get_at_hash(i);
				if (first) {
					first = false;
				}
				else if (XophpTypeUtl.is_string(accum.Get_at_str(lastIndex))) {
					accum.Set(lastIndex, accum.Get_at_str(lastIndex) + "|");
				} else {
					accum.Set(++lastIndex, "|");
				}
				
				XophpArray part_out = ((Xomw_prepro_accum__hash)part.Accum()).Ary();
				int part_out_len = part_out.Len();
				for (int j = 0; j < part_out_len; j++) {
					Object node = part_out.Get_at(j);
					if (XophpTypeUtl.is_string(node) && XophpTypeUtl.is_string(accum.Get_at(lastIndex))) {
						accum.Set(lastIndex, accum.Get_at_str(lastIndex) + (String)node);
					} else {
						accum.Set(++lastIndex, node);
					}
				}
			}
		}
		return accum;
	}
}