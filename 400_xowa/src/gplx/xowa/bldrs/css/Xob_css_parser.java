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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
class Xob_css_parser {
	private final    Bry_bfr bfr = Bry_bfr_.New_w_size(255);
	private final    Xob_mirror_mgr mgr;
	private final    Xob_css_parser__url url_parser; private final    Xob_css_parser__import import_parser;
	private final    Btrie_rv trv = new Btrie_rv();
	public Xob_css_parser(Xob_mirror_mgr mgr) {
		this.mgr = mgr;
		this.url_parser = new Xob_css_parser__url(mgr.Site_url());
		this.import_parser = new Xob_css_parser__import(url_parser);
	}
	public void Parse(byte[] src) {
		int src_len = src.length; int pos = 0;
		while (pos < src_len) {
			byte b = src[pos];
			Object o = tkns_trie.Match_at_w_b0(trv, b, src, pos, src_len);
			if (o == null) {
				bfr.Add_byte(b);
				++pos;
			}
			else {
				byte tkn_tid = ((Byte_obj_val)o).Val();
				int match_pos = trv.Pos();
				Xob_css_tkn__base tkn = null;
				switch (tkn_tid) {
					case Tkn_url:		tkn = url_parser.Parse(src, src_len, pos, match_pos); break;
					case Tkn_import:	tkn = import_parser.Parse(src, src_len, pos, match_pos); break;
				}
				tkn.Process(mgr);
				pos = tkn.Write(bfr, src);
			}
		}
	}
	private static final byte Tkn_import = 1, Tkn_url = 2;
	private static final    Btrie_slim_mgr tkns_trie = Btrie_slim_mgr.ci_a7()
	.Add_str_byte("@import"		, Tkn_import)
	.Add_str_byte(" url("		, Tkn_url)
	;
}
