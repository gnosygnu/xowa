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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
public abstract class Xoh_itm_parser_fxt {
	private final Xoae_app app;
	private final Xowe_wiki wiki;
	private final Bry_err_wkr err_wkr = new Bry_err_wkr();
	protected byte[] src; protected int src_len;
	protected final Xoh_hdoc_ctx hctx = new Xoh_hdoc_ctx();
	public Xoh_itm_parser_fxt() {
		this.app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_tst_(app);
		hctx.Init_by_app(app);
	}
	private Xoh_itm_parser Parser() {return Parser_get();}
	public abstract Xoh_itm_parser Parser_get();
	public void Test__parse__fail(String src_str, String expd) {
		Gfo_usr_dlg_.Test__list__init();
		Parser().Fail_throws_err_(Bool_.N);
		Exec_parse(src_str);
		Parser().Fail_throws_err_(Bool_.Y);
		Tfds.Eq_str(expd, Gfo_usr_dlg_.Test__list__term__get_1st());
	}
	public void Exec_parse(String src_str) {
		this.src = Bry_.new_u8(src_str); this.src_len = src.length;
		hctx.Init_by_page(wiki, Xoa_page_.Main_page_bry);
		err_wkr.Init_by_page(Xoa_page_.Main_page_str, src);
		Exec_parse_hook(err_wkr, hctx, 0, src_len);
	}
	public abstract void Exec_parse_hook(Bry_err_wkr err_wkr, Xoh_hdoc_ctx hctx, int src_bgn, int src_end);
}
