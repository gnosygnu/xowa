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
package gplx.xowa.html.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
class Xow_hzip_mgr_fxt {
	private Bry_bfr bfr = Bry_bfr.reset_(Io_mgr.Len_mb); private Xow_hzip_mgr hzip_mgr; private Xowe_wiki wiki;
	private Xodump_stats_itm stats = new Xodump_stats_itm();
	public void Clear() {
		if (hzip_mgr == null) {
			Xoae_app app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			hzip_mgr = new Xow_hzip_mgr(Gfo_usr_dlg_.I, wiki);
		}
	}
	public void	Init_xwiki(String alias, String domain) {
		wiki.Appe().Wiki_mgr().Get_by_key_or_make(Bry_.new_utf8_(domain));
		wiki.Xwiki_mgr().Add_full(alias, domain);
		wiki.Appe().User().Wiki().Xwiki_mgr().Add_full(domain, domain);
	}
	public void Test_save(byte[][] expd_brys, String html) {Test_save(html, expd_brys);}
	public void Test_save(String html, byte[]... expd_brys) {
		byte[] expd = Bry_.Add(expd_brys);
		hzip_mgr.Write(bfr, stats, Bry_.Empty, Bry_.new_utf8_(html));
		Tfds.Eq_ary(expd, bfr.Xto_bry_and_clear());
	}
	public void Test_load(byte[][] src_brys, String expd) {
		byte[] src = Bry_.Add(src_brys);
		src = hzip_mgr.Parse(bfr, Bry_.Empty, src);
		Tfds.Eq(expd, String_.new_utf8_(src));
	}
	public void Test_html(String html, String expd) {
		Xop_ctx ctx = wiki.Ctx(); Xop_parser parser = wiki.Parser(); Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		ctx.Para().Enabled_n_();
		ctx.Cur_page().Redlink_lnki_list().Clear();			
		byte[] html_bry = Bry_.new_utf8_(html);
		Xop_root_tkn root = ctx.Tkn_mkr().Root(html_bry);
		parser.Parse_page_all_clear(root, ctx, tkn_mkr, html_bry);
		Xoh_wtr_ctx hctx = Xoh_wtr_ctx.Hdump;
		Xoh_html_wtr html_wtr = wiki.Html_mgr().Html_wtr();
		html_wtr.Cfg().Toc_show_(Bool_.Y);	// needed for hdr to show <span class='mw-headline' id='A'>
		html_wtr.Write_all(bfr, ctx, hctx, html_bry, root);
		Tfds.Eq(expd, bfr.Xto_str_and_clear());
	}
}
