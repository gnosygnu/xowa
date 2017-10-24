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
package gplx.langs.htmls.docs; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.core.btries.*;
public class Gfh_doc_parser {
	private final    Btrie_rv trv = new Btrie_rv();
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private final    Gfh_txt_wkr txt_wkr;
	public Gfh_doc_parser(Gfh_txt_wkr txt_wkr, Gfh_doc_wkr... wkr_ary) {
		this.txt_wkr = txt_wkr;
		for (Gfh_doc_wkr wkr : wkr_ary)
			trie.Add_obj(wkr.Hook(), wkr);
	}
	public void Parse(byte[] page_url, byte[] src, int src_bgn, int src_end) {
		int txt_bgn = -1;
		int pos = src_bgn;
		while (pos < src_end) {
			Object o = trie.Match_at(trv, src, pos, src_end);
			if (o == null) {									// not a known hook; add to txt
				if (txt_bgn == -1) txt_bgn = pos;
				++pos;
			}
			else {												// known hook
				if (txt_bgn != -1) {							// txt pending; handle it
					txt_wkr.Parse(txt_bgn, pos);
					txt_bgn = -1;
				}
				Gfh_doc_wkr wkr = (Gfh_doc_wkr)o;
				try {pos = wkr.Parse(src, src_bgn, src_end, pos);}
				catch (Exception e) {
					Gfh_utl.Log(e, "html parse failed", page_url, src, pos);
					txt_bgn = pos;								// set txt_bgn to hook_bgn which is "pos"; i.e.: txt resumes from start of failed hook
					pos = trv.Pos();							// set pos to hook_end
				}
			}	
		}
		if (txt_bgn != -1) txt_wkr.Parse(txt_bgn, src_end);		// handle add pending txt at EOS
	}
}
