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
package gplx.xowa; import gplx.*;
import gplx.core.btries.*;
public class Xobd_parser implements Xobd_wkr {
	private Btrie_slim_mgr trie = Btrie_slim_mgr.ci_ascii_();		// NOTE:ci.ascii:MW_const.en; ctg.v1 assumes [[Category:
	private ListAdp wkr_list = ListAdp_.new_();
	public String Wkr_key() {return KEY;} static final String KEY = "page_parser";
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_add(Xobd_parser_wkr wkr) {wkr_list.Add(wkr);}
	public void Wkr_bgn(Xob_bldr app) {
		int wkr_list_len = wkr_list.Count();
		for (int i = 0; i < wkr_list_len; i++) {
			Xobd_parser_wkr wkr = (Xobd_parser_wkr)wkr_list.FetchAt(i);
			wkr.Wkr_bgn(app);
			int hooks_len = wkr.Wkr_hooks().Count();
			for (int j = 0; j < hooks_len; j++) {
				byte[] bry = (byte[])wkr.Wkr_hooks().FetchAt(j);
				trie.Add_obj(bry, wkr);
			}
		}
	}
	public void Wkr_run(Xodb_page page) {
		byte[] src = page.Text(); int src_len = src.length;
		int pos = 0;
		while (true) {
			if (pos == src_len) break;
			Object o = trie.Match_bgn(src, pos, src_len);
			if (o == null)
				++pos;
			else {
				Xobd_parser_wkr wkr = (Xobd_parser_wkr)o;
				pos = wkr.Wkr_run(page, src, src_len, pos, trie.Match_pos());
			}
		}
	}
	public void Wkr_end() {
		int wkr_list_len = wkr_list.Count();
		for (int i = 0; i < wkr_list_len; i++) {
			Xobd_parser_wkr wkr = (Xobd_parser_wkr)wkr_list.FetchAt(i);
			wkr.Wkr_end();
		}
	}
	public void Wkr_print() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		throw Err_.not_implemented_();
	}
}
