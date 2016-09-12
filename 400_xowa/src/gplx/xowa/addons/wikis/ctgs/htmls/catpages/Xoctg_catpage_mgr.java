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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts.*;
public class Xoctg_catpage_mgr {
	private final    Xoctg_catpage_loader loader = new Xoctg_catpage_loader();
	private final    Xoctg_catpage_url tmp_catpage_url = new Xoctg_catpage_url();
	private final    Xoctg_fmt_grp fmt_subcs = Xoctg_fmt_grp.New__subc(), fmt_pages = Xoctg_fmt_grp.New__page(), fmt_files = Xoctg_fmt_grp.New__file();
	public Xoctg_fmt_grp Fmt(byte tid) {	// TEST:
		switch (tid) {
			case Xoa_ctg_mgr.Tid_subc: return fmt_subcs;
			case Xoa_ctg_mgr.Tid_page: return fmt_pages;
			case Xoa_ctg_mgr.Tid_file: return fmt_files;
			default: throw Err_.new_unhandled(tid);
		}
	}
	public void Write_catpage(Bry_bfr bfr, Xow_wiki wiki, Xoa_page page, Xoh_wtr_ctx hctx) {
		try	{
			// load categories from cat dbs; exit if not found
			tmp_catpage_url.Parse(wiki.App().Usr_dlg(), page.Url());
			Xoctg_catpage_ctg dom_ctg = loader.Load_by_ttl_or_null(wiki, page.Ttl());
			if (dom_ctg == null) return;

			// write html
			Xol_lang_itm lang = page.Lang();
			fmt_subcs.Write_catpage_grp(bfr, wiki, lang, dom_ctg);
			fmt_pages.Write_catpage_grp(bfr, wiki, lang, dom_ctg);
			fmt_files.Write_catpage_grp(bfr, wiki, lang, dom_ctg);
		}
		catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to generate category: title=~{0} err=~{1}", page.Url_bry_safe(), Err_.Message_gplx_log(e));
		}
	}
}
