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
import gplx.xowa.langs.*;
public class Xow_lang_mgr_fxt {
	public void Clear() {
		app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
		Init_langs(wiki);
	}
	public static void Init_langs(Xow_wiki wiki) {
		Xoa_app app = wiki.App();
		Xoa_lang_mgr lang_mgr = app.Lang_mgr();
		lang_mgr.Groups().Set_bulk(Bry_.new_ascii_(String_.Concat_lines_nl
			(	"+||grp|wiki"
			,	"+|wiki|grp|grp1"
			,	"+|wiki|grp|grp2"
			,	"+|grp1|itm|simple|Simple"
			,	"+|grp2|itm|fr|French"
			,	"+|grp1|itm|es|Spanish"
			,	"+|grp2|itm|de|German"
			,	"+|grp1|itm|it|Italian"
			,	"+|grp1|itm|zh|Chinese"
			)));
		wiki.Xwiki_mgr().Add_bulk_langs(Bry_.new_ascii_("wiki"));
		String bulk = String_.Concat_lines_nl
			(	"simple.wikipedia.org|simple.wikipedia.org"
			,	"fr.wikipedia.org|fr.wikipedia.org"
			,	"es.wikipedia.org|es.wikipedia.org"
			,	"de.wikipedia.org|de.wikipedia.org"
			,	"it.wikipedia.org|it.wikipedia.org"
			);
		wiki.App().User().Wiki().Xwiki_mgr().Add_bulk(Bry_.new_ascii_(bulk));
	}
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	Xoa_app app;
	public void tst(String raw, String expd) {
		Xop_ctx ctx = wiki.Ctx();
		ctx.Cur_page().Ttl_(Xoa_ttl.parse_(wiki, Bry_.new_ascii_("test_page")));
		byte[] raw_bry = Bry_.new_utf8_(raw);
		Bry_bfr bfr = Bry_bfr.new_();
		Xop_root_tkn root = ctx.Tkn_mkr().Root(raw_bry);
		wiki.Parser().Parse_page_all_clear(root, ctx, ctx.Tkn_mkr(), raw_bry);
		wiki.Html_mgr().Html_wtr().Write_all(bfr, ctx, raw_bry, root);

		Bry_bfr html_bfr = Bry_bfr.new_();
		wiki.Xwiki_mgr().Lang_mgr().Html_bld(html_bfr, wiki, ctx.Cur_page().Xwiki_langs(), gplx.xowa.xtns.wdatas.Wdata_xwiki_link_wtr.Qid_null);
	    Tfds.Eq_str_lines(expd, html_bfr.Xto_str_and_clear());
	}
}
