/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.langs.htmls.parsers; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.core.btries.*;
public class Html_doc_parser {
	private final Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private final List_adp list = List_adp_.new_();
	private Html_txt_wkr txt_wkr;
	public Html_doc_parser Reg_txt(Html_txt_wkr txt_wkr) {
		this.txt_wkr = txt_wkr;
		return this;
	}
	public void Reg(Html_doc_wkr... wkr_ary) {
		for (Html_doc_wkr wkr : wkr_ary) {
			trie.Add_obj(wkr.Hook(), wkr);
			list.Add(wkr);
		}
	}
	public void Parse(byte[] src, int src_bgn, int src_end) {
		txt_wkr.Init(src, src_bgn, src_end);
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Html_doc_wkr wkr = (Html_doc_wkr)list.Get_at(i);
			wkr.Init(src, src_bgn, src_end);
		}
		int pos = src_bgn;
		int txt_bgn = -1;
		while (pos < src_end) {
			Object o = trie.Match_bgn(src, pos, src_end);
			if (o == null) {
				if (txt_bgn == -1) txt_bgn = pos;
				++pos;
			}
			else {
				if (txt_bgn != -1) {
					txt_wkr.Parse(txt_bgn, pos);
					txt_bgn = -1;
				}
				Html_doc_wkr wkr = (Html_doc_wkr)o;
				pos = wkr.Parse(pos);
			}	
		}
		if (txt_bgn != -1) txt_wkr.Parse(txt_bgn, src_end);
	}
}
